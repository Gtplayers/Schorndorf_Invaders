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
//                                       10_000_000l;    -> 1/100 Sekunde
//                                       100_000_000l;   -> 1/10  Sekunde
//                                       1_000_a000_000l; -> 1     Sekunde
    private AnchorPane canvas = null;

    private long lastCall = 0;
    
    private static final int MAX_ALIENS = 10;
    FXMLDocumentController controller;
    
    Laser laser = new Laser("/res/placeholderLaser.png");
    Spaceship spaceship = new Spaceship("/res/spaceship.png");
    Alien alien = new Alien("/res/alien1.png", controller);
    
    Alien[] aliens = new Alien[MAX_ALIENS];
    
    
    public MyAnimationTimer(AnchorPane canvas, Spaceship spaceship, FXMLDocumentController controller)
    {
        this.canvas = canvas;
        this.lastCall = System.nanoTime();
        this.spaceship = spaceship;
        this.controller = controller;
        aliens[0] = new Alien("/res/alien1.png", controller);
        aliens[1] = new Alien("/res/alien2.png", controller);
        aliens[2] = new Alien("/res/alien1.png", controller);
        aliens[3] = new Alien("/res/alien2.png", controller);
        aliens[4] = new Alien("/res/alien1.png", controller);
        aliens[5] = new Alien("/res/alien1.png", controller);
        aliens[6] = new Alien("/res/alien2.png", controller);
        aliens[7] = new Alien("/res/alien1.png", controller);
        aliens[8] = new Alien("/res/alien2.png", controller);
        aliens[9] = new Alien("/res/alien1.png", controller);
    }

    @Override
    public void handle(KeyEvent event)
    {
        spaceship.checkDirection(event);       
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
            
            Laser[] lasers = spaceship.getLasers();
            
            for (Alien alien : aliens) 
            {
                alien.moveShip(canvas);
                alien.checkCollision(alien, lasers);
            }
            canvas.getChildren().removeIf(node -> !node.isVisible());
            
            lastCall = now;
        }
    }

    public Alien[] getAliens() {
        return aliens;
    }
    
}
