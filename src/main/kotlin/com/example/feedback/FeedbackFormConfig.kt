package com.example.feedback

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "feedback_forms")
data class FeedbackFormConfig(

    @Id
    val id: String? = null,

    val enterpriseId: String,

    val headerText: String,

    val headerDescription: String?,

    val footerText: String?,

    val ratingLabels: List<String>,

    val thankYouText: String,

    val invalidReplyText: String,

    val expiredReplyText: String,

    val skipForChannels: List<String>
)



