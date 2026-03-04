package jurii.cyberguard_server.repo;

import jurii.cyberguard_server.entity.Points;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPointsRepository extends JpaRepository<Points, Long> {
    Points save(Points points);
}
