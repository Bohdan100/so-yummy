package com.soyummy.ingredients

import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Repository
interface IngredientRepository : MongoRepository<Ingredient, String> {
    fun findByTitleContainingIgnoreCase(title: String, pageable: Pageable): Page<Ingredient>
}