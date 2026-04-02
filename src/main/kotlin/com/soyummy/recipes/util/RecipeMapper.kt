package com.soyummy.recipes.util

import org.springframework.stereotype.Component
import java.time.LocalDateTime
import com.soyummy.recipes.Recipe
import com.soyummy.recipes.dto.RecipeCreateDto
import com.soyummy.recipes.dto.RecipeUpdateDto

@Component
class RecipeMapper {

    fun buildNewRecipe(dto: RecipeCreateDto): Recipe = Recipe(
        title = dto.title,
        category = dto.category,
        area = dto.area,
        instructions = dto.instructions,
        description = dto.description,
        thumbnail = dto.thumbnail,
        preview = dto.preview,
        time = dto.time,
        popularity = 0,
        tags = dto.tags,
        ingredients = dto.ingredients,
        youtube = dto.youtube,
        favorites = emptyList(),
        likes = emptyList(),
        createdAt = LocalDateTime.now(),
        updatedAt = LocalDateTime.now()
    )

    fun updateCurrentRecipe(dto: RecipeUpdateDto, existing: Recipe): Recipe =
        existing.copy(
            title = dto.title ?: existing.title,
            category = dto.category ?: existing.category,
            area = dto.area ?: existing.area,
            instructions = dto.instructions ?: existing.instructions,
            description = dto.description ?: existing.description,
            thumbnail = dto.thumbnail ?: existing.thumbnail,
            preview = dto.preview ?: existing.preview,
            time = dto.time ?: existing.time,
            tags = dto.tags ?: existing.tags,
            ingredients = dto.ingredients ?: existing.ingredients,
            youtube = dto.youtube ?: existing.youtube,
            updatedAt = LocalDateTime.now()
        )
}