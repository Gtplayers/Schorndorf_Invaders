package schorndorf_invaders;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.stage.Stage;

/**
 *
 * @author TrogrlicLeon
 */
/**
 * Main class for the Schorndorf Invaders game, extending the JavaFX Application class.
 * This class is responsible for setting up the game's stages and scenes, including backgrounds and controllers.
 */
public class Schorndorf_Invaders extends Application {

    private static Schorndorf_Invaders application;// Singleton instance of the game application
    private Stage stage; // Primary stage for the game
    
    
    ImageView title = new ImageView("/res/startText1.png");// Title image view
    private String currentFxml; // Tracks the current FXML file being displayed

    @Override
    public void start(Stage stage) throws Exception {
        application = this; // Initialize the singleton instance
        this.stage = stage; // Set the primary stage

        // Set the initial scene to the start screen
        setScene("StartScreen.fxml");
    }

    /**
     * Sets the scene for the given FXML file.
     * This method loads the FXML, sets the scene, and applies the appropriate background based on the controller type.
     */
    public void setScene(String fxml) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml)); // Load the FXML file
        Parent root = loader.load(); // Load the root node from FXML
        Scene scene = new Scene(root); // Create a new scene with the loaded root
        scene.getStylesheets().add("/schorndorf_invaders/myCss.css"); // Add CSS stylesheet
        Object controller = loader.getController(); // Get the controller associated with the FXML
        
        this.currentFxml = fxml; // Update the current FXML file

        // Set the background based on the controller type
        if (controller instanceof StartScreenController) 
        {
            setStartScreenBackground((StartScreenController) controller);
        } 
        else if (controller instanceof LevelOneController) 
        {
            setLevelOneBackground((LevelOneController) controller);
        } 
        else if (controller instanceof LevelTwoController)     
        {
            setLevelTwoBackground((LevelTwoController) controller);
        }
        else if (controller instanceof LevelThreeController)     
        {
            setLevelThreeBackground((LevelThreeController) controller);
        }
        else if (controller instanceof FinalBossController)     
        {
            setFinalBossBackground((FinalBossController) controller);
        }
        else if (controller instanceof EndScreenController)     
        {
            setEndScreenBackground((EndScreenController) controller);
        }
        else if (controller instanceof ControlsScreenController)     
        {
            setControlsBackground((ControlsScreenController) controller);
        }
        else if (controller instanceof CreditsScreenController)     
        {
            setCreditsBackground((CreditsScreenController) controller);
        }
        stage.setScene(scene); // Set the scene on the stage
        stage.setFullScreen(true); // Enable fullscreen mode
        stage.show(); // Show the stage
    }

    // Methods below are responsible for setting the background of different scenes based on the controller type.
    // Each method loads an image, creates a BackgroundSize and BackgroundImage, and sets it as the background of the controller's canvas.
    private void setStartScreenBackground(StartScreenController controller) {
        // Load the image
        Image image = new Image("/res/backgrounds/spaceBackground.png");

        // Create a BackgroundSize object
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);

        // Create a BackgroundImage object
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        // Set the background of the pane
        controller.getCanvas().setBackground(new Background(backgroundImage));  

        // Add a listener to the width property
        controller.getCanvas().widthProperty().addListener((obs, oldVal, newVal) -> {
            // This code will be executed when the width of the canvas changes
            title.setFitWidth(newVal.doubleValue());
            System.out.println(newVal);
        });

        title.setY(100);      
        controller.getCanvas().getChildren().add(title);
}
    
    private void setLevelOneBackground(LevelOneController controller) {
        // Load the image
        Image image = new Image("/res/backgrounds/spaceBackground.png");

        // Create a BackgroundSize object
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);

        // Create a BackgroundImage object
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        // Set the background of the pane
        controller.getCanvas().setBackground(new Background(backgroundImage));
    }
    
     private void setLevelTwoBackground(LevelTwoController controller) {
        // Load the image
        Image image = new Image("/res/backgrounds/moonBackgroundDark.png");

        // Create a BackgroundSize object
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);

        // Create a BackgroundImage object
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        // Set the background of the pane
        controller.getCanvas().setBackground(new Background(backgroundImage));
    }
     
     private void setLevelThreeBackground(LevelThreeController controller) {
        // Load the image
        Image image = new Image("/res/backgrounds/oceanBackground.png");

        // Create a BackgroundSize object
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);

        // Create a BackgroundImage object
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        // Set the background of the pane
        controller.getCanvas().setBackground(new Background(backgroundImage));
    }
     
      private void setFinalBossBackground(FinalBossController controller) {
        // Load the image
        Image image = new Image("/res/backgrounds/spaceBackground.png");

        // Create a BackgroundSize object
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);

        // Create a BackgroundImage object
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        // Set the background of the pane
        controller.getCanvas().setBackground(new Background(backgroundImage));
    }
      
      private void setEndScreenBackground(EndScreenController controller) {
        // Load the image
        Image image = new Image("/res/backgrounds/spaceBackground.png");

        // Create a BackgroundSize object
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);

        // Create a BackgroundImage object
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        // Set the background of the pane
        controller.getCanvas().setBackground(new Background(backgroundImage));
    }
      
      private void setControlsBackground(ControlsScreenController controller) {
        // Load the image
        Image image = new Image("/res/backgrounds/spaceBackground.png");

        // Create a BackgroundSize object
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);

        // Create a BackgroundImage object
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        // Set the background of the pane
        controller.getCanvas().setBackground(new Background(backgroundImage));
    }
      
      private void setCreditsBackground(CreditsScreenController controller) {
        // Load the image
        Image image = new Image("/res/backgrounds/spaceBackground.png");

        // Create a BackgroundSize object
        BackgroundSize backgroundSize = new BackgroundSize(100, 100, true, true, true, true);

        // Create a BackgroundImage object
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);

        // Set the background of the pane
        controller.getCanvas().setBackground(new Background(backgroundImage));
    }

     // Getter for the singleton instance of the application
    public static Schorndorf_Invaders getApplication() {
        return application;
    }

    // Getter for the current FXML file being displayed
    public String getCurrentFxml() {
        return currentFxml;
    }

    // Main method to launch the application
    public static void main(String[] args) {
        launch(args);
    }
}
