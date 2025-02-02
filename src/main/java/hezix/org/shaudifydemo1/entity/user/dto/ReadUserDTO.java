package hezix.org.shaudifydemo1.entity.user.dto;

import hezix.org.shaudifydemo1.entity.song.dto.ReadSongDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadUserDTO {
    private Long id;
    private String username;
    private List<ReadSongDTO> authoredSongs;
}
