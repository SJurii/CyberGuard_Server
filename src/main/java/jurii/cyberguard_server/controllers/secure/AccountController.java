package jurii.cyberguard_server.controllers.secure;


import jurii.cyberguard_server.DTO.ScoreRequest;
import jurii.cyberguard_server.entity.AchievementsDirectory;
import jurii.cyberguard_server.entity.User;
import jurii.cyberguard_server.entity.UserAchivement;
import jurii.cyberguard_server.repo.AchivementRepository;
import jurii.cyberguard_server.repo.UserAchivementRepository;
import jurii.cyberguard_server.repo.UserRepesitory;
import jurii.cyberguard_server.services.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class AccountController {

    private final UserRepesitory userRepesitory;
    private final ScoreService scoreService;
    private final AchivementRepository achivementRepository;
    private final UserAchivementRepository userAchivementRepository;

    public AccountController(UserRepesitory userRepesitory, ScoreService scoreService,
                             AchivementRepository achivementRepository,
                             UserAchivementRepository userAchivementRepository) {
        this.userRepesitory = userRepesitory;
        this.scoreService = scoreService;
        this.achivementRepository = achivementRepository;
        this.userAchivementRepository = userAchivementRepository;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        return userRepesitory.findById(id)
                .map(user -> {
                    // 1. Собираем детализированный список ачивок
                    List<Map<String, Object>> achievementsWithDetails = userAchivementRepository.findAllByUserId(id)
                            .stream()
                            .map(ua -> {
                                var details = achivementRepository.findById(ua.getAchivementId()).orElse(null);

                                // Создаем карту данных для фронтенда
                                // Используем HashMap, если какие-то поля могут быть null (Map.of не любит null)
                                Map<String, Object> achMap = new java.util.HashMap<>();
                                achMap.put("title", details != null ? details.getTitle() : "Секретная ачивка");
                                achMap.put("description", details != null ? details.getDescription() : "Описание скрыто");
                                achMap.put("icon", details != null ? details.getIconName() : "🏆");
                                achMap.put("earnedAt", ua.getEarnedAt());

                                return achMap;
                            }).toList();

                    // 2. Возвращаем полный профиль
                    return ResponseEntity.ok(Map.of(
                            "name", user.getName(),
                            "email", user.getEmail(),
                            "rank", user.getRank(),
                            "totalPoints", user.getTotalPoints(),
                            "nextRankPoints", user.getRank().getNextLvl().getMinPoints(),
                            "createdAt", user.getCreatedAt(),
                            "achievements", achievementsWithDetails // ИСПОЛЬЗУЕМ ОБРАБОТАННЫЙ СПИСОК
                    ));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/addPoints")
    public ResponseEntity<?> addPointsController(@PathVariable Long id, @RequestBody ScoreRequest request) {
        List<AchievementsDirectory> newAwards= scoreService.addPoints(id, request.getPoints(), request.getReason());

        User userUpdate = userRepesitory.findById(id).orElse(null);

        return ResponseEntity.ok(Map.of(
                "user", userUpdate,
                "newAchivement", newAwards
        ));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<User>> getLeaderBoard() {
        List<User> users = userRepesitory.findAll();
        return ResponseEntity.ok(users);
    }

}
