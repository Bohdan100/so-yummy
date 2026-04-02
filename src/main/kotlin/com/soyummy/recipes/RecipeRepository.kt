package com.soyummy.recipes

import org.springframework.stereotype.Repository
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

@Repository
interface RecipeRepository : MongoRepository<Recipe, String> {
    fun findByTitleContainingIgnoreCase(title: String, pageable: Pageable): Page<Recipe>
    fun findByCategoryContainingIgnoreCase(category: String, pageable: Pageable): Page<Recipe>
    fun findByAreaContainingIgnoreCase(area: String, pageable: Pageable): Page<Recipe>
    fun findByTagsContainingIgnoreCase(tag: String, pageable: Pageable): Page<Recipe>
}