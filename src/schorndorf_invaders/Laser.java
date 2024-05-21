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
    
    
    public Laser(String url)
    {
        super(new Image(url));
    }
    
    public void moveLaser()
    {
        setY(getY() - 6);
    }
    
    public void alienMoveLaser()
    {
        setY(getY() + 6);
    } 
}
