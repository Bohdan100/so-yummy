package corp.soyummy.userfavorites;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.bson.types.ObjectId;
import java.util.List;

@Repository
public interface UserFavoriteRepository extends MongoRepository<UserFavorite, String> {
    List<UserFavorite> findByUserId(ObjectId userId);
    List<UserFavorite> findByRecipeId(ObjectId recipeId);
}