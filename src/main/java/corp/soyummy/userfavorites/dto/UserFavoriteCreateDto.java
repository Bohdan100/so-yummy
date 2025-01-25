package corp.soyummy.userfavorites.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
public class UserFavoriteCreateDto {
    @NotNull(message = "Recipe ID is required!")
    @Size(min = 1, message = "RecipeId must have at least one character.")
    private String recipeId;

    @NotNull(message = "User ID is required!")
    @Size(min = 1, message = "UserId must have at least one character.")
    private String userId;
}
