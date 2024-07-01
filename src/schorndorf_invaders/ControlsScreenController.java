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
// Controller class for the controls screen of the game
public class ControlsScreenController implements Initializable {

    // FXML annotations to inject JavaFX components defined in FXML file
    @FXML
    private AnchorPane canvas; // The main pane that holds other UI elements including images
    
    @FXML
    private Button menuButton; // Button to navigate back to the menu
    
    private MediaPlayer mediaPlayer; // MediaPlayer to play background music
    
    // Images for the controls screen
    Image black = new Image("/res/blackScreen.jpg"); // Background image
    ImageView blackScreen = new ImageView(black); // ImageView for the background image
    
    Image keys = new Image("/res/keys.png"); // Image showing keyboard controls
    ImageView keysImage = new ImageView(keys); // ImageView for the keyboard controls image

    Image mouse = new Image("/res/mouseClick.png"); // Image showing mouse controls
    ImageView mouseImage = new ImageView(mouse); // ImageView for the mouse controls image
    
    // Getter method for canvas
    public AnchorPane getCanvas() {
        return canvas;
    }

    // Handles the action when the menu button is clicked
     public void handlePlay(ActionEvent event) throws IOException
    {
        blackScreen.setOpacity(0.0);    // Set initial opacity for fade in effect
        blackScreen.toFront();          // Bring the black screen to the front
        blackScreen.setMouseTransparent(true);// Make the black screen transparent to mouse events
        // Create a fade in transition for the black screen
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
                    Schorndorf_Invaders.getApplication().setScene("StartScreen.fxml");  // Switch to StartScreen scene
                    System.out.println("SWITCHED SCENES");
                } catch (IOException ex) {
                    Logger.getLogger(FinalBossTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        pause.play();
        fadeOutMusic();     // Fade out the music before switching scenes
    }
       
       // Method to play background music
       public void playMusic(String musicFile) {
    try {
        URL musicFileUrl = getClass().getResource(musicFile);
        if (musicFileUrl != null) {
            Media media = new Media(musicFileUrl.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setVolume(0.3);     // Set volume to 30%
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
        menuButton.getStyleClass().add("button_start"); // Set CSS class for the menu button
        playMusic("/res/sounds/musicSounds/menuTheme2.mp3"); // Play background music

        // Setup for black screen fade in effect    
        blackScreen.setOpacity(1.0);

        // Bind blackScreen size to canvas size for responsive design
        blackScreen.fitWidthProperty().bind(canvas.widthProperty());
        blackScreen.fitHeightProperty().bind(canvas.heightProperty());

        // Create and play fade in transition for black screen
        FadeTransition fadeInTransition = new FadeTransition(Duration.millis(3100), blackScreen);
        fadeInTransition.setFromValue(1.0);
        fadeInTransition.setToValue(0.0);
        fadeInTransition.play();

        // Add black screen to canvas and set properties
        canvas.getChildren().add(blackScreen);
        blackScreen.toFront();      // Ensure black screen is at the front
        blackScreen.setMouseTransparent(true);      // Make black screen transparent to mouse events

        // Add keysImage to canvas and set initial position
        canvas.getChildren().add(keysImage);
        keysImage.setPreserveRatio(true);
        keysImage.setFitWidth(500); 

        // Use listeners to dynamically position keysImage at the top middle of the canvas
        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
        keysImage.setX(((newVal.doubleValue() - keysImage.getFitWidth()) / 2) - (newVal.doubleValue() * 0.2));
        });
    
        keysImage.setY(530); 
    
        // Add mouseImage to canvas and set initial position and size
        canvas.getChildren().add(mouseImage);
        mouseImage.setPreserveRatio(true);
        mouseImage.setFitWidth(500); 

        // Use listeners to dynamically position keysImage at the top middle of the canvas
        canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
        mouseImage.setX(((newVal.doubleValue() - mouseImage.getFitWidth()) / 2) + (newVal.doubleValue() * 0.2));
        }); 
        mouseImage.setY(500); 
        // TODO
    }       
    
}
