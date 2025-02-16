package com.soyummy.recipes

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import jakarta.validation.Valid
import com.soyummy.recipes.dto.RecipeCreateDto
import com.soyummy.recipes.dto.RecipeUpdateDto
import com.soyummy.constants.Constants.VERSION

@RestController
@RequestMapping("${VERSION}/recipes")
class RecipeController(private val recipeService: RecipeService) {
    // GET http://localhost:8080/api/v1/recipes
    @GetMapping
    fun getAllRecipes(): ResponseEntity<List<Recipe>> =
        ResponseEntity.ok(recipeService.getAllRecipes())

    // GET http://localhost:8080/api/v1/recipes/{id}
    @GetMapping("/{id}")
    fun getRecipeById(@PathVariable id: String): ResponseEntity<Recipe> =
        ResponseEntity.ok(recipeService.getRecipeById(id))

    // GET http://localhost:8080/api/v1/recipes/search?title=pasta
    @GetMapping("/search")
    fun searchRecipesByCategory(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) category: String?,
        @RequestParam(required = false) area: String?,
        @RequestParam(required = false) tag: String?
    ): ResponseEntity<List<Recipe>> {
        val recipes = when {
            title != null -> recipeService.findByTitle(title)
            category != null -> recipeService.findByCategory(category)
            area != null -> recipeService.findByArea(area)
            tag != null -> recipeService.findByTags(tag)
            else -> emptyList()
        }
        return ResponseEntity.ok(recipes)
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
    ): ResponseEntity<Recipe> = ResponseEntity.ok(recipeService.updateRecipe(id, recipeUpdateDto))

    // DELETE http://localhost:8080/api/v1/recipes/{id}
    @DeleteMapping("/{id}")
    fun deleteRecipe(@PathVariable id: String): ResponseEntity<Void> {
        recipeService.deleteRecipe(id)
        return ResponseEntity.noContent().build()
    }
}