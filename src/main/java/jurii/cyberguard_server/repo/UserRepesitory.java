package jurii.cyberguard_server.repo;

import jurii.cyberguard_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepesitory extends JpaRepository<User, Long> {
    User save(User user);
}
