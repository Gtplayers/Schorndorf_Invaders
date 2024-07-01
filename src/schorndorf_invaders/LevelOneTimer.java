/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schorndorf_invaders;

import java.io.IOException;
import javafx.scene.media.AudioClip;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;
/**
 *
 * @author TrogrlicLeon
 */
// This class represents the timer logic for Level One of a game, handling game loop and events.
public class LevelOneTimer extends AnimationTimer implements EventHandler<KeyEvent> 
{
    // Interval for the game loop to update game state.
    private static final long INTERVAL = 1l;
//                                       1l; -> fastest refresh rate
//                                       10_000_000l;    -> 1/100 second
//                                       100_000_000l;   -> 1/10  second
//                                       1_000_000_000l; -> 1     second
    // Main canvas where game elements are drawn.
    private AnchorPane canvas = null;

    // Timestamp of the last game loop iteration.
    private long lastCall = 0;
    
    // Flags to track player's death and reset state.
    private boolean dead = false;
    private boolean deadLaser = false;
    private boolean deathScreenAdded = false;
    private boolean resetDone = true;
    
    // Constants and arrays for managing aliens.
    private static final int MAX_ALIENS = 10;
    Alien[] aliens = new Alien[MAX_ALIENS];
    
    // Array to manage lasers shot by the spaceship.
    Laser[] lasers;
    
    // Player's spaceship.
    Spaceship spaceship;
    
    // Images for death and transition screens.
    Image image = new Image("/res/youDied.jpg");
    ImageView deathScreen = new ImageView(image);
    Image black = new Image("/res/blackScreen.jpg");
    ImageView blackScreen = new ImageView(black);
    
    // Variables for managing movement and shooting mechanics.
    private int movement;
    private static final int MOVEMENT_CHANGE_DELAY = 1000;
    private int movementCounter;
    private boolean alienAlive;
    private static final int ALIEN_SHOT_DELAY = 300;
    private int alienLaserCounter;
    private static final int LASER_SHOT_DELAY = 25;
    private int laserCounter;
       
    // UI element to display reset instructions.
    private Text resetText = new Text();
    
    // Sound effects for explosions.
    URL resource = getClass().getResource("/res/sounds/explosionSounds/explosion3.wav");
    AudioClip deathSound = new AudioClip(resource.toString());
    
    // GIF for player explosion animation.
    URL explosionGifResource = getClass().getResource("/res/gif/playerExplosion.gif");
    Image explosionGif = new Image(explosionGifResource.toString());
    ImageView explosionImageView = new ImageView(explosionGif);
    
    // Player's score.
    private int score;
    
    // Reference to the LevelOneController for UI updates.
    LevelOneController controller;
    
    // Constructor initializes the game state.
    public LevelOneTimer(AnchorPane canvas, Spaceship spaceship, LevelOneController controller)
    {
        this.canvas = canvas;
        this.lastCall = System.nanoTime();
        this.spaceship = spaceship;
        this.aliens = new Alien[MAX_ALIENS];
        this.controller = controller;
        initializeAliens();
    }

    // Handles keyboard input for spaceship movement.
    @Override
    public void handle(KeyEvent event)
    {
        spaceship.checkDirection(event);       
    }

    // Handles mouse input for shooting lasers.
    public void handle(MouseEvent event)
    {
        if (laserCounter >= LASER_SHOT_DELAY)
        {
            spaceship.shootLaser(event, canvas);
            laserCounter = 0;
        }
    }

    // Main game loop, updates game state based on time.
    @Override
    public void handle(long now)
    {  
        if (now > lastCall+INTERVAL)
        {     
            if(resetDone == false)
            {
                laserCounter++;          
                spaceship.moveShip(spaceship, canvas);
                spaceship.updateLasers(canvas);
            
                lasers = spaceship.getLasers();
            
                checkAlienStatus();
                canvas.getChildren().removeIf(node -> !node.isVisible());
                checkDeath();
                try 
                {
                    checkScore();     
                } catch (IOException e) 
                {
                    // Handle the IOException
                    System.err.println("IOException occurred in checkScore: " + e.getMessage());
                    e.printStackTrace();
                }
                lastCall = now;
            }
        }
    }

    // Initializes alien objects at the start of the level.
    public void initializeAliens() {
        for (int i = 0; i < MAX_ALIENS/2; i++) {
            aliens[i] = new Alien("/res/sprites/meteor1.png");
        }
        for (int i = MAX_ALIENS/2; i < MAX_ALIENS; i++) {
            aliens[i] = new Alien("/res/sprites/meteor2.png");
        }
    }
    
    // Checks and handles player death.
    public void checkDeath()
    {
        if((dead || deadLaser) && !deathScreenAdded)
        {
            stop();
            explosionImageView.setFitHeight(150); 
            explosionImageView.setFitWidth(195);  
            explosionImageView.setX(spaceship.getX()); // Position at spaceship's location
            explosionImageView.setY(spaceship.getY()); // Position at spaceship's location
            explosionImageView.setSmooth(true);
            canvas.getChildren().add(explosionImageView);      
                    
            deathScreen.setOpacity(0.0);
                    
            PauseTransition pause = new PauseTransition(Duration.seconds(1)); // Duration of the explosion
            pause.setOnFinished(event -> canvas.getChildren().remove(explosionImageView));
            pause.play();
            deathSound.setVolume(0.5);
            deathSound.play();
                        
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(2500), deathScreen);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
                
            deathScreen.setFitHeight(canvas.getHeight());
            deathScreen.setFitWidth(canvas.getWidth());
            canvas.getChildren().add(deathScreen);
            deathScreenAdded = true;
                
            resetText.setX(canvas.getWidth() -  150); 
            resetText.setY(60); 
            resetText.setFill(Color.WHITE); // Set the text color
            resetText.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Set the font
            canvas.getChildren().add(resetText);
            resetText.setText("Score: 0");
        }
    }
    
    // Checks the status of aliens, including movement and collisions.
    public void checkAlienStatus()
    {
        for (Alien alien : aliens) 
                {
                    Laser[] alienLasers = alien.getLasers();
                    if(alien.isAlive())
                    {  
                        alien.updateLasers(canvas);
                        /*if(alienLaserCounter == ALIEN_SHOT_DELAY)
                        {
                            alienLaserCounter = 0;
                            alien.shootLaser(canvas);
                            alien.checkDirection(movement);
                        }
                        else
                        {
                            alienLaserCounter++;
                        }*/
                    }
                    if(movementCounter == MOVEMENT_CHANGE_DELAY)
                    {
                        movementCounter = 0;
                        movement = util.Zufall.movement();
                        alien.checkDirection(movement);
                    }
                    else
                    {
                        movementCounter++;
                    }
                    alien.moveShip(canvas);
                    alienAlive = alien.checkCollision(alien, lasers);
                    if(alienAlive == false)
                    {
                        controller.updateScore(); 
                    }
                    if(dead == false && deadLaser == false)
                    {
                        deadLaser = spaceship.checkLaserCollision(alienLasers, spaceship);
                        dead = spaceship.checkAlienCollision(alien, spaceship);
                    }     
                }
    }
    
    // Checks the player's score and handles level completion.
    public void checkScore() throws IOException
    {
        if(score >= 10)
        {
            score = 0;
            blackScreen.setOpacity(0.0);
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(3100), blackScreen);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
            blackScreen.setFitHeight(canvas.getHeight());
            blackScreen.setFitWidth(canvas.getWidth());
            canvas.getChildren().add(blackScreen);
            score = 0;
            PauseTransition pause = new PauseTransition(Duration.seconds(3.1));
            pause.setOnFinished(event -> {
            try {
                Schorndorf_Invaders.getApplication().setScene("LevelTwo.fxml");
            controller.stopTimer();
            System.out.println("SWITCHED SCENES");
            } catch (IOException ex) {
                Logger.getLogger(FinalBossTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pause.play();
        }
    }
    
    // Getters and setters for various game state properties.
    public Alien[] getAliens() {
        return aliens;
    }

    public void setDead(boolean dead) {
        this.dead = dead;
    }

    public void setDeathScreenAdded(boolean deathScreenAdded) {
        this.deathScreenAdded = deathScreenAdded;
    }

    public void setResetDone(boolean resetDone) {
        this.resetDone = resetDone;
    }

    public void setDeadLaser(boolean deadLaser) {
        this.deadLaser = deadLaser;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    
}
