package com.soyummy.shoppinglists.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

data class ShoppingListCreateDto(
    @field:NotBlank(message = "Owner is required!")
    var owner: String,

    @field:NotBlank(message = "Ingredient is required!")
    val strIngredient: String,

    @field:NotBlank(message = "Weight is required!")
    @field:Pattern(regexp = "^\\d+$", message = "Weight must be a positive number")
    val weight: String,

    @field:NotBlank(message = "Image is required!")
    val image: String,

    @field:NotBlank(message = "RecipeId is required!")
    val recipeId: String
)