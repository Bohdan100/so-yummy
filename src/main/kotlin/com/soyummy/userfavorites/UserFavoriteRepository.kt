package com.soyummy.userfavorites

import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.repository.MongoRepository
import org.bson.types.ObjectId

@Repository
interface UserFavoriteRepository: MongoRepository<UserFavorite, String> {
    fun findByUserId(userId: ObjectId): List<UserFavorite>
    fun findByRecipeId(recipeId: ObjectId): List<UserFavorite>
}