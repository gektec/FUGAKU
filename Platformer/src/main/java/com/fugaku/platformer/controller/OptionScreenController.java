package com.fugaku.platformer.controller;

import com.fugaku.platformer.model.GameModel;
import com.fugaku.platformer.view.MenuScreen;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class OptionScreenController {
    public Button defaultButton;
    public Button yellowButton;
    public Button blueButton;
    public Button purpleButton;
    public Button orangeButton;
    public Button greenButton;
    public Button redButton;
    public Button pinkButton;
    public Button menuButton;
    @FXML
    private GridPane root;  // Root layout for the option menu
    private Stage primaryStage;

    @FXML
    private void initialize() {
        loadBackground();

        // Set up event handlers for each button
        defaultButton.setOnAction(e -> handleColorChange(Color.web("#1C5261")));
        yellowButton.setOnAction(e -> handleColorChange(Color.YELLOW));
        blueButton.setOnAction(e -> handleColorChange(Color.BLUE));
        purpleButton.setOnAction(e -> handleColorChange(Color.PURPLE));
        orangeButton.setOnAction(e -> handleColorChange(Color.ORANGE));
        greenButton.setOnAction(e -> handleColorChange(Color.GREEN));
        redButton.setOnAction(e -> handleColorChange(Color.RED));
        pinkButton.setOnAction(e -> handleColorChange(Color.PINK));
    }


    /**
     * Loads and sets a pure color background for the completed screen.
     */
    private void loadBackground() {
        Color color = GameModel.getColor();
        root.setStyle(String.valueOf(color));
    }

    /**
     * Handles the color change action.
     * This method updates the GameModel with the new color code and
     * updates the menu controller's background color immediately.
     *
     */
    private void handleColorChange(Color color) {
        // Sets the color in the game model.
        GameModel.setColor(color);
        // Updates the menu background directly if menuController is available.
        updateBackgroundColor(color); // Fallback option to update background directly
    }


    /**
     * Updates the background color of the root element based on the given color code.
     */
    private void updateBackgroundColor(Color color) {
        String colorStr = String.format("#%02X%02X%02X",
                (int) (color.getRed() * 255),
                (int) (color.getGreen() * 255),
                (int) (color.getBlue() * 255));
        root.setStyle("-fx-background-color: " + colorStr + ";");
        GameModel.setColor(color);
    }


    /**
     * Assigns the primary stage to this controller.
     * @param primaryStage the Stage instance to be set
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    /**
     * Navigates back to the main menu when the menu button is pressed.
     */
    @FXML
    public void handleMenu() {
        MenuScreen menuScreen = new MenuScreen();
        menuScreen.show(primaryStage);
    }

}
