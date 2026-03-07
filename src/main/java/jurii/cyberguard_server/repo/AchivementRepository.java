package jurii.cyberguard_server.repo;

import jurii.cyberguard_server.entity.AchievementsDirectory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AchivementRepository extends JpaRepository<AchievementsDirectory, Long> {

    // ВАЖНО: nativeQuery = true позволяет писать чистый SQL
    // Проверь, чтобы имена таблиц (ad и ua) совпадали с твоими в БД
    @Query(value = "SELECT ad.* FROM achivements_directory ad " +
            "WHERE ad.id NOT IN ( " +
            "    SELECT ua.achivement_id FROM user_achivement ua " +
            "    WHERE ua.user_id = :uId" +
            ")", nativeQuery = true)
    List<AchievementsDirectory> findLockedAchievementsForUser(@Param("uId") Long userId);
}