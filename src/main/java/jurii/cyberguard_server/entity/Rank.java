package jurii.cyberguard_server.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "ranks")
@Getter
@Setter
public class Rank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name = "min_points")
    private Integer minPoints;

    @Column(name = "icon_name")
    private String iconName;

    @OneToOne
    @JoinColumn(name = "next_rank_id")
    private Rank nextLvl;
}