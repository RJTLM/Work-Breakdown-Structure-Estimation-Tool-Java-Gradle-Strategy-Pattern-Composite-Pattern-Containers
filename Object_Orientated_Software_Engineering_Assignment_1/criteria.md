# Responses To The Criteria

## (e) Strategy and/or Template Method Patterns
### Strategy Pattern
- **Design Purpose**: Allows dynamic selection of estimation algorithms based on user preference or project phase.
- **Implemented in**: `EstimationManager` class.
- **Polymorphism**:
    - Defined by `EstimationStrategy` interface.
    - Implemented by `ConsensusEstimateStrategy`, `HighestEstimateStrategy`, `MedianEstimateStrategy`.
    - Allows interchangeable use of strategies without altering code structure.

#### Example of Strategy Switching
```java
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
}
estimationManager.setStrategy(strategy);
```

## (f) Composite Pattern
- **Design Purpose**: Manages tasks within a hierarchical WBS, allowing uniform treatment of individual tasks and task compositions.
- **Implemented in**: `Task` class.
- **Polymorphism**:
    - Tasks can be leaf or composite (containing other tasks).
    - Operations applied to a single task are naturally extended to subtasks.

#### Example of Composite Implementation
```java
// Adding a subtask
public void addSubtask(Task subtask) {
    subtasks.add(subtask);
}

// Recursively calculating total effort
public int calculateTotalEffort() {
    int total = effortEstimate != null ? effortEstimate : 0;
    total += subtasks.stream().mapToInt(Task::calculateTotalEffort).sum();
    return total;
}
```

## (a) General Code Quality
- **Comments and Documentation**: Comprehensive comments included throughout.

For example:

```java
/**
 * Manages estimation process for tasks within WBS
 * Responsible for coordinating effort estimation tasks, including handling user input for estimates
 */
public class EstimationManager {
    private EstimationStrategy strategy;  // Current strategy used for reconciling multiple effort estimates.
    private int numEstimators = 3;        // Default number of estimators.
    private final Logger LOGGER;          // Logger for logging information and warnings.
```
- **PMD Compliance**: No warnings.


## (b) Appropriate Use of Containers
- **Purpose**: Enhances data management and access efficiency.
- **Implemented with**: `HashMap` in the `WBS` class:

```java
// In the WBS class, taskMap is declared to manage tasks efficiently.
private final Map<String, Task> taskMap = new HashMap<>();

// Method to load tasks into the map from a file
public void loadWBSFromFile(String filePath) throws IOException {
    dataLoader.loadTasksFromFile(filePath, taskMap);
    LOGGER.info(() -> "WBS data loaded successfully from file.");
}

// Example of accessing a task by its ID from the map
public Task getTaskById(String taskId) {
    return taskMap.get(taskId);
}
```


## (c) Clear and Distinct Class/Interface/Method Responsibilities
### Class Responsibilities
- **Purpose**: Each class is designed with a specific functionality, enhancing modularity and maintainability.
- **Examples**:
    - `DataLoader`: Handles the loading of tasks from files.
    - `DataSaver`: Manages the saving of tasks back to files.

### Method Specificity
- **Purpose**: Methods are crafted to perform singular, well-defined tasks, ensuring clarity and reducing complexity.
- **Example**:
    - `loadTasksFromFile` in `DataLoader`: Dedicated solely to reading tasks from a specified file and loading them into a data structure.


#### Example of Method Specificity in DataLoader
```java
public class DataLoader {
    // Method to load tasks from a file into a map
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
            }
        }
    }
}
```

## (d) Appropriate Error Handling and Logging
### Error Handling
- **Purpose**: Ensures the application handles exceptions gracefully, maintaining robustness and providing clear user feedback during errors, particularly during file operations.
- **Example**:
    - Use of try-catch blocks in file handling operations within `DataLoader` and `DataSaver`.

### Logging
- **Purpose**: Facilitates debugging and tracking of application flow and critical events, utilizing a centralized logging system.
- **Implemented with**: `LoggerUtility` class that configures global logging settings and file handlers.

#### Example of Error Handling and Logging in DataLoader
```java
public class DataLoader {
    public void loadTasksFromFile(String filePath, Map<String, Task> taskMap) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Parsing and loading logic here...
            }
        } catch (FileNotFoundException e) {
            LOGGER.severe("File not found: " + filePath);
            throw new IOException("File not found: " + filePath, e);
        } catch (IOException e) {
            LOGGER.severe("Error reading file: " + filePath);
            throw e;
        }
    }
}
```
#### Example of Logging Setup in LoggerUtility
```java
public class LoggerUtility {
    static {
        try {
            FileHandler fileHandler = new FileHandler("application.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(fileHandler);
            LOGGER.setLevel(Level.ALL);
        } catch (IOException e) {
            System.err.println("Failed to setup logger: " + e.getMessage());
        }
    }
    public static Logger getLogger() {
        return LOGGER;
    }
}
```