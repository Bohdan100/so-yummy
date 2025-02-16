package com.soyummy.recipes

import org.springframework.data.mongodb.core.mapping.Field

data class RecipeIngredient(
    @Field("id")
    val id: String?,
    val measure: String
)