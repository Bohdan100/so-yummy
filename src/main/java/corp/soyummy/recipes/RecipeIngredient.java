package corp.soyummy.recipes;

import lombok.Data;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@AllArgsConstructor
public class RecipeIngredient {
    @Field("id")
    private String id;

    private String measure;
}