package com.fugaku.platformer.controller;

import com.fugaku.platformer.model.GameModel;
import com.fugaku.platformer.view.MenuScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class OptionScreenController {
    public Button blackButton;
    public Button yellowButton;
    public Button blueButton;
    public Button purpleButton;
    public Button orangeButton;
    public Button greenButton;
    public Button redButton;
    public Button pinkButton;
    @FXML
    private GridPane root;  // Root layout for the option menu
    @FXML
    private Stage primaryStage;  // The main application stage
    private MenuScreenController menuController;

    /**
     * Sets the menu controller, allowing updates to the menu screen.
     *
     * @param menuController the MenuScreenController instance
     */
    public void setMenuController(MenuScreenController menuController) {
        this.menuController = menuController;
    }

    @FXML
    private void initialize() {
        loadBackgroundImage();

        // Set up event handlers for each button
        blackButton.setOnAction(e -> handleColorChange('K'));
        yellowButton.setOnAction(e -> handleColorChange('Y'));
        blueButton.setOnAction(e -> handleColorChange('B'));
        purpleButton.setOnAction(e -> handleColorChange('P'));
        orangeButton.setOnAction(e -> handleColorChange('O'));
        greenButton.setOnAction(e -> handleColorChange('G'));
        redButton.setOnAction(e -> handleColorChange('R'));
        pinkButton.setOnAction(e -> handleColorChange('N')); // Assuming 'N' is used for Pink
    }


    /**
     * Loads and sets a pure color background for the completed screen.
     */
    private void loadBackgroundImage() {
        char colorCode = GameModel.getColor();
        switch (colorCode) {
            case 'K':
                root.setStyle("-fx-background-color: #000000;");
                break;
            case 'O':
                root.setStyle("-fx-background-color: #FFA500;");
                break;
            case 'Y':
                root.setStyle("-fx-background-color: #FFFF00;");
                break;
            case 'B':
                root.setStyle("-fx-background-color: #87CEEB;");
                break;
            case 'P':
                root.setStyle("-fx-background-color: #800080;");
                break;
            case 'G':
                root.setStyle("-fx-background-color: #008000;");
                break;
            case 'R':
                root.setStyle("-fx-background-color: #FF0000;");
                break;
            case 'N': // Assuming 'P' is used for both Purple and Pink, using 'N' for Pink to differentiate.
                root.setStyle("-fx-background-color: #FFC0CB;");
                break;
            default:
                root.setStyle("-fx-background-color: #FFFFFF;");
                break;
        }
    }

    /**
     * Handles the color change action.
     * This method updates the GameModel with the new color code and
     * updates the menu controller's background color immediately.
     *
     * @param colorCode the new color code
     */
    private void handleColorChange(char colorCode) {
        // Sets the color in the game model.
        GameModel.setColor(colorCode);
        // Updates the menu background directly if menuController is available.
            updateBackgroundColor(colorCode); // Fallback option to update background directly
    }


    /**
     * Updates the background color of the root element based on the given color code.
     * @param colorCode the code of the color
     */
    private void updateBackgroundColor(char colorCode) {
        switch (colorCode) {
            case 'K':
                root.setStyle("-fx-background-color: #000000;");
                break;
            case 'O':
                root.setStyle("-fx-background-color: #FFA500;");
                break;
            case 'Y':
                root.setStyle("-fx-background-color: #FFFF00;");
                break;
            case 'B':
                root.setStyle("-fx-background-color: #87CEEB;");
                break;
            case 'P':
                root.setStyle("-fx-background-color: #800080;");
                break;
            case 'G':
                root.setStyle("-fx-background-color: #008000;");
                break;
            case 'R':
                root.setStyle("-fx-background-color: #FF0000;");
                break;
            case 'N':
                root.setStyle("-fx-background-color: #FFC0CB;");
                break;
            default:
                root.setStyle("-fx-background-color: #FFFFFF;");
                break;
        }
    }


    /**
     * Assigns the primary stage to this controller.
     * @param primaryStage the Stage instance to be set
     */
    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void handleblack() {
        GameModel.setBlackColor();
    }

    public void handleyellow() {
        GameModel.setYellowColor();
    }

    public void handleblue() {
        GameModel.setBlueColor();
    }

    public void handlepurple() {
        GameModel.setPurpleColor();
    }

    public void handleorange() {
        GameModel.setPinkColor();
    }

    public void handlegreen() {
        GameModel.setGreenColor();
    }

    public void handlered() {
        GameModel.setRedColor();
    }

    public void handlepink() {
        GameModel.setPinkColor();
    }


}
