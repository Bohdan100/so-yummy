package com.soyummy.subscribes.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Email

data class SubscribeCreateDto(
    @field:NotBlank(message = "Owner is required!")
    var owner: String,

    @field:NotBlank(message = "Email is required!")
    @field:Email(message = "Invalid email format")
    val email: String
)