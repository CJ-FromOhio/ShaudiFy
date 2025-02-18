package hezix.org.shaudifydemo1.entity.song.dto;

import jakarta.validation.Valid;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongFormDTO {
    @Valid
    private CreateSongDTO createSongDTO;
    @Valid
    private SongFileDTO songFileDTO;
}
