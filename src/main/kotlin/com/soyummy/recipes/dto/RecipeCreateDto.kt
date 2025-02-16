package com.soyummy.recipes.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import com.soyummy.recipes.RecipeIngredient

data class RecipeCreateDto(
    @field:NotBlank(message = "Title is required!")
    val title: String,

    @field:NotBlank(message = "Category is required!")
    val category: String,

    @field:NotBlank(message = "Area is required!")
    val area: String,

    @field:NotBlank(message = "Instructions are required!")
    val instructions: String,

    @field:NotBlank(message = "Description is required!")
    val description: String,

    @field:NotBlank(message = "Time is required!")
    val time: String,

    @field:Pattern(regexp = "^https://.{3,}", message = "The URL must start with \"https://\" and have at least 3 characters following.")
    val thumbnail: String,

    @field:Pattern(regexp = "^https://.{3,}", message = "The URL must start with \"https://\" and have at least 3 characters following.")
    val preview: String,

    @field:Pattern(regexp = "^https://.{3,}", message = "The URL must start with \"https://\" and have at least 3 characters following.")
    val youtube: String,

    val tags: List<String>? = null,
    val ingredients: List<RecipeIngredient>? = null
)