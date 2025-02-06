package hezix.org.shaudifydemo1.entity.user.dto;

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
    private String username;
    private String password;
    private String passwordConfirmation;
    private String description;
    private String email;
}
