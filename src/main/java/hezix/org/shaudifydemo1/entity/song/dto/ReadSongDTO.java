package hezix.org.shaudifydemo1.entity.song.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadSongDTO implements Serializable {
    private Long id;
    private String title;
    private String description;
}
