package com.soyummy.auth

import org.springframework.web.bind.annotation.*
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import jakarta.validation.Valid
import jakarta.servlet.http.HttpServletResponse
import com.soyummy.auth.dto.LoginDTO
import com.soyummy.auth.dto.RegisterDTO
import com.soyummy.constants.Constants.VERSION

@RestController
@RequestMapping("${VERSION}/auth")
class AuthController(private val authService: AuthService) {

    //  POST  http://localhost:8080/api/v1/auth/register
    @PostMapping("/register")
    fun register(
        @Valid @RequestBody request: RegisterDTO,
        response: HttpServletResponse
    ): ResponseEntity<Map<String, String>> {
        val role = if (request.role != null && (request.role == "ADMIN" || request.role == "USER")) {
            Role.valueOf(request.role)
        } else {
            Role.USER
        }

        val token = authService.register(request, role)

        return ResponseEntity.status(HttpStatus.CREATED).body(mapOf("token" to token))
    }

    //  POST  http://localhost:8080/api/v1/auth/login
    @PostMapping("/login")
    fun login(
        @Valid @RequestBody request: LoginDTO,
        response: HttpServletResponse
    ): ResponseEntity<Map<String, String>> {
        val token = authService.login(request)

        return ResponseEntity.ok(mapOf("token" to token))
    }

    //  POST  http://localhost:8080/api/v1/auth/logout
    @PostMapping("/logout")
    fun logout(
        @RequestHeader(value = "Authorization", required = false) authHeader: String?,
        response: HttpServletResponse
    ): ResponseEntity<String> {
        if (authHeader.isNullOrBlank()) {
            return ResponseEntity.badRequest().body("Authorization header is missing")
        }

        authService.logout(authHeader)

        return ResponseEntity.ok("Logged out successfully")
    }
}