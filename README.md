# NGHMP - Next Generation High Mobility Platformer

## Advanced Features

### Momentum-Based Movement System
- The player gets accelerated when the move button is pressed.
- The accelerated speed is reduced when closer to the goal speed, forming a second derivative velocity curve.
- The player can slide and jump on walls.
- The player can dash in 8 directions.

### Multiple Entities
All objects are based on the `Entity` abstract class:
- Enemy
- Player
- Decoration
- Coin (Shogi)

### Different Types of Tiles
- Ladder
- Spike
- Goal

### Multiple Screens
- Start Screen
- Fail Screen
- Level Choosing Screen
- Pause Screen

### Sound Effects
- Background music
- Fail sound
- Victory sound
- Completed sound

### Textures
- Texture for tiles, player, and enemy.
- Textures can swap between states.
- Textures can have animations.
- Background has moveable textures.

### Others
- Fixed framerate.
- DEBUG mode allows monitoring multiple statuses.


## Demo video

---
# Git Usage
All project progress will be synchronized in real time on GitHub. The following are our specifications for using GitHub:

## 1. Repository Structure

- **Standardized Directory Structure**: Adopt a standardized structural directory for our project to enhance maintainability and readability. Recommended structure includes:
  - `src/` - Source code
  - `tests/` - Test code
  - `docs/` - Documentation
  - `README.md` - Project description

## 2. Commit Messages

- **Clarity and Conciseness**: Commit messages should be concise and accurately describe the changes made.

## 3. Branch Naming Conventions

- **Clear Naming Rules**: Branch names should clearly indicate their function and purpose.
  
- **Work in Personal Branches**: Team members should work on their own branches to avoid impacting the main branch.

## 4. Pull Requests

- **Detailed Descriptions**: When creating a pull request, ensure to provide a clear description that includes:
  - Background: Briefly explain why this change is being made.
  - Changes Made: Overview of what changes have been implemented.

- **Code Review**: Before merging any pull request, at least one team member should conduct a code review to ensure code quality meets the standards.

## 5. Issue Tracking

- **Utilize GitHub Issues**: Use GitHub Issues to track bugs and feature requests in the project. Ensure each issue has clear priorities and owners.

## 6. Version Control

- **Regular Releases**: Regularly release new versions to maintain the project's stability and reliability. Follow semantic versioning principles (MAJOR.MINOR.PATCH) for version management.

## 7. Regular Cleanup

- **Branch Maintenance**: Periodically archive and delete inactive branches to keep the repository clean, helping team members quickly find active workflows.
---

# Refactoring

Refactoring is a crucial part of maintaining and enhancing the quality of our codebase. The following improvements have been made through our refactoring efforts:

## 1. JavaFX MVC GUI Design Pattern

- **Game Code Refactoring**: We have implemented the JavaFX Model-View-Controller (MVC) design pattern to refactor the core game code, achieving a clean separation between the game's logic, user interface, and user input handling.

- **User Interface Design**: In conjunction with the MVC architecture, we designed several user interface screens to enhance player experience. Below is a brief overview of each UI component:
  - **Menu.fxml**: The initial menu presented before the game begins, featuring buttons to start the game (select level), exit, and access help documentation outlining the game's basic rules.
  - **Fail.fxml**: A screen displayed when a player dies, showing relevant failure information along with buttons to return to the menu, restart the game, or exit.
  - **LevelSelect.fxml**: This screen appears once the player clicks the start button in the menu, offering three buttons to select from different game levels.
  - **Transition.fxml**: Shown after a player completes a level (except for level 3), this screen presents information about the time taken, score achieved, and number of enemies defeated, along with buttons to restart, proceed to the next level, or return to the menu.
  - **Pause.fxml**: Displayed when the player clicks the pause button during gameplay, it offers options to restart, return to the menu, exit, and access help information, similar to Menu.fxml.
  - **Complete.fxml**: This screen appears when the player successfully completes level 3, showcasing the final score, total time spent, and number of enemies defeated, with buttons to exit or return to the menu.
  - **style.css**: A CSS file that defines the font styles, button formats, and label styles for all interface components.

## 2. Object-Oriented Design Pattern

### Factory Pattern: 
To streamline the creation of different types of game entities and tiles, we implemented the Factory Pattern through the `EntityFactory` class. This allows us to create entities based on specified types using a switch case structure.

 **Advantages of Using the Factory Pattern**
1. **Encapsulation of Creation Logic**: The Factory Pattern encapsulates the instantiation logic of complex objects within a dedicated factory class, making code easier to maintain and extend.

2. **Separation of Concerns**: The Factory Pattern promotes separation of concerns by segregating the creation logic from the actual gameplay logic, resulting in cleaner and more organized code.

3. **Improved Testability**: With clear separation and encapsulation of object creation, unit testing becomes more straightforward since mocks or stubs can be easily integrated.

### Command Pattern

To facilitate the development and implementation of movement logic for the player character, we adopted the Command Pattern through the `PlayCommand` interface. This design empowers us to create and utilize different movement commands while decoupling the command execution from the command user.

**Advantages of Using the Command Pattern**
1. **Encapsulation of Commands**: The Command Pattern encapsulates all the necessary details for executing a command in a single object, which simplifies the tracking and execution of user commands.

2. **Queueing of Commands**: Commands can be queued and executed sequentially or conditionally, allowing for better control over complex user interactions and multi-step processes.

3. **Enhanced Testability**: Testing becomes more manageable as each command can be tested independently. Mocks or stubs can be easily created for command execution, facilitating unit tests.

### Interpreter Pattern

To compute the player's score at the end of each level, we implemented the Interpreter Pattern through the `Expression` interface. This design pattern allows us to define a grammar for score calculation logic, enabling the parsing and evaluation of various scoring rules. In our implementation, we adjust the player's score based on the time elapsed (which decreases the score) and the number of enemies defeated (which increases the score).

**Advantages of Using the Interpreter Pattern**
1. **Dynamic Scoring Rule Evaluation**: The Interpreter Pattern allows for the dynamic evaluation and modification of scoring rules. This flexibility enables us to adapt scoring criteria easily in response to game balancing or player feedback.

2. **Ease of Extension**: New scoring rules can be introduced by defining additional grammar and expressions without significantly altering existing code. This facilitates growth and changes in future game versions.

3. **Enhanced Readability**: The use of expression trees in the Interpreter Pattern can make the scoring rules more readable and intuitive. This clarity helps both developers and designers who may need to adjust scoring conditions.

### Observer Pattern

In our game project, we implement the Observer Pattern through the `GameModelObserver` interface to provide real-time updates on the player's time and base score during each level. This pattern enables the decoupling of the game's score and timer from the user interface, ensuring that changes in the game state are automatically reflected in the display.

**Advantages of Using the Observer Pattern**
1. **Real-time Updates**: By utilizing the Observer Pattern, we can update the player's score and time in real time, providing immediate feedback to players. This responsiveness improves the overall gameplay experience.

2. **Ease of Maintenance**: When new features are introduced or modifications are needed, the Observer Pattern simplifies maintenance. Observers can be added or removed without significant changes to the core game logic.

3. **Scalability**: The implementation of the Observer Pattern enables easy scaling when adding new features. For instance, if we decide to implement additional UI components or notifications, they can easily listen to the same observable events.

### Singleton Pattern

In our game project, we implemented the Singleton Pattern through create the `ScreenManager` class. This class is responsible for managing the different screens in the game. By using the Singleton Pattern, we ensure that only one instance of `ScreenManager` exists throughout the application, allowing other components to access it easily and consistently to create their respective UI interfaces.

**Advantages of Using the Singleton Pattern**
1. **Global Access Point**: The Singleton Pattern provides a global access point to the `ScreenManager` instance, enabling different UI components to access the same instance without the need to pass references around explicitly.

2. **Controlled Instance Creation**: By controlling the instantiation of `ScreenManager`, we eliminate the risk of multiple instances being created, which could lead to inconsistent behavior and state management across different screens.

3. **Resource Management**: The Singleton Pattern allows for better management of shared resources, reducing memory overhead and enhancing performance. All screens can utilize a single manager instance to handle transitions and state without duplicating logic.

### State Pattern

In our game project, we implemented the State Pattern through create `MoveStateHandler` interface to manage the different movement states of the player. This pattern allows the player to transition between various states (such as sprinting, climbing, falling, jumping, and running) dynamically based on input and game context, providing a richer and more flexible control experience.

**Advantages of Using the State Pattern**
1. **Clear State Management**: The State Pattern encapsulates the various movement states of the player (like sprinting, climbing, falling, jumping, running, etc.) as separate state classes. This separation ensures that the behavior and logic of each state are independent, resulting in a clear and maintainable code structure.

2. **Dynamic State Transitions**: Players can seamlessly transition between different states based on conditions in the game (such as key presses or collision detection). Each state class is responsible for its specific behavior and can easily interact with other states.

3. **Code Reusability and Extensibility**: The State Pattern makes it easier to reuse and extend code. If a new state needs to be added (like "swimming"), we simply create a new state class and implement its specific behavior without altering existing code, adhering to the Open-Closed Principle.

---

## Javadocs

The Javadocs for the NGHMP project provide comprehensive documentation for the classes, interfaces, and methods within the source code. This section is intended to help visitors understand our implementation of JAVAdocs.

### Generating Javadocs

To generate the Javadocs for the project, follow these steps:

1. **Ensure JDK is Installed**: Make sure you have the Java Development Kit (JDK) installed on your machine. You can download it from [Oracle’s official website](https://www.oracle.com/java/technologies/javase-jdk11-downloads.html) or use an open-source alternative like OpenJDK.

2. **Navigate to Project Directory**: Open your command line or terminal and change your directory to the `src/` folder where the source code resides.

3. **Run the Javadoc Command**: Execute the following command to generate the documentation:
    ```bash
    javadoc -d ../docs/javadoc -sourcepath . -subpackages com.yourpackage
    ```
   Replace `com.yourpackage` with the appropriate package name of your source files.

### Documented Features

The Javadocs include documentation for the following components:

- **Classes**: Detailed descriptions of all classes, including the `Entity`, `Player`, `Enemy`, and any other significant classes used throughout the project.
- **Interfaces**: Documentation for key interfaces such as `EntityFactory`, `PlayCommand`, `Expression`, `GameModelObserver`, `MoveStateHandler`, etc.
- **Methods**: A thorough overview of methods within each class and interface, including parameters, return types, and exceptions thrown.

### Documentation Standards

We adhere to best practices for Javadoc comments, including:

- **Class-Level Comments**: Each class should have a comment that describes its purpose, functionality, and any important notes.
- **Method-Level Comments**: Each method should be documented with a brief description of what it does, parameters it takes, return values, and any exceptions that may be thrown.
- **Field-Level Comments**: Important fields should also include comments explaining their purpose within the class.

## Class Diagram

The class diagram is a visual representation of the main classes in the NGHMP project and their relationships. Below is the description and explanation of the class diagram:

### Class Diagram Explanation

- **Main Classes**: The class diagram includes the main classes in the project, including but not limited to:
  - `Entity`: An abstract base class for all game entities, including players, enemies, decorations, and coins.
  - `Player`: A concrete implementation of `Entity` that handles player behavior and state.
  - `Enemy`: A concrete implementation of `Entity` that defines enemy behavior.
  - `LevelManager`: Manages level logic, responsible for loading and switching levels.
  - `GameModel`: Manages game state (such as score, time, etc.) and logic.
  - `ScreenManager`: Implements the singleton pattern, responsible for managing and switching different UI screens.
  - `InputManager`: Handles user input and passes it to the corresponding commands for execution.
  - Various state classes (e.g., `RunningState`, `JumpingState`, `ClimbingState`, `FallingState`, etc.).

### Relationships Between Classes

- **Inheritance Relationships**:
  - The `Player` and `Enemy` classes inherit from the `Entity` class, indicating that they are specific types of entities.

- **Aggregation Relationships**:
  - `GameModel` has an aggregation relationship with `ScreenManager`, indicating that it can utilize `ScreenManager` to manage different screens.

- **Dependency Relationships**:
  - `LevelManager` depends on `GameModel` to update and communicate the current game state.
  - `InputManager` depends on the `Command` interface, allowing various movement commands to be implemented and executed.

### How to View the Class Diagram

The class diagram file can be found in the `Diagram` directory. You can open the `ClassDiagram.jpg` or `.vpp` file to view the complete class diagram. Additionally, you can use UML tools to generate visual representations that help understand the structure of the project.

### Class Diagram Display

![Class Diagram](Diagram/ClassDiagram.jpg)


## Appendix

The following open-source assets were used in this project:

- **Dagon's Fantasy All-In-One [16x16]** by Eduardo Scarpato
  - Author: [Eduardo Scarpato](https://eduardscarpato.itch.io/)
  - Source: [https://eduardscarpato.itch.io/dagon](https://eduardscarpato.itch.io/dagon)
  - License: This asset pack can be used in free and commercial projects. You cannot distribute or sell those assets directly (even modified).


- **Samurai 2D Pixel Art** by Mattz Art  
  - Author: [Mattz Art](https://xzany.itch.io/)  
  - Source: [https://xzany.itch.io/samurai-2d-pixel-art](https://xzany.itch.io/samurai-2d-pixel-art)  
  - License: You can use this asset in any game project, personal or commercial. You cannot resell or redistribute it as a standalone game asset; it must be part of a larger project. Credit is not required but appreciated. You may modify the asset to suit your needs. You are not allowed to turn any of the assets into NFTs.  