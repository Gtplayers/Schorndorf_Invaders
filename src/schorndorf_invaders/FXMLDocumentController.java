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
    private Pane canvas;
    @FXML
    private Circle circle;
    
    private MyAnimationTimer meinAniTimer = null;
    public void handleMoveAction(ActionEvent event)
    {
        // falls noch nicht vorhanden meinAniTimer-Objekt erzeugen
        if (meinAniTimer == null)
        {
            meinAniTimer = new MyAnimationTimer(circle, canvas);
            canvas.getScene().getRoot().setOnKeyPressed(meinAniTimer);
        }

        // timer starten
        meinAniTimer.start();
    }

    public void handleStoppAction(ActionEvent event)
    {
        meinAniTimer.stop();
    }

    public void handleColorAction(ActionEvent event)
    {
        circle.setFill(Color.rgb(
                Zufall.zweihundertFuenfUndFuenfzig(),
                Zufall.zweihundertFuenfUndFuenfzig(),
                Zufall.zweihundertFuenfUndFuenfzig()));
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
