package models;

import java.awt.*;

// Class for power up foods (bombs, special fruit)
public class PowerUpFood {

    private Point position;
    private int type; //0-4
//---------------------Constructor--------------------------------------------------------------------------------------------------------//
    public PowerUpFood(int x,int y,int type){
        position = new Point(x,y);
        this.type = type;
    }
//-----------------------------------------------------------------
//-----------------------------------Getters and Setters-----------	
    public Point getPosition() {
		return position;
	}

	public void setPosition(Point position) {
		this.position = position;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

    

}
