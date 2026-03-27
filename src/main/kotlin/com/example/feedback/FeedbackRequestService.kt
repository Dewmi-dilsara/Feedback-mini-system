package com.example.feedback

import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class FeedbackRequestService(

    private val repository: FeedbackRequestRepository,

    private val formRepository: FeedbackFormRepository

) {

    // -----------------------------
    // Find request safely
    // -----------------------------

    private fun findRequest(
        feedbackId: String
    ): FeedbackRequest {

        repository.findById(feedbackId)
            .let { existing ->

                if (existing.isPresent)
                    return existing.get()
            }

        if (ObjectId.isValid(feedbackId)) {

            val mongoId =
                ObjectId(feedbackId).toHexString()

            repository.findById(mongoId)
                .let { existing ->

                    if (existing.isPresent)
                        return existing.get()
                }
        }

        throw RuntimeException(
            "NOT_FOUND"
        )
    }

    // -----------------------------
    // Respond API
    // -----------------------------

    fun respondToFeedback(

        feedbackId: String,

        rating: Int

    ): String {

        val request =
            findRequest(feedbackId)

        val form =
            formRepository
                .findByEnterpriseId(
                    request.enterpriseId
                )

        // Check expired

        if (
            request.expiresAt
                .isBefore(Instant.now())
        ) {

            return form?.expiredReplyText
                ?: "EXPIRED"
        }

        // Check already responded

        if (
            request.status == "RESPONDED"
        ) {

            return "ALREADY_RESPONDED"
        }

        // Validate rating

        if (rating !in 1..5) {

            return form?.invalidReplyText
                ?: "INVALID_RATING"
        }

        val updated =
            request.copy(

                rating = rating,

                status = "RESPONDED"
            )

        repository.save(updated)

        return form?.thankYouText
            ?: "SUCCESS"
    }

    // -----------------------------
    // Get Feedback Details
    // -----------------------------

    fun getFeedbackDetails(

        feedbackId: String

    ): Any {

        val request =
            findRequest(feedbackId)

        val form =
            formRepository
                .findByEnterpriseId(
                    request.enterpriseId
                )
                ?: throw RuntimeException(
                    "Form config not found"
                )

        // Check expired

        if (
            request.expiresAt
                .isBefore(Instant.now())
        ) {

            return mapOf(

                "message" to
                    form.expiredReplyText
            )
        }

        // Check responded

        if (
            request.status == "RESPONDED"
        ) {

            return mapOf(

                "message" to
                    "Already responded"
            )
        }

        // Normal response

        return PublicFeedbackResponse(

            feedbackId = request.id!!,

            enterpriseId =
                request.enterpriseId,

            expiresAt =
                request.expiresAt.toString(),

            headerText =
                form.headerText,

            headerDescription =
                form.headerDescription ?: "",

            ratingLabels =
                form.ratingLabels,

            footerText =
                form.footerText ?: ""
        )
    }
}