package jurii.cyberguard_server.services;

import jakarta.transaction.Transactional;
import jurii.cyberguard_server.entity.Points;
import jurii.cyberguard_server.entity.Rank;
import jurii.cyberguard_server.entity.User;
import jurii.cyberguard_server.repo.RankRepository;
import jurii.cyberguard_server.repo.UserPointsRepository;
import jurii.cyberguard_server.repo.UserRepesitory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class ScoreService {

    @Autowired private UserRepesitory userRepesitory;
    @Autowired private UserPointsRepository userPointsRepository;
    @Autowired private RankRepository rankRepository;

    public User addPoints(Long userId, Integer points, String reason) {
        User user = userRepesitory.findById(userId).orElseThrow();

        Points histore = new Points(user, points, reason);
        userPointsRepository.save(histore);

        user.setTotalPoints(user.getTotalPoints() + points);

        Rank newRank = rankRepository.findFirstByMinPointsLessThanEqualOrderByMinPointsDesc(user.getTotalPoints());

        if(newRank != null && !newRank.equals(user.getRank())) {
            user.setRank(newRank);
        }

        return userRepesitory.save(user);
    }
}
