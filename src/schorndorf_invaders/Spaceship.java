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
public class Spaceship extends ImageView
{
    @FXML
    private AnchorPane canvas;
    
    @FXML
    private Button pauseButton;
    
    private Direction direction;
    
    private static final int MAX_LASERS = 1000;
    
    private static final int MOVEMENT_SPEED = 5;
    
    private int laserCount = 0;
    
    Laser[] lasers = new Laser[MAX_LASERS];
    
    Parent parent;
    
    private boolean dead;
    
    private boolean deadLaser;
    
    
    URL resource = getClass().getResource("/res/sounds/laserSounds/laserShot.wav");
    AudioClip laserShot = new AudioClip(resource.toString());
    
    public Spaceship(String url)
    {
        super(new Image(url));
        laserShot.setVolume(0.1);
        for (int i = 0; i < MAX_LASERS; i++) 
        {
            lasers[i] = new Laser("/res/lasers/laserGreen.png");
            lasers[i].setFitHeight(15.4);
            lasers[i].setFitWidth(7);
            lasers[i].setSmooth(true);
        } 
    }

    public void checkDirection(KeyEvent event)
    {
        if(event.getEventType() == KeyEvent.KEY_PRESSED)
        {
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
            direction = Direction.NONE;
        }
    }
    
    public void moveShip(Spaceship spaceship, Pane canvas)
    {
        if (direction == Direction.NONE)
        {
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
    
    public void shootLaser(MouseEvent event, AnchorPane canvas) 
    {
        if (event.getEventType() == MouseEvent.MOUSE_CLICKED) 
        {
            laserShot.play();                                             //ENABLE AFTER TESTING OVER
            //lasers[laserCount].setFitHeight(20);
            //lasers[laserCount].setFitWidth(20);
            lasers[laserCount].setY(getY() + 10);
            lasers[laserCount].setX(getX() + 42);
            lasers[laserCount].setSmooth(true);
            laserCount++;
        }
    }
    
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
                lasers[i].moveLaser();
                if (lasers[i].getY() < 0) 
                {
                    canvas.getChildren().remove(lasers[i]);
                    lasers[i] = null;
                }
            }
        }
    }

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
    
    public boolean checkBossCollision(MonkeySoup monkeySoup, Spaceship spaceship) 
    {
        Pane currentParent = (Pane) getParent();
        if (currentParent == null || monkeySoup == null || spaceship == null) return false;

        if (monkeySoup.isVisible() && monkeySoup.getBoundsInParent().intersects(spaceship.getBoundsInParent())) {
            spaceship.setVisible(false);
            currentParent.getChildren().remove(spaceship);
            dead = true;
        }
    return dead;
    }

    public boolean checkLaserCollision(Laser[] alienLasers, Spaceship spaceship) 
    {
    Pane currentParent = (Pane) getParent();
    for (Laser laser : alienLasers) {
        if (currentParent == null || laser == null || spaceship == null) return false;
        if (laser.isVisible() && this.getBoundsInParent().intersects(laser.getBoundsInParent()))
        {
            laser.setVisible(false);
            spaceship.setVisible(false);
            currentParent.getChildren().removeAll(spaceship, laser);
            deadLaser = true;
            break;
        }
    }
    return deadLaser;
}
    
    public void reset() 
    {
        dead = false; // Reset the dead flag
        deadLaser = false;
        setVisible(true); // Make the spaceship visible again
    }
    
    public Laser[] getLasers() {
        return lasers;
    }
    
}
