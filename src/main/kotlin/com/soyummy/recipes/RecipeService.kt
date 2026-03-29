package com.soyummy.recipes

import com.soyummy.recipes.dto.RecipeCreateDto
import com.soyummy.recipes.dto.RecipeUpdateDto

interface RecipeService {
    fun getAllRecipes(): List<Recipe>
    fun getRecipeById(id: String): Recipe
    fun searchRecipes(title: String?, category: String?, area: String?, tag: String?): List<Recipe>
    fun createRecipe(dto: RecipeCreateDto): Recipe
    fun updateRecipe(id: String, dto: RecipeUpdateDto): Recipe
    fun deleteRecipe(id: String)
}