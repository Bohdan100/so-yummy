package corp.soyummy.ingredients;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

import corp.soyummy.ingredients.dto.IngredientCreateDto;
import corp.soyummy.ingredients.dto.IngredientUpdateDto;
import static corp.soyummy.constants.Constants.VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(VERSION + "ingredients")
public class IngredientController {

    private final IngredientService ingredientService;

    //  GET  http://localhost:8080/api/v1/ingredients
    @GetMapping
    public ResponseEntity<List<Ingredient>> getAllIngredients() {
        List<Ingredient> ingredients = ingredientService.getAllIngredients();
        return ResponseEntity.ok(ingredients);
    }

    //  GET  http://localhost:8080/api/v1/ingredients/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Ingredient> getIngredientById(@PathVariable String id) {
        Ingredient ingredient = ingredientService.getIngredientById(id);
        return ResponseEntity.ok(ingredient);
    }

    //  GET  http://localhost:8080/api/v1/ingredients/search?title=beef
    @GetMapping("/search")
    public ResponseEntity<List<Ingredient>> searchIngredientsByTitle(@RequestParam String title) {
        List<Ingredient> ingredients = ingredientService.searchIngredientsByTitle(title);
        return ResponseEntity.ok(ingredients);
    }

    //  POST  http://localhost:8080/api/v1/ingredients
    @PostMapping
    @Valid
    public ResponseEntity<Ingredient> createIngredient(@Valid @RequestBody IngredientCreateDto ingredientCreateDto) {
        Ingredient createdIngredient = ingredientService.createIngredient(ingredientCreateDto);
        return ResponseEntity.status(201).body(createdIngredient);
    }

    //  PUT  http://localhost:8080/api/v1/ingredients/{id}
    @PutMapping("/{id}")
    @Valid
    public ResponseEntity<Ingredient> updateIngredient(
            @PathVariable String id,
            @Valid @RequestBody IngredientUpdateDto ingredientUpdateDto) {
        Ingredient updatedIngredient = ingredientService.updateIngredient(id, ingredientUpdateDto);
        return ResponseEntity.ok(updatedIngredient);
    }

    //  DELETE  http://localhost:8080/api/v1/ingredients/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteIngredient(@PathVariable String id) {
        ingredientService.deleteIngredient(id);
        return ResponseEntity.noContent().build();
    }
}