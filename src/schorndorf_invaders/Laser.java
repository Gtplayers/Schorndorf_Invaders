/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package schorndorf_invaders;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;

/**
 *
 * @author Leon
 */
/**
 * Represents a laser in the game, extending ImageView to display the laser image.
 * This class handles the movement of lasers shot by the player, aliens, and the boss.
 */
public class Laser extends ImageView{
    
    private static final int MOVEMENT_SPEED = 15; // Speed at which the laser moves
    private boolean processed = false; // Flag to track if the laser has been processed (hit an object or moved off screen)
    
    /**
     * Checks if the laser has been processed.
     * @return true if processed, false otherwise.
     */
    public boolean isProcessed() 
    {
        return processed;
    }

     /**
     * Sets the processed status of the laser.
     * @param processed true to mark as processed, false otherwise.
     */
    public void setProcessed(boolean processed) 
    {
        this.processed = processed;
    }
    
    /**
     * Constructs a Laser object with the specified image URL.
     * @param url The URL of the laser image.
     */
    public Laser(String url)
    {
        super(new Image(url));  // Calls the parent class constructor with the laser image
    }
    
    /**
     * Moves the laser upwards. Used for player's laser.
     */
    public void moveLaser()
    {
        setY(getY() - MOVEMENT_SPEED);  // Moves the laser up by MOVEMENT_SPEED
    }
    
    /**
     * Moves the laser downwards. Used for alien's laser.
     */
    public void alienMoveLaser()
    {
        setY(getY() + MOVEMENT_SPEED);  // Moves the laser down by MOVEMENT_SPEED
    }
    
     /**
     * Moves the laser diagonally left and downwards. Used for boss's leftward laser.
     */
    public void bossMoveLeftLaser()
    {
        setY(getY() + MOVEMENT_SPEED);  // Moves the laser down by MOVEMENT_SPEED
        setX(getX() - MOVEMENT_SPEED);  // Moves the laser left by MOVEMENT_SPEED
    } 
    
    /**
     * Moves the laser diagonally right and downwards. Used for boss's rightward laser.
     */
    public void bossMoveRightLaser()
    {
        setY(getY() + MOVEMENT_SPEED);  // Moves the laser down by MOVEMENT_SPEED
        setX(getX() + MOVEMENT_SPEED);  // Moves the laser right by MOVEMENT_SPEED
    } 
}
