/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package schorndorf_invaders;

import java.net.URL;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;


/**
 *
 * @author TrogrlicLeon
 */
/**
 * Controller class for the Level One scene of the game.
 * Implements Initializable to allow initialization logic after the FXML fields are injected.
 */
public class LevelOneController implements Initializable  {
    
    // FXML injected fields
    @FXML
    private AnchorPane canvas; // The main pane that holds other UI elements
    @FXML
    private Button startGameButton; // Button to start the game
    @FXML
    private Button pauseButton; // Button to pause the game
    @FXML
    private Button resumeButton; // Button to resume the game
    Spaceship spaceship = new Spaceship("/res/sprites/spaceship.png"); // Player's spaceship
    
    Image black = new Image("/res/blackScreen.jpg"); // Background image for transitions
    ImageView blackScreen = new ImageView(black); // ImageView for the background image
    
    private LevelOneTimer timer = null; // Timer for game events and updates
    
    private static final int MAX_ALIENS = 10; // Maximum number of aliens
    Alien[] aliens = new Alien[MAX_ALIENS]; // Array to store alien instances
    
    private Text scoreText = new Text(); // Text to display the score
    private int score = 0; // Player's score
    
    private boolean resetDone = true; // Flag to check if the game was reset
    
    // Getter for the canvas
    public Pane getCanvas() {
        return canvas;
    }
    
    // Handles the action to move the spaceship
    public void handleMoveAction(ActionEvent event) {
        startGame();
    }
    
    // Handles the action to stop the game
    public void handleStoppAction(ActionEvent event) {
        timer.stop();
    }
    
    // Handles the action to resume the game
    public void handleResumeAction(ActionEvent event) {
        timer.start();
    }

    // Handles the action when a laser shot is fired
    public void handleLaserShot(MouseEvent event) {
        if(timer != null) {
            timer.handle(event); 
        }
    }
    
    // Stops the game timer
    public void stopTimer() {
        timer.stop();
    }
    
    // Updates the score and displays it
    public void updateScore() 
    {
        score++;
        scoreText.setText("Score: " + score);
        timer.setScore(score);
        if(score == 10)
        {
            showAliens();
        }
        else if(score == 11)
        {
            score = 1;
        }
    }
    
    // Shows the aliens on the screen
    public void showAliens()
    {
        aliens = timer.getAliens();
        for (int i = 0; i < MAX_ALIENS; i++) {
            aliens[i].setY(150);
            aliens[i].setX(i * 200 + 100);
            aliens[i].setSmooth(true);
            if (!canvas.getChildren().contains(aliens[i])) {
                canvas.getChildren().add(aliens[i]);
            }
        }
        System.out.println("GENERATED ALIENS");
    }
    
    // Resets the game to its initial state
    public void resetGame() {
        canvas.getChildren().removeIf(node -> !node.isVisible());
        canvas.getChildren().clear(); // Clear the canvas of any existing game elements
        resetDone = true;
        startGameButton.setVisible(true); // Show the start button again
        canvas.getChildren().add(startGameButton);
        canvas.getChildren().add(pauseButton);
        canvas.getChildren().add(resumeButton);
        startGameButton.requestFocus();

        spaceship.reset(); // Reset spaceship state

        if (timer != null) {
            timer.stop(); // Stop the animation timer if it's running
            timer.setDead(false); // Reset dead flag in animation timer
            timer.setDeadLaser(false); // Reset deadLaser flag in animation timer
            timer.setDeathScreenAdded(false); // Reset deathScreenAdded flag in animation timer
            timer.setResetDone(resetDone); 
            timer.initializeAliens(); // Reinitialize aliens
        }

        score = 0; // Reset the score
        scoreText.setText("Score: 0"); // Update the score display
    }

    // Starts the game
    public void startGame()
    {
        resetDone = false;
        canvas.getChildren().removeIf(node -> !node.isVisible());
        spaceship.setFitHeight(112.5);
        spaceship.setFitWidth(88.5);
        spaceship.setY(canvas.getHeight() - 130);
        spaceship.setX(canvas.getWidth() - canvas.getWidth() / 1.87);
        spaceship.setSmooth(true);
        canvas.getChildren().add(spaceship);

        if (timer == null) {
            timer = new LevelOneTimer(canvas, spaceship, this);
            canvas.getScene().getRoot().setOnKeyPressed(timer);
            canvas.getScene().getRoot().setOnKeyReleased(timer);
        }

        scoreText.setX(canvas.getWidth() - 150);
        scoreText.setY(60);
        scoreText.setFill(Color.WHITE);
        scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        canvas.getChildren().add(scoreText);
        scoreText.setText("Score: 0");

        showAliens();
        
        startGameButton.setVisible(false);
        if (canvas != null) {
            canvas.getScene().setOnKeyPressed(this::handleResetKeyPressed);
        }
        timer.setResetDone(resetDone);
        timer.start();
    }

    // Handles the key press for resetting the game
    @FXML
    public void handleResetKeyPressed(KeyEvent event) 
    {
        if (event.getCode() == KeyCode.R) { // Check if the pressed key is 'R' for reset
        resetGame(); // Call the resetGame method to reset the game
        }     
    } 
    
    // Initializes the controller class
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startGameButton.requestFocus();
        pauseButton.getStyleClass().add("button_control");
        resumeButton.getStyleClass().add("button_control");
        blackScreen.setOpacity(1.0);

        // Bind blackScreen size to canvas size
        blackScreen.fitWidthProperty().bind(canvas.widthProperty());
        blackScreen.fitHeightProperty().bind(canvas.heightProperty());

        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(3100), blackScreen);
        fadeInTransition.setFromValue(1.0);
        fadeInTransition.setToValue(0.0);
        fadeInTransition.play();

        canvas.getChildren().add(blackScreen);
        blackScreen.setMouseTransparent(true);
        resetDone = true;
        // TODO
    } 

    // Getter for score
    public int getScore() {
        return score;
    }

    // Setter for score
    public void setScore(int score) {
        this.score = score;
    }
    
    
}
