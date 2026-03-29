package com.soyummy.recipefavorites

import org.springframework.stereotype.Service
import com.soyummy.recipefavorites.dto.RecipeFavoriteCreateDto
import com.soyummy.recipefavorites.dto.RecipeFavoriteUpdateDto
import com.soyummy.exception.types.ResourceNotFoundException
import org.bson.types.ObjectId

@Service
class RecipeFavoriteServiceImpl(
    private val recipeFavoriteRepository: RecipeFavoriteRepository
) : RecipeFavoriteService {

    override fun getAllFavorites(): List<RecipeFavorite> = recipeFavoriteRepository.findAll()

    override fun getFavoriteById(id: String): RecipeFavorite = recipeFavoriteRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Favorite recipe not found with id: $id") }

    override fun getFavoritesByRecipeId(recipeId: String): List<RecipeFavorite> =
        recipeFavoriteRepository.findByRecipeId(ObjectId(recipeId))

    override fun getFavoritesByAmount(amount: Int): List<RecipeFavorite> =
        recipeFavoriteRepository.findByAmount(amount)

    override fun getFavoritesByRatingDesc(): List<RecipeFavorite> =
        recipeFavoriteRepository.findAllByOrderByAmountDesc()

    override fun createFavorite(createDto: RecipeFavoriteCreateDto): RecipeFavorite {
        if (createDto.recipeId.isBlank()) {
            throw IllegalArgumentException("All fields (recipeId, amount) must be provided.")
        }

        val favorite = RecipeFavorite(
            recipeId = createDto.recipeId,
            amount = createDto.amount
        )

        return recipeFavoriteRepository.save(favorite)
    }

    override fun updateFavorite(id: String, updateDto: RecipeFavoriteUpdateDto): RecipeFavorite {
        val existingFavorite = getFavoriteById(id)
        val updatedFavorite = existingFavorite.copy(amount = updateDto.amount)
        return recipeFavoriteRepository.save(updatedFavorite)
    }

    override fun deleteFavorite(id: String) {
        if (!recipeFavoriteRepository.existsById(id))
            throw ResourceNotFoundException("Favorite recipe not found with id: $id")

        recipeFavoriteRepository.deleteById(id)
    }
}