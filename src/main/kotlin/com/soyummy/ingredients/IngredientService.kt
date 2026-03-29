package com.soyummy.ingredients

import com.soyummy.ingredients.dto.IngredientCreateDto
import com.soyummy.ingredients.dto.IngredientUpdateDto

interface IngredientService {
    fun getAllIngredients(): List<Ingredient>
    fun getIngredientById(id: String): Ingredient
    fun searchIngredients(title: String?): List<Ingredient>
    fun createIngredient(dto: IngredientCreateDto): Ingredient
    fun updateIngredient(id: String, dto: IngredientUpdateDto): Ingredient
    fun deleteIngredient(id: String)
}