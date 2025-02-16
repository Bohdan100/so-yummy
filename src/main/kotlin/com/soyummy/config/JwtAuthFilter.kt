package com.soyummy.config

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.stereotype.Component
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.filter.OncePerRequestFilter
import com.soyummy.auth.AuthRepository
import com.soyummy.constants.Constants.VERSION

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
        val authHeader = request.getHeader("Authorization")

        if (request.requestURI.startsWith("$VERSION/auth/login") || request.requestURI.startsWith("$VERSION/auth/register")) {
            filterChain.doFilter(request, response)
            return
        }

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            sendUnauthorizedResponse(response, "Unauthorized")
            return
        }

        val token = authHeader.substring(7)
        val email = jwtService.extractUserName(token)

        if (email != null && SecurityContextHolder.getContext().authentication == null) {
            val optionalUser = authRepository.findByToken(token)
            if (optionalUser.isEmpty) {
                sendUnauthorizedResponse(response, "Invalid token")
                return
            }

            val user = authRepository.findByEmail(email)
                .orElseThrow { UsernameNotFoundException("User not found") }

            if (jwtService.isTokenValid(token, user)) {
                val authentication = UsernamePasswordAuthenticationToken(user, null, user.authorities)
                SecurityContextHolder.getContext().authentication = authentication
            } else {
                throw IllegalArgumentException("Invalid or expired token")
            }
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
                "error": "$errorMessage"
            }
        """.trimIndent()

        response.writer.write(jsonResponse)
        response.writer.flush()
    }
}