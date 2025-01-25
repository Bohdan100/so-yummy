package corp.soyummy.auth.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequestDTO {
    @NotBlank(message = "Email is required!")
    @Email(message = "Please provide a valid email!")
    private String email;

    @NotBlank(message = "Name is required!")
    private String name;

    @NotBlank(message = "Password is required!")
    @Size(min = 6, message = "Password must be at least 6 characters!")
    private String password;

    private String role;
}


