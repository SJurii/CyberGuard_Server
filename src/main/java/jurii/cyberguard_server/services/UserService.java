package jurii.cyberguard_server.services;

import jakarta.transaction.Transactional;
import jurii.cyberguard_server.entity.Role;
import jurii.cyberguard_server.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import jurii.cyberguard_server.repo.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public void updateUserRole(Long userId, String roleName, Long currentAdminId) {
        // 1. Проверка на саморазжалование
        if (userId.equals(currentAdminId) && roleName.equalsIgnoreCase("USER")) {
            throw new RuntimeException("Вы не можете снять права администратора с самого себя");
        }

        // 2. Поиск пользователя
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        try {
            // 3. Преобразование строки в Enum Role
            Role newRole = Role.valueOf(roleName.toUpperCase());

            // 4. Сохранение
            user.setRole(newRole);
            userRepository.save(user);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Указанная роль не существует: " + roleName);
        }
    }
}
