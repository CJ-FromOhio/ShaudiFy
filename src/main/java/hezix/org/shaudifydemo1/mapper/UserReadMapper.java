package hezix.org.shaudifydemo1.mapper;

import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import hezix.org.shaudifydemo1.entity.user.dto.ReadUserDTO;
import org.springframework.stereotype.Component;

@Component
public class UserReadMapper implements Mapper<ReadUserDTO, User>{
    @Override
    public User toEntity(ReadUserDTO readUserDTO) {
        return null;
    }

    @Override
    public ReadUserDTO toDto(User user) {
        return ReadUserDTO.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .authoredSongs(user.getAuthoredSongs())
                .description(user.getDescription())
                .createdAt(user.getCreatedAt())
                .build();
    }
}
