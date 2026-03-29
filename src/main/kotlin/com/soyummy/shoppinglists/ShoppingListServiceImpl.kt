package com.soyummy.shoppinglists

import org.springframework.stereotype.Service
import org.bson.types.ObjectId
import org.springframework.security.access.AccessDeniedException
import com.soyummy.shoppinglists.dto.ShoppingListCreateDto
import com.soyummy.shoppinglists.dto.ShoppingListUpdateDto
import com.soyummy.auth.User
import com.soyummy.auth.Role
import com.soyummy.exception.types.ResourceNotFoundException
import com.soyummy.exception.types.UnauthorizedException

@Service
class ShoppingListServiceImpl(
    private val shoppingListRepository: ShoppingListRepository
) : ShoppingListService {

    override fun getAllShoppingLists(): List<ShoppingList> =
        shoppingListRepository.findAll()

    override fun getShoppingListById(id: String): ShoppingList =
        shoppingListRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Shopping list not found with id: $id") }

    override fun getShoppingListById(id: String, currentUser: User?): ShoppingList {
        val shoppingList = getShoppingListById(id)
        if (shoppingList.owner != currentUser?.id && currentUser?.role != Role.ADMIN)
            throw AccessDeniedException("Shopping list not found for current user")
        return shoppingList
    }

    override fun findByOwner(ownerId: String, currentUser: User?): List<ShoppingList> {
        if (ownerId != currentUser?.id && currentUser?.role != Role.ADMIN)
            throw AccessDeniedException("Shopping list not found for current owner.")
        return shoppingListRepository.findByOwner(ObjectId(ownerId))
    }

    override fun findByRecipeId(recipeId: String, currentUser: User?): List<ShoppingList> {
        val ownerId = currentUser?.id ?: throw UnauthorizedException("User is not authenticated")
        return shoppingListRepository.findByRecipeIdAndOwner(recipeId, ownerId)
    }

    override fun findByStartIngredient(startIngredient: String, currentUser: User?): List<ShoppingList> {
        val ownerId = currentUser?.id ?: throw UnauthorizedException("User is not authenticated")
        return shoppingListRepository.findByStrIngredientContainingIgnoreCaseAndOwner(startIngredient, ownerId)
    }

    override fun createShoppingList(dto: ShoppingListCreateDto, currentUser: User?): ShoppingList {
        dto.owner = currentUser?.id.toString()
        val shoppingList = createShoppingListFields(dto)
        return shoppingListRepository.save(shoppingList)
    }

    override fun updateShoppingList(id: String, dto: ShoppingListUpdateDto, currentUser: User?): ShoppingList {
        val existing = getShoppingListById(id)
        if (existing.owner != currentUser?.id)
            throw AccessDeniedException("Shopping list not found for current user")

        val updated = updateShoppingListFields(existing, dto)
        return shoppingListRepository.save(updated)
    }

    override fun deleteShoppingList(id: String, currentUser: User?) {
        val existing = getShoppingListById(id)
        if (existing.owner != currentUser?.id && currentUser?.role != Role.ADMIN)
            throw AccessDeniedException("Shopping list not found for current user")

        shoppingListRepository.deleteById(id)
    }

    private fun createShoppingListFields(dto: ShoppingListCreateDto): ShoppingList =
        ShoppingList(
            owner = dto.owner,
            strIngredient = dto.strIngredient,
            weight = dto.weight,
            image = dto.image,
            recipeId = dto.recipeId
        )

    private fun updateShoppingListFields(shoppingList: ShoppingList, dto: ShoppingListUpdateDto): ShoppingList =
        shoppingList.copy(
            strIngredient = dto.strIngredient ?: shoppingList.strIngredient,
            weight = dto.weight ?: shoppingList.weight,
            image = dto.image ?: shoppingList.image,
            recipeId = dto.recipeId ?: shoppingList.recipeId
        )
}