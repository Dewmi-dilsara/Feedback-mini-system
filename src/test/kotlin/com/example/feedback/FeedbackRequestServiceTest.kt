package com.example.feedback

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.kotlin.*
import java.time.Instant
import java.util.Optional

class FeedbackRequestServiceTest {

    private val repository =
        mock<FeedbackRequestRepository>()

    private val formRepository =
        mock<FeedbackFormRepository>()

    private val service =
        FeedbackRequestService(
            repository,
            formRepository
        )

    @Test
    fun shouldRespondSuccessfully() {

        val request =
            FeedbackRequest(
                id = "123",
                enterpriseId = "enterprise1",
                status = "PENDING",
                rating = null,
                createdAt = Instant.now(),
                expiresAt =
                    Instant.now().plusSeconds(3600)
            )

        whenever(
            repository.findById("123")
        ).thenReturn(
            Optional.of(request)
        )

        whenever(
            formRepository.findByEnterpriseId(
                "enterprise1"
            )
        ).thenReturn(
            FeedbackFormConfig(
                enterpriseId = "enterprise1",

                headerText = "Header",

                headerDescription = "Desc",

                footerText = "Footer",

                ratingLabels = listOf(
                    "Poor",
                    "Fair",
                    "Good",
                    "Very Good",
                    "Excellent"
                ),

                thankYouText = "Thanks",

                invalidReplyText = "Invalid rating",

                expiredReplyText = "Expired",

                skipForChannels = emptyList()
            )
        )

        val result =
            service.respondToFeedback(
                "123",
                5
            )

        assertEquals(
            "Thanks",
            result
        )
    }

    @Test
    fun shouldDetectExpiredFeedback() {

        val request =
            FeedbackRequest(
                id = "123",
                enterpriseId = "enterprise1",
                status = "PENDING",
                rating = null,
                createdAt = Instant.now(),
                expiresAt =
                    Instant.now().minusSeconds(10)
            )

        whenever(
            repository.findById("123")
        ).thenReturn(
            Optional.of(request)
        )

        whenever(
            formRepository.findByEnterpriseId(
                "enterprise1"
            )
        ).thenReturn(
            FeedbackFormConfig(
                enterpriseId = "enterprise1",

                headerText = "Header",

                headerDescription = "Desc",

                footerText = "Footer",

                ratingLabels = listOf(
                    "Poor",
                    "Fair",
                    "Good",
                    "Very Good",
                    "Excellent"
                ),

                thankYouText = "Thanks",

                invalidReplyText = "Invalid rating",

                expiredReplyText = "Expired",

                skipForChannels = emptyList()
            )
        )

        val result =
            service.respondToFeedback(
                "123",
                3
            )

        assertEquals(
            "Expired",
            result
        )
    }
}