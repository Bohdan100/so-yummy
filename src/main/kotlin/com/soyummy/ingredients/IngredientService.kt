package com.soyummy.ingredients

import org.springframework.stereotype.Service
import com.soyummy.ingredients.dto.IngredientCreateDto
import com.soyummy.ingredients.dto.IngredientUpdateDto
import com.soyummy.errors.ResourceNotFoundException

@Service
class IngredientService(private val ingredientRepository: IngredientRepository) {

    fun getAllIngredients(): List<Ingredient> = ingredientRepository.findAll()

    fun getIngredientById(id: String): Ingredient = ingredientRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Ingredient not found with id: $id") }

    fun searchIngredientsByTitle(title: String): List<Ingredient> =
        ingredientRepository.findByTitleContainingIgnoreCase(title).toList()

    fun createIngredient(ingredientCreateDto: IngredientCreateDto): Ingredient =
        ingredientRepository.save(buildNewIngredient(ingredientCreateDto))

    fun updateIngredient(id: String, ingredientUpdateDto: IngredientUpdateDto): Ingredient {
        val existingIngredient = getIngredientById(id)
        val updatedIngredient = updateCurrentIngredient(ingredientUpdateDto, existingIngredient)
        return ingredientRepository.save(updatedIngredient)
    }

    fun deleteIngredient(id: String) {
        if (!ingredientRepository.existsById(id))
            throw ResourceNotFoundException("Ingredient not found with id: $id")

        ingredientRepository.deleteById(id)
    }

    private fun buildNewIngredient(ingredientCreateDto: IngredientCreateDto): Ingredient {
        return Ingredient(
            title = ingredientCreateDto.title,
            description = ingredientCreateDto.description,
            thumbnail = ingredientCreateDto.thumbnail
        )
    }

    private fun updateCurrentIngredient(
        ingredientUpdateDto: IngredientUpdateDto,
        existingIngredient: Ingredient
    ): Ingredient {
        return existingIngredient.copy(
            title = ingredientUpdateDto.title?.takeIf { it.isNotBlank() } ?: existingIngredient.title,
            description = ingredientUpdateDto.description?.takeIf { it.isNotBlank() } ?: existingIngredient.description,
            thumbnail = ingredientUpdateDto.thumbnail?.takeIf { it.isNotBlank() } ?: existingIngredient.thumbnail
        )
    }
}