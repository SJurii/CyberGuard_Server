package jurii.cyberguard_server.services;

import jakarta.transaction.Transactional;
import jurii.cyberguard_server.DTO.Registration;
import jurii.cyberguard_server.entity.Role;
import jurii.cyberguard_server.entity.User;
import jurii.cyberguard_server.repo.UserRepesitory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class AuthService {
    private final UserRepesitory userRepesitory;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepesitory userRepesitory, PasswordEncoder passwordEncoder){
        this.userRepesitory = userRepesitory;
        this.passwordEncoder = passwordEncoder;
    }

    public User registration(Registration request){
        User user = new User();

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);
        user.setRole(Role.USER);

        return userRepesitory.save(user);
    }

}
