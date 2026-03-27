package com.example.feedback

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class GlobalExceptionHandler {

    // -------------------------
    // Validation Errors
    // -------------------------

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<Map<String, String>> {

        val message =
            ex.bindingResult
                .allErrors
                .mapNotNull { it.defaultMessage }
                .firstOrNull { it.isNotBlank() }
                ?: "Invalid request"

        return ResponseEntity(
            mapOf("message" to message),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(
        ex: BindException
    ): ResponseEntity<Map<String, String>> {

        val message =
            ex.bindingResult
                .allErrors
                .mapNotNull { it.defaultMessage }
                .firstOrNull { it.isNotBlank() }
                ?: "Invalid request"

        return ResponseEntity(
            mapOf("message" to message),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException
    ): ResponseEntity<Map<String, String>> {

        return ResponseEntity(
            mapOf(
                "message" to "Invalid request body"
            ),
            HttpStatus.BAD_REQUEST
        )
    }

    // -------------------------
    // Runtime Errors
    // -------------------------

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(
        ex: RuntimeException
    ): ResponseEntity<Map<String, String>> {

        val message =
            ex.message ?: "UNKNOWN_ERROR"

        return when {

            message.contains("NOT_FOUND", true) ||

            message.contains("not found", true) ->

                ResponseEntity(
                    mapOf(
                        "message" to
                            "Feedback not found"
                    ),
                    HttpStatus.NOT_FOUND
                )

            message.contains("EXPIRED", true) ->

                ResponseEntity(
                    mapOf(
                        "message" to
                            "This feedback link has expired."
                    ),
                    HttpStatus.OK
                )

            message.contains(
                "ALREADY_RESPONDED",
                true
            ) ->

                ResponseEntity(
                    mapOf(
                        "message" to
                            "Already responded"
                    ),
                    HttpStatus.OK
                )

            message.contains(
                "Invalid rating",
                true
            ) ->

                ResponseEntity(
                    mapOf(
                        "message" to
                            "Invalid rating"
                    ),
                    HttpStatus.BAD_REQUEST
                )

            else ->

                ResponseEntity(
                    mapOf(
                        "message" to message
                    ),
                    HttpStatus.INTERNAL_SERVER_ERROR
                )
        }
    }

    // -------------------------
    // Generic Exception
    // -------------------------

    @ExceptionHandler(Exception::class)
    fun handleGenericException(
        ex: Exception
    ): ResponseEntity<Map<String, String>> {

        return ResponseEntity(
            mapOf(
                "message" to
                    "Internal server error"
            ),
            HttpStatus.INTERNAL_SERVER_ERROR
        )
    }
}