package com.soyummy.recipes

import org.springframework.stereotype.Service
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.beans.factory.annotation.Lookup

import com.soyummy.common.dto.PageResponse
import com.soyummy.common.aggregation.RecipeAggregationHelper
import com.soyummy.common.tracker.ExecutionTracker
import com.soyummy.common.validator.RecipeValidator
import com.soyummy.recipes.dto.RecipeCreateDto
import com.soyummy.recipes.dto.RecipeUpdateDto
import com.soyummy.recipes.util.RecipeMapper
import com.soyummy.exception.types.ResourceNotFoundException

@Service
class RecipeServiceImpl(
    private val recipeRepository: RecipeRepository,
    private val aggregationHelper: RecipeAggregationHelper,
    private val recipeMapper: RecipeMapper
) : RecipeService {

    @Lookup
    open fun getTracker(): ExecutionTracker = throw UnsupportedOperationException()

    @Lookup
    open fun getValidator(): RecipeValidator = throw UnsupportedOperationException()

    override fun getAllRecipes(pageable: Pageable): PageResponse<Recipe> {
        val page = recipeRepository.findAll(pageable)

        return PageResponse.from(page)
    }

    override fun getRecipeById(id: String): Recipe {
        val result = recipeRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Recipe not found with id: $id") }

        return result
    }

    override fun getTopRecipesByPopularity(pageable: Pageable): PageResponse<Recipe> {
        val (results, totalElements) = aggregationHelper.getTopRecipesByPopularity(pageable)

        val page = PageImpl(
            results,
            pageable,
            totalElements
        )

        return PageResponse.from(page)
    }

    override fun searchRecipes(
        title: String?,
        category: String?,
        area: String?,
        tag: String?,
        pageable: Pageable
    ): PageResponse<Recipe> {

        val page = when {
            title != null -> recipeRepository.findByTitleContainingIgnoreCase(title, pageable)
            category != null -> recipeRepository.findByCategoryContainingIgnoreCase(category, pageable)
            area != null -> recipeRepository.findByAreaContainingIgnoreCase(area, pageable)
            tag != null -> recipeRepository.findByTagsContainingIgnoreCase(tag, pageable)
            else -> return PageResponse.from(PageImpl(emptyList(), pageable, 0))
        }

        return PageResponse.from(page)
    }

    override fun createRecipe(dto: RecipeCreateDto): Recipe {
        val tracker = getTracker()
        tracker.logStep("Starting recipe creation: ${dto.title}")

        val validator = getValidator()
        if (!validator.validate(dto).isValid()) {
            tracker.logStep("Validation failed for recipe: ${dto.title}")
            throw IllegalArgumentException("Validation failed: ${validator.getErrors()}")
        }

        val recipe = recipeMapper.buildNewRecipe(dto)
        val savedRecipe = recipeRepository.save(recipe)

        tracker.finish("createRecipe")
        return savedRecipe
    }

    override fun updateRecipe(id: String, dto: RecipeUpdateDto): Recipe {
        val tracker = getTracker()
        tracker.logStep("Updating recipe ID: $id")

        val existingRecipe = getRecipeById(id)
        val updatedRecipe = recipeMapper.updateCurrentRecipe(dto, existingRecipe)
        val result = recipeRepository.save(updatedRecipe)

        tracker.finish("updateRecipe")
        return result
    }

    override fun deleteRecipe(id: String) {
        val tracker = getTracker()
        tracker.logStep("Deleting recipe ID: $id")

        if (!recipeRepository.existsById(id))
            throw ResourceNotFoundException("Recipe not found with id: $id")
        recipeRepository.deleteById(id)

        tracker.finish("deleteRecipe")
    }
}