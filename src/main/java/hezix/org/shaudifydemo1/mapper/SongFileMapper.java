package hezix.org.shaudifydemo1.mapper;

import hezix.org.shaudifydemo1.entity.song.SongFile;
import hezix.org.shaudifydemo1.entity.song.dto.SongFileDTO;
import org.springframework.stereotype.Component;

@Component
public class SongFileMapper implements Mapper<SongFileDTO, SongFile> {
    @Override
    public SongFile toEntity(SongFileDTO songFileDTO) {
        return SongFile.builder()
                .image(songFileDTO.getImage())
                .song(songFileDTO.getSong())
                .build();
    }

    @Override
    public SongFileDTO toDto(SongFile songFile) {
        return SongFileDTO.builder()
                .image(songFile.getImage())
                .song(songFile.getSong())
                .build();
    }
}
