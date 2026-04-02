package com.soyummy.recipefavorites

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Indexed

@Document(collection = "recipefavorites")
@CompoundIndex(name = "recipe_amount_idx", def = "{'recipe': 1, 'amount': -1}")
data class RecipeFavorite(
    @Id
    val id: String? = null,

    @Field("recipe")
    @Indexed
    val recipeId: String,

    @Field("amount")
    @Indexed(direction = org.springframework.data.mongodb.core.index.IndexDirection.DESCENDING)
    val amount: Int
)