package hezix.org.shaudifydemo1.entity.song.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadSongDTO {
    private Long id;
    private String title;
    private String description;
}
