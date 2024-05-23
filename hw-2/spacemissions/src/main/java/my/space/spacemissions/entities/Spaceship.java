package my.space.spacemissions.entities;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
public class Spaceship {

    @ManyToOne
    @JoinColumn(name = "mission_id")
    @JsonBackReference
    private Mission mission;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "spaceship_name", nullable = false, unique = true)
    private String spaceshipName;

    @Column(name = "destination_planet", nullable = false)
    private String destinationPlanet;

    @Column(name = "capacity")
    private int capacity;

    // Constructors
    public Spaceship() {}

    public Spaceship(String spaceshipName, String destinationPlanet, int capacity) {
        this.spaceshipName = spaceshipName;
        this.destinationPlanet = destinationPlanet;
        this.capacity = capacity;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSpaceshipName() {
        return spaceshipName;
    }

    public void setSpaceshipName(String spaceshipName) {
        this.spaceshipName = spaceshipName;
    }

    public String getDestinationPlanet() {
        return destinationPlanet;
    }

    public void setDestinationPlanet(String destinationPlanet) {
        this.destinationPlanet = destinationPlanet;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public Mission getMission() {
        return mission;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }
}
