package jurii.cyberguard_server.repo;

import jurii.cyberguard_server.entity.UserAchivement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserAchivementRepository extends JpaRepository<UserAchivement, Long> {
    UserAchivement save(UserAchivement userAchivement);
    List<UserAchivement> findAllByUserId(Long userId);
}
