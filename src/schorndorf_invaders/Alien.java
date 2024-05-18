/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schorndorf_invaders;

import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 *
 * @author Leon
 */
public class Alien extends ImageView
{
    private Direction direction;
    private boolean dead;
    
    FXMLDocumentController controller;
    
    Parent parent;
    
    
    public Alien(String url, FXMLDocumentController controller)
    {
        super(new Image(url));
        this.controller = controller;
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
                setY(getY() - 2);
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
                setY(getY() + 2);
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
                setX(getX() + 2);
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
                setX(getX() - 2);
            }
            else
            {
                setX(canvas.getWidth() + 100);
            }
        }
    }
    
    public void checkCollision(Alien alien, Laser[] lasers)
    {
        parent = getParent();
        for (Laser laser : lasers) {
            if (parent != null && alien != null && laser != null && alien.getBoundsInParent().intersects(laser.getBoundsInParent())) {
                // Laser intersects with the alien, handle collision
                alien.setVisible(false); // Example: Set the alien to be invisible
                laser.setVisible(false); // Example: Set the laser to be invisible
                ((Pane) parent).getChildren().removeAll(laser, alien);
                controller.updateScore();
                break; // Exit the loop after handling collision with one laser
             }
         }
    }
    
    
}
