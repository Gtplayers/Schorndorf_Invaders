/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schorndorf_invaders;

import java.net.URL;
import java.util.Set;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.util.Duration;

/**
 *
 * @author Leon
 */
/*
 * The Alien class extends ImageView, allowing it to be displayed as an image in a JavaFX application.
 * It represents an alien entity in a game, capable of moving in different directions, shooting lasers, and exploding upon collision.
 */
public class Alien extends ImageView
{
    private Direction direction; // Enum to track the alien's current direction of movement.

    Parent parent; // The parent container in the scene graph. Not used explicitly.
    
    @FXML
    private AnchorPane canvas; // The main canvas where the alien and other game elements are drawn.
    
    @FXML
    private Button pauseButton; // Button to pause the game. Not used explicitly.
    
    // Resources for sound effects and animations.
    URL explosionResource = getClass().getResource("/res/sounds/explosionSounds/explosion4.wav");
    AudioClip alienExplosion = new AudioClip(explosionResource.toString()); // Sound effect for alien explosion.
    
    URL laserResource = getClass().getResource("/res/sounds/laserSounds/alienShot1.wav");
    AudioClip laserShot = new AudioClip(laserResource.toString()); // Sound effect for shooting a laser.
    
    URL explosionGifResource = getClass().getResource("/res/gif/enemyExplosion.gif");
    Image explosionGif = new Image(explosionGifResource.toString()); // Animation for explosion.
    ImageView explosionImageView = new ImageView(explosionGif); // ImageView to display the explosion animation.
       
    PauseTransition pause = new PauseTransition(Duration.seconds(1)); // Pause transition for delays. Not used explicitly.
    
    private static final int MAX_LASERS = 2000; // Maximum number of lasers an alien can shoot.
    
    private static final int MOVEMENT_SPEED = 5; // Speed at which the alien moves.
    
    private static int laserCount = 0; // Counter for the number of lasers shot.
    
    Laser[] lasers = new Laser[MAX_LASERS]; // Array to store laser instances.
    
    private boolean alive = true; // Flag to track if the alien is alive.
    
    Schorndorf_Invaders app = Schorndorf_Invaders.getApplication(); // Reference to the main application class.
    String currentFxmlFile = app.getCurrentFxml(); // Current FXML file being used, to determine game level.
    
    // Constructor: Initializes an alien with an image URL and sets up its properties.
    public Alien(String url)
    { 
        super(new Image(url)); // Call to the superclass (ImageView) constructor to set the image.
        alienExplosion.setVolume(0.2); // Set volume for explosion sound.
        laserShot.setVolume(0.1); // Set volume for laser sound.
        // Initialize lasers based on the current level.
        for (int i = 0; i < MAX_LASERS; i++) 
        {
            // Different laser types for different levels.
            if(currentFxmlFile.equals("LevelTwo.fxml"))
            {
                lasers[i] = new Laser("/res/lasers/laserRed.png");
                lasers[i].setFitHeight(15.4);   //14.3
                lasers[i].setFitWidth(7);     //6.5
                lasers[i].setSmooth(true);
                lasers[i].setX(-1000);
                lasers[i].setY(-1000);
            }
            else if(currentFxmlFile.equals("LevelThree.fxml"))
            {
                lasers[i] = new Laser("/res/lasers/lightning.png");
                lasers[i].setFitHeight(23.8);   //22.1
                lasers[i].setFitWidth(7);     //6.5
                lasers[i].setSmooth(true);
                lasers[i].setX(-1000);
                lasers[i].setY(-1000);
            }
        } 
    }
    
    
    
    // Method to check and update the alien's direction based on an integer input.
    public void checkDirection(int movement)
    {
        // Updates the direction field based on the input.
        if(movement == 0)
        {
            direction = Direction.UP;
        }
        else if(movement == 1)
        {
            direction = Direction.DOWN;
        }
        else if(movement == 2)
        {
            direction = Direction.RIGHT;
        }
        else if(movement == 3)
        {
            direction = Direction.LEFT;
        }
    }
    
    // Method to move the alien in the current direction within the bounds of the canvas.
    public void moveShip(Pane canvas)
    {
         // Moves the alien based on its current direction and the canvas boundaries.
        if (direction == Direction.NONE)
        {
            setY(getY());
            setX(getX());
            direction = Direction.NONE;
        }
        else if(direction == Direction.UP)
        {
            if (canvas.getHeight() - 100 - getY() < canvas.getHeight())
            {
                setY(getY() - MOVEMENT_SPEED);
            }
            else
            {
                setY(canvas.getHeight() + 100);
            }
        }
        else if(direction == Direction.DOWN)
        {
            if (canvas.getHeight() + 100 - getY() > 0)
            {
                setY(getY() + MOVEMENT_SPEED);
            }
            else
            {
                setY(-100);
            }
        }
        else if(direction == Direction.RIGHT)
        {
            if (canvas.getWidth() + 100 - getX() > 0)
            {
                setX(getX() + MOVEMENT_SPEED);
            }
            else
            {
                setX(-100);
            }
        }
        else if(direction == Direction.LEFT)
        {
            if (canvas.getWidth() - 100 - getX() < canvas.getWidth())
            {
                setX(getX() - MOVEMENT_SPEED);
            }
            else
            {
                setX(canvas.getWidth() + 100);
            }
        }
    }

    // Method to check for collisions between this alien and any laser in the provided array.
    public boolean checkCollision(Alien alien, Laser[] lasers)
    {
        // Checks for collision, handles the explosion and removal of the alien and the laser.
        Pane currentParent = (Pane) getParent();
        for (Laser laser : lasers) {
            if (currentParent != null && alien != null && laser != null && alien.getBoundsInParent().intersects(laser.getBoundsInParent())) 
            {
                alive = false;
                removeAlienLasers(currentParent, this);
                alien.setVisible(false); 
                laser.setVisible(false);
                currentParent.getChildren().removeIf(node -> !node.isVisible());
                currentParent.getChildren().removeAll(alien, laser);
                alienExplosion.play();
                explosionImageView.setFitHeight(150); 
                explosionImageView.setFitWidth(150);  
                explosionImageView.setX(alien.getX()); // Position at spaceship's location
                explosionImageView.setY(alien.getY()); // Position at spaceship's location
                explosionImageView.setSmooth(true);
                if (!currentParent.getChildren().contains(explosionImageView)) 
                {
                    currentParent.getChildren().add(explosionImageView);
                }            
                PauseTransition pause = new PauseTransition(Duration.seconds(0.34)); // Duration of the explosion 
                pause.setOnFinished(event -> 
                {
                    currentParent.getChildren().remove(explosionImageView);
                    currentParent.getChildren().removeIf(node -> !node.isVisible());
                });
                pause.play();
                break; // Exit the loop after handling collision with one laser
            }
            else
            {
                alive = true;
            }
        }
        return alive;
    }

    // Helper method to remove all lasers shot by this alien from the canvas.
    private void removeAlienLasers(Pane currentParent, Alien alien) 
    {
        // Removes lasers from the canvas.
        for (Laser laser : alien.lasers) {
            if (laser != null) {
                laser.setVisible(false);
                currentParent.getChildren().remove(laser);
            }
        }
    }
    
    // Method for the alien to shoot a laser.
    public void shootLaser(AnchorPane canvas) {
    // Adds a new laser to the canvas and plays the shooting sound.
    if (alive && this.getParent() != null) { // Check if alien is alive and part of the scene
        if (laserCount < MAX_LASERS) {
            laserCount++;
            if(currentFxmlFile.equals("LevelThree.fxml"))
            {
                lasers[laserCount - 1].setY(getY() + 10);
                lasers[laserCount - 1].setX(getX());
                lasers[laserCount - 1].setSmooth(true);
            }
            else
            {
                lasers[laserCount - 1].setY(getY() + 10);
                lasers[laserCount - 1].setX(getX() + 42);
                lasers[laserCount - 1].setSmooth(true);
            }
            if (!canvas.getChildren().contains(lasers[laserCount - 1])) {
                canvas.getChildren().add(lasers[laserCount - 1]);
            }
            laserShot.play();
        }
    }
}

    // Method to update the position of all lasers shot by this alien.
    public void updateLasers(AnchorPane canvas) {
        // Moves each laser and removes it if it goes off-screen.
        if (alive){// Only update lasers if the alien is alive
            this.canvas = canvas;
            for (int i = 0; i < laserCount; i++) {
                if (lasers[i] != null) {
                    if (!canvas.getChildren().contains(lasers[i])) {
                        canvas.getChildren().add(lasers[i]);
                    }
                    lasers[i].alienMoveLaser();
                    if (lasers[i].getY() > canvas.getHeight()) {
                        canvas.getChildren().remove(lasers[i]);
                    }
                }
            }
        }
    }

    // Getter for the static laserCount field.
    public static int getLaserCount() {
        return laserCount;
    }
    
    // Getter for the lasers array.
    public Laser[] getLasers() 
    {
        return lasers;
    }

    // Getter for the alive flag.
    public boolean isAlive() {
        return alive;
    }
}
