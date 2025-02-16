package com.soyummy.userfavorites

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "userfavorites")
data class UserFavorite(
    @Id
    val id: String? = null,

    @Field("recipe")
    val recipeId: String,

    @Field("userId")
    val userId: String
)