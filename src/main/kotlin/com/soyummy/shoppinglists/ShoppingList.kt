package com.soyummy.shoppinglists

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.index.CompoundIndex
import org.springframework.data.mongodb.core.index.Indexed

@Document(collection = "shoppinglists")
@CompoundIndex(name = "owner_recipe_idx", def = "{'owner': 1, 'recipeId': 1}")
@CompoundIndex(name = "owner_ingredient_idx", def = "{'owner': 1, 'strIngredient': 1}")
data class ShoppingList(
    @Id
    val id: String? = null,

    @Field("owner")
    @Indexed
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