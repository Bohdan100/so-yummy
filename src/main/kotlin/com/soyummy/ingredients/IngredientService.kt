package com.soyummy.ingredients

import org.springframework.data.domain.Pageable
import com.soyummy.common.dto.PageResponse
import com.soyummy.ingredients.dto.IngredientUsageStat
import com.soyummy.ingredients.dto.IngredientCreateDto
import com.soyummy.ingredients.dto.IngredientUpdateDto

interface IngredientService {
    fun getAllIngredients(pageable: Pageable): PageResponse<Ingredient>
    fun getIngredientById(id: String): Ingredient
    fun getTopIngredientsByUsage(pageable: Pageable): PageResponse<IngredientUsageStat>
    fun searchIngredients(title: String?, pageable: Pageable): PageResponse<Ingredient>
    fun createIngredient(dto: IngredientCreateDto): Ingredient
    fun updateIngredient(id: String, dto: IngredientUpdateDto): Ingredient
    fun deleteIngredient(id: String)
}