package com.soyummy.recipefavorites

import org.springframework.data.domain.Pageable
import com.soyummy.common.dto.PageResponse
import com.soyummy.recipefavorites.dto.RecipeFavoriteCreateDto
import com.soyummy.recipefavorites.dto.RecipeFavoriteUpdateDto
import com.soyummy.recipefavorites.dto.TopFavoriteDto

interface RecipeFavoriteService {
    fun getAllFavorites(pageable: Pageable): PageResponse<RecipeFavorite>
    fun getFavoriteById(id: String): RecipeFavorite
    fun getFavoritesByRatingDesc(): List<TopFavoriteDto>
    fun getFavoritesByRecipeId(recipeId: String): List<RecipeFavorite>
    fun getFavoritesByAmount(amount: Int): List<RecipeFavorite>
    fun createFavorite(createDto: RecipeFavoriteCreateDto): RecipeFavorite
    fun updateFavorite(id: String, updateDto: RecipeFavoriteUpdateDto): RecipeFavorite
    fun deleteFavorite(id: String)
}