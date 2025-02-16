package com.soyummy.userfavorites

import org.springframework.stereotype.Service
import com.soyummy.userfavorites.dto.UserFavoriteCreateDto
import com.soyummy.userfavorites.dto.UserFavoriteUpdateDto
import com.soyummy.errors.ResourceNotFoundException
import org.bson.types.ObjectId

@Service
class UserFavoriteService(private val userFavoriteRepository: UserFavoriteRepository) {
    fun getAllFavorites(): List<UserFavorite> = userFavoriteRepository.findAll()

    fun getFavoriteById(id: String): UserFavorite = userFavoriteRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Favorite not found with id: $id") }

    fun getFavoritesByRecipeId(recipeId: String): List<UserFavorite> =
        userFavoriteRepository.findByRecipeId(ObjectId(recipeId))

    fun getFavoritesByUserId(userId: String): List<UserFavorite> =
        userFavoriteRepository.findByUserId(ObjectId(userId))

    fun createFavorite(createDto: UserFavoriteCreateDto): UserFavorite {
        if (createDto.recipeId.isBlank() || createDto.userId.isBlank())
            throw IllegalArgumentException("Missing required fields when adding a new favorite")

        val favorite = UserFavorite(
            recipeId = createDto.recipeId,
            userId = createDto.userId
        )
        return userFavoriteRepository.save(favorite)
    }

    fun updateFavorite(id: String, updateDto: UserFavoriteUpdateDto): UserFavorite {
        if (updateDto.recipeId.isBlank())
            throw IllegalArgumentException("Missing required fields when update a favorite")

        val existingFavorite = getFavoriteById(id)
        val updatedFavorite = existingFavorite.copy(recipeId = updateDto.recipeId)

        return userFavoriteRepository.save(updatedFavorite)
    }

    fun deleteFavorite(id: String) {
        if (!userFavoriteRepository.existsById(id))
            throw ResourceNotFoundException("Favorite not found with id: $id")

        userFavoriteRepository.deleteById(id)
    }
}