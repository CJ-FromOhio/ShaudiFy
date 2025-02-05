package hezix.org.shaudifydemo1.service;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.song.dto.CreateSongDTO;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.mapper.SongMapper;
import hezix.org.shaudifydemo1.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final UserService userService;
    private final SongMapper songMapper;
    @Caching(cacheable = {
            @Cacheable(value = "SongService::findById", key = "#song.id"),
    })
    public Song save(CreateSongDTO createSongDTO) {
        Song song = songMapper.toEntity(createSongDTO);
        return songRepository.save(song);
    }
    @Transactional(readOnly = true)
    public List<Song> findAll() {
        return songRepository.findAll();
    }
    @Transactional(readOnly = true)
    @Cacheable(value = "SongService::findById", key = "#id")
    public Song findById(Long id) {
        return songRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Song not found by id: " + id));
    }
    @CacheEvict(value = "SongService::getById", key = "#id")
    public void delete(Long id) {
        songRepository.deleteById(id);
    }
    @Cacheable(value = "SongService::assignSong", key = "#songId + '.' + #userId")
    @Transactional
    public User assignSong(Long songId, Long userId) {
        User user = userService.findUserById(userId);
        List<Song> list = user.getAuthoredSongs();
        Song song = findById(songId);
        song.setUser(user);
        song.setCreatedBy(user.getUsername());
        list.add(song);
        user.setAuthoredSongs(list);
        return user;
    }
}
