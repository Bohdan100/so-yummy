package com.soyummy.shoppinglists

import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.repository.MongoRepository
import org.bson.types.ObjectId

@Repository
interface ShoppingListRepository : MongoRepository<ShoppingList, String> {
    fun findByOwner(ownerId: ObjectId): List<ShoppingList>
    fun findByRecipeIdAndOwner(recipeId: String, ownerId: String): List<ShoppingList>
    fun findByStrIngredientContainingIgnoreCaseAndOwner(strIngredient: String, ownerId: String): List<ShoppingList>
}