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

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException
    ): ResponseEntity<Map<String, String>> {
        val message = ex.bindingResult
            .allErrors
            .mapNotNull { it.defaultMessage }
            .firstOrNull { it.isNotBlank() }
            ?: "INVALID_REQUEST"

        return ResponseEntity(
            mapOf("error" to message),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(BindException::class)
    fun handleBindException(
        ex: BindException
    ): ResponseEntity<Map<String, String>> {
        val message = ex.bindingResult
            .allErrors
            .mapNotNull { it.defaultMessage }
            .firstOrNull { it.isNotBlank() }
            ?: "INVALID_REQUEST"

        return ResponseEntity(
            mapOf("error" to message),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException
    ): ResponseEntity<Map<String, String>> {
        val message = ex.cause?.message ?: ex.message ?: "invalid request body"
        return ResponseEntity(
            mapOf("error" to message),
            HttpStatus.BAD_REQUEST
        )
    }

    @ExceptionHandler(RuntimeException::class)
    fun handleRuntimeException(
        ex: RuntimeException
    ): ResponseEntity<Map<String, String>> {

        val message = ex.message ?: "UNKNOWN_ERROR"

        return when {

            message.contains("not found", true) ->
                ResponseEntity(
                    mapOf("error" to "NOT_FOUND"),
                    HttpStatus.NOT_FOUND
                )

            message.contains("EXPIRED", true) ->
                ResponseEntity(
                    mapOf("error" to "EXPIRED"),
                    HttpStatus.GONE
                )

            message.contains("ALREADY_RESPONDED", true) ->
                ResponseEntity(
                    mapOf("error" to "ALREADY_RESPONDED"),
                    HttpStatus.CONFLICT
                )

            message.contains("Invalid rating", true) ->
                ResponseEntity(
                    mapOf("error" to "INVALID_RATING"),
                    HttpStatus.BAD_REQUEST
                )

            message.contains("required", true) ||
            message.contains("ratingLabels", true) ||
            message.contains("channel", true) ->

                ResponseEntity(
                    mapOf("error" to message),
                    HttpStatus.BAD_REQUEST
                )

            else ->
                ResponseEntity(
                    mapOf("error" to "INTERNAL_ERROR"),
                    HttpStatus.INTERNAL_SERVER_ERROR
                )
        }
    }
}