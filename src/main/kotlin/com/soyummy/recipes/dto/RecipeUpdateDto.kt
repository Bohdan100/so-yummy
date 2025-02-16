package com.soyummy.recipes.dto

import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size
import jakarta.validation.constraints.AssertTrue
import com.soyummy.recipes.RecipeIngredient

data class RecipeUpdateDto(
    @field:Size(min = 1, message = "Title must have at least one character.")
    val title: String? = null,

    @field:Size(min = 1, message = "Category must have at least one character.")
    val category: String? = null,

    @field:Size(min = 1, message = "Area must have at least one character.")
    val area: String? = null,

    @field:Size(min = 1, message = "Instructions must have at least one character.")
    val instructions: String? = null,

    @field:Size(min = 1, message = "Description must have at least one character.")
    val description: String? = null,

    @field:Size(min = 1, message = "Time must have at least one character.")
    val time: String? = null,

    @field:Pattern(regexp = "^https://.{3,}", message = "The URL must start with \"https://\" and have at least 3 characters following.")
    val thumbnail: String? = null,

    @field:Pattern(regexp = "^https://.{3,}", message = "The URL must start with \"https://\" and have at least 3 characters following.")
    val preview: String? = null,

    @field:Pattern(regexp = "^https://.{3,}", message = "The URL must start with \"https://\" and have at least 3 characters following.")
    val youtube: String? = null,

    val tags: List<String>? = null,
    val ingredients: List<RecipeIngredient>? = null
) {
    @AssertTrue(message = "At least one field (title, category, area, instructions, description, time, thumbnail, preview, youtube, tags, ingredients) must be provided for update.")
    fun isAtLeastOneFieldProvided(): Boolean {
        return !title.isNullOrBlank() ||
                !category.isNullOrBlank() ||
                !area.isNullOrBlank() ||
                !instructions.isNullOrBlank() ||
                !description.isNullOrBlank() ||
                !time.isNullOrBlank() ||
                !thumbnail.isNullOrBlank() ||
                !preview.isNullOrBlank() ||
                !youtube.isNullOrBlank() ||
                !tags.isNullOrEmpty() ||
                !ingredients.isNullOrEmpty()
    }
}