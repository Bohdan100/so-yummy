package corp.soyummy.shoppinglists;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import org.bson.types.ObjectId;
import java.util.List;

@Repository
public interface ShoppingListRepository extends MongoRepository<ShoppingList, String> {
    List<ShoppingList> findByOwner(ObjectId ownerId);
    List<ShoppingList> findByRecipeId(String recipeId);
    List<ShoppingList> findByStrIngredientContainingIgnoreCase(String strIngredient);
}
