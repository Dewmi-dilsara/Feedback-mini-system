package com.example.feedback

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document(collection = "feedback_requests")
data class FeedbackRequest(

    @Id
    val id: String? = null,

    val enterpriseId: String,

    val status: String, // PENDING, RESPONDED, EXPIRED

    val rating: Int?,

    val createdAt: Instant,

    val expiresAt: Instant
)