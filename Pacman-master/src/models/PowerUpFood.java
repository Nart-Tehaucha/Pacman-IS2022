<<<<<<< HEAD:Pacman-master/src/model/PowerUpFood.java
package model;
=======
package models;
>>>>>>> ea6a1897000173c7d22a8c43166c097d24125e39:Pacman-master/src/models/PowerUpFood.java
import java.awt.*;

// Class for power up foods (bombs, special fruit)
public class PowerUpFood {

    public Point position;

    public PowerUpFood(int x,int y,int type){
        position = new Point(x,y);
        this.type = type;
    }

    public int type; //0-4

}
