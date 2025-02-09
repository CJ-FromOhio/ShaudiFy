package hezix.org.shaudifydemo1.repository;

import hezix.org.shaudifydemo1.entity.song.Song;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
}
