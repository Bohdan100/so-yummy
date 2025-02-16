package com.soyummy.shoppinglists

import org.springframework.stereotype.Service
import org.bson.types.ObjectId
import com.soyummy.shoppinglists.dto.ShoppingListCreateDto
import com.soyummy.shoppinglists.dto.ShoppingListUpdateDto
import com.soyummy.errors.ResourceNotFoundException

@Service
class ShoppingListService(private val shoppingListRepository: ShoppingListRepository) {

    fun getAllShoppingLists(): List<ShoppingList> = shoppingListRepository.findAll()

    fun getShoppingListById(id: String): ShoppingList = shoppingListRepository.findById(id)
        .orElseThrow { ResourceNotFoundException("Shopping list not found with id: $id") }

    fun findByOwner(ownerId: String): List<ShoppingList> =
        shoppingListRepository.findByOwner(ObjectId(ownerId))

    fun findByRecipeId(recipeId: String, ownerId: String): List<ShoppingList> =
        shoppingListRepository.findByRecipeIdAndOwner(recipeId, ownerId)

    fun findByStartIngredient(startIngredient: String, ownerId: String): List<ShoppingList> =
        shoppingListRepository.findByStrIngredientContainingIgnoreCaseAndOwner(startIngredient, ownerId)

    fun createShoppingList(shoppingListCreateDto: ShoppingListCreateDto): ShoppingList {
        val createShoppingList = createShoppingListFields(shoppingListCreateDto)
        return shoppingListRepository.save(createShoppingList)
    }

    fun updateShoppingList(id: String, shoppingListUpdateDto: ShoppingListUpdateDto): ShoppingList {
        val existingShoppingList = getShoppingListById(id)
        val updatedShoppingList = updateShoppingListFields(existingShoppingList, shoppingListUpdateDto)
        return shoppingListRepository.save(updatedShoppingList)
    }

    fun deleteShoppingList(id: String) {
        shoppingListRepository.deleteById(id)
    }

    private fun createShoppingListFields(
        createDto: ShoppingListCreateDto
    ): ShoppingList {
        return ShoppingList(
            owner = createDto.owner,
            strIngredient = createDto.strIngredient,
            weight = createDto.weight,
            image = createDto.image,
            recipeId = createDto.recipeId
        )
    }

    private fun updateShoppingListFields(
        shoppingList: ShoppingList,
        updateDto: ShoppingListUpdateDto
    ): ShoppingList {
        return shoppingList.copy(
            strIngredient = updateDto.strIngredient ?: shoppingList.strIngredient,
            weight = updateDto.weight ?: shoppingList.weight,
            image = updateDto.image ?: shoppingList.image,
            recipeId = updateDto.recipeId ?: shoppingList.recipeId
        )
    }
}