package com.soyummy.auth.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class RegisterDTO(
    @field:NotBlank(message = "Email is required!")
    @field:Email(message = "Please provide a valid email!")
    val email: String,

    @field:NotBlank(message = "Name is required!")
    val name: String,

    @field:NotBlank(message = "Password is required!")
    @field:Size(min = 6, message = "Password must be at least 6 characters!")
    val password: String,

    val role: String? = null
)