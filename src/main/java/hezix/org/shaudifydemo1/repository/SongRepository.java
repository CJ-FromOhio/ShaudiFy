package hezix.org.shaudifydemo1.repository;

import hezix.org.shaudifydemo1.entity.song.Song;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SongRepository extends JpaRepository<Song, Long> {
}
