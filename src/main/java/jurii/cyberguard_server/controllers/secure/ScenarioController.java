package jurii.cyberguard_server.controllers.secure;

import jurii.cyberguard_server.DTO.ScenarioDTO;
import jurii.cyberguard_server.entity.Scenario;
import jurii.cyberguard_server.repo.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scenarios")
@CrossOrigin(origins = "*")
public class ScenarioController {

    @Autowired
    private ScenarioRepository scenarioRepository;

    @PostMapping
    public ResponseEntity<?> createScenario(@RequestBody ScenarioDTO scenarioDTO) {
        try {
            Scenario scenario = new Scenario();
            scenario.setName(scenarioDTO.getName());
            scenario.setTitle(scenarioDTO.getTitle());
            scenario.setType(scenarioDTO.getType());
            // Если в Entity поле content типа String, конвертируем Map в String через ObjectMapper
            // Либо сохраняем как есть, если настроен JSON-тип в БД
            scenario.setContent(scenarioDTO.getContent());

            scenarioRepository.save(scenario);
            return ResponseEntity.ok("Операция успешно добавлена в реестр");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка при сохранении: " + e.getMessage());
        }
    }

    @GetMapping
    public List<Scenario> getAllScenarios() {
        return scenarioRepository.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Scenario> getScenarioByName(@PathVariable String name) {
        // Ищем в репозитории по полю name
        return scenarioRepository.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Добавь это в ScenarioController.java
    @GetMapping("/type/{type}")
    public List<Scenario> getScenariosByType(@PathVariable String type) {
        return scenarioRepository.findByType(type);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateScenario(
            @PathVariable Long id,
            @RequestBody Scenario updatedScenario
    ) {

        return scenarioRepository.findById(id)
                .map(scenario -> {

                    scenario.setName(updatedScenario.getName());
                    scenario.setTitle(updatedScenario.getTitle());
                    scenario.setType(updatedScenario.getType());
                    scenario.setContent(updatedScenario.getContent());

                    scenarioRepository.save(scenario);

                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}