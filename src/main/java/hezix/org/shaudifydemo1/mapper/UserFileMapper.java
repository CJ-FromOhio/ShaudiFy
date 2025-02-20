package hezix.org.shaudifydemo1.mapper;

import hezix.org.shaudifydemo1.entity.user.UserFile;
import hezix.org.shaudifydemo1.entity.user.dto.UserFileDTO;
import org.springframework.stereotype.Component;

@Component
public class UserFileMapper implements Mapper<UserFileDTO, UserFile> {
    @Override
    public UserFile toEntity(UserFileDTO userFileDTO) {
        return UserFile.builder()
                .image(userFileDTO.getImage())
                .build();
    }

    @Override
    public UserFileDTO toDto(UserFile userFile) {
        return UserFileDTO.builder()
                .image(userFile.getImage())
                .build();
    }
}
