package com.example.feedback

import org.springframework.stereotype.Service

@Service
class FeedbackFormService(
    private val repository: FeedbackFormRepository
) {

    private val allowedChannels = setOf(
        "WHATSAPP",
        "INSTAGRAM",
        "MESSENGER",
        "WEB"
    )

    fun getForm(
        enterpriseId: String
    ): FeedbackFormConfig? {

        return repository
            .findByEnterpriseId(enterpriseId)
    }

    fun saveForm(
        enterpriseId: String,
        config: FeedbackFormConfig
    ): FeedbackFormConfig {

        validateConfig(config)

        val updatedConfig = config.copy(
            enterpriseId = enterpriseId
        )

        return repository.save(updatedConfig)
    }

    private fun validateConfig(
    config: FeedbackFormConfig
) {

    // -------- REQUIRED TEXT --------

    if (config.headerText.isNullOrBlank()) {
        throw RuntimeException(
            "headerText is required"
        )
    }

    if (config.headerDescription.isNullOrBlank()) {
        throw RuntimeException(
            "headerDescription is required"
        )
    }

    if (config.footerText.isNullOrBlank()) {
        throw RuntimeException(
            "footerText is required"
        )
    }

    if (config.thankYouText.isNullOrBlank()) {
        throw RuntimeException(
            "thankYouText is required"
        )
    }

    if (config.invalidReplyText.isNullOrBlank()) {
        throw RuntimeException(
            "invalidReplyText is required"
        )
    }

    if (config.expiredReplyText.isNullOrBlank()) {
        throw RuntimeException(
            "expiredReplyText is required"
        )
    }

    // -------- RATING LABELS --------

    if (config.ratingLabels.size != 5) {
        throw RuntimeException(
            "ratingLabels must contain exactly 5 items"
        )
    }

    if (config.ratingLabels.any { it.isBlank() }) {
        throw RuntimeException(
            "ratingLabels cannot contain blank values"
        )
    }

    // -------- CHANNEL VALIDATION --------

    val channels =
        config.skipForChannels ?: emptyList()

    if (channels.size != channels.toSet().size) {
        throw RuntimeException(
            "skipForChannels cannot contain duplicates"
        )
    }

    if (channels.any { it !in allowedChannels }) {
        throw RuntimeException(
            "Invalid channel name"
        )
    }
}
}