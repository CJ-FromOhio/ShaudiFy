package hezix.org.shaudifydemo1.entity.song.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSongDTO {
    private Long id;
    @Size(min = 4, max = 32, message = "title must be between 4 and 32 symbols")
    private String title;
    @Size(max = 32, message = "description must be up to 255 symbols")
    private String description;
}