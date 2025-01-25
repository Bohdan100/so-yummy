package corp.soyummy.shoppinglists;

import lombok.Data;
import lombok.Builder;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.annotation.Id;

@Data
@Builder
@Document(collection = "shoppinglists")
public class ShoppingList {
    @Id
    private String id;

    @Field("owner")
    private String owner;

    @Field("strIngredient")
    private String strIngredient;

    @Field("weight")
    private String weight;

    @Field("image")
    private String image;

    @Field("recipeId")
    private String recipeId;
}
