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
public class Laser extends ImageView{
    
    private static final int MOVEMENT_SPEED = 15;
    private boolean processed = false;
    
    public boolean isProcessed() {
        return processed;
    }

    public void setProcessed(boolean processed) {
        this.processed = processed;
    }
    
    public Laser(String url)
    {
        super(new Image(url));
    }
    
    public void moveLaser()
    {
        setY(getY() - MOVEMENT_SPEED);
    }
    
    public void alienMoveLaser()
    {
        setY(getY() + MOVEMENT_SPEED);
    } 
}
