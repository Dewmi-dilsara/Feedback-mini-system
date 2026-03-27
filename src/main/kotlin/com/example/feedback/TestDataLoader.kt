package com.example.feedback

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Instant
import java.time.temporal.ChronoUnit

@Configuration
class TestDataLoader {

    @Bean
    fun loadTestData(
        repository: FeedbackRequestRepository,
        formRepository: FeedbackFormRepository
    ): CommandLineRunner {

        return CommandLineRunner {

            val enterpriseId = "enterprise1"

            // Create form config if not exists

            val existingForm =
                formRepository.findByEnterpriseId(enterpriseId)

            if (existingForm == null) {

                val formConfig = FeedbackFormConfig(

                    enterpriseId = enterpriseId,

                    headerText = "Tell us what you think",

                    headerDescription =
                        "Your feedback helps us improve",

                    footerText =
                        "Thank you for your feedback!",

                    ratingLabels = listOf(
                        "Poor",
                        "Fair",
                        "Good",
                        "Very Good",
                        "Excellent"
                    ),

                    thankYouText =
                        "Thanks for your feedback!",

                    invalidReplyText =
                        "Invalid rating.",

                    expiredReplyText =
                        "This feedback link has expired.",

                    skipForChannels =
                        listOf("WHATSAPP")
                )

                formRepository.save(formConfig)

                println("✅ TEST FORM CONFIG CREATED")
            }

            // -----------------------------
            // VALID FEEDBACK
            // -----------------------------

            val request = FeedbackRequest(
                enterpriseId = enterpriseId,
                status = "PENDING",
                rating = null,
                createdAt = Instant.now(),
                expiresAt =
                    Instant.now().plus(1, ChronoUnit.DAYS)
            )

            val saved = repository.save(request)

            println("✅ VALID FEEDBACK ID: ${saved.id}")

            // -----------------------------
            // EXPIRED FEEDBACK
            // -----------------------------

            val expiredRequest = FeedbackRequest(
                enterpriseId = enterpriseId,
                status = "PENDING",
                rating = null,
                createdAt = Instant.now(),
                expiresAt =
                    Instant.now().minus(1, ChronoUnit.DAYS)
            )

            val expiredSaved =
                repository.save(expiredRequest)

            println(
                "⏰ EXPIRED FEEDBACK ID: ${expiredSaved.id}"
            )

            // -----------------------------
            // RESPONDED FEEDBACK
            // -----------------------------

            val respondedRequest = FeedbackRequest(
                enterpriseId = enterpriseId,
                status = "RESPONDED",
                rating = 4,
                createdAt = Instant.now(),
                expiresAt =
                    Instant.now().plus(1, ChronoUnit.DAYS)
            )

            val respondedSaved =
                repository.save(respondedRequest)

            println(
                "✅ RESPONDED FEEDBACK ID: ${respondedSaved.id}"
            )
        }
    }
}