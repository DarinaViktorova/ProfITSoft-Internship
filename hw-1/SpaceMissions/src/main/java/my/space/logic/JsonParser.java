package my.space.logic;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import my.space.models.Mission;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class JsonParser {

    private final ExecutorService threadPool;
    int numThreads = 2;

    public JsonParser() {
        this.threadPool = Executors.newFixedThreadPool(numThreads);
    }


    /**
     * Loads mission data from a JSON file located at the specified path.
     *
     * @param filePath The path to the JSON file containing mission data.
     * @return A list of missions loaded from the JSON file.
     */
    public List<Mission> loadMissionsFromFile(String filePath) {
        List<Mission> missions = new ArrayList<>();
        File file = new File(filePath);

        if (file.exists()) {
            try (FileReader reader = new FileReader(file)) {
                Gson gson = new Gson();
                Mission[] missionArray = gson.fromJson(reader, Mission[].class);
                missions = new ArrayList<>(Arrays.asList(missionArray));
            } catch (IOException e) {
                System.err.println("Error reading missions from JSON file: " + e.getMessage());
            } catch (JsonSyntaxException | JsonIOException e) {
                System.err.println("Error parsing JSON file: " + e.getMessage());
            }
        } else {
            System.err.println("File not found: " + filePath);
        }

        return missions;
    }

}
