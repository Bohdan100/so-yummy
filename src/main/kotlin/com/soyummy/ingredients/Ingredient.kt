package com.soyummy.ingredients

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field

@Document(collection = "ingredients")
data class Ingredient(
    @Id
    val id: String? = null,

    @Field("ttl")
    val title: String,

    @Field("desc")
    val description: String,

    @Field("thb")
    val thumbnail: String
)