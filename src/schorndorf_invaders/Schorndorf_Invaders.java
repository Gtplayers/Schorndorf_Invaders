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

public class Schorndorf_Invaders extends Application {

    private static Schorndorf_Invaders application;
    private Stage stage;
    
    ImageView title = new ImageView("/res/startText1.png");
    private String currentFxml;

    @Override
    public void start(Stage stage) throws Exception {
        application = this;
        this.stage = stage;

        // Set the initial scene
        setScene("StartScreen.fxml");
    }

    public void setScene(String fxml) throws IOException 
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        scene.getStylesheets().add("/schorndorf_invaders/myCss.css");
        Object controller = loader.getController();
        
        this.currentFxml = fxml;

        // Check the actual type of the controller and set the background accordingly
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
    stage.setScene(scene);
    stage.setFullScreen(true);
    stage.show();
    }

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
        Image image = new Image("/res/backgrounds/moonBackground.png");

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

    public static Schorndorf_Invaders getApplication() {
        return application;
    }

    public String getCurrentFxml() {
        return currentFxml;
    }

    
    public static void main(String[] args) {
        launch(args);
    }
}
