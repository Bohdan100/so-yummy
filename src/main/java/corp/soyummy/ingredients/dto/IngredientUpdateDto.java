package corp.soyummy.ingredients.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.AssertTrue;

@Getter
@Setter
@AllArgsConstructor
public class IngredientUpdateDto {
    private String id;
    @Size(min = 1, message = "Title must have at least one character.")
    private String title;

    @Size(min = 1, message = "Description must have at least one character.")
    private String description;

    @Size(min = 1, message = "Thumbnail must have at least one character.")
    private String thumbnail;

    @AssertTrue(message = "At least one field (title, description, thumbnail) must be provided.")
    private boolean isAtLeastOneFieldProvided() {
        return (title != null && !title.isEmpty()) ||
                (description != null && !description.isEmpty()) ||
                (thumbnail != null && !thumbnail.isEmpty());
    }
}
