package jurii.cyberguard_server.controllers.common;

import org.springframework.web.bind.annotation.*;
import jurii.cyberguard_server.DTO.Login;
import jurii.cyberguard_server.DTO.Registration;
import jurii.cyberguard_server.entity.User;
import jurii.cyberguard_server.jwt.JwtTokenUnit;
import jurii.cyberguard_server.repo.UserRepository;
import jurii.cyberguard_server.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUnit jwtTokenUnit;
    private final UserRepository userRepository;

    public RegistrationController(AuthService service, PasswordEncoder passwordEncoder, JwtTokenUnit jwtTokenUnit, UserRepository userRepository){
        this.authService = service;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUnit = jwtTokenUnit;
        this.userRepository = userRepository;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Registration request){
        System.out.println(request.getEmail());

        authService.registration(request);

        return ResponseEntity.ok("User registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody Login request) {
        User user = userRepository.findByEmail(request.getEmail());

        if (user != null && passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtTokenUnit.generateToken(user);
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "id", user.getId(),
                    "username", user.getName(),
                    "email", user.getEmail(),
                    "userRole", user.getRole(),
                    "totalPoints", user.getTotalPoints(),
                    "nextRankPoints", user.getRank().getNextLvl().getMinPoints(),
                    "createdAt", user.getCreatedAt()
            ));
        } else {
            return ResponseEntity.status(401).body("Неверный логин или пароль");
        }
    }
}
