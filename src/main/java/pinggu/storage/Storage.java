package pinggu.storage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import pinggu.exception.PingguException;
import pinggu.task.Deadline;
import pinggu.task.Event;
import pinggu.task.Task;
import pinggu.task.Todo;

/**
 * Handles loading tasks and saving tasks to file specified in file path.
 */
public class Storage {
    private final String filePath;

    /**
     * Initializes Storage object with the given file path.
     *
     * @param filePath The file path to store and load from.
     */
    public Storage(String filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads the save file from given file path if it exists.
     * Creates a new save file if it does not exist.
     *
     * @return The TaskList that is loaded from save file.
     * @throws PingguException If file cannot be read.
     */
    public List<Task> load() throws PingguException {
        List<Task> tasks = new ArrayList<>();
        File file = new File(filePath);
        if (file.exists()) {
            try (Scanner scanner = new Scanner(file)) {
                while (scanner.hasNextLine()) {
                    String line = scanner.nextLine();
                    Task task = parseLine(line);
                    if (task != null) {
                        tasks.add(task);
                    }
                }
            } catch (IOException e) {
                System.out.println("Error reading file" + e.getMessage());
            }
        } else { //file does not exist, so we make directory and file
            try {
                if (file.getParentFile() != null) {
                    file.getParentFile().mkdirs();
                }
                boolean isCreated = file.createNewFile();
                if (isCreated) {
                    System.out.println("File created at " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                System.out.println("File can not be created" + e.getMessage());
            }
        }
        return tasks;
    }

    /**
     * Saves each task in TaskList to save file in the specified format from Task class.
     *
     * @param tasks The TaskList object to save into save file.
     */
    public void save(List<Task> tasks) {
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            for (Task task : tasks) {
                fileWriter.write(task.toStringInSaveFile() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error saving file" + e.getMessage());
        }
    }

    private Task parseLine(String line) {
        String[] parts = line.split("\\|");
        if (parts.length < 3) {
            return null; //task is invalid
        }
        String taskType = parts[0].trim();
        boolean isDone = parts[1].trim().equals("1");
        String taskDescription = parts[2].trim();
        Task task;
        switch (taskType) {
        case "T":
            task = new Todo(taskDescription);
            break;
        case "D":
            task = new Deadline(taskDescription, parts[3].trim());
            break;
        case "E":
            task = new Event(taskDescription, parts[3].trim(), parts[4].trim());
            break;
        default:
            return null;
        }
        if (isDone) {
            task.setDone();
        }
        return task;
    }
}
