/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package schorndorf_invaders;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author TrogrlicLeon
 */
public class StartScreenController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    private AnchorPane canvas;
    
    ImageView image = new ImageView("/res/startText1.png");

    public AnchorPane getCanvas() {
        return canvas;
    }

     public void handlePlay(ActionEvent event) throws IOException
    {
        Schorndorf_Invaders.getApplication().setScene("LevelOne.fxml");
    }
     
      public void handleControls(ActionEvent event)
    {
        
    }
      
       public void handleAbout(ActionEvent event)
    {
        
    }
       
    @Override
    public void initialize(URL url, ResourceBundle rb) {       
        /*image.setFitWidth(canvas.getWidth());
        System.out.println(canvas.getWidth());
        image.setY(100);      
        canvas.getChildren().add(image);*/
        // TODO
    }  
}
