/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schorndorf_invaders;

import java.net.URL;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;

/**
 *
 * @author TrogrlicLeon
 */
/**
 * Represents the spaceship controlled by the player in the game.
 * Inherits from ImageView to display the spaceship image.
 */
public class Spaceship extends ImageView
{
    // FXML annotations to inject components from the FXML layout
    @FXML
    private AnchorPane canvas; // The main game canvas where the spaceship is displayed
    
    @FXML
    private Button pauseButton; // Button to pause the game
    
    private Direction direction; // Current direction of the spaceship
    
    private static final int MAX_LASERS = 1000; // Maximum number of lasers the spaceship can shoot
    
    private static final int MOVEMENT_SPEED = 10; // Speed at which the spaceship moves
    
    private int laserCount = 0; // Counter for the number of lasers shot
    
    Laser[] lasers = new Laser[MAX_LASERS]; // Array to store lasers
    
    Parent parent; // Parent node of the spaceship
    
    private boolean dead; // Flag to indicate if the spaceship is dead
    
    private boolean deadLaser; // Flag to indicate if a laser has hit the spaceship
    
    // Sound effect for shooting a laser
    URL resource = getClass().getResource("/res/sounds/laserSounds/laserShot.wav");
    AudioClip laserShot = new AudioClip(resource.toString());
    
    // Constructor to initialize the spaceship with an image URL
    public Spaceship(String url) {
        super(new Image(url)); // Call the superclass constructor to set the image
        laserShot.setVolume(0.1); // Set the volume for the laser shot sound
        // Initialize lasers
        generateLasers();
    }

    // Method to check the direction of the spaceship based on the key pressed
    public void checkDirection(KeyEvent event)
    {
        if(event.getEventType() == KeyEvent.KEY_PRESSED)
        {
            // Set direction based on the key pressed
            if(event.getCode() == KeyCode.W)
            {
                direction = Direction.UP;
            }
            else if(event.getCode() == KeyCode.S)
            {
                direction = Direction.DOWN;
            }
            else if(event.getCode() == KeyCode.D)
            {
                direction = Direction.RIGHT;
            }
            else if(event.getCode() == KeyCode.A)
            {
                direction = Direction.LEFT;
            }
        }
        else if(event.getEventType() == KeyEvent.KEY_RELEASED)
        {
            direction = Direction.NONE;     // Stop moving when key is released
        }
    }
    
    // Method to move the spaceship based on the current direction
    public void moveShip(Spaceship spaceship, Pane canvas)
    {
        // Movement logic based on direction
        if (direction == Direction.NONE)
        {
            // Do nothing if no direction is set
            spaceship.setY(spaceship.getY());
            spaceship.setX(spaceship.getX());
            direction = Direction.NONE;
        }
        else if(direction == Direction.UP)
        {
            if (canvas.getHeight() - 100 - spaceship.getY() < canvas.getHeight())
            {
                spaceship.setY(spaceship.getY() - MOVEMENT_SPEED);
            }
            else
            {
                spaceship.setY(canvas.getHeight() + 100);
            }
        }
        else if(direction == Direction.DOWN)
        {
            if (canvas.getHeight() + 100 - spaceship.getY() > 0)
            {
                spaceship.setY(spaceship.getY() + MOVEMENT_SPEED);
            }
            else
            {
                spaceship.setY(-100);
            }
        }
        else if(direction == Direction.RIGHT)
        {
            if (canvas.getWidth() + 100 - spaceship.getX() > 0)
            {
                spaceship.setX(spaceship.getX() + MOVEMENT_SPEED);
            }
            else
            {
                spaceship.setX(-100);
            }
        }
        else if(direction == Direction.LEFT)
        {
            if (canvas.getWidth() - 100 - spaceship.getX() < canvas.getWidth())
            {
                spaceship.setX(spaceship.getX() - MOVEMENT_SPEED);
            }
            else
            {
                spaceship.setX(canvas.getWidth() + 100);
            }
        }
    }
    
    // Method to shoot a laser when the mouse is clicked
    public void shootLaser(MouseEvent event, AnchorPane canvas) 
    {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) 
        {
            laserShot.play();                                             // Play laser shot sound
            // Position the laser relative to the spaceship
            lasers[laserCount].setY(getY() + 10);
            lasers[laserCount].setX(getX() + 42);
            lasers[laserCount].setSmooth(true);
            lasers[laserCount].setVisible(true);            //When shot, set laser to visible
            laserCount++;
        }
    }
    
    // Method to update the position of lasers on the canvas
    public void updateLasers(AnchorPane canvas) 
    {
        this.canvas = canvas;
        for (int i = 0; i < laserCount; i++) 
        {
            if (lasers[i] != null) 
            {
                if (!canvas.getChildren().contains(lasers[i])) 
                {
                    canvas.getChildren().add(lasers[i]);
                }
                lasers[i].moveLaser();  // Move the laser
                // Remove the laser if it goes off-screen
                if (lasers[i].getY() < 0) 
                {
                    canvas.getChildren().remove(lasers[i]);
                    lasers[i] = null;
                }
            }
        }
    }

    // Method to check collision between the spaceship and an alien
    public boolean checkAlienCollision(Alien alien, Spaceship spaceship) 
    {
        Pane currentParent = (Pane) getParent();
        if (currentParent == null || alien == null || spaceship == null) return false;

        if (alien.isVisible() && alien.getBoundsInParent().intersects(spaceship.getBoundsInParent())) {
            // Handle spaceship and alien collision
            alien.setVisible(false);
            spaceship.setVisible(false);
            currentParent.getChildren().removeAll(spaceship, alien);
            dead = true;
        }
    return dead;
    }
    
    // Method to check collision between the spaceship and the boss
    public boolean checkBossCollision(MonkeySoup monkeySoup, Spaceship spaceship) 
    {
        Pane currentParent = (Pane) getParent();
        if (currentParent == null || monkeySoup == null || spaceship == null) return false;

        if (monkeySoup.isVisible() && monkeySoup.getBoundsInParent().intersects(spaceship.getBoundsInParent())) {
            // Handle collision
            spaceship.setVisible(false);
            currentParent.getChildren().remove(spaceship);
            dead = true;
        }
    return dead;
    }

    // Method to check collision between the spaceship and lasers from aliens
    public boolean checkLaserCollision(Laser[] alienLasers, Spaceship spaceship) 
    {
        Pane currentParent = (Pane) getParent();
        for (Laser laser : alienLasers) {
            if (currentParent == null || laser == null || spaceship == null) return false;
            if (laser.isVisible() && this.getBoundsInParent().intersects(laser.getBoundsInParent()))
            {
                // Handle collision
                laser.setVisible(false);
                spaceship.setVisible(false);
                currentParent.getChildren().removeAll(spaceship, laser);
                deadLaser = true;
                break;
            }
        }
        return deadLaser;
    }
    
    // Method to generate all of the spaceship's lasers
    public void generateLasers()
    {
        for (int i = 0; i < MAX_LASERS; i++) {
            lasers[i] = new Laser("/res/lasers/laserGreen.png");
            lasers[i].setFitHeight(15.4);
            lasers[i].setFitWidth(7);
            lasers[i].setSmooth(true);
            lasers[i].setX(-1000); // Position off-screen initially
            lasers[i].setY(-1000); // Position off-screen initially
            lasers[i].setVisible(false);    //Set laser to be intially invisible
        }
    }
    
    // Method to reset the spaceship state
    public void reset() 
    {
        // Removes lasers from the canvas.
        for (Laser laser : lasers) {
            if (laser != null) {
                laser.setVisible(false);
                laser.setX(-1000); // Position off-screen initially
                laser.setY(-1000); // Position off-screen initially
                canvas.getChildren().remove(laser); // Remove from canvas
            }
        }
        dead = false; // Reset the dead flag
        deadLaser = false;
        setVisible(true); // Make the spaceship visible again
    }
    
    // Getter for the lasers array
    public Laser[] getLasers() {
        return lasers;
    }
    
}
