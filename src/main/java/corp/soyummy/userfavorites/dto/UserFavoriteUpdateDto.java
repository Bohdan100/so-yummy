package corp.soyummy.userfavorites.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserFavoriteUpdateDto {
    @NotNull(message = "RecipeId is required!")
    @Size(min = 1, message = "RecipeId must have at least one character.")
    private String recipeId;
}
