package hezix.org.shaudifydemo1.entity.song.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SongFileDTO {

    private MultipartFile image;

    private MultipartFile song;
}
