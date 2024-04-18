package my.space.logic;

import my.space.models.Mission;
import my.space.statistic.MissionStatistics;
import my.space.utils.FileUtil;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static my.space.utils.Constants.PATH_TO_JSON_FILE;
import static my.space.utils.Constants.PATH_TO_XML_FILE;

public class MissionService {

    private final ExecutorService executorService;
    JsonParser jsonParser = new JsonParser();

    public MissionService(int threadPoolSize) {
        this.executorService = Executors.newFixedThreadPool(threadPoolSize);
    }

    /**
     * Generates mission statistics based on the specified attribute across all JSON files in the directory.
     * Returns a map containing attribute values and their corresponding counts.
     *
     * @param attribute The attribute (e.g., planetName, missionYear, spaceshipCount) for which statistics are generated.
     * @return A map containing attribute values and their corresponding counts across all JSON files.
     */
    public Map<String, Integer> generateMissionStatistics(String attribute) {
        Map<String, Integer> attributeCountMap = new HashMap<>();

        File[] jsonFiles = PATH_TO_JSON_FILE.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (jsonFiles == null || jsonFiles.length == 0) {
            System.out.println("No JSON files found in the directory: " + PATH_TO_JSON_FILE);
            return attributeCountMap; // Return empty map if no files found
        }

        List<Future<Map<String, Integer>>> futures = new ArrayList<>();
        long startTime = System.currentTimeMillis();

        for (File jsonFile : jsonFiles) {
            Future<Map<String, Integer>> future = executorService.submit(() -> processFile(jsonFile.getPath(), attribute));
            futures.add(future);
        }

        for (Future<Map<String, Integer>> future : futures) {
            try {
                Map<String, Integer> result = future.get();
                mergeAttributeCounts(attributeCountMap, result);
            } catch (InterruptedException | ExecutionException e) {
                System.err.println("Error processing file: " + e.getMessage());
            }
        }

        long endTime = System.currentTimeMillis();
        long elapsedTime = endTime - startTime;
        System.out.println("Total processing time for threads: " + elapsedTime + " ms");

        saveAttributeStatisticsToXML(attributeCountMap, attribute);
        return attributeCountMap;
    }

    /**
     * Process the missions from a JSON file to generate attribute counts.
     *
     * @param filePath  The path to the JSON file containing missions
     * @param attribute The attribute to be processed (e.g., planetName, missionYear, spaceshipCount)
     * @return A map containing counts of each unique attribute value found in the missions
     */
    private Map<String, Integer> processFile(String filePath, String attribute) {
        Map<String, Integer> attributeCountMap = new HashMap<>();
        List<Mission> missions = jsonParser.loadMissionsFromFile(filePath);

        for (Mission mission : missions) {
            String attributeValue = mission.getAttributeValue(attribute);
            if (attributeValue != null && !attributeValue.isEmpty()) {
                attributeCountMap.put(attributeValue, attributeCountMap.getOrDefault(attributeValue, 0) + 1);
            }
        }

        return attributeCountMap;
    }

    /**
     * Merges counts from the 'toMerge' map into the 'mainMap'.
     * If a key already exists in 'mainMap', the corresponding values are summed.
     *
     * @param mainMap The main map to merge counts into.
     * @param toMerge The map containing counts to merge into the main map.
     */
    private void mergeAttributeCounts(Map<String, Integer> mainMap, Map<String, Integer> toMerge) {
        for (Map.Entry<String, Integer> entry : toMerge.entrySet()) {
            mainMap.merge(entry.getKey(), entry.getValue(), Integer::sum);
        }
    }

    /**
     * Saves the attribute statistics to an XML file and prompts the user to display the statistics on the console.
     *
     * @param attributeCountMap A map containing attribute values and their corresponding counts
     * @param attribute         The attribute for which statistics are generated
     */
    private void saveAttributeStatisticsToXML(Map<String, Integer> attributeCountMap, String attribute) {
        try {
            JAXBContext context = JAXBContext.newInstance(MissionStatistics.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            MissionStatistics missionStatistics = new MissionStatistics();
            missionStatistics.setTotalMissions(attributeCountMap.size());
            missionStatistics.setAttributeStatistics(attributeCountMap);

            String fileName = "statistics_by_" + attribute + ".xml";
            File xmlFile = new File(PATH_TO_XML_FILE, fileName);

            marshaller.marshal(missionStatistics, xmlFile);

            System.out.println("Mission statistics saved to XML file: " + FileUtil.getRelativePath(xmlFile));


        } catch (JAXBException e) {
            System.err.println("Error marshalling mission statistics to XML: " + e.getMessage());
        }
    }

    /**
     * Display the attribute statistics on the console.
     * This method iterates through the attributeCountMap and prints each attribute value along with its count.
     *
     * @param attributeCountMap A map containing attribute values as keys and their corresponding counts as values.
     */
    public void displayStatisticsOnConsole(Map<String, Integer> attributeCountMap) {
        attributeCountMap.forEach((value, count) -> {
            System.out.println("Attribute Value: " + value + ", Count: " + count);
        });
    }

    /**
     * Searches for a mission in the given list of missions by matching the planet name and mission year.
     *
     * @param missions     The list of missions to search through.
     * @param planetName   The planet name to match.
     * @param missionYear  The mission year to match.
     * @return             The mission object if found, or null if  matching mission isn`t found.
     */
    public Mission findMissionByPlanetAndYear(List<Mission> missions, String planetName, int missionYear) {
        for (Mission mission : missions) {
            if (mission.getPlanetName().equalsIgnoreCase(planetName) && mission.getMissionYear() == missionYear) {
                return mission;
            }
        }
        return null;
    }

    public void shutdown() {
        executorService.shutdown();
    }

//    public static void main(String[] args) {
//        // Example usage
//        MissionService missionService = new MissionService(4); // Use 4 threads
//
//        // Generate statistics for a specific attribute
//        Map<String, Integer> statistics = missionService.generateMissionStatistics("planetName");
//
//        // Shutdown the executor service when done
//        missionService.shutdown();
//    }
}
