package com.soyummy.recipes

import org.springframework.data.domain.Pageable
import com.soyummy.common.dto.PageResponse
import com.soyummy.recipes.dto.RecipeCreateDto
import com.soyummy.recipes.dto.RecipeUpdateDto

interface RecipeService {
    fun getAllRecipes(pageable: Pageable): PageResponse<Recipe>
    fun getRecipeById(id: String): Recipe
    fun getTopRecipesByPopularity(pageable: Pageable): PageResponse<Recipe>
    fun searchRecipes(
        title: String?,
        category: String?,
        area: String?,
        tag: String?,
        pageable: Pageable
    ): PageResponse<Recipe>
    fun createRecipe(dto: RecipeCreateDto): Recipe
    fun updateRecipe(id: String, dto: RecipeUpdateDto): Recipe
    fun deleteRecipe(id: String)
}