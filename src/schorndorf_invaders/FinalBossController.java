/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package schorndorf_invaders;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Leon
 */
// This class controls the final boss level in a JavaFX application. It handles game initialization, user input, and game state management.
public class FinalBossController implements Initializable {

    // JavaFX components from the FXML file
    @FXML
    private AnchorPane canvas; // The main canvas where game elements are displayed
    @FXML
    private Button startGameButton; // Button to start the game
    @FXML
    private Button pauseButton; // Button to pause the game
    @FXML
    private Button resumeButton; // Button to resume the game
    Spaceship spaceship = new Spaceship("/res/sprites/spaceship.png"); // Player's spaceship object
    
    Image black = new Image("/res/blackScreen.jpg"); // Image for transition effect
    ImageView blackScreen = new ImageView(black); // ImageView for the transition effect
    
    private FinalBossTimer timer = null; // Timer for game loop and events
    
    private MediaPlayer mediaPlayer; // MediaPlayer for playing background music
    
    private static final int MAX_ALIENS = 10; // Maximum number of aliens (unused in this context)
    MonkeySoup monkeySoup; // Final boss object
    
    private int health = 20; // Health of the final boss
    
    private boolean resetDone = true; // Flag to check if the game has been reset
    
    // Returns the canvas pane
    public Pane getCanvas() {
        return canvas;
    }
    
    // Starts the game when the move action is triggered
    public void handleMoveAction(ActionEvent event)
    {
        startGame();
    }
    
    // Stops the game timer when the stop action is triggered
    public void handleStoppAction(ActionEvent event)
    {
        timer.stop();
    }
    
    // Resumes the game timer when the resume action is triggered
    public void handleResumeAction(ActionEvent event)
    {      
        timer.start();
    }

    // Handles laser shot actions when mouse events occur
    public void handleLaserShot(MouseEvent event)
    {
        if(timer != null)
        {
            timer.handle(event); 
        }               
    }
    
    // Stops the game timer
    public void stopTimer()
    {
        timer.stop();
    }
    
    // Plays background music from a given file path
    public void playMusic(String musicFile) {
    try {
        URL musicFileUrl = getClass().getResource(musicFile);
        if (musicFileUrl != null) {
            Media media = new Media(musicFileUrl.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.3);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music indefinitely
            mediaPlayer.play();
        } else {
            System.out.println("File not found: " + musicFile);
        }
    } catch (URISyntaxException | MediaException e) {
        e.printStackTrace();
    }
}

    // Stops the background music
    public void stopMusic() {
    if (mediaPlayer != null) {
        mediaPlayer.stop();
        mediaPlayer.dispose(); // Properly release resources
        mediaPlayer = null; // Ensure it is set to null
    }
}
    
    // Fades out the background music over 4 seconds and then stops it
    public void fadeOutMusic() {
    if (mediaPlayer != null) {
            final double startVolume = mediaPlayer.getVolume();
            Timeline fadeOut = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(mediaPlayer.volumeProperty(), startVolume)),
                new KeyFrame(Duration.seconds(4), new KeyValue(mediaPlayer.volumeProperty(), 0))
            );
            fadeOut.setOnFinished(event -> mediaPlayer.stop());
            fadeOut.play();
        }
    }
    
    // Displays the final boss on the canvas
    public void showBoss()
    { 
        monkeySoup = timer.getMonkeySoup();
        monkeySoup.setX(canvas.getWidth()/2.05);
        monkeySoup.setY(-200);
        monkeySoup.setSmooth(true);
        health = monkeySoup.getHealth();
        canvas.getChildren().add(monkeySoup);
        System.out.println("GENERATED ALIENS");
    }
    
    // Resets the game to its initial state
    public void resetGame() 
    {
        stopMusic(); // Ensure the music is stopped during game reset
        mediaPlayer = null; // Nullify the mediaPlayer to force reinitialization

        canvas.getChildren().removeIf(node -> !node.isVisible());
        canvas.getChildren().clear(); // Clear the canvas of any existing game elements
        resetDone = true;
        startGameButton.setVisible(true); // Show the start button again
        canvas.getChildren().add(startGameButton);
        canvas.getChildren().add(pauseButton);
        canvas.getChildren().add(resumeButton);
        monkeySoup.setHealth(20);
    
        startGameButton.requestFocus();

        monkeySoup.reset();
        spaceship.reset(); // Reset spaceship state

        if (timer != null) {
            timer.stop(); // Stop the animation timer if it's running
            timer.setDead(false); // Reset dead flag in animation timer
            timer.setDeadLaser(false); // Reset deadLaser flag in animation timer
            timer.setDeathScreenAdded(false); // Reset deathScreenAdded flag in animation timer
            timer.setLaughPlayed(false);
            timer.setStartingAnimationCounter(0);
            timer.setTextShown(false);
            timer.setBossHealth(20);
            timer.setResetDone(resetDone);     
            timer.initializeBoss(); // Reinitialize aliens
        }
    }

    // Initializes and starts the game
    public void startGame()
    {
        playMusic("/res/sounds/musicSounds/bossTheme.wav");
        resetDone = false;
        canvas.getChildren().removeIf(node -> !node.isVisible());
        spaceship.setFitHeight(112.5);
        spaceship.setFitWidth(88.5);
        spaceship.setY(canvas.getHeight() - 130);
        spaceship.setX(canvas.getWidth() - canvas.getWidth() / 2.05);
        spaceship.setSmooth(true);
        canvas.getChildren().add(spaceship);

        if (timer == null) {
            timer = new FinalBossTimer(canvas, spaceship, this);
            canvas.getScene().getRoot().setOnKeyPressed(timer);
            canvas.getScene().getRoot().setOnKeyReleased(timer);
        }

        showBoss();
        
        startGameButton.setVisible(false);
        if (canvas != null) {
            canvas.getScene().setOnKeyPressed(this::handleResetKeyPressed);
        }
        timer.setResetDone(resetDone);
        timer.start();
    }

    // Handles the reset key press event to reset the game
    @FXML
    public void handleResetKeyPressed(KeyEvent event) 
    {
        if (event.getCode() == KeyCode.R) { // Check if the pressed key is 'R' for reset
        resetGame(); // Call the resetGame method to reset the game
        }     
    } 
    
    // Initializes the controller and sets up the UI components
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
        
    
}
