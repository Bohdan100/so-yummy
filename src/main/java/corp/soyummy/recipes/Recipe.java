package corp.soyummy.recipes;

import lombok.Data;
import lombok.Builder;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "recipes")
public class Recipe {
    @Id
    private String id;

    @Field("title")
    private String title;

    @Field("category")
    private String category;

    @Field("area")
    private String area;

    @Field("instructions")
    private String instructions;

    @Field("description")
    private String description;

    @Field("thumb")
    private String thumbnail;

    @Field("preview")
    private String preview;

    @Field("time")
    private String time;

    @Field("popularity")
    private Integer popularity;

    @Field("favorites")
    private List<String> favorites;

    @Field("likes")
    private List<String> likes;

    @Field("youtube")
    private String youtube;

    @Field("tags")
    private List<String> tags;

    @Field("createdAt")
    private LocalDateTime createdAt;

    @Field("updatedAt")
    private LocalDateTime updatedAt;

    @Field("ingredients")
    private List<RecipeIngredient> ingredients;
}

