package hezix.org.shaudifydemo1.entity.user.dto;

import hezix.org.shaudifydemo1.entity.song.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadUserDTO {
    private String username;
    private String password;
    private String description;
    private String email;
    private List<Song> authoredSongs;
    private LocalDateTime createdAt;
}
