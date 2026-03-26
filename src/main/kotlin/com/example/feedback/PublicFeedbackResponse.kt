package com.example.feedback

data class PublicFeedbackResponse(

    val feedbackId: String,

    val enterpriseId: String,

    val expiresAt: String,

    val headerText: String,

    val headerDescription: String,

    val ratingLabels: List<String>,

    val footerText: String
)