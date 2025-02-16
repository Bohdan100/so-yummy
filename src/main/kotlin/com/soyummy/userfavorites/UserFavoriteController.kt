package com.soyummy.userfavorites

import org.springframework.web.bind.annotation.*
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.access.AccessDeniedException
import org.springframework.http.ResponseEntity
import jakarta.validation.Valid

import com.soyummy.userfavorites.dto.UserFavoriteCreateDto
import com.soyummy.userfavorites.dto.UserFavoriteUpdateDto
import com.soyummy.auth.User
import com.soyummy.errors.UnauthorizedException
import com.soyummy.constants.Constants.VERSION

@RestController
@RequestMapping("${VERSION}/userfavorites")
class UserFavoriteController(private val userFavoriteService: UserFavoriteService) {
    // GET http://localhost:8080/api/v1/userfavorites
    @GetMapping
    fun getAllFavorites(): ResponseEntity<List<UserFavorite>> =
        ResponseEntity.ok(userFavoriteService.getAllFavorites())

    // GET http://localhost:8080/api/v1/userfavorites/{id}
    @GetMapping("/{id}")
    fun getFavoriteById(@PathVariable id: String): ResponseEntity<UserFavorite> =
        ResponseEntity.ok(userFavoriteService.getFavoriteById(id))

    // GET http://localhost:8080/api/v1/userfavorites/byRecipe/{recipeId}
    @GetMapping("/byRecipe/{recipeId}")
    fun getFavoritesByRecipeId(@PathVariable recipeId: String): ResponseEntity<List<UserFavorite>> =
        ResponseEntity.ok(userFavoriteService.getFavoritesByRecipeId(recipeId))

    // GET http://localhost:8080/api/v1/userfavorites/byUser/{userId}
    @GetMapping("/byUser/{userId}")
    fun getFavoritesByUserId(
        @PathVariable userId: String,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<List<UserFavorite>> {
        if (userId != currentUser?.id)
            throw AccessDeniedException("You can only access your own favorites")

        return ResponseEntity.ok(userFavoriteService.getFavoritesByUserId(userId))
    }

    // POST http://localhost:8080/api/v1/userfavorites
    @PostMapping
    fun createFavorite(
        @Valid @RequestBody createDto: UserFavoriteCreateDto,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<UserFavorite> {
        if (currentUser?.id == null) throw UnauthorizedException("User is not authenticated")

        createDto.userId = currentUser.id
        val favorite = userFavoriteService.createFavorite(createDto)
        return ResponseEntity.status(201).body(favorite)
    }

    // PUT http://localhost:8080/api/v1/userfavorites/{id}
    @PutMapping("/{id}")
    fun updateFavorite(
        @PathVariable id: String,
        @Valid @RequestBody updateDto: UserFavoriteUpdateDto,
        @AuthenticationPrincipal currentUser: User?
    ): ResponseEntity<UserFavorite> {
        val existingFavorite = userFavoriteService.getFavoriteById(id)
        if (existingFavorite.userId != currentUser?.id)
            throw AccessDeniedException("Favorite not found for current user")

        return ResponseEntity.ok(userFavoriteService.updateFavorite(id, updateDto))
    }

    // DELETE http://localhost:8080/api/v1/userfavorites/{id}
    @DeleteMapping("/{id}")
    fun deleteFavorite(
        @PathVariable id: String,
        @AuthenticationPrincipal currentUser: User
    ): ResponseEntity<Void> {
        val existingFavorite = userFavoriteService.getFavoriteById(id)
        if (existingFavorite.userId != currentUser.id)
            throw AccessDeniedException("Favorite not found for current user")

        userFavoriteService.deleteFavorite(id)
        return ResponseEntity.noContent().build()
    }
}