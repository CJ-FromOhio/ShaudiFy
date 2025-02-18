package hezix.org.shaudifydemo1.entity.song.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReadSongDTO implements Serializable {
//    @Serial
//    private static final long serialVersionUID = 8520719140941611134L;
    private Long id;
    private String title;
    private String description;
    private String imageUrl;
    private String image;
    private String songUrl;
    private String song;
}
