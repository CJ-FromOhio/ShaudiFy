package hezix.org.shaudifydemo1.service;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;

    public Song save(Song song) {
        return songRepository.save(song);
    }
    public List<Song> findAll() {
        return songRepository.findAll();
    }
    public Song findById(Long id) {
        return songRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Song not found by id: " + id));
    }

    public void delete(Long id) {
        songRepository.deleteById(id);
    }

}
