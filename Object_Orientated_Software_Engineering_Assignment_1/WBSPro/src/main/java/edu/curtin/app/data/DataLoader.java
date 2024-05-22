package edu.curtin.app.data;

import edu.curtin.app.Task;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Logger;

// Method to load tasks from a file, demonstrating exception handling and file operations.
// Reference: Lecture 4 on Error Handling and Logging.
public class DataLoader {
    private static final Logger LOGGER = Logger.getLogger(DataLoader.class.getName());

    public void loadTasksFromFile(String filePath, Map<String, Task> taskMap) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                String parentId = parts.length > 0 && !parts[0].trim().isEmpty() ? parts[0].trim() : null;
                String id = parts[1].trim();
                String description = parts[2].trim();
                Integer effort = parts.length > 3 && !parts[3].trim().isEmpty() ? Integer.parseInt(parts[3].trim()) : null;

                Task task = new Task(id, description, effort);
                task.setParentId(parentId);
                taskMap.put(id, task);

                if (parentId != null && taskMap.containsKey(parentId)) {
                    taskMap.get(parentId).addSubtask(task);
                }

                LOGGER.info(() -> "Task with ID " + id + " loaded from file.");
            }
        }
    }
}
