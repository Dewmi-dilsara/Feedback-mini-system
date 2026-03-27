package com.example.feedback

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class FeedbackApplicationTests {

	@Autowired
	lateinit var mockMvc: MockMvc

	@Test
	fun contextLoads() {
	}

	@Test
	fun `save form with missing headerText should return bad request`() {
		val payload = """
		{
		  "enterpriseId": "enterprise1",
		  "headerText": "",
		  "headerDescription": "Test",
		  "footerText": "Test",
		  "ratingLabels": ["A", "B", "C", "D", "E"],
		  "thankYouText": "Thanks",
		  "invalidReplyText": "Invalid",
		  "expiredReplyText": "Expired",
		  "skipForChannels": ["WHATSAPP"]
		}
		""".trimIndent()

		mockMvc.perform(
			put("/api/admin/enterprises/enterprise1/session-feedback-form")
				.contentType(MediaType.APPLICATION_JSON)
				.content(payload)
		)
			.andExpect(status().isBadRequest)
			.andExpect(jsonPath("$.error").value("headerText is required"))
	}

	@Test
	fun `save form with invalid ratingLabels should return bad request`() {
		val payload = """
		{
		  "enterpriseId": "enterprise1",
		  "headerText": "Test",
		  "headerDescription": "Test",
		  "footerText": "Test",
		  "ratingLabels": ["A", "B", "C"],
		  "thankYouText": "Thanks",
		  "invalidReplyText": "Invalid",
		  "expiredReplyText": "Expired",
		  "skipForChannels": ["WHATSAPP"]
		}
		""".trimIndent()

		mockMvc.perform(
			put("/api/admin/enterprises/enterprise1/session-feedback-form")
				.contentType(MediaType.APPLICATION_JSON)
				.content(payload)
		)
			.andExpect(status().isBadRequest)
			.andExpect(jsonPath("$.error").value("ratingLabels must contain exactly 5 items"))
	}

}
