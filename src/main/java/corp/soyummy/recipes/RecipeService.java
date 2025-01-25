package corp.soyummy.recipes;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import corp.soyummy.recipes.dto.RecipeCreateDto;
import corp.soyummy.recipes.dto.RecipeUpdateDto;
import corp.soyummy.errors.ResourceNotFoundException;

import java.util.List;
import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RecipeService {

    private final RecipeRepository recipeRepository;

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public Recipe getRecipeById(String id) {
        return recipeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Recipe not found with id: " + id));
    }

    public List<Recipe> findByTitle(String title) {
        return new ArrayList<>(recipeRepository.findByTitleContainingIgnoreCase(title));
    }

    public List<Recipe> findByCategory(String category) {
        return recipeRepository.findByCategoryContainingIgnoreCase(category);
    }

    public List<Recipe> findByArea(String area) {
        return recipeRepository.findByAreaContainingIgnoreCase(area);
    }

    public List<Recipe> findByTags(String tag) {
        return recipeRepository.findByTagsContainingIgnoreCase(tag);
    }

    public Recipe createRecipe(RecipeCreateDto recipeCreateDto) {
        Recipe recipe = buildNewRecipe(recipeCreateDto);
        return recipeRepository.save(recipe);
    }

    public Recipe updateRecipe(String id, RecipeUpdateDto recipeUpdateDto) {
        Recipe existingRecipe = getRecipeById(id);
        if (existingRecipe == null) {
            throw new ResourceNotFoundException("Recipe not found with id: " + id);
        }

        Recipe updatedRecipe = updateCurrentRecipe(recipeUpdateDto, existingRecipe);
        return recipeRepository.save(updatedRecipe);
    }

    public void deleteRecipe(String id) {
        if (!recipeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recipe not found with id: " + id);
        }
        recipeRepository.deleteById(id);
    }

    private Recipe buildNewRecipe(RecipeCreateDto recipeCreateDto) {
        return Recipe.builder()
                .title(recipeCreateDto.getTitle())
                .category(recipeCreateDto.getCategory())
                .area(recipeCreateDto.getArea())
                .instructions(recipeCreateDto.getInstructions())
                .description(recipeCreateDto.getDescription())
                .thumbnail(recipeCreateDto.getThumbnail())
                .preview(recipeCreateDto.getPreview())
                .time(recipeCreateDto.getTime())
                .popularity(0)
                .tags(recipeCreateDto.getTags())
                .ingredients(recipeCreateDto.getIngredients())
                .youtube(recipeCreateDto.getYoutube())
                .favorites(new ArrayList<>())
                .likes(new ArrayList<>())
                .build();
    }

    private Recipe updateCurrentRecipe(RecipeUpdateDto recipeUpdateDto, Recipe existingRecipe) {
        return Recipe.builder()
                .id(existingRecipe.getId())
                .title(recipeUpdateDto.getTitle() != null ? recipeUpdateDto.getTitle() : existingRecipe.getTitle())
                .category(recipeUpdateDto.getCategory() != null ? recipeUpdateDto.getCategory() : existingRecipe.getCategory())
                .area(recipeUpdateDto.getArea() != null ? recipeUpdateDto.getArea() : existingRecipe.getArea())
                .instructions(recipeUpdateDto.getInstructions() != null ? recipeUpdateDto.getInstructions() : existingRecipe.getInstructions())
                .description(recipeUpdateDto.getDescription() != null ? recipeUpdateDto.getDescription() : existingRecipe.getDescription())
                .thumbnail(recipeUpdateDto.getThumbnail() != null ? recipeUpdateDto.getThumbnail() : existingRecipe.getThumbnail())
                .preview(recipeUpdateDto.getPreview() != null ? recipeUpdateDto.getPreview() : existingRecipe.getPreview())
                .time(recipeUpdateDto.getTime() != null ? recipeUpdateDto.getTime() : existingRecipe.getTime())
                .tags(recipeUpdateDto.getTags() != null ? recipeUpdateDto.getTags() : existingRecipe.getTags())
                .ingredients(recipeUpdateDto.getIngredients() != null ? recipeUpdateDto.getIngredients() : existingRecipe.getIngredients())
                .youtube(recipeUpdateDto.getYoutube() != null ? recipeUpdateDto.getYoutube() : existingRecipe.getYoutube())
                .popularity(existingRecipe.getPopularity())
                .favorites(existingRecipe.getFavorites())
                .likes(existingRecipe.getLikes())
                .build();
    }
}

