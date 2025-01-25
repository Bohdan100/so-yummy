package corp.soyummy.ingredients;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Repository
public interface IngredientRepository extends MongoRepository<Ingredient, String> {
    List<Ingredient> findByTitleContainingIgnoreCase(String title);
}

