package jurii.cyberguard_server.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_achivement")
@Getter
@Setter
public class UserAchivement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "user_id")
    private Long userId;

    @Column(nullable = false, name = "achivement_id")
    private Long achivementId;

    @Column(name = "earned_at")
    private LocalDateTime earnedAt = LocalDateTime.now();

}
