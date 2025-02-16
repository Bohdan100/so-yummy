package com.soyummy.ingredients

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import jakarta.validation.Valid
import com.soyummy.ingredients.dto.IngredientCreateDto
import com.soyummy.ingredients.dto.IngredientUpdateDto
import com.soyummy.constants.Constants.VERSION

@RestController
@RequestMapping("${VERSION}/ingredients")
class IngredientController(private val ingredientService: IngredientService) {
    //  GET  http://localhost:8080/api/v1/ingredients
    @GetMapping
    fun getAllIngredients(): ResponseEntity<List<Ingredient>> =
        ResponseEntity.ok(ingredientService.getAllIngredients())

    //  GET  http://localhost:8080/api/v1/ingredients/{id}
    @GetMapping("/{id}")
    fun getIngredientById(@PathVariable id: String): ResponseEntity<Ingredient> =
        ResponseEntity.ok(ingredientService.getIngredientById(id))

    //  GET  http://localhost:8080/api/v1/ingredients/search?title=beef
    @GetMapping("/search")
    fun searchIngredientsByTitle(@RequestParam title: String): ResponseEntity<List<Ingredient>> =
        ResponseEntity.ok(ingredientService.searchIngredientsByTitle(title))


    //  POST  http://localhost:8080/api/v1/ingredients
    @PostMapping
    fun createIngredient(@Valid @RequestBody ingredientCreateDto: IngredientCreateDto): ResponseEntity<Ingredient> =
        ResponseEntity.status(201).body(ingredientService.createIngredient(ingredientCreateDto))

    //  PUT  http://localhost:8080/api/v1/ingredients/{id}
    @PutMapping("/{id}")
    fun updateIngredient(
        @PathVariable id: String,
        @Valid @RequestBody ingredientUpdateDto: IngredientUpdateDto
    ): ResponseEntity<Ingredient> = ResponseEntity.ok(ingredientService.updateIngredient(id, ingredientUpdateDto))

    //  DELETE  http://localhost:8080/api/v1/ingredients/{id}
    @DeleteMapping("/{id}")
    fun deleteIngredient(@PathVariable id: String): ResponseEntity<Void> {
        ingredientService.deleteIngredient(id)
        return ResponseEntity.noContent().build()
    }
}