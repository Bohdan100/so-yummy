package com.soyummy.recipefavorites

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import jakarta.validation.Valid

import com.soyummy.common.dto.PageResponse
import com.soyummy.recipefavorites.dto.RecipeFavoriteCreateDto
import com.soyummy.recipefavorites.dto.RecipeFavoriteUpdateDto
import com.soyummy.recipefavorites.dto.TopFavoriteDto
import com.soyummy.constants.Constants.VERSION

@RestController
@RequestMapping("${VERSION}/recipefavorites")
class RecipeFavoriteController(private val recipeFavoriteService: RecipeFavoriteService) {
    // GET http://localhost:8080/api/v1/recipefavorites?page=0&size=10
    @GetMapping
    fun getAllFavorites(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "amount,desc") sort: String
    ): ResponseEntity<PageResponse<RecipeFavorite>> {
        val sortParams = sort.split(",")
        val sortDirection = if (sortParams.size > 1 && sortParams[1].lowercase() == "desc") {
            Sort.Direction.DESC
        } else {
            Sort.Direction.ASC
        }
        val sortField = sortParams[0]

        val pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField))
        val result = recipeFavoriteService.getAllFavorites(pageable)

        return ResponseEntity.ok(result)
    }

    // GET http://localhost:8080/api/v1/recipefavorites/{id}
    @GetMapping("/{id}")
    fun getFavoriteById(@PathVariable id: String): ResponseEntity<RecipeFavorite> =
        ResponseEntity.ok(recipeFavoriteService.getFavoriteById(id))

    // GET http://localhost:8080/api/v1/recipefavorites/rating
    @GetMapping("/rating")
    fun getTopFavorites(): ResponseEntity<List<TopFavoriteDto>> =
        ResponseEntity.ok(recipeFavoriteService.getFavoritesByRatingDesc())

    // GET http://localhost:8080/api/v1/recipefavorites/byRecipeId/{recipeId}
    @GetMapping("/byRecipeId/{recipeId}")
    fun getFavoritesByRecipeId(@PathVariable recipeId: String): ResponseEntity<List<RecipeFavorite>> =
        ResponseEntity.ok(recipeFavoriteService.getFavoritesByRecipeId(recipeId))

    // GET http://localhost:8080/api/v1/recipefavorites/byAmount/4
    @GetMapping("/byAmount/{amount}")
    fun getFavoritesByAmount(@PathVariable amount: Int): ResponseEntity<List<RecipeFavorite>> =
        ResponseEntity.ok(recipeFavoriteService.getFavoritesByAmount(amount))

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