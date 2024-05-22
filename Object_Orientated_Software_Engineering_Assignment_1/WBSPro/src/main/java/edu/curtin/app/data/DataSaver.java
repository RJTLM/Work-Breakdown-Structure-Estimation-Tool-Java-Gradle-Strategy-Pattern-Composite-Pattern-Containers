package edu.curtin.app.data;

import edu.curtin.app.Task;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

// Method to load tasks from a file, demonstrating exception handling and file operations.
// Reference: Lecture 4 on Error Handling + Logging.
public class DataSaver {
    public void saveTasksToFile(String filePath, Map<String, Task> taskMap) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (Task task : taskMap.values()) {
                String line = formatTaskForSaving(task);
                writer.write(line);
                writer.newLine();
            }
        }
    }

    private String formatTaskForSaving(Task task) {
        // Format: ParentID; ID; Description; Effort
        // Ensure to start with a semicolon if it's a root task
        String parentPrefix = (task.getParentId() != null && !task.getParentId().isEmpty()) ? task.getParentId() + " ; " : "; ";
        return parentPrefix + task.getId() + " ; " + task.getDescription() +
                (task.getEffortEstimate() != null ? " ; " + task.getEffortEstimate() : " ;");
    }
}
