package com.example.feedback

import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/admin/enterprises")
class FeedbackFormController(
    private val service: FeedbackFormService
) {

    @GetMapping("/{enterpriseId}/session-feedback-form")
    fun getForm(
        @PathVariable enterpriseId: String
    ): FeedbackFormConfig? {

        return service.getForm(enterpriseId)
    }

    @PutMapping("/{enterpriseId}/session-feedback-form")
    fun saveForm(
        @PathVariable enterpriseId: String,
        @RequestBody config: FeedbackFormConfig
    ): FeedbackFormConfig {

        return service.saveForm(
            enterpriseId,
            config
        )
    }
}