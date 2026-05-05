package jurii.cyberguard_server.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class UpdateNameRequest {
    private String name;
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}