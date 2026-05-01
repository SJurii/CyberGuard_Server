package jurii.cyberguard_server.controllers.secure;

import jurii.cyberguard_server.entity.Scenario;
import jurii.cyberguard_server.repo.ScenarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scenarios")
@CrossOrigin(origins = "*")
public class ScenarioController {

    @Autowired
    private ScenarioRepository scenarioRepository;

    @GetMapping
    public List<Scenario> getAllScenarios() {
        return scenarioRepository.findAll();
    }

    @GetMapping("/{name}")
    public ResponseEntity<Scenario> getScenarioByName(@PathVariable String name) {
        return scenarioRepository.findByName(name)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/type/{type}")
    public List<Scenario> getScenariosByType(@PathVariable String type) {
        return scenarioRepository.findAllByType(type);
    }
}