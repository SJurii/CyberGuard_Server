package jurii.cyberguard_server.controllers.secure;

import jurii.cyberguard_server.entity.AchievementsDirectory;
import jurii.cyberguard_server.repo.AchievementRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/achievements")
public class AchievementController {

    @Autowired
    private AchievementRepository achievementRepository;

    @GetMapping
    public ResponseEntity<?> getAllAchievements() {

        return ResponseEntity.ok(
                achievementRepository.findAll()
        );
    }

    @PostMapping
    public ResponseEntity<?> createAchievement(
            @RequestBody AchievementsDirectory achievement
    ) {

        achievementRepository.save(achievement);

        return ResponseEntity.ok("Achievement created");
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateAchievement(
            @PathVariable Long id,
            @RequestBody AchievementsDirectory updatedAchievement
    ) {

        return achievementRepository.findById(id)
                .map(achievement -> {

                    achievement.setAchievementName(updatedAchievement.getAchievementName());
                    achievement.setTitle(updatedAchievement.getTitle());
                    achievement.setDescription(updatedAchievement.getDescription());
                    achievement.setMinPointer(updatedAchievement.getMinPointer());
                    achievement.setIconName(updatedAchievement.getIconName());

                    achievementRepository.save(achievement);

                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}