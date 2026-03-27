package com.example.feedback

import org.junit.jupiter.api.Test
import org.mockito.kotlin.whenever
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(PublicFeedbackController::class)
class PublicFeedbackControllerTest {

    @Autowired
    lateinit var mockMvc: MockMvc

    @MockBean
    lateinit var service: FeedbackRequestService

    @Test
    fun shouldReturnFeedbackDetails() {

        whenever(
            service.getFeedbackDetails("123")
        ).thenReturn(
            mapOf(
                "headerText" to "Header",
                "footerText" to "Footer"
            )
        )

        mockMvc.perform(
            get("/api/public/feedback/123")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.headerText").exists())
    }

    @Test
    fun shouldSubmitRating() {

        whenever(
            service.respondToFeedback("123", 5)
        ).thenReturn("SUCCESS")

        mockMvc.perform(
            post("/api/public/feedback/123/respond")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"rating":5}""")
        )
            .andExpect(status().isOk)
    }
}