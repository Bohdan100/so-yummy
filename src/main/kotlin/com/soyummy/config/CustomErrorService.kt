package com.soyummy.config

import org.springframework.stereotype.Service
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType

@Service
class CustomErrorService {
    fun unauthorizedHandler(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { request, response, authException ->
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.writer.write(
                """
                {
                    "status": 401,
                    "error": "Unauthorized",
                    "message": "Invalid name or password"
                }
                """.trimIndent()
            )
        }
    }

    fun accessDeniedHandler(): AccessDeniedHandler {
        return AccessDeniedHandler { request, response, accessDeniedException ->
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.writer.write(
                """
                {
                    "status": 403,
                    "error": "Access Denied",
                    "message": "${accessDeniedException.message}"
                }
                """.trimIndent()
            )
        }
    }
}