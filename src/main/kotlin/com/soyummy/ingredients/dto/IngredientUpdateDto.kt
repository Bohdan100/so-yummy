package com.soyummy.ingredients.dto

import jakarta.validation.constraints.Size
import jakarta.validation.constraints.AssertTrue

data class IngredientUpdateDto(
    val id: String? = null,

    @field:Size(min = 1, message = "Title must have at least one character.")
    val title: String? = null,

    @field:Size(min = 1, message = "Description must have at least one character.")
    val description: String? = null,

    @field:Size(min = 1, message = "Thumbnail must have at least one character.")
    val thumbnail: String? = null
) {
    @AssertTrue(message = "At least one field (title, description, thumbnail) must be provided.")
    fun isAtLeastOneFieldProvided(): Boolean {
        return !title.isNullOrBlank() || !description.isNullOrBlank() || !thumbnail.isNullOrBlank()
    }
}