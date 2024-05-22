# Work Breakdown Structure Estimation Tool

This project is a simple software application designed to help software engineers perform work planning and effort estimation using a hierarchical Work Breakdown Structure (WBS). The application is implemented in Java and uses the Gradle build system. Key concepts from Object-Oriented Software Engineering (OOSE) are demonstrated, including the Strategy Pattern, Composite Pattern, and the use of containers.

## Table of Contents
- [Features](#features)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)
  - [Running the Application](#running-the-application)
- [Usage](#usage)
  - [Menu Options](#menu-options)
- [Design Patterns](#design-patterns)
- [Class Responsibilities](#class-responsibilities)
- [Error Handling and Logging](#error-handling-and-logging)
- [UML Class Diagram](#uml-class-diagram)
- [Contributing](#contributing)
- [License](#license)

## Features
- Load and save WBS data files.
- Console-based menu for interacting with the WBS.
- Estimate effort for tasks and subtasks.
- Configure the number of estimators and reconciliation approach for effort estimates.
- Display the WBS as a visual tree structure.
- Provide a summary of total known effort and the number of unknown tasks.

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Gradle 6.0 or higher

### Installation
1. Clone the repository:
   ```sh
   git clone https://github.com/RJTLM/Work-Breakdown-Structure-Estimation-Tool-Java-Gradle-Strategy-Pattern-Composite-Pattern-Containers.git
   cd Work-Breakdown-Structure-Estimation-Tool-Java-Gradle-Strategy-Pattern-Composite-Pattern-Containers
   ```

2. Build the project using Gradle:
   ```sh
   ./gradlew build
   ```

### Running the Application
To run the application, use the following command:
```sh
./gradlew run --args="path/to/your/datafile.txt"
```
Replace `path/to/your/datafile.txt` with the actual path to your WBS data file.

## Usage

### Menu Options
- **Estimate effort**: Allows you to estimate effort for a task or subtask.
- **Configure**: Configure the number of estimators and the approach for reconciling different estimates.
- **Quit**: Exit the application.

## Design Patterns
This project demonstrates the use of the following design patterns:
- **Strategy Pattern**: Used for reconciling different effort estimates.
- **Composite Pattern**: Used for representing tasks and subtasks in the WBS.
- **Containers**: Various uses of Java containers for managing tasks and observers.

## Class Responsibilities
- **MainClass**: Entry point of the application. Handles command-line arguments.
- **WBS**: Manages the hierarchical structure of tasks.
- **Task**: Represents a single task in the WBS.
- **TaskList**: Container for managing a list of tasks.
- **EffortEstimator**: Handles effort estimation logic.
- **Configuration**: Manages configuration settings for the application.

## Error Handling and Logging
The application uses exception handling to manage external errors gracefully without compromising internal debugging. Logging statements are used to track the application's state and actions.

## UML Class Diagram
A UML class diagram of the application is provided in the `docs` folder. It includes all the classes and their relationships, demonstrating the design of the application.

## Contributing
Contributions are welcome! Please fork the repository and submit pull requests for any improvements or bug fixes.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
