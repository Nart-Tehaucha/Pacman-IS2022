package models;
import java.awt.*;


public class QuestionIcon {
	// Generic Question (icon on board) class. Saves position on the map.
    public Point position;

    public QuestionIcon(int x,int y, int type){
        position = new Point(x,y);
        this.type = type;
    } 
     
    public int type; //0-2
    //0-easy
    //1-medium
    //2-hard
}

