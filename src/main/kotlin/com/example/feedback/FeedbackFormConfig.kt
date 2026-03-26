package com.example.feedback

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "feedback_forms")
data class FeedbackFormConfig(

    @Id
    val id: String? = null,

    @field:NotBlank(message = "Enterprise ID is required")
    val enterpriseId: String,

    @field:NotBlank(message = "Header text is required")
    @field:Size(max = 200)
    val headerText: String,

    @field:Size(max = 500)
    val headerDescription: String?,

    @field:Size(max = 200)
    val footerText: String?,

    @field:NotEmpty(message = "Rating labels required")
    @field:Size(min = 5, max = 5,
        message = "Rating labels must contain exactly 5 items")
    val ratingLabels: List<String>,

    @field:NotBlank
    val thankYouText: String,

    @field:NotBlank
    val invalidReplyText: String,

    @field:NotBlank
    val expiredReplyText: String,

    val skipForChannels: List<String>
)