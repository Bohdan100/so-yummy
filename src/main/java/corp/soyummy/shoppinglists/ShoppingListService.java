package corp.soyummy.shoppinglists;

import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import java.util.List;

import corp.soyummy.shoppinglists.dto.ShoppingListCreateDto;
import corp.soyummy.shoppinglists.dto.ShoppingListUpdateDto;
import corp.soyummy.errors.ResourceNotFoundException;

@Service
@RequiredArgsConstructor
public class ShoppingListService {

    private final ShoppingListRepository shoppingListRepository;

    public List<ShoppingList> getAllShoppingLists() {
        return shoppingListRepository.findAll();
    }

    public ShoppingList getShoppingListById(String id) {
        return shoppingListRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Shopping list not found with id: " + id));
    }

    public List<ShoppingList> findByOwner(String ownerId) {
        ObjectId ownerObjectId = new ObjectId(ownerId);
        return shoppingListRepository.findByOwner(ownerObjectId);
    }

    public List<ShoppingList> findByRecipeId(String recipeId) {
        return shoppingListRepository.findByRecipeId(recipeId);
    }

    public List<ShoppingList> findByStartIngredient(String startIngredient) {
        return shoppingListRepository.findByStrIngredientContainingIgnoreCase(startIngredient);
    }

    public ShoppingList createShoppingList(ShoppingListCreateDto shoppingListCreateDto) {
        ShoppingList shoppingList = ShoppingList.builder()
                .owner(shoppingListCreateDto.getOwner())
                .strIngredient(shoppingListCreateDto.getStrIngredient())
                .weight(shoppingListCreateDto.getWeight())
                .image(shoppingListCreateDto.getImage())
                .recipeId(shoppingListCreateDto.getRecipeId())
                .build();

        return shoppingListRepository.save(shoppingList);
    }

    public ShoppingList updateShoppingList(String id, ShoppingListUpdateDto shoppingListUpdateDto) {
        ShoppingList existingShoppingList = getShoppingListById(id);
        if (existingShoppingList == null) {
            throw new ResourceNotFoundException("Shopping list not found with id: " + id);
        }

        updateShoppingListFields(existingShoppingList, shoppingListUpdateDto);

        return shoppingListRepository.save(existingShoppingList);
    }

    public void deleteShoppingList(String id) {
        if (!shoppingListRepository.existsById(id)) {
            throw new ResourceNotFoundException("Recipe not found with id: " + id);
        }
        shoppingListRepository.deleteById(id);
    }

    private void updateShoppingListFields(ShoppingList shoppingList, ShoppingListUpdateDto updateDto) {
        shoppingList.setStrIngredient(
                ObjectUtils.isEmpty(updateDto.getStrIngredient())
                        ? shoppingList.getStrIngredient()
                        : updateDto.getStrIngredient()
        );

        shoppingList.setWeight(
                ObjectUtils.isEmpty(updateDto.getWeight())
                        ? shoppingList.getWeight()
                        : updateDto.getWeight()
        );

        shoppingList.setImage(
                ObjectUtils.isEmpty(updateDto.getImage())
                        ? shoppingList.getImage()
                        : updateDto.getImage()
        );

        shoppingList.setRecipeId(
                ObjectUtils.isEmpty(updateDto.getRecipeId())
                        ? shoppingList.getRecipeId()
                        : updateDto.getRecipeId()
        );
    }
}
