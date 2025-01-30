package hezix.org.shaudifydemo1.repository;

import hezix.org.shaudifydemo1.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
