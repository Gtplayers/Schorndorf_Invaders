/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schorndorf_invaders;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

/**
 *
 * @author TrogrlicLeon
 */
public class Spaceship extends ImageView
{
    @FXML
    private AnchorPane canvas;
    
    private Direction direction;
    
    private static final int MAX_LASERS = 1000;
    
    private int laserCount = 0;
    
    Laser[] lasers = new Laser[MAX_LASERS];
    
    public Spaceship(String url)
    {
        super(new Image(url));
        for (int i = 0; i < MAX_LASERS; i++) 
        {
            lasers[i] = new Laser("/res/placeholderLaser.png");
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
                spaceship.setY(spaceship.getY() - 2);
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
                spaceship.setY(spaceship.getY() + 2);
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
                spaceship.setX(spaceship.getX() + 2);
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
                spaceship.setX(spaceship.getX() - 2);
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
            lasers[laserCount].setFitHeight(100);
            lasers[laserCount].setFitWidth(100);
            lasers[laserCount].setY(getY() - 50);
            lasers[laserCount].setX(getX() + 27);
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
                    // If the laser has disappeared (e.g., off-screen), mark its slot as available
                    canvas.getChildren().remove(lasers[i]);
                    lasers[i] = null;
                }
            }
        }
    }

    public Laser[] getLasers() {
        return lasers;
    }
    
}
