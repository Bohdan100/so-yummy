package com.soyummy.recipefavorites

import com.soyummy.recipefavorites.dto.RecipeFavoriteCreateDto
import com.soyummy.recipefavorites.dto.RecipeFavoriteUpdateDto

interface RecipeFavoriteService {
    fun getAllFavorites(): List<RecipeFavorite>
    fun getFavoriteById(id: String): RecipeFavorite
    fun getFavoritesByRecipeId(recipeId: String): List<RecipeFavorite>
    fun getFavoritesByAmount(amount: Int): List<RecipeFavorite>
    fun getFavoritesByRatingDesc(): List<RecipeFavorite>
    fun createFavorite(createDto: RecipeFavoriteCreateDto): RecipeFavorite
    fun updateFavorite(id: String, updateDto: RecipeFavoriteUpdateDto): RecipeFavorite
    fun deleteFavorite(id: String)
}