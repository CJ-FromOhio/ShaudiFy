package hezix.org.shaudifydemo1.mapper;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.song.dto.ReadSongDTO;
import org.springframework.stereotype.Component;

@Component
public class ReadSongMapper implements Mapper<ReadSongDTO, Song> {
    @Override
    public Song toEntity(ReadSongDTO readSongDTO) {
        return Song.builder()
                .id(readSongDTO.getId())
                .title(readSongDTO.getTitle())
                .description(readSongDTO.getDescription())
                .build();
    }

    @Override
    public ReadSongDTO toDto(Song song) {
        return ReadSongDTO.builder()
                .id(song.getId())
                .title(song.getTitle())
                .description(song.getDescription())
                .image(song.getImage())
                .song(song.getSong())
                .build();
    }
}
