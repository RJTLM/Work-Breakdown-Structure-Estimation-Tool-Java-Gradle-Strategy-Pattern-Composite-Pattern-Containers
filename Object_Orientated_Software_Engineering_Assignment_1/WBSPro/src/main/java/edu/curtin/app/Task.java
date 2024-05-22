package edu.curtin.app;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * Represents a single task within a WBS
 * May have a unique identifier, a description, an optional effort estimate, and may be broken down into subtasks
 */
// This class represents the Composite Pattern, used to manage a hierarchical structure of tasks.
// Reference: Composite Pattern, Lecture Notes (Lecture 3, 2024-04-24)
public class Task {
    static {
        Logger.getLogger(Task.class.getName());
    }

    // Unique identifier for the task
    private final String id;
    // Detailed description of what the task involves
    private final String description;
    // Optional numeric estimate of effort required to complete task (null if not set)
    private Integer effortEstimate;
    // Identifier of parent task to establish hierarchical relationship in WBS (null if task is top-level)
    private String parentId;
    // List to hold subtasks, illustrating use of Composite pattern to manage hierarchical structures
    private final List<Task> subtasks = new ArrayList<>();

    /**
     * Constructs a new Task with specified details
     *
     * @param id Unique identifier for the task
     * @param description Description of task
     * @param effortEstimate Optional effort estimate (can be null if not applicable)
     */
    public Task(String id, String description, Integer effortEstimate) {
        this.id = id;
        this.description = description;
        this.effortEstimate = effortEstimate;
    }

    /**
     * Sets parent ID for this task to establish a parent-child relationship in WBS
     *
     * @param parentId The ID of parent task
     */
    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    /**
     * Retrieves the parent ID of this task
     *
     * @return ID of parent task (or null if top-level)
     */
    public String getParentId() {
        return parentId;
    }

    /**
     * Adds a subtask to this task, demonstrating the Composite pattern
     *
     * @param subtask is subtask to be added
     */
    public void addSubtask(Task subtask) {
        subtasks.add(subtask);
    }

    /**
     * Returns task's unique ID
     *
     * @return unique identifier for this task
     */
    public String getId() {
        return id;
    }

    /**
     * Returns task description.
     *
     * @return description of task.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Returns effort estimate for this task.
     *
     * @return effort estimate (null if not set)
     */
    public Integer getEffortEstimate() {
        return effortEstimate;
    }

    /**
     * Sets or updates effort estimate for this task
     *
     * @param effortEstimate new effort estimate to set
     */
    public void setEffortEstimate(Integer effortEstimate) {
        this.effortEstimate = effortEstimate;
    }

    /**
     * Returns a list of subtasks, making a defensive copy to prevent external modifications
     *
     * @return list of subtasks
     */
    public List<Task> getSubtasks() {
        return new ArrayList<>(subtasks);
    }

    /**
     * Determines if task is a leaf node (i.e. has no subtasks)
     *
     * @return True if task has no subtasks (otherwise false)
     */
    public boolean isLeaf() {
        return subtasks.isEmpty();
    }

    /**
     * Recursively calculates total effort for this task and all its subtasks
     *
     * @return total effort for this task and its entire subtree
     */
    public int calculateTotalEffort() {
        int total = effortEstimate != null ? effortEstimate : 0;
        total += subtasks.stream().mapToInt(Task::calculateTotalEffort).sum();
        return total;
    }

    /**
     * Provides a string representation of task to be displayed correctly
     *
     * @return Formatted string showing task details
     */
    @Override
    public String toString() {
        return id + ": " + description + (effortEstimate != null ? ", effort = " + effortEstimate : "");
    }
}
