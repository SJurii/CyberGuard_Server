package jurii.cyberguard_server.repo;

import jurii.cyberguard_server.entity.AchievementsDirectory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AchievementRepository extends JpaRepository<AchievementsDirectory, Long> {
    AchievementsDirectory save(AchievementsDirectory achievementsDirectory);
}