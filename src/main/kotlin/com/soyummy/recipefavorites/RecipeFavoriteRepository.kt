package com.soyummy.recipefavorites

import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.bson.types.ObjectId

@Repository
interface RecipeFavoriteRepository : MongoRepository<RecipeFavorite, String> {
    fun findByRecipeId(recipeId: ObjectId): List<RecipeFavorite>
    fun findByAmount(amount: Int): List<RecipeFavorite>
    fun findAllByOrderByAmountDesc(pageable: Pageable): Page<RecipeFavorite>
}