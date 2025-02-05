package hezix.org.shaudifydemo1.mapper;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.song.dto.ReadSongDTO;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.ReadUserDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ReadUserMapper implements Mapper<ReadUserDTO, User> {

    private final ReadSongMapper readSongMapper;

    @Override
    public User toEntity(ReadUserDTO readUserDTO) {
        return User.builder()
                .id(readUserDTO.getId())
                .username(readUserDTO.getUsername())
                .email(readUserDTO.getEmail())
                .role(readUserDTO.getRole())
                .createdAt(readUserDTO.getCreatedAt())
                .build();
    }

    @Override
    public ReadUserDTO toDto(User user) {
        return ReadUserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .description(user.getDescription())
                .authoredSongs(user.getAuthoredSongs().stream()
                        .map(readSongMapper::toDto)
                        .collect(Collectors.toList()))
                .build();
    }
}
