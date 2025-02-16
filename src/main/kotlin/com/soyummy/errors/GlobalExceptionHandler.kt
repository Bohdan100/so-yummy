package com.soyummy.errors

import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import org.springframework.security.access.AccessDeniedException
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity

@RestControllerAdvice
class GlobalExceptionHandler: ResponseEntityExceptionHandler() {
    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleResourceNotFoundException(ex: ResourceNotFoundException): ResponseEntity<Any> {
        val body = LinkedHashMap<String, Any>()
        body["status"] = HttpStatus.NOT_FOUND.value()
        body["error"] = "Not Found"
        body["message"] = ex.message ?: "Resource not found"
        body["timestamp"] = System.currentTimeMillis()

        return ResponseEntity(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Any> {
        val body = LinkedHashMap<String, Any>()
        body["status"] = HttpStatus.BAD_REQUEST.value()
        body["error"] = "Bad Request"
        body["message"] = ex.message ?: "Invalid input"
        body["timestamp"] = System.currentTimeMillis()

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException): ResponseEntity<Any> {
        val body = LinkedHashMap<String, Any>()
        body["status"] = HttpStatus.FORBIDDEN.value()
        body["error"] = "Access Denied"
        body["message"] = ex.message ?: "You do not have permission to access this resource"
        body["timestamp"] = System.currentTimeMillis()

        return ResponseEntity(body, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler(UnauthorizedException::class)
    fun handleUnauthorizedException(ex: UnauthorizedException): ResponseEntity<Any> {
        val body = LinkedHashMap<String, Any>()
        body["status"] = HttpStatus.UNAUTHORIZED.value()
        body["error"] = "Unauthorized"
        body["message"] = ex.message ?: "User is not authenticated"
        body["timestamp"] = System.currentTimeMillis()

        return ResponseEntity(body, HttpStatus.UNAUTHORIZED)
    }

    @ExceptionHandler(ValidationException::class)
    fun handleValidationException(ex: ValidationException): ResponseEntity<Any> {
        val body = LinkedHashMap<String, Any>()
        body["status"] = HttpStatus.BAD_REQUEST.value()
        body["error"] = "Validation Error"
        body["message"] = ex.message ?: "Validation failed"
        body["timestamp"] = System.currentTimeMillis()

        return ResponseEntity(body, HttpStatus.BAD_REQUEST)
    }

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val errors = ex.bindingResult.fieldErrors.joinToString(", ") { error ->
            "${error.field}: ${error.defaultMessage}"
        }
        throw ValidationException("Validation failed: $errors")
    }

    override fun handleHttpMessageNotReadable(
        ex: HttpMessageNotReadableException,
        headers: HttpHeaders,
        status: HttpStatusCode,
        request: WebRequest
    ): ResponseEntity<Any> {
        val body = LinkedHashMap<String, Any>()
        body["status"] = status.value()
        body["error"] = "Malformed JSON request"
        body["message"] = ex.message ?: "No message available"
        body["timestamp"] = System.currentTimeMillis()

        return ResponseEntity(body, headers, status)
    }
}