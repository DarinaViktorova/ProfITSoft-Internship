package my.space.utils;

import java.io.File;

public class FileUtil {
    /**
     * Retrieves the relative path of a file relative to the current project directory.
     *
     * @param file The file for which the relative path needs to be determined.
     * @return The relative path of the file relative to the project directory.
     */
    public static String getRelativePath(File file) {
        String filePath = file.getPath();
        String projectDir = System.getProperty("user.dir");

        if (filePath.startsWith(projectDir)) {
            return filePath.substring(projectDir.length() + 1);
        } else return filePath;
    }
}
