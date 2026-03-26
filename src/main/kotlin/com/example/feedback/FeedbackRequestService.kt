package com.example.feedback

import com.example.feedback.FeedbackFormRepository
import org.springframework.stereotype.Service
import java.time.Instant

@Service
class FeedbackRequestService(

    private val repository: FeedbackRequestRepository,

    private val formRepository: FeedbackFormRepository

) {

    fun respondToFeedback(
        feedbackId: String,
        rating: Int
    ): String {

        val request = repository.findById(feedbackId)
            .orElseThrow {
                RuntimeException("Feedback not found")
            }

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

    val request = repository.findById(feedbackId)
        .orElseThrow {
            RuntimeException("Feedback not found")
        }

    if (request.expiresAt.isBefore(Instant.now())) {
        throw RuntimeException("EXPIRED")
    }

    val form = formRepository
        .findByEnterpriseId(request.enterpriseId)
        ?: throw RuntimeException("Form config not found")

    return PublicFeedbackResponse(

        feedbackId = request.id!!,

        enterpriseId = request.enterpriseId,

        expiresAt = request.expiresAt.toString(),

        headerText = form.headerText,

        headerDescription = form.headerDescription,

        ratingLabels = form.ratingLabels,

        footerText = form.footerText
    )
}: FeedbackRequest {

    val request = repository.findById(feedbackId)
        .orElseThrow {
            RuntimeException("Feedback not found")
        }

    // Check if expired
    if (request.expiresAt.isBefore(Instant.now())) {
        throw RuntimeException("EXPIRED")
    }

    return request
}
}