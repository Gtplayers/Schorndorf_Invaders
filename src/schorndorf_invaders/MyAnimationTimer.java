/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schorndorf_invaders;

import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
/**
 *
 * @author TrogrlicLeon
 */
public class MyAnimationTimer extends AnimationTimer implements EventHandler<KeyEvent> 
{
    private static final long INTERVAL = 1l;
//                                       1l; -> schellste Bewegung
//                                       10_000_000l;    -> 1/100 Sekunde  (schnellste Bewegung)
//                                       100_000_000l;   -> 1/10  Sekunde
//                                       1_000_000_000l; -> 1     Sekunde
    private AnchorPane canvas = null;

    private long lastCall = 0;
    
    Laser laser = new Laser("/res/placeholderLaser.png");
    Spaceship spaceship = new Spaceship("/res/spaceship.png");
    Alien alien = new Alien("/res/alien1.png");
    
    
    
    public MyAnimationTimer(AnchorPane canvas, Spaceship spaceship, Alien alien)
    {
        this.canvas = canvas;
        this.lastCall = System.nanoTime();
        this.spaceship = spaceship;
        this.alien = alien;
    }

    @Override
    public void handle(KeyEvent event)
    {
        spaceship.checkDirection(event);
        alien.checkCollision(alien, laser);
    }

    public void handle(MouseEvent event)
    {
        spaceship.shootLaser(event, canvas);
    }
    @Override
    public void handle(long now)
    {
        if (now > lastCall+INTERVAL)
        {
            spaceship.moveShip(spaceship, canvas);
            spaceship.updateLasers(canvas);
            lastCall = now;
        }
    }
}
