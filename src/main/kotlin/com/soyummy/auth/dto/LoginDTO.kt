package com.soyummy.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

data class LoginDTO(
    @field:NotBlank(message = "Email is required!")
    @field:Email(message = "Please provide a valid email!")
    val email: String,

    @field:NotBlank(message = "Password is required!")
    val password: String
)