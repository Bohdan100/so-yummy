package corp.soyummy.recipefavorites.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RecipeFavoriteUpdateDto {
    @NotNull(message = "Amount is required!")
    @Positive(message = "Amount must be a positive number!")
    private Integer amount;
}