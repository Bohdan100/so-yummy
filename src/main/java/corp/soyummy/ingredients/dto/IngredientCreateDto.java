package corp.soyummy.ingredients.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class IngredientCreateDto {
    @NotNull(message = "Title is required!")
    @NotBlank(message = "Title is required!")
    private String title;

    @NotNull(message = "Description is required!")
    @NotBlank(message = "Description is required!")
    private String description;

    @NotNull(message = "Thumbnail is required!")
    @NotBlank(message = "Thumbnail is required!")
    private String thumbnail;

}
