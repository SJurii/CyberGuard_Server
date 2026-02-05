package jurii.cyberguard_server.controllers.common;

import jurii.cyberguard_server.DTO.Registration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("/api/auth")
public class RegistrationController {

    @GetMapping("/registration")
    public ResponseEntity<?> register(@RequestBody Registration request){
        System.out.println(request.getEmail());
        return ResponseEntity.ok("User registered");
    }
}
