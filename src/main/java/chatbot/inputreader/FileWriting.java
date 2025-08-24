package chatbot.inputreader;

import chatbot.taskhandler.Task;

import java.nio.file.Files;
import java.nio.file.Path;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.util.List;

public class FileWriting {

    /**
     * Writes the list of tasks to a file at the specified file path.
     *
     * @param filePath The path to the file where tasks will be written.
     * @param tasks    The list of tasks to write to the file.
     * @throws IOException If an I/O error occurs.
     */
    public static void writeToFile(String filePath, List<Task> tasks) throws IOException {
        FileWriter writer = new FileWriter(filePath);
        for (Task task : tasks) {
            writer.write(task.formatData() + System.lineSeparator());
        }
        writer.close();
    }

    /**
     * Reads lines from a file at the specified file path.
     * If the file does not exist, it creates the file and its parent directories.
     *
     * @param filePath The path to the file to read from.
     * @return A list of strings, each representing a line from the file.
     * @throws IOException If an I/O error occurs.
     */
    public static List<String> readFromFile(String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            // Create parent directories if they don't exist
            boolean createDirectory = file.getParentFile().mkdirs();
            // Create the file if it doesn't exist
            boolean createFile = file.createNewFile();
            if (!createDirectory && !createFile) {
                throw new IOException("Failed to create directory and file: " + filePath);
            }
        }
        return Files.readAllLines(Path.of(filePath));
    }


}
