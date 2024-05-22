package edu.curtin.app;

import java.util.Map;

/**
 * Handles display of tasks and their summaries within a WBS
 * Provides methods to display all tasks in a structured tree format and summarises effort estimates
 */
// Reference: Composite Pattern, Lecture Notes (Lecture 3, 2024-04-26).
public class TaskDisplay
{
    /**
     * Displays all tasks in WBS in a structured format
     * Tasks are shown hierarchically based on parent-child relationships
     *
     * @param taskMap map containing all tasks (keyed by task ID)
     */
    public void displayTasks(Map<String, Task> taskMap) {
        // Begin with root tasks (i.e. without parent) and recursively display their hierarchies.
        taskMap.values().stream()
                .filter(t -> t.getParentId() == null)  // Filter to include only root level tasks
                .forEach(t -> displayTasksHelper("", t));  // Start recursive display with no prefix
    }

    /**
     * Helper method to recursively display tasks and their subtasks with indentation representing hierarchy.
     *
     * @param prefix string prefix used for indentation and hierarchy visualisation.
     * @param task current task to display.
     */
    private void displayTasksHelper(String prefix, Task task) {
        // Display current task with appropriate prefix
        System.out.println(prefix + task);
        // Recursively display each subtask by increasing indentation level
        task.getSubtasks().forEach(subtask -> displayTasksHelper(prefix + "  ", subtask));
    }

    /**
     * Displays summary of WBS, including total known effort and count of tasks without effort estimates
     *
     * @param taskMap map of all tasks in WBS
     */
    public void displaySummary(Map<String, Task> taskMap) {
        // Calculate total known effort by summing the effort of all tasks
        int totalEffort = taskMap.values().stream()
                .mapToInt(Task::calculateTotalEffort)
                .sum();

        // Count number of tasks that are leaves (no subtasks) and don't have an effort estimate set
        long unknownTasks = taskMap.values().stream()
                .filter(t -> t.getEffortEstimate() == null && t.isLeaf())
                .count();

        // Output total effort and number of tasks without estimates
        System.out.println("Total known effort = " + totalEffort);
        System.out.println("Unknown tasks = " + unknownTasks);
    }
}
