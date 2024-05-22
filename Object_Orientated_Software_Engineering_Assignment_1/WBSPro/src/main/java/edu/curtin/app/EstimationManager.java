package edu.curtin.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Manages estimation process for tasks within WBS
 * Responsible for coordinating effort estimation tasks, including handling user input for estimates
 */
// This class uses the Strategy Pattern to handle different effort estimation strategies.
// Reference: Strategy Pattern, Lecture Notes (Lecture 2, 2024-04-24)
public class EstimationManager {
    private EstimationStrategy strategy;  // Current strategy used for reconciling multiple effort estimates.
    private int numEstimators = 3;        // Default number of estimators.
    private final Logger LOGGER;          // Logger for logging information and warnings.

    /**
     * Constructs an EstimationManager with a specific estimation strategy and logger
     * @param strategy The strategy used for estimating efforts
     * @param logger Logger to record operational events
     */
    public EstimationManager(EstimationStrategy strategy, Logger logger) {
        this.strategy = strategy;
        this.LOGGER = logger;
    }

    /**
     * Sets the estimation strategy
     * @param strategy The new estimation strategy to use
     */
    public void setStrategy(EstimationStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Sets the number of estimators
     * @param numEstimators The number of estimators to be used in the estimation process
     * @throws IllegalArgumentException if numEstimators is less than or equal to 0
     */
    public void setNumEstimators(int numEstimators) {
        if (numEstimators > 0) {
            this.numEstimators = numEstimators;
        } else {
            LOGGER.warning("Number of estimators must be greater than 0");
            throw new IllegalArgumentException("Number of estimators must be greater than 0");
        }
    }

    /**
     * Initiates the effort estimation process for a specific task
     * @param taskMap Map of task IDs to Task objects
     * @param sc Scanner to read user input
     */
    public void estimateEffort(Map<String, Task> taskMap, Scanner sc) {
        System.out.print("Enter Task ID for effort estimation: ");
        String taskId = sc.next().trim().toUpperCase();
        Task task = taskMap.get(taskId);
        if (task != null) {
            System.out.println("Estimating effort for task: " + task.getDescription());
            if (task.isLeaf()) {
                estimateTaskEffort(task, sc);
            } else {
                estimateEffortRecursively(task, sc);
            }
        } else {
            LOGGER.warning(() -> "Task ID not found: " + taskId);
            System.out.println("Task ID not found. Please try again.");
        }
    }

    /**
     * Estimates effort for a single task using the configured number of estimators
     * @param task The task to estimate
     * @param sc Scanner to read estimates from the console
     */
    private void estimateTaskEffort(Task task, Scanner sc) {
        System.out.println("Enter estimates for the task: " + task.getDescription());
        List<Integer> estimates = getEstimatesFromUser(sc, numEstimators);
        int reconciledEffort = strategy.estimate(estimates);
        task.setEffortEstimate(reconciledEffort);
        LOGGER.info(() -> "Task " + task.getId() + " effort estimated: " + reconciledEffort);
    }

    /**
     * Recursively estimates effort for a task and all its subtasks
     * @param task The task to start the recursive estimation from
     * @param sc Scanner to read estimates from the console
     */
    private void estimateEffortRecursively(Task task, Scanner sc) {
        System.out.println("Estimating effort recursively for task and its subtasks: " + task.getDescription());
        if (task.isLeaf()) {
            estimateTaskEffort(task, sc);
        } else {
            for (Task subtask : task.getSubtasks()) {
                estimateEffortRecursively(subtask, sc);
            }
        }
    }

    /**
     * Helper method to gather estimates from the user
     * @param sc Scanner to read user input
     * @param numEstimators Number of estimators to collect estimates from
     * @return A list of integer estimates
     */
    private List<Integer> getEstimatesFromUser(Scanner sc, int numEstimators) {
        List<Integer> estimates = new ArrayList<>();
        for (int i = 0; i < numEstimators; i++) {
            System.out.print("Estimator " + (i + 1) + ", enter your estimate: ");
            while (!sc.hasNextInt()) {
                LOGGER.warning("Invalid input. Please enter a number.");
                sc.next(); // Consume the invalid input
            }
            estimates.add(sc.nextInt());
        }
        return estimates;
    }
}
