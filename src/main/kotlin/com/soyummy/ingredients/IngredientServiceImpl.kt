package com.soyummy.ingredients

import org.springframework.stereotype.Service
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.beans.factory.annotation.Lookup

import com.soyummy.common.dto.PageResponse
import com.soyummy.common.aggregation.RecipeAggregationHelper
import com.soyummy.common.tracker.ExecutionTracker
import com.soyummy.common.validator.IngredientValidator
import com.soyummy.ingredients.dto.IngredientUsageStat
import com.soyummy.ingredients.dto.IngredientCreateDto
import com.soyummy.ingredients.dto.IngredientUpdateDto
import com.soyummy.ingredients.util.IngredientMapper
import com.soyummy.exception.types.ResourceNotFoundException

@Service
open class IngredientServiceImpl(
    private val ingredientRepository: IngredientRepository,
    private val aggregationHelper: RecipeAggregationHelper,
    private val ingredientMapper: IngredientMapper
) : IngredientService {

    @Lookup
    open fun getTracker(): ExecutionTracker = throw UnsupportedOperationException()

    @Lookup
    open fun getValidator(): IngredientValidator = throw UnsupportedOperationException()

    override fun getAllIngredients(pageable: Pageable): PageResponse<Ingredient> {
        val page = ingredientRepository.findAll(pageable)

        return PageResponse.from(page)
    }

    override fun getIngredientById(id: String): Ingredient {
        val result = ingredientRepository.findById(id)
            .orElseThrow { ResourceNotFoundException("Ingredient not found with id: $id") }

        return result
    }

    override fun getTopIngredientsByUsage(pageable: Pageable): PageResponse<IngredientUsageStat> {
        val (results, totalElements) = aggregationHelper.getTopIngredientsByUsage(pageable)

        val page = PageImpl(
            results,
            pageable,
            totalElements
        )

        return PageResponse.from(page)
    }

    override fun searchIngredients(title: String?, pageable: Pageable): PageResponse<Ingredient> {
        if (title == null) {
            return PageResponse.from(PageImpl(emptyList(), pageable, 0))
        }
        val page = ingredientRepository.findByTitleContainingIgnoreCase(title, pageable)

        return PageResponse.from(page)
    }

    override fun createIngredient(dto: IngredientCreateDto): Ingredient {
        val tracker = getTracker()
        tracker.logStep("Creating ingredient: ${dto.title}")

        val validator = getValidator()
        if (!validator.validate(dto).isValid()) {
            tracker.logStep("Validation failed for ingredient: ${dto.title}")
            throw IllegalArgumentException("Validation failed: ${validator.getErrors()}")
        }

        val ingredient = ingredientMapper.buildNewIngredient(dto)
        val result = ingredientRepository.save(ingredient)

        tracker.finish("createIngredient")
        return result
    }

    override fun updateIngredient(id: String, dto: IngredientUpdateDto): Ingredient {
        val tracker = getTracker()
        tracker.logStep("Updating ingredient ID: $id")

        val existing = getIngredientById(id)
        val updated = ingredientMapper.updateCurrentIngredient(dto, existing)
        val result = ingredientRepository.save(updated)

        tracker.finish("updateIngredient")
        return result
    }

    override fun deleteIngredient(id: String) {
        val tracker = getTracker()
        tracker.logStep("Deleting ingredient ID: $id")

        if (!ingredientRepository.existsById(id))
            throw ResourceNotFoundException("Ingredient not found with id: $id")
        ingredientRepository.deleteById(id)

        tracker.finish("deleteIngredient")
    }
}