package com.soyummy.config

import org.springframework.stereotype.Service
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.security.web.access.AccessDeniedHandler
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType

@Service
    class SecurityExceptionHandler {
    fun unauthorized(): AuthenticationEntryPoint {
        return AuthenticationEntryPoint { request, response, authException ->
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.writer.write(
                """
                {   
                    "status": 401,
                    "error": "Unauthorized",
                    "message": "Invalid name or password",
                    "timestamp": ${System.currentTimeMillis()}
                }
                """.trimIndent()
            )
        }
    }

    fun accessDenied(): AccessDeniedHandler {
        return AccessDeniedHandler { request, response, accessDeniedException ->
            response.status = HttpServletResponse.SC_FORBIDDEN
            response.contentType = MediaType.APPLICATION_JSON_VALUE
            response.writer.write(
                """
                {
                    "status": 403,
                    "error": "Access Denied",
                    "message": "${accessDeniedException.message}",
                    "timestamp": ${System.currentTimeMillis()}
                }
                """.trimIndent()
            )
        }
    }
}