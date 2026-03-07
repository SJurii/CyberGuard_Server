package jurii.cyberguard_server.services;

import jakarta.transaction.Transactional;
import jurii.cyberguard_server.entity.*;
import jurii.cyberguard_server.repo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class ScoreService {

    @Autowired private UserRepesitory userRepesitory;
    @Autowired private UserPointsRepository userPointsRepository;
    @Autowired private RankRepository rankRepository;
    @Autowired private AchivementRepository achivementRepository;
    @Autowired private UserAchivementRepository userAchivementRepository;

    public List<AchievementsDirectory> addPoints(Long userId, Integer points, String reason) {
        User user = userRepesitory.findById(userId).orElseThrow();

        Points histore = new Points(user, points, reason);
        userPointsRepository.save(histore);

        user.setTotalPoints(user.getTotalPoints() + points);

        Rank newRank = rankRepository.findFirstByMinPointsLessThanEqualOrderByMinPointsDesc(user.getTotalPoints());

        if(newRank != null && !newRank.equals(user.getRank())) {
            user.setRank(newRank);
        }

        userRepesitory.save(user);

        List<AchievementsDirectory> newAchievements = new ArrayList<>();
        List<AchievementsDirectory> locked = achivementRepository.findLockedAchievementsForUser(userId);

        for (AchievementsDirectory ach : locked) {
            if (user.getTotalPoints() >= ach.getMinPointer()) {
                UserAchivement grant = new UserAchivement();
                grant.setUserId(user.getId());
                grant.setAchivementId(ach.getId());
                grant.setEarnedAt(LocalDateTime.now());
                userAchivementRepository.save(grant);
                newAchievements.add(ach);
            }
        }

        return newAchievements;
    }


}
