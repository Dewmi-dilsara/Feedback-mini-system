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
        repository: FeedbackRequestRepository
    ): CommandLineRunner {

        return CommandLineRunner {

            val request = FeedbackRequest(
                enterpriseId = "enterprise1",
                status = "PENDING",
                rating = null,
                createdAt = Instant.now(),
                expiresAt = Instant.now()
                    .plus(1, ChronoUnit.DAYS)
            )

            val saved = repository.save(request)

            println("TEST FEEDBACK ID: ${saved.id}")
        }
    }
}