package com.soyummy.recipes

import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.Field
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.index.TextIndexed
import java.time.LocalDateTime

@Document(collection = "recipes")
data class Recipe(
    @Id
    val id: String? = null,

    @Field("title")
    @Indexed
    @TextIndexed(weight = 3f)
    val title: String,

    @Field("category")
    @Indexed
    val category: String,

    @Field("area")
    @Indexed
    val area: String = "Unknown",

    @Field("instructions")
    val instructions: String,

    @Field("description")
    @TextIndexed(weight = 1f)
    val description: String,

    @Field("thumb")
    val thumbnail: String,

    @Field("preview")
    val preview: String,

    @Field("time")
    val time: String,

    @Field("popularity")
    @Indexed(direction = org.springframework.data.mongodb.core.index.IndexDirection.DESCENDING)
    val popularity: Int = 0,

    @Field("favorites")
    val favorites: List<String> = emptyList(),

    @Field("likes")
    val likes: List<String> = emptyList(),

    @Field("youtube")
    val youtube: String,

    @Field("tags")
    @Indexed
    val tags: List<String>? = emptyList(),

    @Field("ingredients")
    val ingredients: List<RecipeIngredient>? = emptyList(),

    @Field("createdAt")
    val createdAt: LocalDateTime,

    @Field("updatedAt")
    val updatedAt: LocalDateTime
)