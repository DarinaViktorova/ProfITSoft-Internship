package my.space.utils;

import org.junit.jupiter.api.Test;

import java.io.File;

import static my.space.utils.FileUtil.getRelativePath;
import static org.junit.jupiter.api.Assertions.*;

class FileUtilTest {

    @Test
    void testGetRelativePath() {

        String projectDir = System.getProperty("user.dir");

        File fileWithinProject = new File(projectDir + "/src/main/resources/test.txt");

        String relativePath = getRelativePath(fileWithinProject);

        // Expected relative path should be "/src/main/resources/test.txt"
        String expectedRelativePath = "src/main/resources/test.txt";

        // Replace backslashes with forward slashes in the actual relative path
        relativePath = relativePath.replace("\\", "/");

        assertEquals(expectedRelativePath, relativePath);
    }
}