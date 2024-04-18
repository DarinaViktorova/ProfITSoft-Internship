package my.space.models;

import java.util.List;

public class Mission {

    private String planetName;
    private int missionYear;
    private List<Spaceship> spaceships;

    private String attributeValue;

    public Mission(String planetName, List<Spaceship> spaceshipsList, int missionYear) {
        this.planetName = planetName;
        this.missionYear = missionYear;
        this.spaceships = spaceshipsList;
    }


    public String getPlanetName() {
        return planetName;
    }

    public void setPlanetName(String planetName) {
        this.planetName = planetName;
    }

    public int getMissionYear() {
        return missionYear;
    }

    public void setMissionYear(int missionYear) {
        this.missionYear = missionYear;
    }

    public List<Spaceship> getSpaceships() {
        return spaceships;
    }

    public void addSpaceship(Spaceship spaceship) {
        spaceships.add(spaceship);
    }

    public void removeSpaceship(Spaceship spaceship) {
        spaceships.remove(spaceship);
    }

    public String getAttributeValue(String attribute) {
        switch (attribute) {
            case "planetName":
                return planetName != null ? planetName : "";
            case "missionYear":
                return String.valueOf(missionYear);
            case "spaceshipCount":
                return String.valueOf(spaceships.size());
            default:
                return "";
        }
    }

    public String toStringMission() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mission to ").append(planetName).append(" (Year: ").append(missionYear).append(")\n");
        sb.append("Spaceships involved:\n");
        for (Spaceship spaceship : spaceships) {
            sb.append("- ")
                    .append(spaceship.getSpaceshipName())
                    .append(" (Destination: ")
                    .append(spaceship.getDestinationPlanet())
                    .append("; Capacity: ")
                    .append(spaceship.getCapacity())
                    .append(")\n");
        }
        return sb.toString();
    }
}
