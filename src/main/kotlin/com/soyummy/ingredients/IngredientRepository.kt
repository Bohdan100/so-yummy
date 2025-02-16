package com.soyummy.ingredients

import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.repository.MongoRepository

@Repository
interface IngredientRepository : MongoRepository<Ingredient, String> {
    fun findByTitleContainingIgnoreCase(title: String): List<Ingredient>
}