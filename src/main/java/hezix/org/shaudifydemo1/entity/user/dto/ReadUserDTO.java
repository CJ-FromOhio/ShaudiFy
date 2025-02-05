package hezix.org.shaudifydemo1.entity.user.dto;

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
    private String username;
    private String description;
    private String email;
    private LocalDateTime createdAt;
}
