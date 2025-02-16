package com.soyummy.recipes

import org.springframework.stereotype.Service
import com.soyummy.recipes.dto.RecipeCreateDto
import com.soyummy.recipes.dto.RecipeUpdateDto
import com.soyummy.errors.ResourceNotFoundException
import java.time.LocalDateTime

@Service
class RecipeService(private val recipeRepository: RecipeRepository) {
    fun getAllRecipes(): List<Recipe> = recipeRepository.findAll()

    fun getRecipeById(id: String): Recipe = recipeRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Recipe not found with id: $id") }

    fun findByTitle(title: String): List<Recipe> =
        recipeRepository.findByTitleContainingIgnoreCase(title)

    fun findByCategory(category: String): List<Recipe> =
        recipeRepository.findByCategoryContainingIgnoreCase(category)

    fun findByArea(area: String): List<Recipe> =
        recipeRepository.findByAreaContainingIgnoreCase(area)

    fun findByTags(tag: String): List<Recipe> =
        recipeRepository.findByTagsContainingIgnoreCase(tag)

    fun createRecipe(recipeCreateDto: RecipeCreateDto): Recipe =
        recipeRepository.save(buildNewRecipe(recipeCreateDto))


    fun updateRecipe(id: String, recipeUpdateDto: RecipeUpdateDto): Recipe {
        val existingRecipe = getRecipeById(id)
        val updatedRecipe = updateCurrentRecipe(recipeUpdateDto, existingRecipe)
        return recipeRepository.save(updatedRecipe)
    }

    fun deleteRecipe(id: String) {
        if (!recipeRepository.existsById(id))
            throw ResourceNotFoundException("Recipe not found with id: $id")

        recipeRepository.deleteById(id)
    }

    private fun buildNewRecipe(recipeCreateDto: RecipeCreateDto): Recipe {
        return Recipe(
            title = recipeCreateDto.title,
            category = recipeCreateDto.category,
            area = recipeCreateDto.area,
            instructions = recipeCreateDto.instructions,
            description = recipeCreateDto.description,
            thumbnail = recipeCreateDto.thumbnail,
            preview = recipeCreateDto.preview,
            time = recipeCreateDto.time,
            popularity = 0,
            tags = recipeCreateDto.tags,
            ingredients = recipeCreateDto.ingredients,
            youtube = recipeCreateDto.youtube,
            favorites = emptyList(),
            likes = emptyList(),
            createdAt = LocalDateTime.now(),
            updatedAt = LocalDateTime.now()
        )
    }

    private fun updateCurrentRecipe(recipeUpdateDto: RecipeUpdateDto, existingRecipe: Recipe): Recipe {
        return existingRecipe.copy(
            title = recipeUpdateDto.title ?: existingRecipe.title,
            category = recipeUpdateDto.category ?: existingRecipe.category,
            area = recipeUpdateDto.area ?: existingRecipe.area,
            instructions = recipeUpdateDto.instructions ?: existingRecipe.instructions,
            description = recipeUpdateDto.description ?: existingRecipe.description,
            thumbnail = recipeUpdateDto.thumbnail ?: existingRecipe.thumbnail,
            preview = recipeUpdateDto.preview ?: existingRecipe.preview,
            time = recipeUpdateDto.time ?: existingRecipe.time,
            tags = recipeUpdateDto.tags ?: existingRecipe.tags,
            ingredients = recipeUpdateDto.ingredients ?: existingRecipe.ingredients,
            youtube = recipeUpdateDto.youtube ?: existingRecipe.youtube
        )
    }
}