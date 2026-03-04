package jurii.cyberguard_server.DTO;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreRequest {
    private int points;
    private String reason;
}
