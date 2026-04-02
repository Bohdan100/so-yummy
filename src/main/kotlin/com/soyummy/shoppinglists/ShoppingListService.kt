package com.soyummy.shoppinglists

import org.springframework.data.domain.Pageable
import com.soyummy.common.dto.PageResponse
import com.soyummy.shoppinglists.dto.ShoppingListCreateDto
import com.soyummy.shoppinglists.dto.ShoppingListUpdateDto
import com.soyummy.auth.User

interface ShoppingListService {
    fun getAllShoppingLists(pageable: Pageable): PageResponse<ShoppingList>
    fun getShoppingListById(id: String): ShoppingList
    fun getShoppingListById(id: String, currentUser: User?): ShoppingList
    fun findByOwner(ownerId: String, currentUser: User?): List<ShoppingList>
    fun findByRecipeId(recipeId: String, currentUser: User?): List<ShoppingList>
    fun findByStartIngredient(startIngredient: String, currentUser: User?): List<ShoppingList>
    fun createShoppingList(dto: ShoppingListCreateDto, currentUser: User?): ShoppingList
    fun updateShoppingList(id: String, dto: ShoppingListUpdateDto, currentUser: User?): ShoppingList
    fun deleteShoppingList(id: String, currentUser: User?)
}