package hezix.org.shaudifydemo1.entity.song;

import hezix.org.shaudifydemo1.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Song extends BaseEntity {
    private String title;
    private String description;
    @Enumerated(EnumType.STRING)
    private SongType type;
}
