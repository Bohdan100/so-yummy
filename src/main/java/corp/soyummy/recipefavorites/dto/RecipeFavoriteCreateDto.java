package corp.soyummy.recipefavorites.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class RecipeFavoriteCreateDto {
    @NotNull(message = "Recipe ID is required!")
    @Size(min = 1, message = "RecipeId must have at least one character.")
    private String recipeId;

    @NotNull(message = "Amount is required!")
    @Positive(message = "Amount must be a positive number!")
    private Integer amount;
}