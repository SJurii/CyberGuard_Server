package jurii.cyberguard_server.controllers.secure;


import jurii.cyberguard_server.repo.UserRepesitory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import java.util.List;

@RestController
@RequestMapping("/api/profile") // <-- Проверь, чтобы было именно так
public class AccountController {

    private final UserRepesitory userRepesitory;

    public AccountController(UserRepesitory userRepesitory) {
        this.userRepesitory = userRepesitory;
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserProfile(@PathVariable Long id) {
        return userRepesitory.findById(id)
                .map(user -> ResponseEntity.ok(Map.of(
                        "name", user.getName(),
                        "email", user.getEmail(),
                        "rank", "Тестер",
                        "points", 1000,
                        "createdAt", user.getCreatedAt(),
                        "achievements", List.of() // Пока пустой список, чтобы React не упал
                )))
                .orElse(ResponseEntity.notFound().build());
    }
}
