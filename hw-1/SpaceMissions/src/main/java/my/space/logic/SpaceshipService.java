package my.space.logic;

import my.space.models.Mission;
import my.space.models.Spaceship;

public class SpaceshipService {

    /**
     * Adds a new spaceship to the specified mission with the provided spaceship name and capacity.
     *
     * @param mission        The mission to which the spaceship will be added.
     * @param spaceshipName  The name of the spaceship to add.
     * @param spaceShipCapacity  The capacity of the spaceship to add.
     */
    public void addSpaceshipToMission(Mission mission, String spaceshipName, String spaceShipCapacity) {
        int capacity = Integer.parseInt(spaceShipCapacity);
        Spaceship newSpaceship = new Spaceship(spaceshipName, mission.getPlanetName(), capacity);
        mission.addSpaceship(newSpaceship);
    }
}
