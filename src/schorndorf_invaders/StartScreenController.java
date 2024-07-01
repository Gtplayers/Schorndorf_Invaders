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
 * @author TrogrlicLeon
 */
// Controller class for the start screen of the game
public class StartScreenController implements Initializable {

    // FXML annotations to inject JavaFX components defined in FXML file
    @FXML
    private AnchorPane canvas; // The main pane that holds other UI elements
    
    @FXML
    private Button startButton; // Button to start the game
    
    @FXML
    private Button controlsButton; // Button to show game controls
    
    @FXML
    private Button aboutButton; // Button to show about information
    
    private MediaPlayer mediaPlayer; // MediaPlayer to play background music
    
    // Image for transition effect
    Image black = new Image("/res/blackScreen.jpg");
    ImageView blackScreen = new ImageView(black); // ImageView to display the black image

    // Getter method for canvas
    public AnchorPane getCanvas() {
        return canvas;
    }

    // Handles the action when the play button is clicked
    public void handlePlay(ActionEvent event) throws IOException
    {
        blackScreen.setOpacity(0.0);    // Set initial opacity to 0 for fade in effect
        // Create a fade in transition for the black screen
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(3100), blackScreen);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
            // Adjust the size of the black screen to match the canvas
            blackScreen.setFitHeight(canvas.getHeight());
            blackScreen.setFitWidth(canvas.getWidth());
            canvas.getChildren().add(blackScreen);  // Add the black screen to the canvas
            // Pause transition to delay the scene switch
            PauseTransition pause = new PauseTransition(Duration.seconds(3.1));
            pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent events) {
                try {
                    Schorndorf_Invaders.getApplication().setScene("LevelOne.fxml");         // Switch to LevelOne scene
                    System.out.println("SWITCHED SCENES");
                } catch (IOException ex) {
                    Logger.getLogger(FinalBossTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        pause.play();
        fadeOutMusic();             // Fade out the music before switching scenes
    }
     
    // Similar methods for handling controls and about button actions with different scene switches
    public void handleControls(ActionEvent event)
    {
        blackScreen.setOpacity(0.0);
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(3100), blackScreen);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
            blackScreen.setFitHeight(canvas.getHeight());
            blackScreen.setFitWidth(canvas.getWidth());
            canvas.getChildren().add(blackScreen);
            PauseTransition pause = new PauseTransition(Duration.seconds(3.1));
            pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent events) {
                try {
                    Schorndorf_Invaders.getApplication().setScene("ControlsScreen.fxml");
                    System.out.println("SWITCHED SCENES");
                } catch (IOException ex) {
                    Logger.getLogger(FinalBossTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        pause.play();
        fadeOutMusic();
    }
      
       public void handleAbout(ActionEvent event)
    {
        blackScreen.setOpacity(0.0);
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(3100), blackScreen);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
            blackScreen.setFitHeight(canvas.getHeight());
            blackScreen.setFitWidth(canvas.getWidth());
            canvas.getChildren().add(blackScreen);
            PauseTransition pause = new PauseTransition(Duration.seconds(3.1));
            pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent events) {
                try {
                    Schorndorf_Invaders.getApplication().setScene("CreditsScreen.fxml");
                    System.out.println("SWITCHED SCENES");
                } catch (IOException ex) {
                    Logger.getLogger(FinalBossTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        pause.play();
        fadeOutMusic();
    }
       // Method to play background music
       public void playMusic(String musicFile) {
    try {
        URL musicFileUrl = getClass().getResource(musicFile);
        if (musicFileUrl != null) {
            Media media = new Media(musicFileUrl.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.3); // Set volume to 30%
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music indefinitely
            mediaPlayer.play();
        } else {
            System.out.println("File not found: " + musicFile);
        }
    } catch (URISyntaxException | MediaException e) {
        e.printStackTrace();
    }
}
       // Method to fade out the music with a timeline animation 
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
    // Initialize method called after all @FXML annotated members have been injected   
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        // Set CSS classes for buttons 
        startButton.getStyleClass().add("button_start");
        controlsButton.getStyleClass().add("button_controls");
        aboutButton.getStyleClass().add("button_about");
        playMusic("/res/sounds/musicSounds/menuTheme2.mp3");    // Play background music
        // TODO
    }  
}
