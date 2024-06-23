/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package schorndorf_invaders;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Leon
 */
public class LevelTwoController implements Initializable {

    @FXML
    private AnchorPane canvas;
    @FXML
    private Button startGameButton;
    @FXML
    private Button pauseButton;
    @FXML
    private Button resumeButton;
    //Spaceship spaceship = new Spaceship("/res/sprites/spaceship.png", this);
    
    
    private LevelOneTimer meinAniTimer = null;
    
    private static final int MAX_ALIENS = 10;
    Alien[] aliens = new Alien[MAX_ALIENS];
    
    private Text scoreText = new Text();
    private int score = 0;
    
    private boolean resetDone = true;
    
    public Pane getCanvas() {
        return canvas;
    }
    
    public void handleMoveAction(ActionEvent event)
    {
        //startGame();
    }
    
    public void handleStoppAction(ActionEvent event)
    {
        meinAniTimer.stop();
    }
    
    public void handleResumeAction(ActionEvent event)
    {      
        meinAniTimer.start();
    }

    public void handleLaserShot(MouseEvent event)
    {
        if(meinAniTimer != null)
        {
            meinAniTimer.handle(event); 
        }
               
    }
    
    /*public void updateScore() 
    {
        score++;
        scoreText.setText("Score: " + score);
        meinAniTimer.setScore(score);
        if(score == 10)
        {
            showAliens();
        }
        else if(score == 11)
        {
            score = 1;
        }
    }
    
    public void showAliens()
    {
        aliens = meinAniTimer.getAliens();
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

    if (meinAniTimer != null) {
        meinAniTimer.stop(); // Stop the animation timer if it's running
        meinAniTimer.setDead(false); // Reset dead flag in animation timer
        meinAniTimer.setDeadLaser(false); // Reset deadLaser flag in animation timer
        meinAniTimer.setDeathScreenAdded(false); // Reset deathScreenAdded flag in animation timer
        meinAniTimer.setResetDone(resetDone); 
        meinAniTimer.initializeAliens(this); // Reinitialize aliens
    }

    score = 0; // Reset the score
    scoreText.setText("Score: 0"); // Update the score display
}
    public void startGame()
    {
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

        if (meinAniTimer == null) {
            meinAniTimer = new MyAnimationTimer(canvas, spaceship, this);
            canvas.getScene().getRoot().setOnKeyPressed(meinAniTimer);
            canvas.getScene().getRoot().setOnKeyReleased(meinAniTimer);
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
        meinAniTimer.setResetDone(resetDone);
        meinAniTimer.start();
    }
    @FXML
    public void handleResetKeyPressed(KeyEvent event) 
    {
        if (event.getCode() == KeyCode.R) { // Check if the pressed key is 'R' for reset
        resetGame(); // Call the resetGame method to reset the game
        }     
    } 
    */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        startGameButton.requestFocus();
        resetDone = true;
        // TODO
    } 

    /*public int getScore() {
        return score;
    }  */
}
