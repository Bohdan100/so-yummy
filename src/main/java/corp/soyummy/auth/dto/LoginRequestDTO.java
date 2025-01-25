package corp.soyummy.auth.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDTO {
    @NotBlank(message = "Email is required!")
    @Email(message = "Please provide a valid email!")
    private String email;

    @NotBlank(message = "Password is required!")
    private String password;
}
