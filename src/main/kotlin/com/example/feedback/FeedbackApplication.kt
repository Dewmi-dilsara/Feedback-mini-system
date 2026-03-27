package com.example.feedback

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import java.time.Instant
import java.time.temporal.ChronoUnit

@SpringBootApplication
class FeedbackApplication

fun main(args: Array<String>) {
    runApplication<FeedbackApplication>(*args)
}

@Bean
fun createTestFeedback(
    repository: FeedbackRequestRepository,
    formRepository: FeedbackFormRepository
): CommandLineRunner {

    return CommandLineRunner {

        val enterpriseId = "enterprise1"

        // -------- CREATE FORM CONFIG --------

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

                // 🔴 REQUIRED FIELDS

                thankYouText =
                    "Thank you for your response!",

                invalidReplyText =
                    "Invalid rating received.",

                expiredReplyText =
                    "This feedback link has expired.",

                skipForChannels =
                    listOf("WHATSAPP")
            )

            formRepository.save(formConfig)

            println("✅ TEST FORM CONFIG CREATED")
        }

        // -------- VALID FEEDBACK --------

        val request = FeedbackRequest(
            enterpriseId = enterpriseId,
            createdAt = Instant.now(),
            expiresAt =
                Instant.now().plus(1, ChronoUnit.DAYS),
            status = "PENDING",
            rating = null
        )

        val saved = repository.save(request)

        println("✅ TEST FEEDBACK ID: ${saved.id}")

        // -------- EXPIRED FEEDBACK --------

        val expiredRequest = FeedbackRequest(
            enterpriseId = enterpriseId,
            createdAt = Instant.now(),
            expiresAt =
                Instant.now().minus(1, ChronoUnit.DAYS),
            status = "PENDING",
            rating = null
        )

        repository.save(expiredRequest)

        println("✅ EXPIRED FEEDBACK CREATED")

        // -------- RESPONDED FEEDBACK --------

        val respondedRequest = FeedbackRequest(
            enterpriseId = enterpriseId,
            createdAt = Instant.now(),
            expiresAt =
                Instant.now().plus(1, ChronoUnit.DAYS),
            status = "RESPONDED",
            rating = 4
        )

        repository.save(respondedRequest)

        println("✅ RESPONDED FEEDBACK CREATED")
    }
}