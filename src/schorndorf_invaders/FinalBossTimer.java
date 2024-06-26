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
public class FinalBossTimer extends AnimationTimer implements EventHandler<KeyEvent>{
    
    private static final long INTERVAL = 1l;
//                                       1l; -> schellste Bewegung
//                                       10_000_000l;    -> 1/100 Sekunde
//                                       100_000_000l;   -> 1/10  Sekunde
//                                       1_000_a000_000l; -> 1     Sekunde
    private AnchorPane canvas = null;

    private long lastCall = 0;
    
    private boolean dead = false;
    private boolean deadLaser = false;
    private boolean deathScreenAdded = false;
    private boolean resetDone = true;
    
    Laser[] lasers;
    
    Spaceship spaceship;
    
    Image death = new Image("/res/youDied.jpg");
    ImageView deathScreen = new ImageView(death);
   
    
    private int movement;
    private static final int MOVEMENT_CHANGE_DELAY = 100;
    private int movementCounter;
    
    private static final int STARTING_ANIMATION_LENGTH = 250;                   // 250 on fast PC
    private int startingAnimationCounter;
    
    private static final int ALIEN_SHOT_DELAY = 50;
    private int alienLaserCounter;
    
    private static final int LASER_SHOT_DELAY = 25;
    private int laserCounter;
    
    private int bossHealth = 2;
    private boolean bossDead;
       
    private Text resetText = new Text();
    private Text bossHealthText = new Text();
    
    boolean textShown;
    
    private boolean laughPlayed;
    URL laughResource = getClass().getResource("/res/sounds/laughSounds/evilLaugh2.mp3");
    AudioClip evilLaugh = new AudioClip(laughResource.toString());
    
    MonkeySoup monkeySoup = new MonkeySoup("/res/sprites/monkeySoupBoss.png");  
    
    URL resource = getClass().getResource("/res/sounds/explosionSounds/explosion3.wav");
    AudioClip deathSound = new AudioClip(resource.toString());
    
    URL explosionGifResource = getClass().getResource("/res/gif/playerExplosion.gif");
    Image explosionGif = new Image(explosionGifResource.toString());
    ImageView explosionImageView = new ImageView(explosionGif);
    
    URL bossExplosionGifResource = getClass().getResource("/res/gif/bossExplosion.gif");
    Image bossExplosionGif = new Image(bossExplosionGifResource.toString());
    ImageView bossExplosionImageView = new ImageView(bossExplosionGif);
    
    
    FinalBossController controller;
    
    private boolean winHandled = false;
    
    
    public FinalBossTimer(AnchorPane canvas, Spaceship spaceship, FinalBossController controller)
    {
        this.canvas = canvas;
        this.lastCall = System.nanoTime();
        this.spaceship = spaceship;
        this.controller = controller;
        initializeBoss();
    }

    @Override
    public void handle(KeyEvent event)
    {
        spaceship.checkDirection(event);       
    }

    public void handle(MouseEvent event)
    {
        if (laserCounter >= LASER_SHOT_DELAY)
        {
            spaceship.shootLaser(event, canvas);
            laserCounter = 0;
        }
    }
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

    public void playEvilLaugh()
    {
        if(laughPlayed == false)
        {
            laughPlayed = true;
            evilLaugh.play();
        }
    }
    
    public void initializeBoss() { 
        if(monkeySoup == null)
        {
            monkeySoup = new MonkeySoup("/res/sprites/monkeySoupBoss.png");
        }       
    }
    
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

    public void updateBossHealthText()
    {
        bossHealthText.setText("Boss Health: " +  bossHealth);
    }
    
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
