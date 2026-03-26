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

    // Enterprise ID
    @field:NotBlank(message = "Enterprise ID is required")
    val enterpriseId: String,

    // Header Text
    @field:NotBlank(message = "Header text is required")
    @field:Size(
        max = 200,
        message = "Header text must not exceed 200 characters"
    )
    val headerText: String,

    // Header Description (Optional)
    @field:Size(
        max = 500,
        message = "Header description must not exceed 500 characters"
    )
    val headerDescription: String? = null,

    // Footer Text (Optional)
    @field:Size(
        max = 200,
        message = "Footer text must not exceed 200 characters"
    )
    val footerText: String? = null,

    // Rating Labels — Must be exactly 5
    @field:NotEmpty(message = "Rating labels are required")
    @field:Size(
        min = 5,
        max = 5,
        message = "Rating labels must contain exactly 5 items"
    )
    val ratingLabels: List<
        @NotBlank(message = "Rating label cannot be blank")
        String
    >,

    // Thank You Text
    @field:NotBlank(message = "Thank you text is required")
    val thankYouText: String,

    // Invalid Reply Text
    @field:NotBlank(message = "Invalid reply text is required")
    val invalidReplyText: String,

    // Expired Reply Text
    @field:NotBlank(message = "Expired reply text is required")
    val expiredReplyText: String,

    // Skip Channels (Optional)
    val skipForChannels: List<String> = emptyList()

)