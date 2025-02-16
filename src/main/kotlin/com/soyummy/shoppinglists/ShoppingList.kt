package com.soyummy.shoppinglists

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "shoppinglists")
data class ShoppingList(
    @Id
    val id: String? = null,

    @Field("owner")
    val owner: String,

    @Field("strIngredient")
    val strIngredient: String,

    @Field("weight")
    val weight: String,

    @Field("image")
    val image: String,

    @Field("recipeId")
    val recipeId: String
)