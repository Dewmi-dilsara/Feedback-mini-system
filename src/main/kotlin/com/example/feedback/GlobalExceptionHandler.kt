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

            else -> ResponseEntity(
                mapOf("error" to "INTERNAL_ERROR"),
                HttpStatus.INTERNAL_SERVER_ERROR
            )
        }
    }
}