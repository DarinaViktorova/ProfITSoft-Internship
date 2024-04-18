package my.space.models;

public class Spaceship {

    private String spaceshipName;
    private String destinationPlanet;
    private int capacity;

    public Spaceship(String spaceshipName, String destinationPlanet, int capacity) {
        this.spaceshipName = spaceshipName;
        this.destinationPlanet = destinationPlanet;
        this.capacity = capacity;
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