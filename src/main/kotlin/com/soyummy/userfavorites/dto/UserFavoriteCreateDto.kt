package com.soyummy.userfavorites.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserFavoriteCreateDto(
    @field:NotBlank(message = "Recipe ID is required!")
    @field:Size(min = 1, message = "RecipeId must have at least one character.")
    val recipeId: String,

    @field:NotBlank(message = "User ID is required!")
    @field:Size(min = 1, message = "UserId must have at least one character.")
    var userId: String
)