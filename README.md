# FUGAKU - the Fast Unprecedented Gripping Arcade Kinetic Unique PLATFORMER

![GameScreenshot](FUGAKU.png)

## Advanced Features

### Momentum-Based Movement System

- The player gets accelerated when the move button is pressed.
- The accelerated speed is reduced when closer to the goal speed, forming a second derivative velocity curve.
- The player can slide and jump on walls.
- The player can dash in 8 directions.

### Auto-Switch Move State System

- Both player and enemy have a `MoveState` that can be automatically switched.
- Different behaviour is applied on each State.

### 1. Multiple Entities

All objects are based on the `Entity` abstract class:

#### 1.1 Different Types of Moveable Entities

- Enemy
- Player

#### 1.2 Different Types of Tiles

- Ladder
- Spike
- Goal
- Decoration
- Coin

### 2. Multiple Screens

- Start Screen
- Fail Screen
- Level Choosing Screen
- Pause Screen
- Ranking Screen
- Level Transistion Screen
- Level Completed Screen
- Option Screen

### 3. Sound Effects

- Background music
- Jump Sound
- Dash Sound
- Step Sound


### 4. Textures

- Texture for tiles, player, and enemy.
- Textures can swap between states.
- Textures can have animations.
- Background has textures that can move for perspective effent.

### 5. Others

- Fixed framerate.
- DEBUG mode allows monitoring multiple statuses.
- Consistent and advanced Retro art style.


## Demo video

<video width="320" height="240" controls>
  <source src="Video/" type="video/mp4">
  Your browser does not support the video tag.
</video>


## Git Usage

We synchronize all project progress in real time on GitHub following these guidelines:

- **Standardized Directory Structure**: Adopt a standardized structural directory for our project to enhance maintainability and readability. Our structure includes:
  - `src/` - Source code
  - `tests/` - Test code
  - `resources/` - Resources

- **Flexible Branch Usage** We feel sorry that we deleted merged branch. Please do check our [repository graph](https://csprojects.nottingham.edu.cn/scycl11/platformer/-/network/main?ref_type=heads) to evaluate our Branch usage.

- **Efficient Issue Tracking**


## Refactoring

### JavaFX MVC GUI Design Pattern

- **MVC Pattern**: Separates game logic, UI, and input handling for cleaner code.

- **UI Components**: Multiple FXML screens (Menu, Fail, LevelSelect, Transition, Pause, Complete) enhanced with style.css.

### Object-Oriented Design Pattern

- **Singleton Pattern** : Manages screens with a single ScreenManager instance.

- **Factory Pattern**: Simplifies creation of game entities and tiles via EntityFactory.

- **Command Pattern** : Manages player movements using PlayCommand interface.

- **Interpreter Pattern** : Calculates scores with Expression interface based on time and enemies defeated.

- **State Pattern** : Handles player movement states with MoveStateHandler.

- **Observer Pattern** : Provides real-time updates on player's score and time through GameModelObserver.

## Javadocs

Our Javadoc is a comprehensive documentation for classes, interfaces, and methods.

- **Classes**: Descriptions of purpose and functionality.
- **Interfaces**: Details on functionality, parameters, and returns.
- **Methods**: Explanations of important fields.


## Class Diagram

Visual representation of main classes and their relationships is available in the Diagram directory as ClassDiagram-20513999-20513997.png or .vpp. Use UML tools for detailed views.

### Class Diagram Display

![Class Diagram](Diagram/ClassDiagram-20513999-20513997.png)


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

- **Retro Player 90 Movement SFX** by Leohpaz
  - Author: [Leohpaz](https://leohpaz.itch.io/)
  - Source: [https://leohpaz.itch.io/90-retro-player-movement-sfx](https://leohpaz.itch.io/90-retro-player-movement-sfx)
  - License: Both the demo and the complete pack are free to use in your projects. You may not sell it or distribute this asset pack for free; please redirect people to this page if someone else shows interest in my work. Credits are not mandatory, but much appreciated!

- **将棋駒／Shogi Pieces** by AlisAlia
  - Author: [AlisAlia](https://alisalia.itch.io/)
  - Source: [https://alisalia.itch.io/shogi-pieces](https://alisalia.itch.io/shogi-pieces)
  - License: 個人または商用プロジェクトも自由に使用できます。あなたのゲームに合わせて編集または変更できます。このアセットパックを変更していない状態で再販することはできません。このアセットパックを自分のものとして主張することはできません。不明点がございましたらよりお願いします。

- **m6x11** by Daniel Linssen
  - Author: [Daniel Linssen](https://managore.itch.io/)
  - Source: [https://managore.itch.io/m6x11](https://managore.itch.io/m6x11)
  - License: Free to use with attribution.

- **Hybrid song 2:20 (Funky stars)** 
  - Author: Quazar of Sanxion
  - License: copyright-free.