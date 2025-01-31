package hezix.org.shaudifydemo1.mapper;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.song.SongType;
import hezix.org.shaudifydemo1.entity.song.dto.CreateSongDTO;
import hezix.org.shaudifydemo1.entity.user.Role;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class SongMapper implements Mapper<CreateSongDTO, Song> {


    @Override
    public Song toEntity(CreateSongDTO createSongDTO) {
        return Song.builder()
                .title(createSongDTO.getTitle())
                .description(createSongDTO.getDescription())
                .type(SongType.DEFAULT)
                .createdAt(LocalDateTime.now())
                .build();
    }

    @Override
    public CreateSongDTO toDto(Song song) {
        return CreateSongDTO.builder()
                .title(song.getTitle())
                .description(song.getDescription())
                .build();
    }
}