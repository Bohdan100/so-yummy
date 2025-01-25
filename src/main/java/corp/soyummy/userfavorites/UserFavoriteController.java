package corp.soyummy.userfavorites;

import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.http.ResponseEntity;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

import corp.soyummy.userfavorites.dto.UserFavoriteCreateDto;
import corp.soyummy.userfavorites.dto.UserFavoriteUpdateDto;
import corp.soyummy.auth.User;
import corp.soyummy.errors.ResourceNotFoundException;
import static corp.soyummy.constants.Constants.VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(VERSION + "userfavorites")
public class UserFavoriteController {

    private final UserFavoriteService userFavoriteService;

    // GET http://localhost:8080/api/v1/userfavorites
    @GetMapping
    public ResponseEntity<List<UserFavorite>> getAllFavorites() {
        List<UserFavorite> favorites = userFavoriteService.getAllFavorites();
        return ResponseEntity.ok(favorites);
    }

    // GET http://localhost:8080/api/v1/userfavorites/{id}
    @GetMapping("/{id}")
    public ResponseEntity<UserFavorite> getFavoriteById(@PathVariable String id) {
        UserFavorite favorite = userFavoriteService.getFavoriteById(id);
        return ResponseEntity.ok(favorite);
    }

    // GET http://localhost:8080/api/v1/userfavorites/byRecipe/{recipeId}
    @GetMapping("/byRecipe/{recipeId}")
    public ResponseEntity<List<UserFavorite>> getFavoritesByRecipeId(@PathVariable String recipeId) {
        List<UserFavorite> favorites = userFavoriteService.getFavoritesByRecipeId(recipeId);
        return ResponseEntity.ok(favorites);
    }

    // GET http://localhost:8080/api/v1/userfavorites/byUser/{userId}
    @GetMapping("/byUser/{userId}")
    public ResponseEntity<List<UserFavorite>> getFavoritesByUserId(
            @PathVariable String userId,
            @AuthenticationPrincipal User currentUser) {
        if (!userId.equals(currentUser.getId())) {
            throw new ResourceNotFoundException("You can only access your own favorites");
        }

        List<UserFavorite> favorites = userFavoriteService.getFavoritesByUserId(userId);
        return ResponseEntity.ok(favorites);
    }

    // POST http://localhost:8080/api/v1/userfavorites
    @PostMapping
    public ResponseEntity<UserFavorite> createFavorite(
            @Valid @RequestBody UserFavoriteCreateDto createDto,
            @AuthenticationPrincipal User currentUser) {
        createDto.setUserId(currentUser.getId());

        UserFavorite favorite = userFavoriteService.createFavorite(createDto);
        return ResponseEntity.status(201).body(favorite);
    }

    // PUT http://localhost:8080/api/v1/userfavorites/{id}
    @PutMapping("/{id}")
    public ResponseEntity<UserFavorite> updateFavorite(
            @PathVariable String id,
            @Valid @RequestBody UserFavoriteUpdateDto updateDto,
            @AuthenticationPrincipal User currentUser) {
        UserFavorite existingFavorite = userFavoriteService.getFavoriteById(id);
        if (!existingFavorite.getUserId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Favorite not found for current user");
        }

        UserFavorite updatedFavorite = userFavoriteService.updateFavorite(id, updateDto);
        return ResponseEntity.ok(updatedFavorite);
    }

    // DELETE http://localhost:8080/api/v1/userfavorites/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFavorite(
            @PathVariable String id,
            @AuthenticationPrincipal User currentUser) {
        UserFavorite existingFavorite = userFavoriteService.getFavoriteById(id);
        if (!existingFavorite.getUserId().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Favorite not found for current user");
        }

        userFavoriteService.deleteFavorite(id);
        return ResponseEntity.noContent().build();
    }
}
