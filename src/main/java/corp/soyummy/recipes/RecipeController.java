package corp.soyummy.recipes;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;

import java.util.List;
import java.util.ArrayList;

import corp.soyummy.recipes.dto.RecipeCreateDto;
import corp.soyummy.recipes.dto.RecipeUpdateDto;
import static corp.soyummy.constants.Constants.VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(VERSION + "recipes")
public class RecipeController {

    private final RecipeService recipeService;

    // GET http://localhost:8080/api/v1/recipes
    @GetMapping
    public ResponseEntity<List<Recipe>> getAllRecipes() {
        return ResponseEntity.ok(recipeService.getAllRecipes());
    }

    // GET http://localhost:8080/api/v1/recipes/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable String id) {
        return ResponseEntity.ok(recipeService.getRecipeById(id));
    }

    // GET http://localhost:8080/api/v1/recipes/search?title=pasta
    @GetMapping("/search")
    public ResponseEntity<List<Recipe>> searchRecipesByCategory(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String area,
            @RequestParam(required = false) String tag) {

        List<Recipe> recipes;
        if (title != null) {
            recipes = recipeService.findByTitle(title);
        } else if (category != null) {
            recipes = recipeService.findByCategory(category);
        } else if (area != null) {
            recipes = recipeService.findByArea(area);
        } else if (tag != null) {
            recipes = recipeService.findByTags(tag);
        } else {
            recipes = new ArrayList<>();
        }
        return ResponseEntity.ok(recipes);
    }

    // POST http://localhost:8080/api/v1/recipes
    @PostMapping
    public ResponseEntity<Recipe> createRecipe(@Valid @RequestBody RecipeCreateDto recipeCreateDto) {
        return ResponseEntity.status(201).body(recipeService.createRecipe(recipeCreateDto));
    }

    // PUT http://localhost:8080/api/v1/recipes/{id}
    @PutMapping("/{id}")
    public ResponseEntity<Recipe> updateRecipe(
            @PathVariable String id,
            @Valid @RequestBody RecipeUpdateDto recipeUpdateDto) {
        return ResponseEntity.ok(recipeService.updateRecipe(id, recipeUpdateDto));
    }

    // DELETE http://localhost:8080/api/v1/recipes/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.noContent().build();
    }
}

