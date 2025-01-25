package corp.soyummy.recipefavorites;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

import corp.soyummy.recipefavorites.dto.RecipeFavoriteCreateDto;
import corp.soyummy.recipefavorites.dto.RecipeFavoriteUpdateDto;
import static corp.soyummy.constants.Constants.VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(VERSION + "recipefavorites")
public class RecipeFavoriteController {

    private final RecipeFavoriteService recipeFavoriteService;

    // GET http://localhost:8080/api/v1/recipefavorites
    @GetMapping
    public ResponseEntity<List<RecipeFavorite>> getAllFavorites() {
        List<RecipeFavorite> favorites = recipeFavoriteService.getAllFavorites();
        return ResponseEntity.ok(favorites);
    }

    // GET http://localhost:8080/api/v1/recipefavorites/{id}
    @GetMapping("/{id}")
    public ResponseEntity<RecipeFavorite> getFavoriteById(@PathVariable String id) {
        RecipeFavorite favorite = recipeFavoriteService.getFavoriteById(id);
        return ResponseEntity.ok(favorite);
    }

    // GET http://localhost:8080/api/v1/recipefavorites/byRecipeId/{recipeId}
    @GetMapping("/byRecipeId/{recipeId}")
    public ResponseEntity<List<RecipeFavorite>> getFavoritesByRecipeId(@PathVariable String recipeId) {
        List<RecipeFavorite> favorites = recipeFavoriteService.getFavoritesByRecipeId(recipeId);
        return ResponseEntity.ok(favorites);
    }

    // GET http://localhost:8080/api/v1/recipefavorites/byAmount/4
    @GetMapping("/byAmount/{amount}")
    public ResponseEntity<List<RecipeFavorite>> getFavoritesByAmount(@PathVariable Integer amount) {
        List<RecipeFavorite> favorites = recipeFavoriteService.getFavoritesByAmount(amount);
        return ResponseEntity.ok(favorites);
    }

    // GET http://localhost:8080/api/v1/recipefavorites/rating
    @GetMapping("/rating")
    public ResponseEntity<List<RecipeFavorite>> getTopFavorites() {
        List<RecipeFavorite> favorites = recipeFavoriteService.getFavoritesByRatingDesc();
        return ResponseEntity.ok(favorites);
    }

    // POST http://localhost:8080/api/v1/recipefavorites
    @PostMapping
    public ResponseEntity<RecipeFavorite> createFavorite(@Valid @RequestBody RecipeFavoriteCreateDto createDto) {
        RecipeFavorite favorite = recipeFavoriteService.createFavorite(createDto);
        return ResponseEntity.status(201).body(favorite);
    }

    // PUT http://localhost:8080/api/v1/recipefavorites
    @PutMapping("/{id}")
    public ResponseEntity<RecipeFavorite> updateFavorite(
            @PathVariable String id,
            @Valid @RequestBody RecipeFavoriteUpdateDto updateDto) {
        RecipeFavorite updatedFavorite = recipeFavoriteService.updateFavorite(id, updateDto);
        return ResponseEntity.ok(updatedFavorite);
    }

    // DELETE http://localhost:8080/api/v1/recipefavorites/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(@PathVariable String id) {
        recipeFavoriteService.deleteFavorite(id);
        return ResponseEntity.noContent().build();
    }
}