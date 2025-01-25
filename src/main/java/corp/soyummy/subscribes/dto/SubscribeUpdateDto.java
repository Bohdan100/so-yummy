package corp.soyummy.subscribes.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SubscribeUpdateDto {
    @Email(message = "Incorrect email format")
    private String email;
}
