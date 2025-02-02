package hezix.org.shaudifydemo1.service;

import hezix.org.shaudifydemo1.entity.song.Song;
import hezix.org.shaudifydemo1.entity.user.Role;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import hezix.org.shaudifydemo1.exception.PasswordAndPasswordConfirmationNotEquals;
import hezix.org.shaudifydemo1.exception.EntityNotFoundException;
import hezix.org.shaudifydemo1.mapper.UserMapper;
import hezix.org.shaudifydemo1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.context.annotation.Lazy;
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

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found by id: " + id));
    }

    @Transactional(readOnly = true)
    public User findUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User not found by username: " + username));
    }
    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
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
    public User update(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        if(userRepository.existsById(id)) {
            userRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException("User for delete not found by id: " + id);
        }
    }
}
