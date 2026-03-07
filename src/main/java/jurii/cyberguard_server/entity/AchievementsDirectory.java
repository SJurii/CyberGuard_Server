package jurii.cyberguard_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "achivements_directory")
@Getter
@Setter
public class AchievementsDirectory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, name = "achivement_name")
    private String achievementName;

    private String title;
    private String description;

    @Column(name = "min_pointer")
    private Integer minPointer;

    @Column(name = "icon_name")
    private String iconName;
}
