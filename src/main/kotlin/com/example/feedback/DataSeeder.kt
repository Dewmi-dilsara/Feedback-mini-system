package com.example.feedback

import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.time.Instant
import java.time.temporal.ChronoUnit

@Configuration
class DataSeeder {

    @Bean
    fun seedData(
        formRepository: FeedbackFormRepository,
        requestRepository: FeedbackRequestRepository
    ): CommandLineRunner {

        return CommandLineRunner {

            // Clear existing (optional)
            formRepository.deleteAll()
            requestRepository.deleteAll()

            val enterpriseId = "enterprise-1"

            // -------- FORM CONFIG --------

            val form = FeedbackFormConfig(
                enterpriseId = enterpriseId,
                headerText = "Rate your experience",
                headerDescription = "How was your chat session?",
                footerText = "Thank you!",
                ratingLabels = listOf(
                    "Very Bad",
                    "Bad",
                    "Okay",
                    "Good",
                    "Excellent"
                ),
                thankYouText = "Thanks!",
                invalidReplyText = "Invalid",
                expiredReplyText = "Expired",
                skipForChannels = listOf("WHATSAPP")
            )

            formRepository.save(form)

            // -------- VALID REQUEST --------

            val validRequest = FeedbackRequest(
                enterpriseId = enterpriseId,
                status = "PENDING",
                rating = null,
                createdAt = Instant.now(),
                expiresAt = Instant.now().plus(1, ChronoUnit.DAYS)
            )

            val savedValid = requestRepository.save(validRequest)

            // -------- EXPIRED REQUEST --------

            val expiredRequest = FeedbackRequest(
                enterpriseId = enterpriseId,
                status = "PENDING",
                rating = null,
                createdAt = Instant.now(),
                expiresAt = Instant.now().minus(1, ChronoUnit.DAYS)
            )

            requestRepository.save(expiredRequest)

            // -------- RESPONDED REQUEST --------

            val respondedRequest = FeedbackRequest(
                enterpriseId = enterpriseId,
                status = "RESPONDED",
                rating = 4,
                createdAt = Instant.now(),
                expiresAt = Instant.now().plus(1, ChronoUnit.DAYS)
            )

            requestRepository.save(respondedRequest)

            println("✅ Seed Data Created")
            println("VALID FEEDBACK ID: ${savedValid.id}")
        }
    }
}