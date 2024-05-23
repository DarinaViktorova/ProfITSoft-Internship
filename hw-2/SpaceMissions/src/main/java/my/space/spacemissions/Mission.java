package my.space.spacemissions;

import jakarta.persistence.*;
import java.util.List;

@Entity
public class Mission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long missionId;

    @Column(nullable = false)
    private String missionName;

    @Column(nullable = false)
    private String destinationPlanet;

    @Column(nullable = false)
    private int missionYear;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Spaceship> spaceships;

    // Конструкторы, геттеры и сеттеры
}
