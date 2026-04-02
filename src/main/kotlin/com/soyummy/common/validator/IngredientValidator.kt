package com.soyummy.common.validator

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import com.soyummy.ingredients.dto.IngredientCreateDto

@Component
@Scope("prototype")
class IngredientValidator {
    private val errors = mutableListOf<String>()

    fun validate(ingredientDto: IngredientCreateDto): IngredientValidator {
        if (ingredientDto.title.isBlank()) {
            errors.add("Ingredient title cannot be empty")
        }

        if (ingredientDto.title.length > 50) {
            errors.add("Ingredient title is too long (max 50 characters)")
        }

        if (ingredientDto.description.isBlank()) {
            errors.add("Ingredient description cannot be empty")
        }

        return this
    }

    fun isValid(): Boolean = errors.isEmpty()

    fun getErrors(): String = errors.joinToString("; ")
}