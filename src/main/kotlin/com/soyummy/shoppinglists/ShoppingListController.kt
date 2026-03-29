package com.soyummy.shoppinglists

import org.springframework.web.bind.annotation.*
import org.springframework.security.access.annotation.Secured
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.http.ResponseEntity
import jakarta.validation.Valid

import com.soyummy.shoppinglists.dto.ShoppingListCreateDto
import com.soyummy.shoppinglists.dto.ShoppingListUpdateDto
import com.soyummy.auth.User
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
    ): ResponseEntity<ShoppingList> =
        ResponseEntity.ok(shoppingListService.getShoppingListById(id, currentUser))

    //  GET  http://localhost:8080/api/v1/shoppinglists/byOwner/{ownerId}
    @GetMapping("/byOwner/{ownerId}")
    fun getShoppingListsByOwnerId(
        @PathVariable ownerId: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<List<ShoppingList>> =
        ResponseEntity.ok(shoppingListService.findByOwner(ownerId, currentUser))

    //  GET  http://localhost:8080/api/v1/shoppinglists/byRecipeId/{recipeId}
    @GetMapping("/byRecipeId/{recipeId}")
    fun getShoppingListsByRecipeId(
        @PathVariable recipeId: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<List<ShoppingList>> =
        ResponseEntity.ok(shoppingListService.findByRecipeId(recipeId, currentUser))

    //  GET  http://localhost:8080/api/v1/shoppinglists/byStartIngredient/{startIngredient}
    @GetMapping("/byStartIngredient/{startIngredient}")
    fun getShoppingListsByStartIngredient(
        @PathVariable startIngredient: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<List<ShoppingList>> =
        ResponseEntity.ok(shoppingListService.findByStartIngredient(startIngredient, currentUser))

    //  POST  http://localhost:8080/api/v1/shoppinglists
    @PostMapping
    fun createShoppingList(
        @Valid @RequestBody shoppingListCreateDto: ShoppingListCreateDto,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<ShoppingList> =
        ResponseEntity.status(201).body(shoppingListService.createShoppingList(shoppingListCreateDto, currentUser))

    //  PUT  http://localhost:8080/api/v1/shoppinglists/{id}
    @PutMapping("/{id}")
    fun updateShoppingList(
        @PathVariable id: String,
        @Valid @RequestBody shoppingListUpdateDto: ShoppingListUpdateDto,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<ShoppingList> =
        ResponseEntity.ok(shoppingListService.updateShoppingList(id, shoppingListUpdateDto, currentUser))

    //  DELETE  http://localhost:8080/api/v1/shoppinglists/{id}
    @DeleteMapping("/{id}")
    fun deleteShoppingList(
        @PathVariable id: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<Void> {
        shoppingListService.deleteShoppingList(id, currentUser)
        return ResponseEntity.noContent().build()
    }
}