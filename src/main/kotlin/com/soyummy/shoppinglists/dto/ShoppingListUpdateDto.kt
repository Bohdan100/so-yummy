package com.soyummy.shoppinglists.dto

import jakarta.validation.constraints.Size
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.AssertTrue

data class ShoppingListUpdateDto(
    @field:Size(min = 1, message = "Ingredient must have at least one character.")
    val strIngredient: String? = null,

    @field:Pattern(regexp = "^\\d+$", message = "Weight must be a positive number")
    val weight: String? = null,

    @field:Size(min = 1, message = "Image must have at least one character.")
    val image: String? = null,

    @field:Size(min = 1, message = "RecipeId must have at least one character.")
    val recipeId: String? = null
) {
    @AssertTrue(message = "At least one field (strIngredient, weight, image, recipeId) must be provided.")
    fun isAtLeastOneFieldProvided(): Boolean {
        return !strIngredient.isNullOrBlank() ||
                !weight.isNullOrBlank() ||
                !image.isNullOrBlank() ||
                !recipeId.isNullOrBlank()
    }
}