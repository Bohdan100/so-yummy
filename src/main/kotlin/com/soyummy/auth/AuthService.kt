package com.soyummy.auth

import org.springframework.stereotype.Service
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import com.soyummy.config.JwtService
import com.soyummy.auth.dto.LoginDTO
import com.soyummy.auth.dto.RegisterDTO

@Service
class AuthService(
    private val authRepository: AuthRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtService: JwtService
) {

    fun register(request: RegisterDTO, role: Role): String {
        val user = User(
            name = request.name,
            email = request.email,
            password = passwordEncoder.encode(request.password),
            role = role
        )

        val token = jwtService.generateToken(user.email)
        user.token = token

        authRepository.save(user)

        return token
    }

    fun login(request: LoginDTO): String {
        val user = authRepository.findByEmail(request.email)
            .orElseThrow { UsernameNotFoundException("User not found") }

        if (!passwordEncoder.matches(request.password, user.password)) {
            throw BadCredentialsException("Invalid password")
        }

        val token = jwtService.generateToken(user.email)
        user.token = token
        authRepository.save(user)

        return token
    }

    fun logout(token: String) {
        if (token.isBlank()) {
            throw IllegalArgumentException("Token is required")
        }

        val cleanedToken = if (token.startsWith("Bearer ")) {
            token.substring(7)
        } else {
            throw IllegalArgumentException("Invalid token format")
        }

        val user = authRepository.findByToken(cleanedToken)
            .orElseThrow { IllegalArgumentException("Invalid token") }

        user.token = null
        authRepository.save(user)
    }
}