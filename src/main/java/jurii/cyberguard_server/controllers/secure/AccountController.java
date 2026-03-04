package jurii.cyberguard_server.controllers.secure;


import jurii.cyberguard_server.DTO.ScoreRequest;
import jurii.cyberguard_server.entity.User;
import jurii.cyberguard_server.repo.UserRepesitory;
import jurii.cyberguard_server.services.ScoreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/profile")
public class AccountController {

    private final UserRepesitory userRepesitory;
    private final ScoreService scoreService;

    public AccountController(UserRepesitory userRepesitory, ScoreService scoreService) {
        this.userRepesitory = userRepesitory;
        this.scoreService = scoreService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        return userRepesitory.findById(id)
                .map(user -> ResponseEntity.ok(Map.of(
                        "name", user.getName(),
                        "email", user.getEmail(),
                        "rank", user.getRank(),
                        "totalPoints", user.getTotalPoints(),
                        "nextRankPoints", user.getRank().getNextLvl().getMinPoints(),
                        "createdAt", user.getCreatedAt(),
                        "achievements", List.of()
                )))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/addPoints")
    public ResponseEntity<?> addPointsController(@PathVariable Long id, @RequestBody ScoreRequest request) {
        scoreService.addPoints(id, request.getPoints(), request.getReason());
        return ResponseEntity.ok(userRepesitory.findById(id));
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<List<User>> getLeaderBoard() {
        List<User> users = userRepesitory.findAll();
        return ResponseEntity.ok(users);
    }
}
