package com.soyummy.ingredients.dto

data class IngredientUsageStat(
    val ingredientId: String,
    val ingredientTitle: String,
    val usageCount: Long,
    val thumbnail: String
)