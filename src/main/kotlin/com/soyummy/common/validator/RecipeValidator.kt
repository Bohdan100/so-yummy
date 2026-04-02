package com.soyummy.common.validator

import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import com.soyummy.recipes.dto.RecipeCreateDto

@Component
@Scope("prototype")
class RecipeValidator {
    private val errors = mutableListOf<String>()

    fun validate(recipeDto: RecipeCreateDto): RecipeValidator {
        if (recipeDto.title.isBlank()) {
            errors.add("Recipe title cannot be empty")
        }

        if (recipeDto.title.length > 100) {
            errors.add("Recipe title is too long (max 100 characters)")
        }

        if (recipeDto.instructions.isBlank()) {
            errors.add("Instructions cannot be empty")
        }

        if (recipeDto.instructions.length < 50) {
            errors.add("Instructions should be at least 50 characters long")
        }

        if (recipeDto.ingredients.isNullOrEmpty()) {
            errors.add("Recipe must have at least one ingredient")
        }

        return this
    }

    fun isValid(): Boolean = errors.isEmpty()

    fun getErrors(): String = errors.joinToString("; ")
}