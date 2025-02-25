package hezix.org.shaudifydemo1.service;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.song.SongFile;
import hezix.org.shaudifydemo1.entity.song.dto.ReadSongDTO;
import hezix.org.shaudifydemo1.entity.song.dto.SongFileDTO;
import hezix.org.shaudifydemo1.entity.user.Role;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.UserFile;
import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import hezix.org.shaudifydemo1.entity.user.dto.ReadUserDTO;
import hezix.org.shaudifydemo1.entity.user.dto.UserFileDTO;
import hezix.org.shaudifydemo1.entity.user.dto.UserFormDTO;
import hezix.org.shaudifydemo1.exception.PasswordAndPasswordConfirmationNotEquals;
import hezix.org.shaudifydemo1.exception.EntityNotFoundException;
import hezix.org.shaudifydemo1.mapper.ReadSongMapper;
import hezix.org.shaudifydemo1.mapper.ReadUserMapper;
import hezix.org.shaudifydemo1.mapper.UserFileMapper;
import hezix.org.shaudifydemo1.mapper.UserMapper;
import hezix.org.shaudifydemo1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ReadSongMapper readSongMapper;
    private final ReadUserMapper readUserMapper;
    private final MinioService minioService;
    private final UserFileMapper userFileMapper;

    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getById", key = "#id")
    public ReadUserDTO findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id: " + id));
        List<ReadSongDTO> songs = user.getAuthoredSongs()
                .stream()
                .map(readSongMapper::toDto)
                .toList();
        ReadUserDTO readUserDTO = readUserMapper.toDto(user);
        readUserDTO.setAuthoredSongs(songs);
        if(readUserDTO.getImage() != null) {
            String imageUrl = minioService.getPresignedUrl("images", user.getImage());
            readUserDTO.setImageUrl(imageUrl);
        }
        return readUserDTO;
    }
    @Transactional(readOnly = true)
    public User findUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User Entity not found by id: " + id));
    }
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getByUsername", key = "#username")
    public Optional<User> findUserEntityByUsername(String username) {
        return userRepository.findByUsername(username);
    }
    @Cacheable(value = "UserService::getByUsername", key = "#username")
    @Transactional(readOnly = true)
    public ReadUserDTO findUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found by username: " + username));
        List<ReadSongDTO> songs = user.getAuthoredSongs()
                .stream()
                .map(readSongMapper::toDto)
                .toList();
        ReadUserDTO readUserDTO = readUserMapper.toDto(user);
        readUserDTO.setAuthoredSongs(songs);
        return readUserDTO;
    }
    @Transactional(readOnly = true)
    public List<ReadUserDTO> findAllUsers() {
        List<ReadUserDTO> arr = userRepository.findAll()
                .stream()
                .map(readUserMapper::toDto)
                .toList();
        for (ReadUserDTO user : arr) {
            if(user.getImage() != null) {
                String imageUrl = minioService.getPresignedUrl("images", user.getImage());
                user.setImageUrl(imageUrl);
            }
        }
        return arr;
    }

    @Transactional
    public ReadUserDTO save(CreateUserDTO createUserDTO) {
        if (createUserDTO.getPassword().equals(createUserDTO.getPasswordConfirmation())) {
            User user = userMapper.toEntity(createUserDTO);
            user.setRole(Role.ROLE_USER);
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
            return readUserMapper.toDto(user);
        } else {
            log.error("Password : {} , Password Confirmation: {}", createUserDTO.getPassword(), createUserDTO.getPasswordConfirmation());
            throw new PasswordAndPasswordConfirmationNotEquals("Password and password confirmation cannot be same");
        }
    }
    @Transactional
    @Caching(put = {
            @CachePut(value = "UserService::getById", key = "#user.id"),
            @CachePut(value = "UserService::getByUsername", key = "#user.username"),
    })
    public User update(User user) {
        return userRepository.save(user);
    }

    @Transactional
    @CacheEvict(value = "UserService::getById", key = "#id")
    public void delete(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User for delete not found by id: " + id);
        }
    }
    @Transactional
    public void uploadImage(Long id, UserFileDTO userFileDTO) {
        UserFile userFile = userFileMapper.toEntity(userFileDTO);
        User user = findUserEntityById(id);
        String filename = minioService.uploadUserImage(userFile);
        user.setImage(filename);
        userRepository.save(user);
    }
}
