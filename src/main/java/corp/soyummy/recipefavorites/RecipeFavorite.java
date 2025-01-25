package corp.soyummy.recipefavorites;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "recipefavorites")
public class RecipeFavorite {
    @Id
    private String id;

    @Field("recipe")
    private String recipeId;

    @Field("amount")
    private Integer amount;
}