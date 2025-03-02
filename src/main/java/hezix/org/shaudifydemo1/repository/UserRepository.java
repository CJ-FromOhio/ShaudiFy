package hezix.org.shaudifydemo1.repository;

import hezix.org.shaudifydemo1.entity.user.User;
import io.micrometer.core.annotation.Timed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    @Timed("userR_findingUserByUsername")
    Optional<User> findByUsername(String username);
    @Timed("userR_savingUser")
    User save(User user);
}
