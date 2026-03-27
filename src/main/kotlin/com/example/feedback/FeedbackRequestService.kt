package com.example.feedback

import org.bson.types.ObjectId
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class FeedbackRequestService(

    private val repository: FeedbackRequestRepository,

    private val formRepository: FeedbackFormRepository

) {

    private fun findRequest(feedbackId: String): FeedbackRequest {
        repository.findById(feedbackId).let { existing ->
            if (existing.isPresent) return existing.get()
        }

        if (ObjectId.isValid(feedbackId)) {
            val mongoId = ObjectId(feedbackId).toHexString()
            repository.findById(mongoId).let { existing ->
                if (existing.isPresent) return existing.get()
            }
        }

        throw RuntimeException("Feedback not found")
    }

    fun respondToFeedback(
        feedbackId: String,
        rating: Int
    ): String {

        val request = findRequest(feedbackId)

        // Check expired
        if (request.expiresAt.isBefore(Instant.now())) {
            return "EXPIRED"
        }

        // Check already responded
        if (request.status == "RESPONDED") {
            return "ALREADY_RESPONDED"
        }

        // Validate rating
        if (rating !in 1..5) {
            throw RuntimeException("Invalid rating")
        }

        val updated = request.copy(
            rating = rating,
            status = "RESPONDED"
        )

        repository.save(updated)

        return "SUCCESS"
    }

    fun getFeedbackDetails(
        feedbackId: String
    ): PublicFeedbackResponse {

        val request = findRequest(feedbackId)

        // Check expired
        if (request.expiresAt.isBefore(Instant.now())) {
            throw RuntimeException("EXPIRED")
        }

        // ✅ Correct place for this check
        if (request.status == "RESPONDED") {
            throw RuntimeException("ALREADY_RESPONDED")
        }

        // Load form config
        val form = formRepository
            .findByEnterpriseId(request.enterpriseId)
            ?: throw RuntimeException("Form config not found")

        return PublicFeedbackResponse(

    feedbackId = request.id!!,

    enterpriseId = request.enterpriseId,

    expiresAt = request.expiresAt.toString(),

    headerText = form.headerText,

    headerDescription = form.headerDescription ?: "",

    ratingLabels = form.ratingLabels,

    footerText = form.footerText ?: ""
)
    }

}