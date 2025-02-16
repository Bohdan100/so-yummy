package com.soyummy.ingredients.dto

import jakarta.validation.constraints.NotBlank

data class IngredientCreateDto(
    @field:NotBlank(message = "Title is required!")
    val title: String,

    @field:NotBlank(message = "Description is required!")
    val description: String,

    @field:NotBlank(message = "Thumbnail is required!")
    val thumbnail: String
)