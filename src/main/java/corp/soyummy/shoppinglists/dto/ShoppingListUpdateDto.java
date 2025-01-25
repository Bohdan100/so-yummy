package corp.soyummy.shoppinglists.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.AssertTrue;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingListUpdateDto {
    @Size(min = 1, message = "Ingredient must have at least one character.")
    private String strIngredient;

    @Pattern(regexp = "^\\d+$", message = "Weight must be a positive number")
    private String weight;

    @Size(min = 1, message = "Image must have at least one character.")
    private String image;

    @Size(min = 1, message = "Image must have at least one character.")
    private String recipeId;

    @AssertTrue(message = "At least one field (strIngredient, weight, image, recipeId) must be provided.")
    private boolean isAtLeastOneFieldProvided() {
        return (strIngredient != null && !strIngredient.isEmpty()) ||
                (weight != null && !weight.isEmpty()) ||
                (image != null && !image.isEmpty()) ||
                (recipeId != null && !recipeId.isEmpty());
    }
}
