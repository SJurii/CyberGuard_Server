package jurii.cyberguard_server.controllers.common;

import jurii.cyberguard_server.DTO.Registration;
import jurii.cyberguard_server.entity.Role;
import jurii.cyberguard_server.entity.User;
import jurii.cyberguard_server.services.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class RegistrationController {

    private final AuthService authService;

    public RegistrationController(AuthService service){
        this.authService = service;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Registration request){
        System.out.println(request.getEmail());

        authService.registration(request);

        return ResponseEntity.ok("User registered");
    }
}
