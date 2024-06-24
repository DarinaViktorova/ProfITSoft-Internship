package my.space.spacemissions.dto;

import my.space.spacemissions.entities.Spaceship;

import java.util.List;

public class MissionUpdateRequest {
    private String planetName;
    private Integer missionYear;
    private List<Spaceship> spaceships;

    // Getters and setters
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
        this.spaceships = spaceships;
    }

    public static class SpaceshipDTO {
        private String spaceshipName;
        private String destinationPlanet;
        private int capacity;

        public SpaceshipDTO(String redRocket, String jupiter, int i) {
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
    }
}
