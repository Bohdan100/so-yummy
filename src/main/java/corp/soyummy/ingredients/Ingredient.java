package corp.soyummy.ingredients;

import lombok.Data;
import lombok.Builder;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(collection = "ingredients")
public class Ingredient {
    @Id
    private String id;

    @Field("ttl")
    private String title;

    @Field("desc")
    private String description;

    @Field("thb")
    private String thumbnail;
}
