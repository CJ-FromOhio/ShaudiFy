package hezix.org.shaudifydemo1.entity.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {
    private Long id;
    @Size(min = 4, max = 32, message = "username must be between 4 and 32 symbols")
    private String username;
    @Size(min = 8, max = 32, message = "password must be between 8 and 32 symbols")
    private String password;
    @Size(min = 8, max = 32, message = "passwordConfirmation must be between 8 and 32 symbols")
    private String passwordConfirmation;
    @Size(max = 32, message = "description must be up to 255 symbols")
    private String description;
    @Email(message = "mail must be correct")
    private String email;
}
