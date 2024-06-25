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
public class Alien extends ImageView
{
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
    
    private static final int MAX_LASERS = 2000;
    
    private static final int MOVEMENT_SPEED = 10;
    
    private static int laserCount = 0; // Make laserCount static
    
    Laser[] lasers = new Laser[MAX_LASERS];
    
    private boolean alive = true;
    
    Schorndorf_Invaders app = Schorndorf_Invaders.getApplication();
    String currentFxmlFile = app.getCurrentFxml();
    
    public Alien(String url)
    { 
        super(new Image(url));
        alienExplosion.setVolume(0.2);
        laserShot.setVolume(0.1);
        for (int i = 0; i < MAX_LASERS; i++) 
        {
            if(currentFxmlFile.equals("LevelTwo.fxml"))
            {
                lasers[i] = new Laser("/res/lasers/laserRed.png");
                lasers[i].setFitHeight(15.4);   //14.3
                lasers[i].setFitWidth(7);     //6.5
                lasers[i].setSmooth(true);
            }
            else if(currentFxmlFile.equals("LevelThree.fxml"))
            {
                lasers[i] = new Laser("/res/lasers/lightning.png");
                lasers[i].setFitHeight(23.8);   //22.1
                lasers[i].setFitWidth(7);     //6.5
                lasers[i].setSmooth(true);
            }
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

    public boolean checkCollision(Alien alien, Laser[] lasers)
    {
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
                explosionImageView.setFitHeight(150); // Set size as needed
                explosionImageView.setFitWidth(150);  // Set size as needed
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

    private void removeAlienLasers(Pane currentParent, Alien alien) 
    {
        for (Laser laser : alien.lasers) {
            if (laser != null) {
                laser.setVisible(false);
                currentParent.getChildren().remove(laser);
            }
        }
    }
    
    public void shootLaser(AnchorPane canvas) {
    if (alive && this.getParent() != null) { // Check if alien is alive and part of the scene
        if (laserCount < MAX_LASERS) {
            laserCount++;
            System.out.println(laserCount);
            lasers[laserCount - 1].setY(getY() + 10);
            lasers[laserCount - 1].setX(getX() + 42);
            lasers[laserCount - 1].setSmooth(true);
            if (!canvas.getChildren().contains(lasers[laserCount - 1])) {
                canvas.getChildren().add(lasers[laserCount - 1]);
            }
            laserShot.play();
            System.out.println(laserCount);
        }
    }
}

    
    public void updateLasers(AnchorPane canvas) {
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

    public static int getLaserCount() {
        return laserCount;
    }
    
    public Laser[] getLasers() 
    {
        return lasers;
    }

    public boolean isAlive() {
        return alive;
    }
}
