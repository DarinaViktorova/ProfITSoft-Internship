package my.space.spacemissions.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Mission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "planet_name", nullable = false)
    private String planetName;

    @Column(name = "mission_year", nullable = false)
    private Integer missionYear;

    @OneToMany(mappedBy = "mission", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Spaceship> spaceships = new ArrayList<>();

    // Constructors
    public Mission() {}

    public Mission(String planetName, Integer missionYear) {
        this.planetName = planetName;
        this.missionYear = missionYear;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public Integer getMissionYear() {
        return missionYear;
    }

    public void setMissionYear(Integer missionYear) {
        this.missionYear = missionYear;
    }

    public List<Spaceship> getSpaceships() {
        return spaceships;
    }

    public void setSpaceships(List<Spaceship> spaceships) {
        this.spaceships.clear();
        if (spaceships != null) {
            this.spaceships.addAll(spaceships);
        }
    }

    public void addSpaceship(Spaceship spaceship) {
        spaceships.add(spaceship);
        spaceship.setMission(this);
    }

    public void removeSpaceship(Spaceship spaceship) {
        spaceships.remove(spaceship);
        spaceship.setMission(null);
    }
}
