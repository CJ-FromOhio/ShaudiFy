package hezix.org.shaudifydemo1.service;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.song.SongFile;
import hezix.org.shaudifydemo1.entity.song.dto.CreateSongDTO;
import hezix.org.shaudifydemo1.entity.song.dto.ReadSongDTO;
import hezix.org.shaudifydemo1.entity.song.dto.SongFileDTO;
import hezix.org.shaudifydemo1.entity.song.dto.SongFormDTO;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.ReadUserDTO;
import hezix.org.shaudifydemo1.mapper.ReadSongMapper;
import hezix.org.shaudifydemo1.mapper.ReadUserMapper;
import hezix.org.shaudifydemo1.mapper.SongFileMapper;
import hezix.org.shaudifydemo1.mapper.SongMapper;
import hezix.org.shaudifydemo1.props.MinioProperties;
import hezix.org.shaudifydemo1.repository.SongRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class SongService {

    private final SongRepository songRepository;
    private final UserService userService;
    private final SongMapper songMapper;
    private final ReadSongMapper readSongMapper;
    private final ReadUserMapper readUserMapper;
    private final MinioService minioService;
    private final SongFileMapper songFileMapper;
    private final Random random;

    @Transactional
//    @CacheEvict(value = "SongService::findById", key = "#createSongDTO.id")
    public Song save(CreateSongDTO createSongDTO) {
        Song song = songMapper.toEntity(createSongDTO);
        return songRepository.save(song);
    }

    @Transactional(readOnly = true)
    public List<ReadSongDTO> findAll() {
        List<ReadSongDTO> songs = songRepository.findAll().stream()
                .map(readSongMapper::toDto)
                .toList();
        for (ReadSongDTO song : songs) {
            if(song.getImage() != null) {
                String imageUrl = minioService.getPresignedUrl("images", song.getImage());
                song.setImageUrl(imageUrl);
            }
            if(song.getSong() != null) {
                String songUrl = minioService.getPresignedUrl("songs", song.getSong());
                song.setSongUrl(songUrl);
            }
        }
        return songs;
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "SongService::findById", key = "#id")
    public ReadSongDTO findById(Long id) {
        Song song = songRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Song not found by id: " + id));
        ReadSongDTO readSongDTO = readSongMapper.toDto(song);
        if(song.getImage() != null) {
            String imageUrl = minioService.getPresignedUrl("images", readSongDTO.getImage());
            readSongDTO.setImageUrl(imageUrl);
        }
        if(song.getSong() != null) {
            String songUrl = minioService.getPresignedUrl("songs", readSongDTO.getSong());
            readSongDTO.setSongUrl(songUrl);
        }
        return readSongDTO;
    }
    @Transactional(readOnly = true)
    public ReadSongDTO findRandomSong(){
        List<ReadSongDTO> songs = findAll();
        int index = random.nextInt(songs.size());
        return songs.get(index);
    }

    @Transactional(readOnly = true)
    @Cacheable(value = "SongService::findImageById", key = "#id")
    public String findImageById(Long id) {
        return songRepository.findImageById(id).orElseThrow(() -> new EntityNotFoundException("Song image not found by id: " + id));
    }
    @Transactional(readOnly = true)
    public Song findSongEntityById(Long id) {
        return songRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Song not found by id: " + id));
    }
    @Transactional
    @CacheEvict(value = "SongService::getById", key = "#id")
    public void delete(Long id) {
        songRepository.deleteById(id);
    }

    @Transactional
    public ReadUserDTO assignSong(Long songId, Long userId) {
        User user = userService.findUserEntityById(userId);
        Song song = findSongEntityById(songId);
        song.setUser(user);
        song.setCreatedBy(user.getUsername());
        user.getAuthoredSongs().add(song);
        return readUserMapper.toDto(user);
    }
//    @Cacheable(value = "SongService::findById", key = "#id")
    @Transactional
    public void uploadImage(Long id, SongFileDTO songFileDTO) {
        SongFile imageFile = songFileMapper.toEntity(songFileDTO);
        Song song = findSongEntityById(id);
        String filename = minioService.uploadSongImage(imageFile);
        song.setImage(filename);
        songRepository.save(song);
    }
    @Transactional
    public void uploadSong(Long id, SongFileDTO songFileDTO) {
        SongFile songFile = songFileMapper.toEntity(songFileDTO);
        Song song = findSongEntityById(id);
        String filename = minioService.uploadSong(songFile);
        song.setSong(filename);
        songRepository.save(song);
    }
}
