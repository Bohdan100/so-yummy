package corp.soyummy.recipefavorites;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;
import java.util.List;

@Repository
public interface RecipeFavoriteRepository extends MongoRepository<RecipeFavorite, String> {
    List<RecipeFavorite> findByRecipeId(ObjectId recipeId);
    List<RecipeFavorite> findByAmount(Integer amount);
    List<RecipeFavorite> findAllByOrderByAmountDesc();
}
