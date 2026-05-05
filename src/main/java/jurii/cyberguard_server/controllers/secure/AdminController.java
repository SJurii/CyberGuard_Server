package jurii.cyberguard_server.controllers.secure;

import jurii.cyberguard_server.entity.User;
import jurii.cyberguard_server.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController {

    @Autowired
    private UserService userService;

    // Метод для обновления роли пользователя
    @PatchMapping("/{id}/role")
// Доступ разрешен только тем, у кого есть ROLE_ADMIN в токене
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> changeUserRole(
            @PathVariable Long id,
            @RequestBody Map<String, String> payload,
            java.security.Principal principal
    ) {
        // 1. Находим админа по email из Principal
        String adminEmail = principal.getName();
        User admin = userService.findByEmail(adminEmail);

        if (admin == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Администратор не найден");
        }

        String newRole = payload.get("role");

        // 2. Валидация входных данных
        if (!"ADMIN".equals(newRole) && !"USER".equals(newRole)) {
            return ResponseEntity.badRequest().body("Недопустимая роль");
        }

        try {
            // 3. Используем admin.getId() вместо несуществующего currentUser
            userService.updateUserRole(id, newRole, admin.getId());
            return ResponseEntity.ok().build();
        } catch (RuntimeException e) {
            // Обработка ошибки "Вы не можете снять права с самого себя"
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Ошибка сервера");
        }
    }
}