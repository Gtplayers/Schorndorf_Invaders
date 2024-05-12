/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML2.java to edit this template
 */
package schorndorf_invaders;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


/**
 *
 * @author TrogrlicLeon
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML
    private AnchorPane canvas;
    @FXML
    private Button startGame;
    Spaceship spaceship = new Spaceship("/res/spaceship.png");
    
    
    private MyAnimationTimer meinAniTimer = null;
    
    private static final int MAX_ALIENS = 10;
    Alien[] aliens = new Alien[MAX_ALIENS];
    
    private Text scoreText = new Text();
    private int score = 0;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    public Pane getCanvas() {
        return canvas;
    }
    
    public void handleMoveAction(ActionEvent event)
    {
        spaceship.setFitHeight(150);
        spaceship.setFitWidth(150);
        spaceship.setY(canvas.getHeight() - 130);
        spaceship.setX(canvas.getWidth() - canvas.getWidth()/1.87);
        spaceship.setSmooth(true);
        canvas.getChildren().add(spaceship);
        
        // falls noch nicht vorhanden meinAniTimer-Objekt erzeugen
        if (meinAniTimer == null)
        {
            meinAniTimer = new MyAnimationTimer(canvas, spaceship, this);
            canvas.getScene().getRoot().setOnKeyPressed(meinAniTimer);
            canvas.getScene().getRoot().setOnKeyReleased(meinAniTimer);
        }
        
        aliens = meinAniTimer.getAliens();
        for (int i = 0; i < MAX_ALIENS; i++) 
        {
            aliens[i].setFitHeight(100);
            aliens[i].setFitWidth(100);
            aliens[i].setY(150);
            aliens[i].setX(i*200 + 100);
            aliens[i].setSmooth(true);
            if (aliens[i] != null) 
            {
                if (!canvas.getChildren().contains(aliens[i])) 
                {
                    canvas.getChildren().add(aliens[i]);
                }
            }
        }
        scoreText.setX(canvas.getWidth() -  150); 
        scoreText.setY(60); 
        scoreText.setFill(Color.WHITE); // Set the text color
        scoreText.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Set the font
        canvas.getChildren().add(scoreText);
        scoreText.setText("Score: 0");
        
        canvas.getChildren().remove(startGame);
        startGame.setVisible(false);
        // timer starten
        meinAniTimer.start();
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
        meinAniTimer.handle(event);
    }
    
    public void updateScore() {
        score++;
        scoreText.setText("Score: " + score);
    }
    
    
}
