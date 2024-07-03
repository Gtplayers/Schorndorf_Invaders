/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schorndorf_invaders;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 *
 * @author Leon
 */
/*
 * This class represents the timer logic for level three of the game. It extends AnimationTimer and implements EventHandler for KeyEvent.
 * It manages game timing, alien and spaceship interactions, and the game state (e.g., death, score).
 */
public class LevelThreeTimer extends AnimationTimer implements EventHandler<KeyEvent>{
    
        private static final long INTERVAL = 1l;
//                                       1l; -> fastest refresh rate
//                                       10_000_000l;    -> 1/100 second
//                                       100_000_000l;   -> 1/10  second
//                                       1_000_a000_000l; -> 1     second

    private AnchorPane canvas = null; // The game canvas where all game elements are drawn.

    private long lastCall = 0; // Tracks the last time the handle method was called.
    
    private boolean dead = false; // Flag to check if the player is dead.
    private boolean deadLaser = false; // Flag to check if the player is killed by a laser.
    private boolean deathScreenAdded = false; // Flag to check if the death screen has been added to the canvas.
    private boolean resetDone = true; // Flag to check if the game has been reset.
    
    private static final int MAX_ALIENS = 10; // Maximum number of aliens in the level.
    Alien[] aliens = new Alien[MAX_ALIENS]; // Array to store aliens.
    
    Laser[] lasers; // Array to store lasers.
    
    Spaceship spaceship; // The player's spaceship.
    
    Image image = new Image("/res/youDied.jpg"); // Image for the death screen.
    ImageView deathScreen = new ImageView(image); // ImageView for the death screen.
    
    Image black = new Image("/res/blackScreen.jpg"); // Image for the transition screen.
    ImageView blackScreen = new ImageView(black); // ImageView for the transition screen.
    
    private int movement; // Variable to control alien movement.
    private static final int MOVEMENT_CHANGE_DELAY = 1000; // Delay for changing alien movement.
    private int movementCounter; // Counter for alien movement change.
    
    private boolean alienAlive; // Flag to check if any alien is alive.
    private static final int ALIEN_SHOT_DELAY = 300; // Delay between alien shots.
    private int alienLaserCounter; // Counter for alien laser shots.
    
    private static final int LASER_SHOT_DELAY = 25; // Delay between player's laser shots.
    private int laserCounter; // Counter for player's laser shots.
       
    private Text resetText = new Text(); // Text for the reset message.
    
    URL resource = getClass().getResource("/res/sounds/explosionSounds/explosion3.wav"); // Resource for the death sound.
    AudioClip deathSound = new AudioClip(resource.toString()); // AudioClip for the death sound.
    
    URL explosionGifResource = getClass().getResource("/res/gif/playerExplosion.gif"); // Resource for the explosion GIF.
    Image explosionGif = new Image(explosionGifResource.toString()); // Image for the explosion GIF.
    ImageView explosionImageView = new ImageView(explosionGif); // ImageView for the explosion GIF.
    
    private int score; // The player's score.
    
    LevelThreeController controller; // The controller for level three.
    
    // Constructor initializes the game state with the provided canvas, spaceship, and controller.
    public LevelThreeTimer(AnchorPane canvas, Spaceship spaceship, LevelThreeController controller)
    {
        this.canvas = canvas;
        this.lastCall = System.nanoTime();
        this.spaceship = spaceship;
        this.aliens = new Alien[MAX_ALIENS];
        this.controller = controller;
        initializeAliens();
    }

    // Handles keyboard input for controlling the spaceship.
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

    // Main game loop, called at every frame.
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

    // Initializes the aliens at the start of the level.
    public void initializeAliens() {
        for (int i = 0; i < MAX_ALIENS/2; i++) {
            aliens[i] = new Alien("/res/sprites/eel.png");
            //aliens[i].setFitHeight(62.4);
            //aliens[i].setFitWidth(68.9);
            aliens[i].setSmooth(true);
        }
        for (int i = MAX_ALIENS/2; i < MAX_ALIENS; i++) {
            aliens[i] = new Alien("/res/sprites/jellyfish.png");
            //aliens[i].setFitWidth(68.9);
            //aliens[i].setFitHeight(76.7);
            aliens[i].setSmooth(true);
        }
    }
    
    // Checks if the player is dead and updates the game state accordingly.
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
    
    // Checks the status of aliens (alive, moving, shooting) and updates the game state.
    public void checkAlienStatus()
    {
        for (Alien alien : aliens) 
                {
                    Laser[] alienLasers = alien.getLasers();
                    if(alien.isAlive())
                    {  
                        alien.updateLasers(canvas);
                        if(alienLaserCounter == ALIEN_SHOT_DELAY)
                        {
                            alienLaserCounter = 0;
                            alien.shootLaser(canvas);
                            alien.checkDirection(movement);
                        }
                        else
                        {
                            alienLaserCounter++;
                        }
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
                        System.out.println("ALIEN DEAD");
                        controller.updateScore(); 
                    }
                    if(dead == false && deadLaser == false)
                    {
                        deadLaser = spaceship.checkLaserCollision(alienLasers, spaceship);
                        dead = spaceship.checkAlienCollision(alien, spaceship);
                    }     
                }
    }
    
    // Checks the player's score and updates the game state.
    public void checkScore() throws IOException
    {
        if(score >= 10)
        {
            blackScreen.setOpacity(0.0);
            FadeTransition fadeInTransition = new FadeTransition(Duration.millis(3100), blackScreen);
            fadeInTransition.setFromValue(0.0);
            fadeInTransition.setToValue(1.0);
            fadeInTransition.play();
            blackScreen.setFitHeight(canvas.getHeight());
            blackScreen.setFitWidth(canvas.getWidth());
            canvas.getChildren().add(blackScreen);
                
            score = 0;
            PauseTransition pause = new PauseTransition(Duration.seconds(3.1)); // Duration of the explosion 
        pause.setOnFinished(event -> {
            try {
                Schorndorf_Invaders.getApplication().setScene("FinalBoss.fxml");
            controller.stopTimer();
            System.out.println("SWITCHED SCENES");
            } catch (IOException ex) {
                Logger.getLogger(FinalBossTimer.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        pause.play();
            
        }
    }
    
    // Getter and setter methods for various game state variables.
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
