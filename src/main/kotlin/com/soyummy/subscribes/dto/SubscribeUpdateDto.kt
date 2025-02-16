package com.soyummy.subscribes.dto

import jakarta.validation.constraints.Email

data class SubscribeUpdateDto(
    @field:Email(message = "Incorrect email format")
    val email: String? = null
)