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
public class MonkeySoup  extends ImageView{
     private Direction direction;

    Parent parent;
    
    @FXML
    private AnchorPane canvas;
    
    @FXML
    private Button pauseButton;
    
    URL explosionResource = getClass().getResource("/res/sounds/explosionSounds/explosion4.wav");
    AudioClip alienExplosion = new AudioClip(explosionResource.toString());
    
    URL laserResource = getClass().getResource("/res/sounds/laserSounds/alienShot1.wav");
    AudioClip laserShot = new AudioClip(laserResource.toString());
    
    URL explosionGifResource = getClass().getResource("/res/gif/enemyExplosion.gif");
    Image explosionGif = new Image(explosionGifResource.toString());
    ImageView explosionImageView = new ImageView(explosionGif);
       
    PauseTransition pause = new PauseTransition(Duration.seconds(1)); 
    
    private static final int MAX_LASERS = 1000;
    
    private static final int MOVEMENT_SPEED = 5;
    
    private int laserCount = 0;
    
    Laser[] lasers = new Laser[MAX_LASERS];
    
    private int health = 2000;
    
    Schorndorf_Invaders app = Schorndorf_Invaders.getApplication();
    String currentFxmlFile = app.getCurrentFxml();
    
    public MonkeySoup(String url)
    { 
        super(new Image(url));
        alienExplosion.setVolume(0.2);
        laserShot.setVolume(0.1);
        for (int i = 0; i < MAX_LASERS; i++) 
        {
            lasers[i] = new Laser("/res/lasers/laserRed.png");
            lasers[i].setFitHeight(15.4);   //14.3
            lasers[i].setFitWidth(7);     //6.5
            lasers[i].setSmooth(true);
        } 
    }

    
    public void checkDirection(int movement)
    {
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
    
    public void moveShip(Pane canvas)
    {
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
    
    public void startingAnimation()
    {
       setY(getY() + 2); 
    }

    
    public int checkCollision(MonkeySoup monkeySoup, Laser[] lasers) 
    {
        Pane currentParent = (Pane) getParent();
        for (Laser laser : lasers) {
            if (currentParent != null && monkeySoup != null && laser != null && !laser.isProcessed() && monkeySoup.getBoundsInParent().intersects(laser.getBoundsInParent())) {
                health--;
                laser.setVisible(false);
                laser.setProcessed(true); // Mark the laser as processed
                currentParent.getChildren().remove(laser);  
                alienExplosion.play();
                explosionImageView.setFitHeight(150); // Set size as needed
                explosionImageView.setFitWidth(150);  // Set size as needed
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
    return health;
    } 
    
    public void shootLaser(AnchorPane canvas) {
        if (health != 0){ // Only shoot if the boss is alive
            if (laserCount < MAX_LASERS) {
                lasers[laserCount].setY(getY() + 10);
                lasers[laserCount].setX(getX() + 42);
                lasers[laserCount].setSmooth(true);
                laserCount++;
                laserShot.play();
            }
        }
    }
    
    public void updateLasers(AnchorPane canvas) {
        if (health != 0){ // Only update lasers if the boss is alive
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

    public Laser[] getLasers() 
    {
        return lasers;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
