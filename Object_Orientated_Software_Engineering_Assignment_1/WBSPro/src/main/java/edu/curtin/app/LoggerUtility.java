package edu.curtin.app;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Utility class to configure and provide a centralised Logger instance
 * Sets up a Logger with a file handler to write log messages to a file
 * Makes it easier to maintain and review logs separate from standard console output
 */
public class LoggerUtility {
    // Static Logger instance. Configured and provided for program-wide use
    private static final Logger LOGGER = Logger.getLogger(LoggerUtility.class.getName());

    // Static initialiser to configure LOGGER before its used anywhere in the program
    static {
        try {
            // FileHandler to log messages into a file ('true' = append to existing log file)
            FileHandler fileHandler = new FileHandler("application.log", true);

            // Setting a SimpleFormatter to format log messages in readable format
            fileHandler.setFormatter(new SimpleFormatter());

            // Adding FileHandler to LOGGER.
            LOGGER.addHandler(fileHandler);

            // Setting log level to ALL to capture all types of log messages (ass per oose-pmd-rules.xml)
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            // Print error message to standard error if log file couldn't be accessed
            System.err.println("Failed to setup logger: " + e.getMessage());
        }
    }

    /**
     * Provides centralised Logger instance configured with file handler
     * @return Configured Logger instance
     */
    public static Logger getLogger() {
        return LOGGER;
    }
}
