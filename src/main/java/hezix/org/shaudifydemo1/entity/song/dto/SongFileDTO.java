package hezix.org.shaudifydemo1.entity.song.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class SongFileDTO {
    private MultipartFile image;
}
