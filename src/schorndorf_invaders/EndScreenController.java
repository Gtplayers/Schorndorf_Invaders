/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package schorndorf_invaders;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class EndScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane canvas;
    
    private MediaPlayer mediaPlayer;

    public AnchorPane getCanvas() {
        return canvas;
    }

     public void handlePlay(ActionEvent event) throws IOException
    {
        Schorndorf_Invaders.getApplication().setScene("StartScreen.fxml");
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
        playMusic("/res/sounds/musicSounds/menuTheme.mp3");
        // TODO
    }     
    
}
