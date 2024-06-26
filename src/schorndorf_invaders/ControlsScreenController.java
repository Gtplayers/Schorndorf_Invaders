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
public class ControlsScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane canvas;
    
    private MediaPlayer mediaPlayer;
    
    Image black = new Image("/res/blackScreen.jpg");
    ImageView blackScreen = new ImageView(black);
    
    Image keys = new Image("/res/keys.png");
    ImageView keysImage = new ImageView(keys);

    Image mouse = new Image("/res/mouseClick.png");
    ImageView mouseImage = new ImageView(mouse);
    
    public AnchorPane getCanvas() {
        return canvas;
    }

     public void handlePlay(ActionEvent event) throws IOException
    {
        blackScreen.setOpacity(0.0);
        blackScreen.toFront();
        blackScreen.setMouseTransparent(true);
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(3100), blackScreen);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
            PauseTransition pause = new PauseTransition(Duration.seconds(3.1));
            pause.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent events) {
                try {
                    Schorndorf_Invaders.getApplication().setScene("StartScreen.fxml");
                    System.out.println("SWITCHED SCENES");
                } catch (IOException ex) {
                    Logger.getLogger(FinalBossTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        pause.play();
        fadeOutMusic();
    }
       
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
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {   
        playMusic("/res/sounds/musicSounds/menuTheme2.mp3");
    blackScreen.setOpacity(1.0);

    // Bind blackScreen size to canvas size
    blackScreen.fitWidthProperty().bind(canvas.widthProperty());
    blackScreen.fitHeightProperty().bind(canvas.heightProperty());

    FadeTransition fadeInTransition = new FadeTransition(Duration.millis(3100), blackScreen);
    fadeInTransition.setFromValue(1.0);
    fadeInTransition.setToValue(0.0);
    fadeInTransition.play();

    canvas.getChildren().add(blackScreen);
    blackScreen.toFront();
    blackScreen.setMouseTransparent(true);

    // Add keysImage to canvas and set initial position
    canvas.getChildren().add(keysImage);
    keysImage.setPreserveRatio(true);
    keysImage.setFitWidth(500); // Set this to your desired width or use binding if the size should be dynamic

    // Use listeners to dynamically position keysImage at the top middle of the canvas
    canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
    keysImage.setX(((newVal.doubleValue() - keysImage.getFitWidth()) / 2) - (newVal.doubleValue() * 0.2));
    });
    // Set initial Y position or use a listener if the Y position needs to be dynamic based on canvas height
    keysImage.setY(530); 
    
    // Add keysImage to canvas and set initial position
    canvas.getChildren().add(mouseImage);
    mouseImage.setPreserveRatio(true);
    mouseImage.setFitWidth(500); 

    // Use listeners to dynamically position keysImage at the top middle of the canvas
    canvas.widthProperty().addListener((obs, oldVal, newVal) -> {
    mouseImage.setX(((newVal.doubleValue() - mouseImage.getFitWidth()) / 2) + (newVal.doubleValue() * 0.2));
    });
    // Set initial Y position or use a listener if the Y position needs to be dynamic based on canvas height
    mouseImage.setY(500); 

    // TODO
    }       
    
}
