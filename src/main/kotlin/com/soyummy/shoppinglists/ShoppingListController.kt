package com.soyummy.shoppinglists

import org.springframework.web.bind.annotation.*
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.access.AccessDeniedException
import org.springframework.http.ResponseEntity
import jakarta.validation.Valid

import com.soyummy.shoppinglists.dto.ShoppingListCreateDto
import com.soyummy.shoppinglists.dto.ShoppingListUpdateDto
import com.soyummy.auth.User
import com.soyummy.auth.Role
import com.soyummy.errors.UnauthorizedException
import com.soyummy.constants.Constants.VERSION

@RestController
@RequestMapping("${VERSION}/shoppinglists")
class ShoppingListController(private val shoppingListService: ShoppingListService) {
    //  GET  http://localhost:8080/api/v1/shoppinglists
    @Secured("ROLE_ADMIN")
    @GetMapping
    fun getAllShoppingLists(): ResponseEntity<List<ShoppingList>> =
        ResponseEntity.ok(shoppingListService.getAllShoppingLists())

    //  GET  http://localhost:8080/api/v1/shoppinglists/{id}
    @GetMapping("/{id}")
    fun getShoppingListByListId(
        @PathVariable id: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<ShoppingList> {
        val shoppingList = shoppingListService.getShoppingListById(id)
        if (shoppingList.owner != currentUser?.id && currentUser?.role != Role.ADMIN)
            throw AccessDeniedException("Shopping list not found for current user")

        return ResponseEntity.ok(shoppingList)
    }

    //  GET  http://localhost:8080/api/v1/shoppinglists/byOwner/{ownerId}
    @GetMapping("/byOwner/{ownerId}")
    fun getShoppingListsByOwnerId(
        @PathVariable ownerId: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<List<ShoppingList>> {
        if (ownerId != currentUser?.id && currentUser?.role != Role.ADMIN)
            throw AccessDeniedException("Shopping list not found for current owner.")

        return ResponseEntity.ok(shoppingListService.findByOwner(ownerId))
    }

    //  GET  http://localhost:8080/api/v1/shoppinglists/byRecipeId/{recipeId}
    @GetMapping("/byRecipeId/{recipeId}")
    fun getShoppingListsByRecipeId(
        @PathVariable recipeId: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<List<ShoppingList>> {
        if (currentUser?.id == null) throw UnauthorizedException("User is not authenticated")
        return ResponseEntity.ok(shoppingListService.findByRecipeId(recipeId, currentUser.id))
    }

    //  GET  http://localhost:8080/api/v1/shoppinglists/byStartIngredient/{startIngredient}
    @GetMapping("/byStartIngredient/{startIngredient}")
    fun getShoppingListsByStartIngredient(
        @PathVariable startIngredient: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<List<ShoppingList>> {
        if (currentUser?.id == null) throw UnauthorizedException("User is not authenticated")
        return ResponseEntity.ok(shoppingListService.findByStartIngredient(startIngredient, currentUser.id))
    }

    //  POST  http://localhost:8080/api/v1/shoppinglists
    @PostMapping
    fun createShoppingList(
        @Valid @RequestBody shoppingListCreateDto: ShoppingListCreateDto,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<ShoppingList> {
        shoppingListCreateDto.owner = currentUser?.id.toString()
        val createdShoppingList = shoppingListService.createShoppingList(shoppingListCreateDto)

        return ResponseEntity.status(201).body(createdShoppingList)
    }

    //  PUT  http://localhost:8080/api/v1/shoppinglists/{id}
    @PutMapping("/{id}")
    fun updateShoppingList(
        @PathVariable id: String,
        @Valid @RequestBody shoppingListUpdateDto: ShoppingListUpdateDto,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<ShoppingList> {
        val existingShoppingList = shoppingListService.getShoppingListById(id)
        if (existingShoppingList.owner != currentUser?.id)
            throw AccessDeniedException("Shopping list not found for current user")

        val updatedShoppingList = shoppingListService.updateShoppingList(id, shoppingListUpdateDto)
        return ResponseEntity.ok(updatedShoppingList)
    }

    //  DELETE  http://localhost:8080/api/v1/shoppinglists/{id}
    @DeleteMapping("/{id}")
    fun deleteShoppingList(
        @PathVariable id: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Void> {
        val existingShoppingList = shoppingListService.getShoppingListById(id)
        if (existingShoppingList.owner != currentUser?.id && currentUser?.role != Role.ADMIN)
            throw AccessDeniedException("Shopping list not found for current user")

        shoppingListService.deleteShoppingList(id)
        return ResponseEntity.noContent().build()
    }
}