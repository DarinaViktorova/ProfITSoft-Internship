package my.space.ui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import my.space.logic.JsonParser;
import my.space.logic.MissionService;
import my.space.logic.SpaceshipService;
import my.space.models.Mission;
import my.space.utils.FileUtil;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static my.space.utils.Constants.*;

public class ConsoleUI {

    private final Scanner scanner;

    private final JsonParser jsonParser = new JsonParser();
    private final MissionService missionService = new MissionService(threadPoolSize);
    private final SpaceshipService spaceshipService = new SpaceshipService();

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    Map<String, Integer> attributeCountMap = new HashMap<>();

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
    }

    public void displayMenu() {
        System.out.println("=== Mission Control Menu ===");
        System.out.println("1. Add a new mission");
        System.out.println("2. Display all missions");
        System.out.println("3. Get statistics");
        System.out.println("4. Exit");
    }

    public void handleUserChoice(String choice) {
        switch (choice) {
            case "1":
                addMission();
                break;
            case "2":
                displayMissions();
                break;
            case "3":
                displayStatistics(attributeCountMap);
                break;
            case "4":
                closeScanner();
                executorService.shutdown();
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
    }

    /**
     * Executes the main loop of the program, continuously displaying the menu
     * and handling user input until the user chooses to exit.
     */
    public void run() {
        boolean running = true;
        while (running) {
            displayMenu();
            System.out.print("Choose an option: ");
            String choice = scanner.nextLine();
            handleUserChoice(choice.trim());
        }
    }


    /**
     * Adds a new mission to a selected JSON file.
     * Prompts the user to choose a JSON file from a directory and then
     * collects information about the new mission including planet name and year of flight.
     * If the chosen JSON file does not exist or is invalid, it displays an error message.
     * After adding the mission information to the selected JSON file, it saves the updated
     * missions list back to the JSON file in a formatted manner using Gson.
     */
    private void addMission() {

        // Get a list of JSON files in the specified directory
        File[] jsonFiles = PATH_TO_JSON_FILE.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (jsonFiles == null || jsonFiles.length == 0) {
            System.out.println("No JSON files found in the directory: " + PATH_TO_JSON_FILE.getAbsolutePath());
            return;
        }

        // Display the list of files for the user to choose from
        System.out.println("Available JSON files:");
        for (int i = 0; i < jsonFiles.length; i++) {
            System.out.println((i + 1) + ". " + jsonFiles[i].getName());
        }

        System.out.print("Choose a JSON file to add mission information to (enter number): ");
        int fileIndex;
        try {
            fileIndex = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid file number.");
            return;
        }

        if (fileIndex < 0 || fileIndex >= jsonFiles.length) {
            System.out.println("Invalid file selection.");
            return;
        }

        String filePath = jsonFiles[fileIndex].getPath();

        String newPlanetName = getUserInput("\nInput a planet name: ");
        int missionYear;
        try {
            missionYear = Integer.parseInt(getUserInput("Input a year of flight: "));
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a valid year.");
            return;
        }

        List<Mission> missions = jsonParser.loadMissionsFromFile(filePath);

        Mission existingMission = missionService.findMissionByPlanetAndYear(missions, newPlanetName, missionYear);

        if (existingMission == null) {
            Mission newMission = new Mission(newPlanetName, new ArrayList<>(), missionYear);
            addSpaceshipsToMission(newMission);
            missions.add(newMission);
        } else addSpaceshipsToMission(existingMission);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (Writer writer = new FileWriter(jsonFiles[fileIndex])) {
            gson.toJson(missions, writer);
            System.out.println("Mission information added to file: " + FileUtil.getRelativePath(jsonFiles[fileIndex]));
        } catch (IOException e) {
            System.err.println("Error saving missions to JSON file: " + e.getMessage());
        }
    }

    /**
     * Allows the user to add spaceships to a given mission interactively.
     * The user is prompted to input spaceship names and capacities until they enter 'done'.
     *
     * @param mission The mission to which spaceships are being added.
     */
    private void addSpaceshipsToMission(Mission mission) {
        boolean addingSpaceships = true;
        while (addingSpaceships) {
            String spaceshipName = getUserInput("Input a spaceship's name (or 'done' to finish adding spaceships): ");
            if (spaceshipName.equalsIgnoreCase(DONE_COMMAND)) {
                addingSpaceships = false;
            } else {
                String spaceShipCapacity = getUserInput("Input spaceship capacity: ");
                spaceshipService.addSpaceshipToMission(mission, spaceshipName, spaceShipCapacity);
            }
        }
    }

    /**
     * Displays missions from JSON files located in the specified directory.
     * Allows the user to choose a JSON file and displays missions from that file.
     * If no JSON files are found in the directory, it notifies the user.
     * After displaying missions, it checks if any missions are present and prints them to the console,
     * or notifies the user if no missions are found in the selected JSON file.
     */
    void displayMissions() {

        File[] jsonFiles = PATH_TO_JSON_FILE.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (jsonFiles == null || jsonFiles.length == 0) {
            System.out.println("No JSON files found in the directory: " + PATH_TO_JSON_FILE);
            return;
        }

        System.out.println("Available JSON files:");
        for (int i = 0; i < jsonFiles.length; i++) {
            System.out.println((i + 1) + ". " + jsonFiles[i].getName());
        }

        System.out.print("Choose a JSON file to display missions from (enter number): ");
        int fileIndex = Integer.parseInt(scanner.nextLine()) - 1;

        if (fileIndex < 0 || fileIndex >= jsonFiles.length) {
            System.out.println("Invalid file selection.");
            return;
        }

        String filePath = jsonFiles[fileIndex].getPath();

        List<Mission> missions = jsonParser.loadMissionsFromFile(filePath);

        if (!missions.isEmpty()) {
            System.out.println("Displaying missions from file: " + jsonFiles[fileIndex].getName());
            missions.forEach(mission -> System.out.println(mission.toStringMission()));
        } else {
            System.out.println("No missions found in the selected JSON file.");
        }
    }


    /**
     * Displays mission statistics based on the specified attribute.
     * Prompts the user to enter an attribute (planetName, missionYear, spaceshipCount),
     * generates statistics using the MissionService, and prints the statistics to the console.
     */
    private void displayStatistics (Map<String, Integer> attributeCountMap) {
        String attribute = getUserInput("Enter attribute for mission statistics (planetName, missionYear, spaceshipCount): ");
        Map<String, Integer> attributeStatistics = missionService.generateMissionStatistics(attribute);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Display statistics on console? (yes/no): ");
            String response = scanner.nextLine().trim().toLowerCase();

            if (response.equals("yes")) {
                missionService.displayStatisticsOnConsole(attributeCountMap);
                attributeStatistics.forEach((value, count) -> {
                    System.out.println("Attribute Value: " + value + ", Count: " + count);
                });
                break;
            } else if (response.equals("no")) {
                System.out.println("Statistics display declined.");
                break;
            } else {
                System.out.println("Invalid response. Please enter 'yes' or 'no'.");
            }
        }

    }

    /**
     * Prompts the user with the given message and retrieves input from the console.
     *
     * @param prompt The message displayed to the user as a prompt.
     * @return The input string provided by the user.
     */
    public String getUserInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }

    /**
     * Closes the underlying scanner to release system resources.
     */
    public void closeScanner() {
        scanner.close();
    }
}
