package com.soyummy.recipefavorites

import org.springframework.stereotype.Service
import com.soyummy.recipefavorites.dto.RecipeFavoriteCreateDto
import com.soyummy.recipefavorites.dto.RecipeFavoriteUpdateDto
import com.soyummy.errors.ResourceNotFoundException
import org.bson.types.ObjectId

@Service
class RecipeFavoriteService(private val recipeFavoriteRepository: RecipeFavoriteRepository) {
    fun getAllFavorites(): List<RecipeFavorite> = recipeFavoriteRepository.findAll()

    fun getFavoriteById(id: String): RecipeFavorite = recipeFavoriteRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Favorite recipe not found with id: $id") }

    fun getFavoritesByRecipeId(recipeId: String): List<RecipeFavorite> =
        recipeFavoriteRepository.findByRecipeId(ObjectId(recipeId))

    fun getFavoritesByAmount(amount: Int): List<RecipeFavorite> =
        recipeFavoriteRepository.findByAmount(amount)

    fun getFavoritesByRatingDesc(): List<RecipeFavorite> =
        recipeFavoriteRepository.findAllByOrderByAmountDesc()

    fun createFavorite(createDto: RecipeFavoriteCreateDto): RecipeFavorite {
        if (createDto.recipeId.isBlank()) {
            throw IllegalArgumentException("All fields (recipeId, amount) must be provided.")
        }

        val favorite = RecipeFavorite(
            recipeId = createDto.recipeId,
            amount = createDto.amount
        )

        return recipeFavoriteRepository.save(favorite)
    }

    fun updateFavorite(id: String, updateDto: RecipeFavoriteUpdateDto): RecipeFavorite {
        val existingFavorite = getFavoriteById(id)
        val updatedFavorite = existingFavorite.copy(amount = updateDto.amount)
        return recipeFavoriteRepository.save(updatedFavorite)
    }

    fun deleteFavorite(id: String) {
        if (!recipeFavoriteRepository.existsById(id))
            throw ResourceNotFoundException("Favorite recipe not found with id: $id")

        recipeFavoriteRepository.deleteById(id)
    }
}