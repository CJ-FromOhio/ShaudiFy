package hezix.org.shaudifydemo1.entity.song.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mapstruct.Mapper;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateSongDTO {
    private String title;
    private String description;
}