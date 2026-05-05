package jurii.cyberguard_server.DTO;

import lombok.Data;
import java.util.Map;

@Data
public class ScenarioDTO {
    private String name;
    private String title;
    private String type;
    private Map<String, Object> content; // Используем Map для гибкости JSON-структуры
}