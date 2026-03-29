package com.soyummy.ingredients

import org.springframework.stereotype.Service
import com.soyummy.ingredients.dto.IngredientCreateDto
import com.soyummy.ingredients.dto.IngredientUpdateDto
import com.soyummy.exception.types.ResourceNotFoundException

@Service
class IngredientServiceImpl(private val ingredientRepository: IngredientRepository) : IngredientService {

    override fun getAllIngredients(): List<Ingredient> = ingredientRepository.findAll()

    override fun getIngredientById(id: String): Ingredient = ingredientRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Ingredient not found with id: $id") }

    override fun searchIngredients(title: String?): List<Ingredient> {
        if (title == null) return emptyList()
        return ingredientRepository.findByTitleContainingIgnoreCase(title).toList()
    }

    override fun createIngredient(dto: IngredientCreateDto): Ingredient =
        ingredientRepository.save(buildNewIngredient(dto))

    override fun updateIngredient(id: String, dto: IngredientUpdateDto): Ingredient {
        val existing = getIngredientById(id)
        val updated = updateCurrentIngredient(dto, existing)
        return ingredientRepository.save(updated)
    }

    override fun deleteIngredient(id: String) {
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