package corp.soyummy.subscribes;

import lombok.Data;
import lombok.Builder;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

@Data
@Builder
@Document(collection = "subscribes")
public class Subscribe {
    @Id
    private String id;

    @Field("owner")
    private String ownerId;

    @Field("email")
    private String email;
}