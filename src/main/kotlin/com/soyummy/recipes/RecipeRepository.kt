package com.soyummy.recipes

import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.repository.MongoRepository

@Repository
interface RecipeRepository : MongoRepository<Recipe, String> {
    fun findByTitleContainingIgnoreCase(title: String): List<Recipe>
    fun findByCategoryContainingIgnoreCase(category: String): List<Recipe>
    fun findByAreaContainingIgnoreCase(area: String): List<Recipe>
    fun findByTagsContainingIgnoreCase(tag: String): List<Recipe>
}