package my.space.logic;

import my.space.models.Mission;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

import static my.space.utils.Constants.threadPoolSize;
import static org.junit.jupiter.api.Assertions.*;

class MissionServiceTest {

    private MissionService missionService;

    @BeforeEach
    void setUp() {
        missionService = new MissionService(threadPoolSize);
    }

    @Test
    void findMissionByPlanetAndYear () {
        List<Mission> missions = new ArrayList<>();
        missions.add(new Mission("Mars", new ArrayList<>(), 2025));
        missions.add(new Mission("Venus", new ArrayList<>(), 2030));
        missions.add(new Mission("Jupiter", new ArrayList<>(), 2035));

        String planetName = "Venus";
        int missionYear = 2030;

        // Execution of the method we are testing
        Mission result = missionService.findMissionByPlanetAndYear(missions, planetName, missionYear);

        // Result
        assertNotNull(result);
        assertEquals(planetName, result.getPlanetName());
        assertEquals(missionYear, result.getMissionYear());
    }

}