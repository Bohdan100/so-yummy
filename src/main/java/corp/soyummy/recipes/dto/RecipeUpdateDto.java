package corp.soyummy.recipes.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.AssertTrue;

import java.util.List;
import corp.soyummy.recipes.RecipeIngredient;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeUpdateDto {
    @Size(min = 1, message = "Title must have at least one character.")
    private String title;

    @Size(min = 1, message = "Category must have at least one character.")
    private String category;

    @Size(min = 1, message = "Area must have at least one character.")
    private String area;

    @Size(min = 1, message = "Instructions must have at least one character.")
    private String instructions;

    @Size(min = 1, message = "Description must have at least one character.")
    private String description;

    @Size(min = 1, message = "Time must have at least one character.")
    private String time;

    @Pattern(regexp = "^https://.{3,}", message = "The URL must start with \"https://\" and have at least 3 characters following.")
    private String thumbnail;

    @Pattern(regexp = "^https://.{3,}", message = "The URL must start with \"https://\" and have at least 3 characters following.")
    private String preview;

    @Pattern(regexp = "^https://.{3,}", message = "The URL must start with \"https://\" and have at least 3 characters following.")
    private String youtube;

    private List<String> tags;
    private List<RecipeIngredient> ingredients;

    @AssertTrue(message = "At least one field (title, category, area, instructions, description, time, thumbnail, preview, youtube, tags, ingredients) must be provided for update.")
    private boolean isAtLeastOneFieldProvided() {
        return (title != null && !title.isEmpty()) ||
                (category != null && !category.isEmpty()) ||
                (area != null && !area.isEmpty()) ||
                (instructions != null && !instructions.isEmpty()) ||
                (description != null && !description.isEmpty()) ||
                (time != null && !time.isEmpty()) ||
                (thumbnail != null && !thumbnail.isEmpty()) ||
                (preview != null && !preview.isEmpty()) ||
                (youtube != null && !youtube.isEmpty()) ||
                (tags != null && !tags.isEmpty()) ||
                (ingredients != null && !ingredients.isEmpty());
    }
}

