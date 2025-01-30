package hezix.org.shaudifydemo1.entity.user;

import hezix.org.shaudifydemo1.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends BaseEntity {
    private String username;
    private String password;
    private String description;
    @Enumerated(EnumType.STRING)
    private Role role;
    private String email;

}
