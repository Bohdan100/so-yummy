package corp.soyummy.shoppinglists.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

@Getter
@Setter
@AllArgsConstructor
public class ShoppingListCreateDto {
    @NotNull(message = "Owner is required!")
    @NotBlank(message = "Owner is required!")
    private String owner;

    @NotNull(message = "Ingredient is required!")
    @NotBlank(message = "Ingredient is required!")
    private String strIngredient;

    @NotNull(message = "Weight is required!")
    @NotBlank(message = "Weight is required!")
    @Pattern(regexp = "^\\d+$", message = "Weight must be a positive number")
    private String weight;

    @NotNull(message = "Image is required!")
    @NotBlank(message = "Image is required!")
    private String image;

    @NotNull(message = "RecipeId is required!")
    @NotBlank(message = "RecipeId is required!")
    private String recipeId;
}
