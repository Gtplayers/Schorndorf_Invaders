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
 * This class represents the animation timer for the final boss battle in a game. It handles the game logic for the final boss encounter, including movement, shooting, health management, and win/loss conditions.
 */
public class FinalBossTimer extends AnimationTimer implements EventHandler<KeyEvent>{
    
    private static final long INTERVAL = 1l;
//                                       1l; -> fastest refresh rate
//                                       10_000_000l;    -> 1/100 second
//                                       100_000_000l;   -> 1/10  second
//                                       1_000_a000_000l; -> 1     second

    // The main canvas where the game is drawn
    private AnchorPane canvas = null;

    // Timestamp of the last call to handle()
    private long lastCall = 0;
    
    // Flags to track the death state of the player and the laser
    private boolean dead = false;
    private boolean deadLaser = false;
    
    // Flag to check if the death screen has been added
    private boolean deathScreenAdded = false;
    
    // Flag to check if the game has been reset
    private boolean resetDone = true;
    
    // Array of lasers in the game
    Laser[] lasers;
    
    // The player's spaceship
    Spaceship spaceship;
    
    // Image and ImageView for the death screen
    Image death = new Image("/res/youDied.jpg");
    ImageView deathScreen = new ImageView(death);
    
    // Variables for managing movement and timing
    private int movement;
    private static final int MOVEMENT_CHANGE_DELAY = 100;
    private int movementCounter;
    
    private static final int STARTING_ANIMATION_LENGTH = 250;           //NOTE: Value is 250 only on faster Computers, otherwise decrease
    private int startingAnimationCounter;
    
    private static final int ALIEN_SHOT_DELAY = 50;
    private int alienLaserCounter;
    
    private static final int LASER_SHOT_DELAY = 25;
    private int laserCounter;
    
    // Boss health management
    private int bossHealth = 20;
    private boolean bossDead;
       
    // Text elements for displaying reset information and boss health
    private Text resetText = new Text();
    private Text bossHealthText = new Text();
    
    // Flag to check if the boss health text is shown
    boolean textShown;
    
    // Flag and resources for playing the evil laugh sound
    private boolean laughPlayed;
    URL laughResource = getClass().getResource("/res/sounds/laughSounds/evilLaugh2.mp3");
    AudioClip evilLaugh = new AudioClip(laughResource.toString());
    
    // The final boss character
    MonkeySoup monkeySoup = new MonkeySoup("/res/sprites/monkeySoupBoss.png");  
    
    // Resources for playing the death sound and displaying explosion animations
    URL resource = getClass().getResource("/res/sounds/explosionSounds/explosion3.wav");
    AudioClip deathSound = new AudioClip(resource.toString());
    
    URL explosionGifResource = getClass().getResource("/res/gif/playerExplosion.gif");
    Image explosionGif = new Image(explosionGifResource.toString());
    ImageView explosionImageView = new ImageView(explosionGif);
    
    URL bossExplosionGifResource = getClass().getResource("/res/gif/bossExplosion.gif");
    Image bossExplosionGif = new Image(bossExplosionGifResource.toString());
    ImageView bossExplosionImageView = new ImageView(bossExplosionGif);
    
    
    // Reference to the final boss controller
    FinalBossController controller;
    
    // Flag to check if the win condition has been handled
    private boolean winHandled = false;
    
    // Constructor initializes the game state for the final boss battle
    public FinalBossTimer(AnchorPane canvas, Spaceship spaceship, FinalBossController controller)
    {
        this.canvas = canvas;
        this.lastCall = System.nanoTime();
        this.spaceship = spaceship;
        this.controller = controller;
        initializeBoss();
    }

    // Handles keyboard input for controlling the spaceship
    @Override
    public void handle(KeyEvent event)
    {
        spaceship.checkDirection(event);       
    }

    // Handles mouse input for shooting lasers
    public void handle(MouseEvent event)
    {
        if (laserCounter >= LASER_SHOT_DELAY)
        {
            spaceship.shootLaser(event, canvas);
            laserCounter = 0;
        }
    }

    // Main game loop, called at each frame
    @Override
    public void handle(long now)
    {  
        if (now > lastCall+INTERVAL)
        {     
            if(resetDone == false)
            {
                if(startingAnimationCounter == STARTING_ANIMATION_LENGTH)
                {
                    laserCounter++;          
                    spaceship.moveShip(spaceship, canvas);
                    spaceship.updateLasers(canvas);
            
                    lasers = spaceship.getLasers();
                    updateBossHealthText();
                    checkAlienStatus();
                    canvas.getChildren().removeIf(node -> !node.isVisible());
                    checkDeath();
                    try 
                    {
                        checkWin();     
                    } catch (IOException e) 
                    {
                        // Handle the IOException
                        System.err.println("IOException occurred in checkScore: " + e.getMessage());
                        e.printStackTrace();
                    }
                    lastCall = now;
                }
                else
                {
                    showBossHealthText();
                    playEvilLaugh();
                    monkeySoup.startingAnimation();
                    startingAnimationCounter++;
                }
            }
        }
    }

    // Plays the evil laugh sound once
    public void playEvilLaugh()
    {
        if(laughPlayed == false)
        {
            laughPlayed = true;
            evilLaugh.play();
        }
    }
    
    // Initializes the boss character
    public void initializeBoss() { 
        if(monkeySoup == null)
        {
            monkeySoup = new MonkeySoup("/res/sprites/monkeySoupBoss.png");
        }       
    }
    
    // Checks and handles the player's death
    public void checkDeath()
    {
        if((dead || deadLaser) && !deathScreenAdded)
        {
            stop();
            controller.fadeOutMusic();
            explosionImageView.setFitHeight(150); // Set size as needed
            explosionImageView.setFitWidth(195);  // Set size as needed
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
    
    // Displays the boss health text
    public void showBossHealthText()
    { 
        if(textShown == false)
        {
            bossHealthText.setX(canvas.getWidth()/2.05); 
            bossHealthText.setY(100); 
            bossHealthText.setFill(Color.RED); // Set the text color
            bossHealthText.setFont(Font.font("Arial", FontWeight.BOLD, 20)); // Set the font
            canvas.getChildren().add(bossHealthText);
            bossHealthText.setText("Boss Health: " +  bossHealth);
        }
        textShown = true;
    }

    // Updates the boss health text
    public void updateBossHealthText()
    {
        bossHealthText.setText("Boss Health: " +  bossHealth);
    }
    
    // Checks the status of aliens and handles collisions
    public void checkAlienStatus()
    {
        Laser[] alienLasers = monkeySoup.getLasers();
        monkeySoup.updateLasers(canvas);
        if(alienLaserCounter == ALIEN_SHOT_DELAY)
        {
            alienLaserCounter = 0;
            monkeySoup.shootLaser(canvas);
            monkeySoup.checkDirection(movement);
        }
        else
        {
        alienLaserCounter++;
        }
        if(movementCounter == MOVEMENT_CHANGE_DELAY)
        {
            movementCounter = 0;
            movement = util.Zufall.movement();
            monkeySoup.checkDirection(movement);
        }
            else
        {
             movementCounter++;
        }
        monkeySoup.moveShip(canvas);
        bossHealth = monkeySoup.checkCollision(monkeySoup, lasers);
        if(dead == false && deadLaser == false)
        {
            deadLaser = spaceship.checkLaserCollision(alienLasers, spaceship);
            dead = spaceship.checkBossCollision(monkeySoup, spaceship);    
        }
        if(bossHealth == 0 && bossDead == false)
        {
            monkeySoup.bossDeath();
            bossDead = true;
        }
    }
    
    // Checks if the player has won and handles the win condition
    public void checkWin() throws IOException {
        if(bossHealth == 0 && !winHandled) {
            winHandled = true; // Prevent further scene switches
            controller.fadeOutMusic();
            PauseTransition pause = new PauseTransition(Duration.seconds(3.1)); // Duration of the explosion 
            pause.setOnFinished(event -> {
                try {
                    Schorndorf_Invaders.getApplication().setScene("EndScreen.fxml");
                    controller.stopTimer();
                    System.out.println("SWITCHED SCENES");
                } catch (IOException ex) {
                    Logger.getLogger(FinalBossTimer.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
            pause.play();
        }
    }

    // Getter and setter methods
    public MonkeySoup getMonkeySoup() {
        return monkeySoup;
    }

    public void setStartingAnimationCounter(int startingAnimationCounter) {
        this.startingAnimationCounter = startingAnimationCounter;
    }
  
    
    public void setLaughPlayed(boolean laughPlayed) {
        this.laughPlayed = laughPlayed;
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

    public void setTextShown(boolean textShown) {
        this.textShown = textShown;
    }

    public void setBossHealth(int bossHealth) {
        this.bossHealth = bossHealth;
    } 
}
