package edu.curtin.app;

import edu.curtin.app.data.DataLoader;
import edu.curtin.app.data.DataSaver;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * WBS class manages tasks and their interactions
 * Includes loading and saving tasks, and providing methods to estimate and configure task effort
 */
public class WBS {
    // A map to store tasks by their IDs for efficient retrieval which demonstrates appropriate use of containers
    private final Map<String, Task> taskMap = new HashMap<>();
    // Strategy pattern used to handle different strategies for estimating effort
    private EstimationStrategy strategy;
    // Logger for logging events within WBS class
    private static final Logger LOGGER = LoggerUtility.getLogger();
    // Services for loading and saving tasks
    private final DataLoader dataLoader;
    private final DataSaver dataSaver;
    // Components for displaying tasks and managing effort estimation
    private final TaskDisplay taskDisplay;
    private final EstimationManager estimationManager;

    /**
     * Constructs a new WBS instance with required services and initialises components
     *
     * @param dataLoader DataLoader service for loading tasks from file
     * @param dataSaver DataSaver service for saving tasks to file
     */
    public WBS(DataLoader dataLoader, DataSaver dataSaver) {
        this.dataLoader = dataLoader;
        this.dataSaver = dataSaver;
        this.estimationManager = new EstimationManager(new ConsensusEstimateStrategy(), LOGGER);
        this.taskDisplay = new TaskDisplay();
        this.strategy = new ConsensusEstimateStrategy();
        LOGGER.info("WBS initialised with default strategy.");
    }

    /**
     * Loads tasks from a specified file path into task map
     *
     * @param filePath file path from which to load tasks
     * @throws IOException If error occurs during file reading
     */
    // Proper error handling as per Lecture 4 on Error Handling + Logging.
    // Source: Error Handling and Logging, Lecture Notes (Lecture 4, 2024-04-26)
    public void loadWBSFromFile(String filePath) throws IOException {
        try {
            dataLoader.loadTasksFromFile(filePath, taskMap);
            LOGGER.info(() -> "WBS data loaded successfully from file.");
        } catch (IOException e) {
            LOGGER.severe(() -> "Failed to load data: " + e.getMessage());
            throw new IOException("Error loading data from file: " + e.getMessage(), e);
        }
    }

    /**
     * Saves current state of tasks to a specified file path
     *
     * @param filePath File path where tasks should be saved
     * @throws IOException If error occurs during file writing
     */
    public void saveWBSToFile(String filePath) throws IOException {
        try {
            dataSaver.saveTasksToFile(filePath, taskMap);
            LOGGER.info(() -> "WBS data saved successfully to file.");
        } catch (IOException e) {
            LOGGER.severe(() -> "Failed to save data: " + e.getMessage());
            throw new IOException("Error saving data to file: " + e.getMessage(), e);
        }
    }

    /**
     * Estimates effort for a specified task by invoking EstimationManager
     *
     * @param sc Scanner to read user input
     */
    public void estimateEffort(Scanner sc) {
        estimationManager.estimateEffort(taskMap, sc);
    }

    /**
     * Configures estimation settings including number of estimators and estimation strategy
     *
     * @param sc Scanner to read user input
     */
    public void configureEstimation(Scanner sc) {
        System.out.print("\nEnter number of estimators (must be at least 1): ");
        int numEstimatorsInput;
        do {
            while (!sc.hasNextInt()) {
                LOGGER.warning(() -> "Invalid input for number of estimators.");
                System.out.println("Invalid input. Please enter a valid integer for number of estimators.");
                sc.next();
            }
            numEstimatorsInput = sc.nextInt();
            if (numEstimatorsInput < 1) {
                LOGGER.warning(() -> "Number of estimators cannot be less than 1.");
                System.out.println("Number of estimators must be at least 1.");
            }
        } while (numEstimatorsInput < 1);

        estimationManager.setNumEstimators(numEstimatorsInput);
        System.out.println("Select the reconciliation method:");
        System.out.println("1. Highest");
        System.out.println("2. Median");
        System.out.println("3. Consensus");
        int choice = sc.nextInt();
        switch (choice) {
            case 1:
                strategy = new HighestEstimateStrategy();
                break;
            case 2:
                strategy = new MedianEstimateStrategy();
                break;
            case 3:
                strategy = new ConsensusEstimateStrategy();
                break;
            default:
                LOGGER.info(() -> "Invalid choice for reconciliation method. Defaulting to Consensus.");
                System.out.println("Invalid choice, using default.");
                strategy = new ConsensusEstimateStrategy();
                break;
        }
        estimationManager.setStrategy(strategy);
        int finalNumEstimatorsInput = numEstimatorsInput;
        LOGGER.info(() -> "Configuration updated: Number of estimators set to " + finalNumEstimatorsInput + ", Reconciliation method set to: " + strategy.getClass().getSimpleName());
        System.out.println("Configuration updated successfully.");
    }

    /**
     * Displays entire WBS structure and the summary of efforts using TaskDisplay.
     */
    public void displayTasks() {
        taskDisplay.displayTasks(taskMap);
        taskDisplay.displaySummary(taskMap);
    }
}
