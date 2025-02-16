package com.soyummy.recipefavorites

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import jakarta.validation.Valid
import com.soyummy.recipefavorites.dto.RecipeFavoriteCreateDto
import com.soyummy.recipefavorites.dto.RecipeFavoriteUpdateDto
import com.soyummy.constants.Constants.VERSION

@RestController
@RequestMapping("${VERSION}/recipefavorites")
class RecipeFavoriteController(private val recipeFavoriteService: RecipeFavoriteService) {
    // GET http://localhost:8080/api/v1/recipefavorites
    @GetMapping
    fun getAllFavorites(): ResponseEntity<List<RecipeFavorite>> =
        ResponseEntity.ok(recipeFavoriteService.getAllFavorites())

    // GET http://localhost:8080/api/v1/recipefavorites/{id}
    @GetMapping("/{id}")
    fun getFavoriteById(@PathVariable id: String): ResponseEntity<RecipeFavorite> =
        ResponseEntity.ok(recipeFavoriteService.getFavoriteById(id))

    // GET http://localhost:8080/api/v1/recipefavorites/byRecipeId/{recipeId}
    @GetMapping("/byRecipeId/{recipeId}")
    fun getFavoritesByRecipeId(@PathVariable recipeId: String): ResponseEntity<List<RecipeFavorite>> =
        ResponseEntity.ok(recipeFavoriteService.getFavoritesByRecipeId(recipeId))

    // GET http://localhost:8080/api/v1/recipefavorites/byAmount/4
    @GetMapping("/byAmount/{amount}")
    fun getFavoritesByAmount(@PathVariable amount: Int): ResponseEntity<List<RecipeFavorite>> =
        ResponseEntity.ok(recipeFavoriteService.getFavoritesByAmount(amount))

    // GET http://localhost:8080/api/v1/recipefavorites/rating
    @GetMapping("/rating")
    fun getTopFavorites(): ResponseEntity<List<RecipeFavorite>> =
        ResponseEntity.ok(recipeFavoriteService.getFavoritesByRatingDesc())

    // POST http://localhost:8080/api/v1/recipefavorites
    @PostMapping
    fun createFavorite(@Valid @RequestBody createDto: RecipeFavoriteCreateDto): ResponseEntity<RecipeFavorite> =
        ResponseEntity.status(201).body(recipeFavoriteService.createFavorite(createDto))

    // PUT http://localhost:8080/api/v1/recipefavorites/{id}
    @PutMapping("/{id}")
    fun updateFavorite(
        @PathVariable id: String,
        @Valid @RequestBody updateDto: RecipeFavoriteUpdateDto
    ): ResponseEntity<RecipeFavorite> = ResponseEntity.ok(recipeFavoriteService.updateFavorite(id, updateDto))

    // DELETE http://localhost:8080/api/v1/recipefavorites/{id}
    @DeleteMapping("/{id}")
    fun deleteFavorite(@PathVariable id: String): ResponseEntity<Void> {
        recipeFavoriteService.deleteFavorite(id)
        return ResponseEntity.noContent().build()
    }
}