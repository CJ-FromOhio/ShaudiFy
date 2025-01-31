package hezix.org.shaudifydemo1.repository;

import hezix.org.shaudifydemo1.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    User save(User user);
}
