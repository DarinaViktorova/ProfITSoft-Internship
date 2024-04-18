package my.space.logic;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class JsonParserTest {
    private JsonParser jsonParser;

    @BeforeEach
    void setUp() {
        jsonParser = new JsonParser();
    }

    @Test
    void loadMissionsFromFile() {
        Assertions.assertDoesNotThrow(
                () -> jsonParser.loadMissionsFromFile("src/main/resources/json-files/missions_1.json")
        );
    }
}