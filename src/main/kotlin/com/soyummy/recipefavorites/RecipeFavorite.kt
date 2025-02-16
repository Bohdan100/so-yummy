package com.soyummy.recipefavorites

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "recipefavorites")
data class RecipeFavorite(
    @Id
    val id: String? = null,

    @Field("recipe")
    val recipeId: String,

    @Field("amount")
    val amount: Int
)