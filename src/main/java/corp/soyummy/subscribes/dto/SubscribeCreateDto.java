package corp.soyummy.subscribes.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class SubscribeCreateDto {
    @NotNull(message = "Owner is required!")
    @NotBlank(message = "Owner is required!")
    private String owner;

    @NotNull(message = "Email is required!")
    @Email(message = "Invalid email format")
    private String email;
}