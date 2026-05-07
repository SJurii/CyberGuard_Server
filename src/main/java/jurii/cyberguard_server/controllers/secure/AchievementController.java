package jurii.cyberguard_server.controllers.secure;

import jurii.cyberguard_server.entity.AchievementsDirectory;
import jurii.cyberguard_server.repo.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/achievements")
public class AchievementController {

    @Autowired
    private AchievementRepository achievementRepository;

    @PostMapping
    public ResponseEntity<?> createAchievement(@RequestBody AchievementsDirectory achievement) {

        achievementRepository.save(achievement);

        return ResponseEntity.ok("Achievement created");
    }
}