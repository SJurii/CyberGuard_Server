package jurii.cyberguard_server.repo;

import jurii.cyberguard_server.entity.Scenario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

// ScenarioRepository.java
public interface ScenarioRepository extends JpaRepository<Scenario, Long> {
    // Поиск всех сценариев конкретного типа (например, 'Email')
    List<Scenario> findAllByType(String type);
    List<Scenario> findByType(String type);

    // Поиск конкретного сценария по его техническому имени
    Optional<Scenario> findByName(String name);

}