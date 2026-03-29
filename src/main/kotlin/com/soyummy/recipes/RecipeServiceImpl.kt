package com.soyummy.recipes

import org.springframework.stereotype.Service
import com.soyummy.recipes.dto.RecipeCreateDto
import com.soyummy.recipes.dto.RecipeUpdateDto
import com.soyummy.exception.types.ResourceNotFoundException
import java.time.LocalDateTime

@Service
class RecipeServiceImpl(private val recipeRepository: RecipeRepository) : RecipeService {

    override fun getAllRecipes(): List<Recipe> = recipeRepository.findAll()

    override fun getRecipeById(id: String): Recipe = recipeRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Recipe not found with id: $id") }

    override fun searchRecipes(title: String?, category: String?, area: String?, tag: String?): List<Recipe> {
        return when {
            title != null -> recipeRepository.findByTitleContainingIgnoreCase(title)
            category != null -> recipeRepository.findByCategoryContainingIgnoreCase(category)
            area != null -> recipeRepository.findByAreaContainingIgnoreCase(area)
            tag != null -> recipeRepository.findByTagsContainingIgnoreCase(tag)
            else -> emptyList()
        }
    }

    override fun createRecipe(dto: RecipeCreateDto): Recipe =
        recipeRepository.save(buildNewRecipe(dto))

    override fun updateRecipe(id: String, dto: RecipeUpdateDto): Recipe {
        val existingRecipe = getRecipeById(id)
        val updatedRecipe = updateCurrentRecipe(dto, existingRecipe)
        return recipeRepository.save(updatedRecipe)
    }

    override fun deleteRecipe(id: String) {
        if (!recipeRepository.existsById(id))
            throw ResourceNotFoundException("Recipe not found with id: $id")
        recipeRepository.deleteById(id)
    }

    private fun buildNewRecipe(dto: RecipeCreateDto): Recipe = Recipe(
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

    private fun updateCurrentRecipe(dto: RecipeUpdateDto, existing: Recipe): Recipe =
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