package jurii.cyberguard_server.services;

import jakarta.transaction.Transactional;
import jurii.cyberguard_server.DTO.Registration;
import jurii.cyberguard_server.entity.Rank;
import jurii.cyberguard_server.entity.Role;
import jurii.cyberguard_server.entity.User;
import jurii.cyberguard_server.repo.RankRepository;
import jurii.cyberguard_server.repo.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Transactional
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RankRepository rankRepository;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, RankRepository rankRepository){
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.rankRepository = rankRepository;
    }

    public User registration(Registration request){
        User user = new User();
        Rank rank = rankRepository.getRankById(1);

        String hashedPassword = passwordEncoder.encode(request.getPassword());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(hashedPassword);
        user.setRole(Role.USER);
        user.setTotalPoints(0);
        user.setRank(rank);

        return userRepository.save(user);
    }

}
