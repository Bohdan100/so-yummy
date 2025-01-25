package corp.soyummy.recipes.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.util.List;
import corp.soyummy.recipes.RecipeIngredient;

@Getter
@Setter
@AllArgsConstructor
public class RecipeCreateDto {
    @NotNull(message = "Title is required!")
    @NotBlank(message = "Title is required!")
    private String title;

    @NotNull(message = "Category is required!")
    @NotBlank(message = "Category is required!")
    private String category;

    @NotNull(message = "Area is required!")
    @NotBlank(message = "Area is required!")
    private String area;

    @NotNull(message = "Instructions is required!")
    @NotBlank(message = "Instructions are required!")
    private String instructions;

    @NotNull(message = "Description is required!")
    @NotBlank(message = "Description is required!")
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
}

