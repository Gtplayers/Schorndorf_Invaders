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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import util.Zufall;


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
            meinAniTimer = new MyAnimationTimer(canvas, spaceship);
            canvas.getScene().getRoot().setOnKeyPressed(meinAniTimer);
            canvas.getScene().getRoot().setOnKeyReleased(meinAniTimer);
        }
        startGame.setVisible(false);
        // timer starten
        meinAniTimer.start();
    }

    public void handleStoppAction(ActionEvent event)
    {
        meinAniTimer.stop();
    }

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
