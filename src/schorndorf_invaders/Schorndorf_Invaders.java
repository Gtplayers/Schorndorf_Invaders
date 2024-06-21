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

    @Override
    public void start(Stage stage) throws Exception {
        application = this;
        this.stage = stage;

        // Set the initial scene
        setScene("StartScreen.fxml");
    }

    public void setScene(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        // Check the FXML file and set the background for the start screen
        if (fxml.equals("StartScreen.fxml")) {
            StartScreenController controller = loader.getController();
            setStartScreenBackground(controller);
        }
        else if (fxml.equals("LevelOne.fxml")) {
            LevelOneController controller = loader.getController();
            setLevelOneBackground(controller);
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

    public static Schorndorf_Invaders getApplication() {
        return application;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
