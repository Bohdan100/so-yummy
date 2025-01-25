package corp.soyummy.shoppinglists;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import lombok.RequiredArgsConstructor;
import jakarta.validation.Valid;
import java.util.List;

import corp.soyummy.auth.User;
import corp.soyummy.shoppinglists.dto.ShoppingListCreateDto;
import corp.soyummy.shoppinglists.dto.ShoppingListUpdateDto;
import corp.soyummy.errors.ResourceNotFoundException;
import static corp.soyummy.constants.Constants.VERSION;

@RestController
@RequiredArgsConstructor
@RequestMapping(VERSION + "shoppinglists")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;

    //  GET  http://localhost:8080/api/v1/shoppinglists
    @GetMapping
    public ResponseEntity<List<ShoppingList>> getAllShoppingLists(@AuthenticationPrincipal User user) {
        List<ShoppingList> shoppingLists = shoppingListService.getAllShoppingLists();
        return ResponseEntity.ok(shoppingLists);
    }

    //  GET  http://localhost:8080/api/v1/shoppinglists/{id}
    @GetMapping("/{id}")
    public ResponseEntity<ShoppingList> getShoppingListById(
            @PathVariable String id,
            @AuthenticationPrincipal User currentUser) {
        ShoppingList shoppingList = shoppingListService.getShoppingListById(id);
        if (!shoppingList.getOwner().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Shopping list not found for current user");
        }

        return ResponseEntity.ok(shoppingList);
    }

    //  GET  http://localhost:8080/api/v1/shoppinglists/byOwner/{ownerId}
    @GetMapping("/byOwner/{ownerId}")
    public ResponseEntity<List<ShoppingList>> getShoppingListsByOwner(
            @PathVariable String ownerId,
            @AuthenticationPrincipal User currentUser) {
        if (!ownerId.equals(currentUser.getId())) {
            throw new ResourceNotFoundException("You can only access your own shopping list");
        }

        List<ShoppingList> shoppingLists = shoppingListService.findByOwner(ownerId);
        return ResponseEntity.ok(shoppingLists);
    }

    //  GET  http://localhost:8080/api/v1/shoppinglists/byRecipeId/{recipeId}
    @GetMapping("/byRecipeId/{recipeId}")
    public ResponseEntity<List<ShoppingList>> getShoppingListsByRecipeId(@PathVariable String recipeId) {
        List<ShoppingList> shoppingLists = shoppingListService.findByRecipeId(recipeId);
        return ResponseEntity.ok(shoppingLists);
    }

    //  GET  http://localhost:8080/api/v1/shoppinglists/byStartIngredient/{startIngredient}
    @GetMapping("/byStartIngredient/{startIngredient}")
    public ResponseEntity<List<ShoppingList>> getShoppingListsByStartIngredient(@PathVariable String startIngredient) {
        List<ShoppingList> shoppingLists = shoppingListService.findByStartIngredient(startIngredient);
        return ResponseEntity.ok(shoppingLists);
    }

    //  POST  http://localhost:8080/api/v1/shoppinglists
    @PostMapping
    public ResponseEntity<ShoppingList> createShoppingList(
            @Valid @RequestBody ShoppingListCreateDto shoppingListCreateDto,
            @AuthenticationPrincipal User currentUser) {
        shoppingListCreateDto.setOwner(currentUser.getId());
        ShoppingList createdShoppingList = shoppingListService.createShoppingList(shoppingListCreateDto);

        return ResponseEntity.status(201).body(createdShoppingList);
    }

    //  PUT  http://localhost:8080/api/v1/shoppinglists/{id}
    @PutMapping("/{id}")
    public ResponseEntity<ShoppingList> updateShoppingList(
            @PathVariable String id,
            @Valid @RequestBody ShoppingListUpdateDto shoppingListUpdateDto,
            @AuthenticationPrincipal User currentUser) {
        ShoppingList existingShoppingList = shoppingListService.getShoppingListById(id);
        if (!existingShoppingList.getOwner().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Shopping list not found for current user");
        }

        ShoppingList updatedShoppingList = shoppingListService.updateShoppingList(id, shoppingListUpdateDto);
        return ResponseEntity.ok(updatedShoppingList);
    }

    //  DELETE  http://localhost:8080/api/v1/shoppinglists/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShoppingList(
            @PathVariable String id,
            @AuthenticationPrincipal User currentUser) {
        ShoppingList existingShoppingList = shoppingListService.getShoppingListById(id);
        if (!existingShoppingList.getOwner().equals(currentUser.getId())) {
            throw new ResourceNotFoundException("Shopping list not found for current user");
        }

        shoppingListService.deleteShoppingList(id);
        return ResponseEntity.noContent().build();
    }
}
