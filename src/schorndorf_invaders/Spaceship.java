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
    Laser laser = new Laser("/res/placeholderLaser.png");
    
    public Spaceship(String url)
    {
        super(new Image(url));
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
            System.out.println("STOP");
            direction = Direction.NONE;
        }
        else if(direction == Direction.UP)
        {
            if (canvas.getHeight() - 100 - spaceship.getY() < canvas.getHeight())
            {
                spaceship.setY(spaceship.getY() - 1);
            }
            else
            {
                spaceship.setY(canvas.getHeight() + 100);
            }
            System.out.println("UP");
        }
        else if(direction == Direction.DOWN)
        {
            if (canvas.getHeight() + 100 - spaceship.getY() > 0)
            {
                spaceship.setY(spaceship.getY() + 1);
            }
            else
            {
                spaceship.setY(-100);
            }
            System.out.println("DOWN");
        }
        else if(direction == Direction.RIGHT)
        {
            if (canvas.getWidth() + 100 - spaceship.getX() > 0)
            {
                spaceship.setX(spaceship.getX() + 1);
            }
            else
            {
                spaceship.setX(-100);
            }
            System.out.println("RIGHT");
        }
        else if(direction == Direction.LEFT)
        {
            if (canvas.getWidth() - 100 - spaceship.getX() < canvas.getWidth())
            {
                spaceship.setX(spaceship.getX() - 1);
            }
            else
            {
                spaceship.setX(canvas.getWidth() + 100);
            }
            System.out.println("LEFT");
        }
    }
    
    public void shootLaser(MouseEvent event, Laser laser, AnchorPane canvas, Spaceship spaceship)
    {
        if(event.getEventType() == MouseEvent.MOUSE_CLICKED)
        {
            this.laser = laser;
            this.canvas = canvas;
            laser.setFitHeight(100);
            laser.setFitWidth(100);
            laser.setY(spaceship.getY());
            laser.setX(spaceship.getX());
            laser.setSmooth(true);
            canvas.getChildren().add(laser);
            System.out.println("LASER SHOT");  
        }
    }
}
