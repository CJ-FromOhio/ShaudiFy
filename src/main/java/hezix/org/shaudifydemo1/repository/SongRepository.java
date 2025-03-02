package hezix.org.shaudifydemo1.repository;

import hezix.org.shaudifydemo1.entity.song.Song;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface SongRepository extends JpaRepository<Song, Long> {
    @Query("SELECT s.image FROM Song s WHERE s.id = :id")
    @Timed("songR_findImageById")
    Optional<String> findImageById(@Param("id") Long id);

}
