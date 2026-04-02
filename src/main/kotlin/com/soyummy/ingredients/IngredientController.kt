package com.soyummy.ingredients

import org.springframework.web.bind.annotation.*
import org.springframework.http.ResponseEntity
import jakarta.validation.Valid
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort

import com.soyummy.common.dto.PageResponse
import com.soyummy.ingredients.dto.IngredientUsageStat
import com.soyummy.ingredients.dto.IngredientCreateDto
import com.soyummy.ingredients.dto.IngredientUpdateDto
import com.soyummy.constants.Constants.VERSION

@RestController
@RequestMapping("${VERSION}/ingredients")
class IngredientController(private val ingredientService: IngredientService) {

    //  GET  http://localhost:8080/api/v1/ingredients?page=3&size=4
    @GetMapping
    fun getAllIngredients(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "title,asc") sort: String
    ): ResponseEntity<PageResponse<Ingredient>> {
        val sortParams = sort.split(",")
        val sortDirection = if (sortParams.size > 1 && sortParams[1].lowercase() == "desc") {
            Sort.Direction.DESC
        } else {
            Sort.Direction.ASC
        }
        val sortField = sortParams[0]

        val pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField))
        val result = ingredientService.getAllIngredients(pageable)

        return ResponseEntity.ok(result)
    }

    //  GET  http://localhost:8080/api/v1/ingredients/{id}
    @GetMapping("/{id}")
    fun getIngredientById(@PathVariable id: String): ResponseEntity<Ingredient> =
        ResponseEntity.ok(ingredientService.getIngredientById(id))

    //  GET  http://localhost:8080/api/v1/ingredients/top?page=3&size=4
    @GetMapping("/top")
    fun getTopIngredients(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<PageResponse<IngredientUsageStat>> {
        val pageable = PageRequest.of(page, size)
        val result = ingredientService.getTopIngredientsByUsage(pageable)
        return ResponseEntity.ok(result)
    }

    //  GET  http://localhost:8080/api/v1/ingredients/search?title=beef&page=0&size=3
    @GetMapping("/search")
    fun searchIngredients(
        @RequestParam(required = false) title: String?,
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int,
        @RequestParam(defaultValue = "title,asc") sort: String
    ): ResponseEntity<PageResponse<Ingredient>> {
        val sortParams = sort.split(",")
        val sortDirection = if (sortParams.size > 1 && sortParams[1].lowercase() == "desc") {
            Sort.Direction.DESC
        } else {
            Sort.Direction.ASC
        }
        val sortField = sortParams[0]

        val pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortField))
        val result = ingredientService.searchIngredients(title, pageable)

        return ResponseEntity.ok(result)
    }

    //  POST  http://localhost:8080/api/v1/ingredients
    @PostMapping
    fun createIngredient(@Valid @RequestBody dto: IngredientCreateDto): ResponseEntity<Ingredient> =
        ResponseEntity.status(201).body(ingredientService.createIngredient(dto))

    //  PUT  http://localhost:8080/api/v1/ingredients/{id}
    @PutMapping("/{id}")
    fun updateIngredient(
        @PathVariable id: String,
        @Valid @RequestBody dto: IngredientUpdateDto
    ): ResponseEntity<Ingredient> =
        ResponseEntity.ok(ingredientService.updateIngredient(id, dto))

    //  DELETE  http://localhost:8080/api/v1/ingredients/{id}
    @DeleteMapping("/{id}")
    fun deleteIngredient(@PathVariable id: String): ResponseEntity<Void> {
        ingredientService.deleteIngredient(id)
        return ResponseEntity.noContent().build()
    }
}