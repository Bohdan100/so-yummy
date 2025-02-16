package com.soyummy.recipefavorites.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive

data class RecipeFavoriteUpdateDto(
    @field:NotNull(message = "Amount is required!")
    @field:Positive(message = "Amount must be a positive number!")
    val amount: Int
)