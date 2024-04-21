# Space Mission Control System <br/>
The Space Mission Control System is a Java-based application designed to manage and analyze space mission data stored in JSON format. This system provides functionality to add new missions, display mission details, and generate mission statistics based on various attributes.
## Entities
### 1. Mission
Represents a space mission with the following attributes:
- Planet Name: The name of the planet associated with the mission.
- Mission Year: The year in which the mission takes place or took place.
- Spaceships: A list of spaceships assigned to the mission.
### 2. Spaceship
Represents a spaceship used in a space mission with the following attributes:
- Spactship Name:  The name or identifier of the spaceship.
- Destination Planet: The destination planet assigned to the spaceship.
- Capacity: The capacity or passenger limit of the spaceship.
## Key Features
- Add New Mission: Users can add new missions by providing details such as planet name, mission year, and associated spaceships.
- Display All Missions: View detailed information about all missions stored in the system.
- Get Statistics: Generate mission statistics based on attributes like planet name, mission year, or spaceship count across multiple missions.
## Getting Started
To use this application, ensure you have Java installed on your system. Clone this repository using Git:
```
git clone https://github.com/DarinaViktorova/ProfITSoft-Internship.git
```
Ensure that you have JDK 17 or a higher version installed on your system to successfully compile and run the application. <br/>
You can start working with app using `Main` class.
## Input/output examples files
### Input
For example, consider the following JSON file containing mission data:
```
[
  {
    "planetName": "Mars",
    "missionYear": 2025,
    "spaceships": [
      {
        "spaceshipName": "Red Rocket",
        "destinationPlanet": "Mars",
        "capacity": 10
      },
      {
        "spaceshipName": "Martian Explorer",
        "destinationPlanet": "Mars",
        "capacity": 15
      }
    ]
  },
  {
    "planetName": "Mars",
    "missionYear": 2029,
    "spaceships": [
      {
        "spaceshipName": "Red Rocket",
        "destinationPlanet": "Mars",
        "capacity": 10
      },
      {
        "spaceshipName": "Martian Explorer",
        "destinationPlanet": "Mars",
        "capacity": 15
      }
    ]
  },
  {
    "planetName": "Venus",
    "missionYear": 2030,
    "spaceships": [
      {
        "spaceshipName": "Solar Flyer",
        "destinationPlanet": "Venus",
        "capacity": 12
      }
    ]
  },
  {
    "planetName": "Jupiter",
    "missionYear": 2035,
    "spaceships": [
      {
        "spaceshipName": "Galactic Explorer",
        "destinationPlanet": "Jupiter",
        "capacity": 20
      },
      {
        "spaceshipName": "Star Voyager",
        "destinationPlanet": "Jupiter",
        "capacity": 15
      }
    ]
  }
]
```
### Output
After processing the data, the corrected statistics XML will appear as follows:
```
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<missionStatistics>
    <totalMissions>3</totalMissions>
    <totalSpaceships>0</totalSpaceships>
    <attributeStatistics>
        <entry>
            <key>Mars</key>
            <value>2</value>
        </entry>
        <entry>
            <key>Jupiter</key>
            <value>1</value>
        </entry>
        <entry>
            <key>Venus</key>
            <value>1</value>
        </entry>
    </attributeStatistics>
</missionStatistics>
```
