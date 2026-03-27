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

            // Create valid request

            val request = FeedbackRequest(
                enterpriseId = enterpriseId,
                status = "PENDING",
                rating = null,
                createdAt = Instant.now(),
                expiresAt =
                    Instant.now().plus(1, ChronoUnit.DAYS)
            )

            val saved = repository.save(request)

            println("✅ TEST FEEDBACK ID: ${saved.id}")

            // Create expired request

            val expiredRequest = FeedbackRequest(
                enterpriseId = enterpriseId,
                status = "PENDING",
                rating = null,
                createdAt = Instant.now(),
                expiresAt =
                    Instant.now().minus(1, ChronoUnit.DAYS)
            )

            repository.save(expiredRequest)

            println("✅ EXPIRED FEEDBACK CREATED")

            // Create responded request

            val respondedRequest = FeedbackRequest(
                enterpriseId = enterpriseId,
                status = "RESPONDED",
                rating = 4,
                createdAt = Instant.now(),
                expiresAt =
                    Instant.now().plus(1, ChronoUnit.DAYS)
            )

            repository.save(respondedRequest)

            println("✅ RESPONDED FEEDBACK CREATED")
        }
    }
}