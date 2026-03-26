package com.example.feedback

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/public/feedback")
class PublicFeedbackController(
    private val service: FeedbackRequestService
) {

    data class RatingRequest(
        val rating: Int
    )

    @PostMapping("/{feedbackId}/respond")
    fun respond(
        @PathVariable feedbackId: String,
        @RequestBody request: RatingRequest
    ): String {

        return service.respondToFeedback(
            feedbackId,
            request.rating
        )
    }

    @GetMapping("/{feedbackId}")
    fun getFeedback(
       @PathVariable feedbackId: String
): Any {

    return service.getFeedbackDetails(feedbackId)
}
}