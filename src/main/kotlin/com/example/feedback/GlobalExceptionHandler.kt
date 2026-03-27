package com.example.feedback

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(
        ex: RuntimeException
    ): ResponseEntity<Map<String, String>> {

        return when (ex.message) {

            // -------- PUBLIC FEEDBACK ERRORS --------

            "Feedback not found" -> ResponseEntity(
                mapOf("error" to "NOT_FOUND"),
                HttpStatus.NOT_FOUND
            )

            "EXPIRED" -> ResponseEntity(
                mapOf("error" to "EXPIRED"),
                HttpStatus.GONE
            )

            "ALREADY_RESPONDED" -> ResponseEntity(
                mapOf("error" to "ALREADY_RESPONDED"),
                HttpStatus.CONFLICT
            )

            "Invalid rating" -> ResponseEntity(
                mapOf("error" to "INVALID_RATING"),
                HttpStatus.BAD_REQUEST
            )

            // -------- FORM VALIDATION ERRORS --------

            "headerText is required",
            "headerDescription is required",
            "footerText is required",
            "thankYouText is required",
            "invalidReplyText is required",
            "expiredReplyText is required",
            "ratingLabels must contain exactly 5 items",
            "ratingLabels cannot contain blank values",
            "skipForChannels cannot contain duplicates",
            "Invalid channel name" -> ResponseEntity(
                mapOf("error" to ex.message!!),
                HttpStatus.BAD_REQUEST
            )

            // -------- DEFAULT --------

            else -> ResponseEntity(
                mapOf("error" to "INTERNAL_ERROR"),
                HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
    }

    // Catch unexpected exceptions

    @ExceptionHandler(Exception::class)
    fun handleGenericException(
        ex: Exception
    ): ResponseEntity<Map<String, String>> {

        return ResponseEntity(
            mapOf("error" to "INTERNAL_ERROR"),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}