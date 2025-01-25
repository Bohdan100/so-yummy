package corp.soyummy.userfavorites;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "userfavorites")
public class UserFavorite {
    @Id
    private String id;

    @Field("recipe")
    private String recipeId;

    @Field("userId")
    private String userId;
}
