package com.soyummy.exception

import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.security.access.AccessDeniedException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.*
import com.soyummy.exception.types.*

@RestControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {
    private fun createErrorResponse(status: HttpStatus, error: String, message: String?): ResponseEntity<Any> {
        val body = LinkedHashMap<String, Any>()
        body["status"] = status.value()
        body["error"] = error
        body["message"] = message ?: "No message available"
        body["timestamp"] = System.currentTimeMillis()
        return ResponseEntity(body, status)
    }

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFound(ex: ResourceNotFoundException) =
        createErrorResponse(HttpStatus.NOT_FOUND, "Not Found", ex.message)

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgument(ex: IllegalArgumentException) =
        createErrorResponse(HttpStatus.BAD_REQUEST, "Bad Request", ex.message)

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDenied(ex: AccessDeniedException) =
        createErrorResponse(HttpStatus.FORBIDDEN, "Access Denied", ex.message)

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorized(ex: UnauthorizedException) =
        createErrorResponse(HttpStatus.UNAUTHORIZED, "Unauthorized", ex.message)

    @ExceptionHandler(ValidationException::class)
    fun handleValidation(ex: ValidationException) =
        createErrorResponse(HttpStatus.BAD_REQUEST, "Validation Error", ex.message)

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(ex: Exception) =
        createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "An unexpected error occurred")

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage}" }

        return createErrorResponse(HttpStatus.BAD_REQUEST, "Validation Failed", errors)
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        return createErrorResponse(HttpStatus.BAD_REQUEST, "Malformed JSON", ex.message)
    }
}