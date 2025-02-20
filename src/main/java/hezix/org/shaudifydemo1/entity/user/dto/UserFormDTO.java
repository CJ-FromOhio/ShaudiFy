package hezix.org.shaudifydemo1.entity.user.dto;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserFormDTO {
    @Valid
    private CreateUserDTO createUserDTO;
    @Valid
    private UserFileDTO userFileDTO;

}
