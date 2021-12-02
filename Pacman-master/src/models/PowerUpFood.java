package models;
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
