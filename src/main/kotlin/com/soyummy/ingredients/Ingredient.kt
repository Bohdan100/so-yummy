package com.soyummy.ingredients

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.index.TextIndexed

@Document(collection = "ingredients")
data class Ingredient(
    @Id
    val id: String? = null,

    @Field("ttl")
    @Indexed(unique = true)
    @TextIndexed(weight = 3f)
    val title: String,

    @Field("desc")
    @TextIndexed(weight = 1f)
    val description: String,

    @Field("thb")
    val thumbnail: String
)