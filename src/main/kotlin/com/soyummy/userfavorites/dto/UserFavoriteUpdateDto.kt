package com.soyummy.userfavorites.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserFavoriteUpdateDto(
    @field:NotBlank(message = "RecipeId is required!")
    @field:Size(min = 1, message = "RecipeId must have at least one character.")
    val recipeId: String
)