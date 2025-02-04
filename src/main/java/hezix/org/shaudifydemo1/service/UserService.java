package hezix.org.shaudifydemo1.service;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.song.dto.ReadSongDTO;
import hezix.org.shaudifydemo1.entity.user.Role;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import hezix.org.shaudifydemo1.entity.user.dto.ReadUserDTO;
import hezix.org.shaudifydemo1.exception.PasswordAndPasswordConfirmationNotEquals;
import hezix.org.shaudifydemo1.exception.EntityNotFoundException;
import hezix.org.shaudifydemo1.mapper.ReadSongMapper;
import hezix.org.shaudifydemo1.mapper.ReadUserMapper;
import hezix.org.shaudifydemo1.mapper.UserMapper;
import hezix.org.shaudifydemo1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ReadSongMapper readSongMapper;
    private final ReadUserMapper readUserMapper;

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
        return readUserDTO;
    }
    @Transactional(readOnly = true)
    public User findUserEntityById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found by id: " + id));
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
        List<ReadUserDTO> list = userRepository.findAll()
                .stream()
                .map(readUserMapper::toDto)
                .toList();
        return list;
    }

    @Transactional
    public User save(CreateUserDTO createUserDTO) {
        if (createUserDTO.getPassword().equals(createUserDTO.getPasswordConfirmation())) {
            User user = userMapper.toEntity(createUserDTO);
            user.setRole(Role.ROLE_USER);
            user.setCreatedAt(LocalDateTime.now());

            return userRepository.save(user);
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
}
