/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schorndorf_invaders;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
/**
 *
 * @author TrogrlicLeon
 */
public class MyAnimationTimer extends AnimationTimer implements EventHandler<KeyEvent>
{
    private static final long INTERVAL = 10_000_000l;
    //                                       10_000_000l;    -> 1/100 Sekunde  (schnellste Bewegung)
//                                       100_000_000l;   -> 1/10  Sekunde
//                                       1_000_000_000l; -> 1     Sekunde
    private Circle circle = null;
    private Pane   canvas = null;

    private long lastCall = 0;

    private Direction direction;

    public MyAnimationTimer(Circle circle, Pane canvas)
    {
        this.circle = circle;
        this.canvas = canvas;
        this.lastCall = System.nanoTime();
    }
    @Override
    public void handle(KeyEvent event)
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
    @Override
    public void handle(long now)
    {
        if (now > lastCall+INTERVAL)
        {
            if(direction == Direction.UP)
            {
                if (canvas.getHeight() - circle.getRadius() - circle.getCenterY() < canvas.getHeight())
                {
                    circle.setCenterY(circle.getCenterY() - 1);
                }
                else
                {
                    circle.setCenterY(canvas.getHeight() + circle.getRadius());
                }
            }
            else if(direction == Direction.DOWN)
            {
                if (canvas.getHeight() + circle.getRadius() - circle.getCenterY() > 0)
                {
                    circle.setCenterY(circle.getCenterY() + 1);
                }
                else
                {
                    circle.setCenterY(-circle.getRadius());
                }
            }
            else if(direction == Direction.RIGHT)
            {
                if (canvas.getWidth() + circle.getRadius() - circle.getCenterX() > 0)
                {
                    circle.setCenterX(circle.getCenterX() + 1);
                }
                else
                {
                    circle.setCenterX(-circle.getRadius());
                }
            }
            else if(direction == Direction.LEFT)
            {
                if (canvas.getWidth() - circle.getRadius() - circle.getCenterX() < canvas.getWidth())
                {
                    circle.setCenterX(circle.getCenterX() - 1);
                }
                else
                {
                    circle.setCenterX(canvas.getWidth() + circle.getRadius());
                }
            }
            lastCall = now;
        }
    }
}
