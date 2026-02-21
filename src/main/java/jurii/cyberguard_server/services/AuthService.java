package jurii.cyberguard_server.services;

import jurii.cyberguard_server.DTO.Registration;
import jurii.cyberguard_server.entity.Role;
import jurii.cyberguard_server.entity.User;
import jurii.cyberguard_server.repo.UserRepesitory;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepesitory userRepesitory;

    public AuthService(UserRepesitory userRepesitory){
        this.userRepesitory = userRepesitory;
    }

    public User registration(Registration request){
        User user = new User();

        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setRole(Role.USER);

        return userRepesitory.save(user);
    }
}
