package com.soyummy.ingredients.util

import org.springframework.stereotype.Component
import com.soyummy.ingredients.Ingredient
import com.soyummy.ingredients.dto.IngredientCreateDto
import com.soyummy.ingredients.dto.IngredientUpdateDto

@Component
class IngredientMapper {

    fun buildNewIngredient(dto: IngredientCreateDto): Ingredient {
        return Ingredient(
            title = dto.title,
            description = dto.description,
            thumbnail = dto.thumbnail
        )
    }

    fun updateCurrentIngredient(
        dto: IngredientUpdateDto,
        existingIngredient: Ingredient
    ): Ingredient {
        return existingIngredient.copy(
            title = dto.title?.takeIf { it.isNotBlank() } ?: existingIngredient.title,
            description = dto.description?.takeIf { it.isNotBlank() } ?: existingIngredient.description,
            thumbnail = dto.thumbnail?.takeIf { it.isNotBlank() } ?: existingIngredient.thumbnail
        )
    }
}