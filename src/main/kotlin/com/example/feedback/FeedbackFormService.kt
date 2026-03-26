package com.example.feedback

import org.springframework.stereotype.Service

@Service
class FeedbackFormService(
    private val repository: FeedbackFormRepository
) {

    fun getForm(enterpriseId: String): FeedbackFormConfig? {
        return repository.findByEnterpriseId(enterpriseId)
    }

    fun saveForm(
        enterpriseId: String,
        config: FeedbackFormConfig
    ): FeedbackFormConfig {

        val updatedConfig = config.copy(
            enterpriseId = enterpriseId
        )

        return repository.save(updatedConfig)
    }
}