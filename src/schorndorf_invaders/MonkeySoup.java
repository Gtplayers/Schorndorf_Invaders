/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schorndorf_invaders;

import java.net.URL;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.AudioClip;
import javafx.util.Duration;

/**
 *
 * @author Leon
 */
// Define the MonkeySoup class which extends ImageView, allowing it to display images
public class MonkeySoup  extends ImageView{
    // Enum for direction, not shown but assumed to exist elsewhere in the code
    private Direction direction;

    // Parent node in the scene graph
    Parent parent;
    
    // FXML annotations to inject components from the FXML layout
    @FXML
    private AnchorPane canvas;
    
    @FXML
    private Button pauseButton;
    
    // Resources for sound effects and images
    URL damageResource = getClass().getResource("/res/sounds/explosionSounds/explosion4.wav");
    AudioClip alienDamage = new AudioClip(damageResource.toString());
    
    URL explosionResource = getClass().getResource("/res/sounds/bossSounds/bossExplosion7.mp3");
    AudioClip bossExplosion = new AudioClip(explosionResource.toString());
    
    URL laserResource = getClass().getResource("/res/sounds/laserSounds/alienShot1.wav");
    AudioClip laserShot = new AudioClip(laserResource.toString());
    
    URL explosionGifResource = getClass().getResource("/res/gif/enemyExplosion.gif");
    Image explosionGif = new Image(explosionGifResource.toString());
    ImageView explosionImageView = new ImageView(explosionGif);
    
     URL bossExplosionGifResource = getClass().getResource("/res/gif/bossExplosion.gif");
    Image bossExplosionGif = new Image(bossExplosionGifResource.toString());
    ImageView bossExplosionImageView = new ImageView(bossExplosionGif);
       
    // Pause transition for animations      
    PauseTransition pause = new PauseTransition(Duration.seconds(1)); 
    
    // Constants for laser management and movement speed
    private static final int MAX_LASERS = 3000;
    private static final int MOVEMENT_SPEED = 5;
    
    // Variables for laser management and health
    private int laserCount = 0;
    Laser[] lasers = new Laser[MAX_LASERS];
    private int health = 20;
    
    // Reference to the main application class for accessing global methods and variables
    Schorndorf_Invaders app = Schorndorf_Invaders.getApplication();
    String currentFxmlFile = app.getCurrentFxml();
    
    // Constructor that initializes the MonkeySoup object with an image URL
    public MonkeySoup(String url)
    { 
        super(new Image(url));  // Call the parent class constructor with the image
        // Set volume for sound effects
        alienDamage.setVolume(0.2);
        laserShot.setVolume(0.1); 
        // Initialize lasers
        generateLasers();
    }

    // Method to check and set the direction of movement based on an integer input
    public void checkDirection(int movement)
    {
        // Set direction based on the movement parameter
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
    
    // Method to move the ship based on the current direction
    public void moveShip(Pane canvas)
    {
        // Move the ship within the bounds of the canvas
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
                setY(canvas.getHeight() - 100);
            }
        }
        else if(direction == Direction.DOWN)
        {
            if (canvas.getHeight() + getFitHeight() - getY() > 0)
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
    
    // Method for a starting animation, moving the ship slightly
    public void startingAnimation()
    {
       setY(getY() + 1); 
    }

    // Method to check for collisions between the MonkeySoup object and lasers
    public int checkCollision(MonkeySoup monkeySoup, Laser[] lasers) 
    {
        // Check each laser for a collision with the MonkeySoup object
        // If a collision is detected, handle it by reducing health, playing a sound, and showing an explosion animation
        Pane currentParent = (Pane) getParent();
        for (Laser laser : lasers) {
            if (currentParent != null && monkeySoup != null && laser != null && !laser.isProcessed() && monkeySoup.getBoundsInParent().intersects(laser.getBoundsInParent())) {
                health--;
                laser.setVisible(false);
                laser.setProcessed(true); // Mark the laser as processed
                currentParent.getChildren().remove(laser);  
                alienDamage.play();
                explosionImageView.setFitHeight(150); 
                explosionImageView.setFitWidth(150);  
                explosionImageView.setX(this.getX()); // Position at spaceship's location
                explosionImageView.setY(this.getY()); // Position at spaceship's location
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
    }
    return health;  // Return the updated health
    } 
    
    // Method to shoot lasers from the MonkeySoup object
    public void shootLaser(AnchorPane canvas) {
        if (health != 0){ // Only shoot if the boss is alive
            if (laserCount+2000 < MAX_LASERS) {
                System.out.println("LASER SHOT");
                lasers[laserCount].setY(getY() + 100);
                lasers[laserCount].setX(getX() + 100);
                lasers[laserCount].setSmooth(true);
                lasers[laserCount].setVisible(true);            //When shot, set laser to visible
                lasers[laserCount+1000].setY(getY() + 100);
                lasers[laserCount+1000].setX(getX() + 150);
                lasers[laserCount+1000].setSmooth(true);
                lasers[laserCount+1000].setVisible(true);            //When shot, set laser to visible
                lasers[laserCount+2000].setY(getY() + 100);
                lasers[laserCount+2000].setX(getX() + 50);
                lasers[laserCount+2000].setSmooth(true);
                lasers[laserCount+2000].setVisible(true);            //When shot, set laser to visible
                laserCount++;
                laserShot.play();
            }
        }
    }
    
    // Method to update the position of lasers on the screen
    public void updateLasers(AnchorPane canvas) {
        // Update the position of each laser, removing them if they go off-screen
        if (health != 0){ // Only update lasers if the boss is alive
            this.canvas = canvas;
            for (int i = 0; i <= 1000; i++) {
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
            for (int i = 1001; i <= 2000; i++) {
                if (lasers[i] != null) {
                    if (!canvas.getChildren().contains(lasers[i])) {
                        canvas.getChildren().add(lasers[i]);
                    }
                    lasers[i].bossMoveLeftLaser();
                    if (lasers[i].getY() > canvas.getHeight()) {
                        canvas.getChildren().remove(lasers[i]);
                    }
                }
            }
            for (int i = 2001; i < MAX_LASERS; i++) {
                if (lasers[i] != null) {
                    if (!canvas.getChildren().contains(lasers[i])) {
                        canvas.getChildren().add(lasers[i]);
                    }
                    lasers[i].bossMoveRightLaser();
                    if (lasers[i].getY() > canvas.getHeight()) {
                        canvas.getChildren().remove(lasers[i]);
                    }
                }
            }
        }
    }
    
    // Method to handle the death of the MonkeySoup object, playing an animation and sound
    public void bossDeath()
    {
        removeBossLasers(canvas, this);
        // Play explosion sound and animation, then remove the MonkeySoup object from the canvas
        playExplosionSound();
        System.out.println("BOSS DEAD");
        bossExplosionImageView.setFitHeight(500); // Set size
        bossExplosionImageView.setFitWidth(500);  // Set size
        bossExplosionImageView.setX(this.getX()); // Position at bosses location
        bossExplosionImageView.setY(this.getY()); // Position at bosses location
        bossExplosionImageView.setSmooth(true);
        if (!canvas.getChildren().contains(bossExplosionImageView)) 
        {
            canvas.getChildren().add(bossExplosionImageView);
        }            
        PauseTransition pause = new PauseTransition(Duration.seconds(3.1)); // Duration of the explosion 
        pause.setOnFinished(event -> 
        {
            canvas.getChildren().remove(bossExplosionImageView);
            canvas.getChildren().removeIf(node -> !node.isVisible());
        });
        pause.play();
        removeBossLasers(canvas, this);
        this.setVisible(false);
        canvas.getChildren().remove(this);
        canvas.getChildren().removeIf(node -> !node.isVisible());
    }
    
    // Helper method to remove all lasers shot by the boss from the canvas.
    private void removeBossLasers(AnchorPane currentParent, MonkeySoup monkeySoup) 
    {
        // Removes lasers from the canvas.
        for (Laser laser : monkeySoup.lasers) {
            if (laser != null) {
                laser.setVisible(false);
                currentParent.getChildren().remove(laser);
            }
        }
    }
    
    // Method to play the explosion sound effect
    public void playExplosionSound()
    {
        bossExplosion.play();
        // Additional handling after the sound plays
        PauseTransition pause = new PauseTransition(Duration.seconds(0.1));
        pause.setOnFinished(event -> 
        {
            
        });
    }
    
    // Method to generate all of the boss lasers
    public void generateLasers()
    {
        // Initialize lasers with different images and properties
        for (int i = 0; i <= 1000; i++) 
        {
            lasers[i] = new Laser("/res/lasers/laserRed.png");
            // Set properties for the laser
            lasers[i].setFitHeight(15.4);  
            lasers[i].setFitWidth(7);    
            lasers[i].setSmooth(true);
            // Position lasers off-screen initially
            lasers[i].setX(-1000);
            lasers[i].setY(-1000);
            lasers[i].setVisible(false);    //Set laser to be intially invisible
        } 
        // Repeat initialization for other laser types
        for (int i = 1001; i <= 2000; i++) 
        {
            lasers[i] = new Laser("/res/lasers/laserGreen.png");
            // Set properties
            lasers[i].setFitHeight(15.4);  
            lasers[i].setFitWidth(7);     
            lasers[i].setSmooth(true);
            // Position lasers off-screen initially
            lasers[i].setX(-1000);
            lasers[i].setY(-1000);
            lasers[i].setVisible(false);    //Set laser to be intially invisible
        } 
        for (int i = 2001; i < MAX_LASERS; i++) 
        {
            lasers[i] = new Laser("/res/lasers/lightning.png");
            // Set properties
            lasers[i].setFitHeight(15.4);  
            lasers[i].setFitWidth(7);     
            lasers[i].setSmooth(true);
            // Position lasers off-screen initially
            lasers[i].setX(-1000);
            lasers[i].setY(-1000);
            lasers[i].setVisible(false);    //Set laser to be intially invisible
        } 
    }
    
    // Method to reset the boss state
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
        setVisible(true); // Make the boss visible again
    }

    // Getter for the lasers array
    public Laser[] getLasers() 
    {
        return lasers;
    }

    // Getter and setter for the health property
    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
