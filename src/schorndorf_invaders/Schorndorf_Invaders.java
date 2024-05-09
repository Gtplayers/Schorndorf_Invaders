/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXML.java to edit this template
 */
package schorndorf_invaders;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

/**
 *
 * @author TrogrlicLeon
 */
public class Schorndorf_Invaders extends Application {
    @FXML
    private Pane canvas;
    
    
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();
        
        // Get the controller
        FXMLDocumentController controller = loader.getController();

        // Load the image
        Image image = new Image("/res/moonBackground.png");
        
        // Create a BackgroundSize object
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);  

        // Create a BackgroundImage object
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        // Set the background of the pane
        controller.getCanvas().setBackground(new Background(backgroundImage));
        
        Scene scene = new Scene(root);

        
        stage.setScene(scene);
        stage.setFullScreen(true);

        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
