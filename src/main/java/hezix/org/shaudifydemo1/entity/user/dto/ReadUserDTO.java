package hezix.org.shaudifydemo1.entity.user.dto;

import hezix.org.shaudifydemo1.entity.song.dto.ReadSongDTO;
import hezix.org.shaudifydemo1.entity.user.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadUserDTO implements Serializable {
    private Long id;
    private String username;
    private String password;
    private Role role;
    private String description;
    private String email;
    private LocalDateTime createdAt;
    private List<ReadSongDTO> authoredSongs;
}
