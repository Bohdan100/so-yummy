package com.soyummy.recipes

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Field
import java.time.LocalDateTime

@Document(collection = "recipes")
data class Recipe(
    @Id
    val id: String? = null,

    @Field("title")
    val title: String,

    @Field("category")
    val category: String,

    @Field("area", write = Field.Write.ALWAYS)
    val area: String = "Unknown",

    @Field("instructions")
    val instructions: String,

    @Field("description")
    val description: String,

    @Field("thumb")
    val thumbnail: String,

    @Field("preview")
    val preview: String,

    @Field("time")
    val time: String,

    @Field("popularity")
    val popularity: Int,

    @Field("favorites")
    val favorites: List<String>,

    @Field("likes")
    val likes: List<String>,

    @Field("youtube")
    val youtube: String,

    @Field("tags")
    val tags: List<String>?,

    @Field("ingredients")
    val ingredients: List<RecipeIngredient>?,

    @Field("createdAt")
    val createdAt: LocalDateTime,

    @Field("updatedAt")
    val updatedAt: LocalDateTime
)