package edu.curtin.app;

import edu.curtin.app.data.DataLoader;
import edu.curtin.app.data.DataSaver;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Logger;

/**
 * Main class that handles the program execution flow.
 * Initialises system components, loads data, and handles the user interface for task management.
 */
public class App
{
    // Setting up logger
    private static final Logger LOGGER = LoggerUtility.getLogger();


    // Program entry point
    public static void main(String[] args) throws IOException {
        LOGGER.info("Starting application");

        // Validate one CLA provided
        if (args.length != 1) {
            LOGGER.severe("Usage: java App <filename> not provided");
            System.err.println("Usage: java App <filename> not provided: Try adding '--args='<filename.txt>')");
            System.exit(1);
        }

        String filePath = args[0];
        File file = new File(filePath);
        // Check if provided file path exists and is accessible
        if (file.exists()) {
            LOGGER.info(() -> "File existence check: File found at path: " + filePath);
        } else {
            LOGGER.severe(() -> "File does not exist: " + filePath);
            System.err.println("File does not exist: " + filePath);
            System.exit(1);
        }

        // Data loading and saving components initialisation
        // Exception handling and logging, reference from Lecture 4: Error Handling + Logging
        // Source: Error Handling and Logging, Lecture Notes (Lecture 4, 2024-04-26)
        DataLoader dataLoader = new DataLoader();
        DataSaver dataSaver = new DataSaver();
        WBS wbs = new WBS(dataLoader, dataSaver);

        LOGGER.info(() -> "Attempting to load data from file: " + file.getAbsolutePath());
        System.out.println("Loading data from: " + file.getAbsolutePath());
        wbs.loadWBSFromFile(file.getAbsolutePath());
        LOGGER.info(() -> "Data loaded successfully from " + file.getAbsolutePath());

        // Display initial WBS data
        System.out.println("\nWelcome to WBSPro!\n\nExisting WBS:");
        wbs.displayTasks();

        // Handles user input and interaction, demonstrating the use of interfaces and error handling.
        // Reference: Lecture 2 on Polymorphism and Interfaces.
        Scanner sc = new Scanner(System.in);
        boolean running = true;
        while(running) {
            System.out.println("\n1. Estimate Effort");
            System.out.println("2. Display Tasks");
            System.out.println("3. Configure Estimation");
            System.out.println("4. Save and Exit");

            System.out.print("Select an option: ");

            int option; // = -1; "UnusedAssignment: The initializer for variable 'option' is never used (overwritten on lines 79 and 82) > Task :pmdMain FAILED

            if (sc.hasNextInt()) {
                option = sc.nextInt();
                LOGGER.info(() -> "User selected option: " + option);

            } else {
                sc.nextLine(); // Clear the invalid input
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            // Process user choice
            switch (option) {
                case 1:
                    wbs.estimateEffort(sc);
                    break;
                case 2:
                    wbs.displayTasks();
                    break;
                case 3:
                    wbs.configureEstimation(sc);
                    break;
                case 4:
                    LOGGER.info(() -> "Saving data and exiting...");
                    System.out.println("Saving data to: " + file.getAbsolutePath());
                    wbs.saveWBSToFile(file.getAbsolutePath());
                    LOGGER.info(() -> "Data successfully saved to file: " + file.getAbsolutePath());
                    running = false;
                    break;
                default:
                    LOGGER.warning(() -> "Invalid option selected: " + option);
                    System.out.println("Invalid option, try again.");
                    break;
            }
        }
        sc.close();
        LOGGER.info(() -> "Application terminated.");
    }
}
