/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package schorndorf_invaders;

import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
public class FinalBossController implements Initializable {

    @FXML
    private AnchorPane canvas;
    @FXML
    private Button startGameButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button resumeButton;
    Spaceship spaceship = new Spaceship("/res/sprites/spaceship.png");
    
    
    
    private FinalBossTimer timer = null;
    
    private MediaPlayer mediaPlayer;
    
    private static final int MAX_ALIENS = 10;
    MonkeySoup monkeySoup;
    
    private int health;
    
    private boolean resetDone = true;
    
    public Pane getCanvas() {
        return canvas;
    }
    
    public void handleMoveAction(ActionEvent event)
    {
        startGame();
    }
    
    public void handleStoppAction(ActionEvent event)
    {
        timer.stop();
    }
    
    public void handleResumeAction(ActionEvent event)
    {      
        timer.start();
    }

    public void handleLaserShot(MouseEvent event)
    {
        if(timer != null)
        {
            timer.handle(event); 
        }
               
    }
    
    public void stopTimer()
    {
        timer.stop();
    }
    
    public void playMusic(String musicFile) {
    try {
        URL musicFileUrl = getClass().getResource(musicFile);
        if (musicFileUrl != null) {
            Media media = new Media(musicFileUrl.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music indefinitely
            mediaPlayer.play();
        } else {
            System.out.println("File not found: " + musicFile);
        }
    } catch (URISyntaxException | MediaException e) {
        e.printStackTrace();
    }
}

    public void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
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
    
    public void showBoss()
    { 
        monkeySoup = timer.getMonkeySoup();
        monkeySoup.setX(canvas.getWidth()/2.1);
        monkeySoup.setY(-200);
        monkeySoup.setSmooth(true);
        health = monkeySoup.getHealth();
        canvas.getChildren().add(monkeySoup);
        System.out.println("GENERATED ALIENS");
    }
    
    public void resetGame() {
    canvas.getChildren().removeIf(node -> !node.isVisible());
    canvas.getChildren().clear(); // Clear the canvas of any existing game elements
    resetDone = true;
    startGameButton.setVisible(true); // Show the start button again
    canvas.getChildren().add(startGameButton);
    canvas.getChildren().add(pauseButton);
    canvas.getChildren().add(resumeButton);
    monkeySoup.setHealth(2000);
    
    startGameButton.requestFocus();

    spaceship.reset(); // Reset spaceship state

    if (timer != null) {
        timer.stop(); // Stop the animation timer if it's running
        timer.setDead(false); // Reset dead flag in animation timer
        timer.setDeadLaser(false); // Reset deadLaser flag in animation timer
        timer.setDeathScreenAdded(false); // Reset deathScreenAdded flag in animation timer
        timer.setLaughPlayed(false);
        timer.setStartingAnimationCounter(0);
        timer.setTextShown(false);
        timer.setBossHealth(2000);
        timer.setResetDone(resetDone);     
        timer.initializeBoss(); // Reinitialize aliens
    }
}
    public void startGame()
    {
        playMusic("/res/sounds/musicSounds/bossTheme.wav");
        resetDone = false;
        canvas.getChildren().removeIf(node -> !node.isVisible());
        spaceship.setFitHeight(112.5);
        spaceship.setFitWidth(88.5);
        //spaceship.setFitWidth(1600);          TITLE SIZE
        //spaceship.setFitHeight(89);           TITLE SIZE
        spaceship.setY(canvas.getHeight() - 130);
        spaceship.setX(canvas.getWidth() - canvas.getWidth() / 1.87);
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
    @FXML
    public void handleResetKeyPressed(KeyEvent event) 
    {
        if (event.getCode() == KeyCode.R) { // Check if the pressed key is 'R' for reset
        resetGame(); // Call the resetGame method to reset the game
        }     
    } 
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startGameButton.requestFocus();
        resetDone = true;
        // TODO
    } 
        
    
}
