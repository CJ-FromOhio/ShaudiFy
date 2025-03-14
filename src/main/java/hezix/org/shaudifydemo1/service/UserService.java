package hezix.org.shaudifydemo1.service;

import hezix.org.shaudifydemo1.entity.song.dto.ReadSongDTO;
import hezix.org.shaudifydemo1.entity.user.Role;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.UserFile;
import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import hezix.org.shaudifydemo1.entity.user.dto.ReadUserDTO;
import hezix.org.shaudifydemo1.entity.user.dto.UserFileDTO;
import hezix.org.shaudifydemo1.exception.PasswordAndPasswordConfirmationNotEquals;
import hezix.org.shaudifydemo1.exception.EntityNotFoundException;
import hezix.org.shaudifydemo1.mapper.ReadSongMapper;
import hezix.org.shaudifydemo1.mapper.ReadUserMapper;
import hezix.org.shaudifydemo1.mapper.UserFileMapper;
import hezix.org.shaudifydemo1.mapper.UserMapper;
import hezix.org.shaudifydemo1.repository.UserRepository;
import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

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
    private final PasswordEncoder passwordEncoder;
    @Timed("userS_findById")
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
    @Timed("userS_findEntityById")
    @Transactional(readOnly = true)
    public User findUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User Entity not found by id: " + id));
    }
    @Timed("userS_findEntityByUsername")
    @Transactional(readOnly = true)
    @Cacheable(value = "UserService::getByUsername", key = "#username")
    public User findUserEntityByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(()->new EntityNotFoundException(""));
    }
//    @Cacheable(value = "UserService::getByUsername", key = "#username")
    @Timed("userS_findByUsername")
    @Transactional(readOnly = true)
    public ReadUserDTO findUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User not found by username: " + username));
        List<ReadSongDTO> songs = user.getAuthoredSongs()
                .stream()
                .map(readSongMapper::toDto)
                .toList();
        ReadUserDTO readUserDTO = readUserMapper.toDto(user);
        if(readUserDTO.getImage() != null) {
            String imageUrl = minioService.getPresignedUrl("images", user.getImage());
            readUserDTO.setImageUrl(imageUrl);
        }
        readUserDTO.setAuthoredSongs(songs);
        return readUserDTO;
    }
    @Timed("userS_findAll")
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
    @Timed("userS_save")
    @Transactional
    public ReadUserDTO save(CreateUserDTO createUserDTO) {
        if (createUserDTO.getPassword().equals(createUserDTO.getPasswordConfirmation())) {
            User user = userMapper.toEntity(createUserDTO);
            user.setPassword(passwordEncoder.encode(createUserDTO.getPassword()));
            user.setRole(Role.ROLE_USER);
            user.setCreatedAt(LocalDateTime.now());
            userRepository.save(user);
            return readUserMapper.toDto(user);
        } else {
            log.error("Password : {} , Password Confirmation: {}", createUserDTO.getPassword(), createUserDTO.getPasswordConfirmation());
            throw new PasswordAndPasswordConfirmationNotEquals("Password and password confirmation cannot be same");
        }
    }
    @Timed("userS_update")
    @Transactional
    @Caching(put = {
            @CachePut(value = "UserService::getById", key = "#user.id"),
            @CachePut(value = "UserService::getByUsername", key = "#user.username"),
    })
    public User update(User user) {
        return userRepository.save(user);
    }
    @Timed("userS_delete")
    @Transactional
    @CacheEvict(value = "UserService::getById", key = "#id")
    public void delete(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User for delete not found by id: " + id);
        }
    }
    @Timed("userS_uploadImage")
    @Transactional
    public void uploadImage(Long id, UserFileDTO userFileDTO) {
        UserFile userFile = userFileMapper.toEntity(userFileDTO);
        User user = findUserEntityById(id);
        String filename = minioService.uploadUserImage(userFile);
        user.setImage(filename);
        userRepository.save(user);
    }
}
