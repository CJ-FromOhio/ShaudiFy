package hezix.org.shaudifydemo1.service;

import hezix.org.shaudifydemo1.entity.user.Role;
import hezix.org.shaudifydemo1.entity.user.User;
import hezix.org.shaudifydemo1.entity.user.dto.CreateUserDTO;
import hezix.org.shaudifydemo1.exception.PasswordAndPasswordConfirmationNotEquals;
import hezix.org.shaudifydemo1.exception.EntityNotFoundException;
import hezix.org.shaudifydemo1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final SongService songService;

    @Transactional(readOnly = true)
    public User findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found by id: " + id));
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User save(CreateUserDTO createUserDTO) {
        if (createUserDTO.password().equals(createUserDTO.passwordConfirmation())) {
            User user = User.builder()
                    .username(createUserDTO.username())
                    .password(createUserDTO.password())
                    .role(Role.ROLE_USER)
                    .email(createUserDTO.email())
                    .description(createUserDTO.desctiprion())

                    .build();

            return userRepository.save(user);
        } else {
            log.error("Password : {} , Password Confirmation: {}", createUserDTO.password(), createUserDTO.passwordConfirmation());
            throw new PasswordAndPasswordConfirmationNotEquals("Password and password confirmation cannot be same");
        }
    }

    @Transactional
    public User update(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
