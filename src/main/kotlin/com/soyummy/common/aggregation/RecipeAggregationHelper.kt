package com.soyummy.common.aggregation

import org.springframework.stereotype.Component
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.aggregation.AggregationResults
import org.bson.Document

import com.soyummy.recipes.Recipe
import com.soyummy.ingredients.dto.IngredientUsageStat
import com.soyummy.recipefavorites.dto.TopFavoriteDto

@Component
class RecipeAggregationHelper(
    private val mongoTemplate: MongoTemplate
) {

    fun getTopRecipesByPopularity(pageable: Pageable): Pair<List<Recipe>, Long> {
        val aggregation = buildTopRecipesAggregation(pageable)
        val results = executeTopRecipesAggregation(aggregation)
        val totalElements = countTopRecipesTotal()
        return Pair(results.mappedResults, totalElements)
    }

    fun getTopIngredientsByUsage(pageable: Pageable): Pair<List<IngredientUsageStat>, Long> {
        val aggregation = buildTopIngredientsAggregation(pageable)
        val results = executeTopIngredientsAggregation(aggregation)
        val totalElements = countTopIngredientsTotal()
        return Pair(results.mappedResults, totalElements)
    }

    fun getTopFavoritesByRating(): List<TopFavoriteDto> {
        val aggregation = buildTopFavoritesAggregation()
        val results = executeTopFavoritesAggregation(aggregation)
        return results.mappedResults
    }

    private fun buildTopRecipesAggregation(pageable: Pageable): Aggregation {
        val skipStage = Aggregation.skip((pageable.pageNumber * pageable.pageSize).toLong())
        val limitStage = Aggregation.limit(pageable.pageSize.toLong())
        val sortStage = Aggregation.sort(Sort.Direction.DESC, "popularity")

        return Aggregation.newAggregation(
            sortStage,
            skipStage,
            limitStage
        )
    }

    private fun executeTopRecipesAggregation(aggregation: Aggregation): AggregationResults<Recipe> {
        return mongoTemplate.aggregate(
            aggregation,
            "recipes",
            Recipe::class.java
        )
    }

    private fun countTopRecipesTotal(): Long {
        val countAggregation = Aggregation.newAggregation(
            Aggregation.group().count().`as`("total")
        )

        val countResult = mongoTemplate.aggregate(
            countAggregation,
            "recipes",
            Document::class.java
        )

        return countResult.uniqueMappedResult?.get("total") as? Long ?: 0L
    }

    private fun buildTopIngredientsAggregation(pageable: Pageable): Aggregation {
        val unwindStage = Aggregation.unwind("ingredients")
        val groupStage = Aggregation.group("\$ingredients.id")
            .count().`as`("usageCount")
            .first("\$ingredients.id").`as`("ingredientId")
        val sortStage = Aggregation.sort(Sort.Direction.DESC, "usageCount")
        val skipStage = Aggregation.skip((pageable.pageNumber * pageable.pageSize).toLong())
        val limitStage = Aggregation.limit(pageable.pageSize.toLong())
        val lookupStage = Aggregation.lookup("ingredients", "ingredientId", "_id", "ingredientDetails")
        val unwindLookupStage = Aggregation.unwind("ingredientDetails", false)
        val projectStage = Aggregation.project()
            .and("ingredientId").`as`("ingredientId")
            .and("ingredientDetails.ttl").`as`("ingredientTitle")
            .and("ingredientDetails.thb").`as`("thumbnail")
            .and("usageCount").`as`("usageCount")

        return Aggregation.newAggregation(
            unwindStage,
            groupStage,
            sortStage,
            skipStage,
            limitStage,
            lookupStage,
            unwindLookupStage,
            projectStage
        )
    }

    private fun executeTopIngredientsAggregation(aggregation: Aggregation): AggregationResults<IngredientUsageStat> {
        return mongoTemplate.aggregate(
            aggregation,
            "recipes",
            IngredientUsageStat::class.java
        )
    }

    private fun countTopIngredientsTotal(): Long {
        val unwindStage = Aggregation.unwind("ingredients")
        val groupStage = Aggregation.group("\$ingredients.id")
            .count().`as`("usageCount")
            .first("\$ingredients.id").`as`("ingredientId")
        val countAggregation = Aggregation.newAggregation(
            unwindStage,
            groupStage,
            Aggregation.group().count().`as`("total")
        )

        val countResult = mongoTemplate.aggregate(
            countAggregation,
            "recipes",
            Document::class.java
        )

        return countResult.uniqueMappedResult?.get("total") as? Long ?: 0L
    }

    private fun buildTopFavoritesAggregation(): Aggregation {
        val groupStage = Aggregation.group("recipe")
            .sum("amount").`as`("amount")

        val sortStage = Aggregation.sort(Sort.Direction.DESC, "amount")
        val limitStage = Aggregation.limit(10)

        val projectStage = Aggregation.project()
            .and("_id").`as`("recipeId")
            .and("amount").`as`("amount")
            .andExclude("_id")

        return Aggregation.newAggregation(
            groupStage,
            sortStage,
            limitStage,
            projectStage
        )
    }

    private fun executeTopFavoritesAggregation(aggregation: Aggregation): AggregationResults<TopFavoriteDto> {
        return mongoTemplate.aggregate(
            aggregation,
            "recipefavorites",
            TopFavoriteDto::class.java
        )
    }
}