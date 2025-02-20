package hezix.org.shaudifydemo1.mapper;

import hezix.org.shaudifydemo1.entity.user.Role;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class UserMapper implements Mapper<CreateUserDTO,User> {


    @Override
    public User toEntity(CreateUserDTO createUserDTO) {
        return User.builder()
                .username(createUserDTO.getUsername())
                .password(createUserDTO.getPassword())
                .email(createUserDTO.getEmail())
                .description(createUserDTO.getDescription())
                .createdAt(LocalDateTime.now())
                .role(Role.ROLE_USER)

                .build();
    }

    @Override
    public CreateUserDTO toDto(User user) {
        return CreateUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .description(user.getDescription())
                .passwordConfirmation(user.getPassword())
                .build();
    }
}

