<<<<<<< HEAD

package models;

=======
package models;
>>>>>>> 0cf91c0e8e0315fa2516fb15e07907f79a9fb731
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
