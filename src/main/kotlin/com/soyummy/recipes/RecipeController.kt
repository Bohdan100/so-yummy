package com.soyummy.recipes

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import jakarta.validation.Valid

import com.soyummy.common.dto.PageResponse
import com.soyummy.recipes.dto.RecipeCreateDto
import com.soyummy.recipes.dto.RecipeUpdateDto
import com.soyummy.constants.Constants.VERSION

@RestController
@RequestMapping("${VERSION}/recipes")
class RecipeController(private val recipeService: RecipeService) {

    // GET http://localhost:8080/api/v1/recipes?page=3&size=4
    @GetMapping
    fun getAllRecipes(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "popularity,desc") sort: String
    ): ResponseEntity<PageResponse<Recipe>> {
        val sortParams = sort.split(",")
        val sortDirection = if (sortParams.size > 1 && sortParams[1].lowercase() == "desc") {
            Sort.Direction.DESC
        } else {
            Sort.Direction.ASC
        }
        val sortField = sortParams[0]

        val pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField))
        val result = recipeService.getAllRecipes(pageable)

        return ResponseEntity.ok(result)
    }

    // GET http://localhost:8080/api/v1/recipes/{id}
    @GetMapping("/{id}")
    fun getRecipeById(@PathVariable id: String): ResponseEntity<Recipe> =
        ResponseEntity.ok(recipeService.getRecipeById(id))


    // GET http://localhost:8080/api/v1/recipes/top?page=3&size=4
    @GetMapping("/top")
    fun getTopRecipes(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PageResponse<Recipe>> {
        val pageable = PageRequest.of(page, size)
        val result = recipeService.getTopRecipesByPopularity(pageable)
        return ResponseEntity.ok(result)
    }

    // GET http://localhost:8080/api/v1/recipes/search?title=pas&page=0&size=3
    @GetMapping("/search")
    fun searchRecipesByCategory(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) area: String?,
        @RequestParam(required = false) tag: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "popularity,desc") sort: String
    ): ResponseEntity<PageResponse<Recipe>> {
        val sortParams = sort.split(",")
        val sortDirection = if (sortParams.size > 1 && sortParams[1].lowercase() == "desc") {
            Sort.Direction.DESC
        } else {
            Sort.Direction.ASC
        }
        val sortField = sortParams[0]

        val pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField))
        val result = recipeService.searchRecipes(title, category, area, tag, pageable)

        return ResponseEntity.ok(result)
    }

    // POST http://localhost:8080/api/v1/recipes
    @PostMapping
    fun createRecipe(@Valid @RequestBody recipeCreateDto: RecipeCreateDto): ResponseEntity<Recipe> =
        ResponseEntity.status(201).body(recipeService.createRecipe(recipeCreateDto))

    // PUT http://localhost:8080/api/v1/recipes/{id}
    @PutMapping("/{id}")
    fun updateRecipe(
        @PathVariable id: String,
        @Valid @RequestBody recipeUpdateDto: RecipeUpdateDto
    ): ResponseEntity<Recipe> =
        ResponseEntity.ok(recipeService.updateRecipe(id, recipeUpdateDto))

    // DELETE http://localhost:8080/api/v1/recipes/{id}
    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: String): ResponseEntity<Void> {
        recipeService.deleteRecipe(id)
        return ResponseEntity.noContent().build()
    }
}