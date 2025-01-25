package corp.soyummy.recipes;

import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

@Repository
public interface RecipeRepository extends MongoRepository<Recipe, String> {
    List<Recipe> findByTitleContainingIgnoreCase(String title);
    List<Recipe> findByCategoryContainingIgnoreCase(String category);
    List<Recipe> findByAreaContainingIgnoreCase(String area);
    List<Recipe> findByTagsContainingIgnoreCase(String tag);
}

