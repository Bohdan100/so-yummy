package com.soyummy.token

import org.springframework.stereotype.Component
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.web.filter.OncePerRequestFilter
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import com.soyummy.auth.AuthRepository

@Component
class JwtAuthFilter(
    private val authRepository: AuthRepository,
    private val jwtService: JwtService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val path = request.requestURI

        if (path.endsWith("/auth/register") || path.endsWith("/auth/login")) {
            filterChain.doFilter(request, response)
            return
        }

        val authHeader = request.getHeader("Authorization")
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendUnauthorizedResponse(response, "Missing or invalid Authorization header")
            return
        }

        val token = authHeader.substring(7)
        if (token.isBlank()) {
            sendUnauthorizedResponse(response, "Empty token")
            return
        }

        try {
            val email = jwtService.extractUserName(token)

            if (email != null && SecurityContextHolder.getContext().authentication == null) {
                val user = authRepository.findByEmail(email)
                    .orElseThrow { UsernameNotFoundException("User not found in DB") }

                if (user.token != token) {
                    sendUnauthorizedResponse(response, "Invalid token (not matching session)")
                    return
                }

                if (jwtService.isTokenValid(token, user)) {
                    val authentication = UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        user.authorities
                    )
                    SecurityContextHolder.getContext().authentication = authentication
                } else {
                    sendUnauthorizedResponse(response, "Token expired or invalid signature")
                    return
                }
            }
        } catch (e: Exception) {
            sendUnauthorizedResponse(response, "Token processing error: ${e.message}")
            return
        }

        filterChain.doFilter(request, response)
    }

    private fun sendUnauthorizedResponse(response: HttpServletResponse, errorMessage: String) {
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        val jsonResponse = """
            {
                "status": 401,
                "error": "Unauthorized",
                "message": "$errorMessage"
            }
        """.trimIndent()

        response.writer.write(jsonResponse)
        response.writer.flush()
    }
}