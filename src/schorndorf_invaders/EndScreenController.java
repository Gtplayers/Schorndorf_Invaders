/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package schorndorf_invaders;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Leon
 */
/**
 * Controller class for the end screen of the game.
 * Implements Initializable to allow initialization logic after the FXML fields are injected.
 */
public class EndScreenController implements Initializable {
 // FXML injected fields
    @FXML
    private AnchorPane canvas; // The main pane that holds other UI elements
    
    @FXML
    private Button menuButton; // Button to navigate back to the menu
    
    // MediaPlayer for playing background music
    private MediaPlayer mediaPlayer;
    
    // Images for the end screen
    Image black = new Image("/res/blackScreen.jpg"); // Background image
    ImageView blackScreen = new ImageView(black); // ImageView for the background image

    // Getter for the canvas
    public AnchorPane getCanvas() {
        return canvas;
    }

    /**
     * Handles the action when the menu button is clicked.
     * Initiates a fade transition effect and switches to the StartScreen scene.
     */
    public void handlePlay(ActionEvent event) throws IOException
    {
        blackScreen.setOpacity(0.0);    // Set initial opacity for fade in effect
        // Create and play a fade in transition for the black screen
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(3100), blackScreen);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
            // Pause transition to delay the scene switch
            PauseTransition pause = new PauseTransition(Duration.seconds(3.1));
            pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent events) {
                try {
                    Schorndorf_Invaders.getApplication().setScene("StartScreen.fxml");      // Switch to StartScreen scene
                    System.out.println("SWITCHED SCENES");
                } catch (IOException ex) {
                    Logger.getLogger(FinalBossTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        pause.play();
        fadeOutMusic();     // Fade out the music before switching scenes
    }
       
    /**
     * Plays the background music for the end screen.
     * @param musicFile The path to the music file to be played
     */
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

    /**
     * Fades out the currently playing music.
     */   
    public void fadeOutMusic() {
    if (mediaPlayer != null) {
        final double startVolume = mediaPlayer.getVolume();
        Timeline fadeOut = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(mediaPlayer.volumeProperty(), startVolume)),
            new KeyFrame(Duration.seconds(3), new KeyValue(mediaPlayer.volumeProperty(), 0))
        );
        fadeOut.setOnFinished(event -> mediaPlayer.stop());
        fadeOut.play();
    }
}
    
    /**
     * Initializes the controller class.
     * This method is called after all @FXML annotated members have been injected.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        menuButton.getStyleClass().add("button_start"); // Set CSS class for the menu button
        playMusic("/res/sounds/musicSounds/menuTheme.mp3"); // Play background music
        blackScreen.setOpacity(1.0); // Set initial opacity for fade in effect

        // Bind blackScreen size to canvas size
        blackScreen.fitWidthProperty().bind(canvas.widthProperty());
        blackScreen.fitHeightProperty().bind(canvas.heightProperty());

        // Create and play fade in transition for black screen
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(3100), blackScreen);
        fadeInTransition.setFromValue(1.0);
        fadeInTransition.setToValue(0.0);
        fadeInTransition.play();

        canvas.getChildren().add(blackScreen);// Add the black screen to the canvas
        blackScreen.setMouseTransparent(true); // Make black screen transparent to mouse events
        // TODO
    }     
    
}
