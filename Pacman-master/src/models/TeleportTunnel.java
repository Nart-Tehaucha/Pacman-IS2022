<<<<<<< HEAD:Pacman-master/src/model/TeleportTunnel.java
package model;
=======
package models;
>>>>>>> ea6a1897000173c7d22a8c43166c097d24125e39:Pacman-master/src/models/TeleportTunnel.java
import java.awt.*;

// Class for handling passage tunnels
public class TeleportTunnel {

    private Point from; // Entrance of tunnel
    private Point to; // Exit of tunnel
    private moveType reqMove;

    public Point getTo() {
        return to;
    }

    public void setTo(Point to) {
        this.to = to;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public moveType getReqMove() {
        return reqMove;
    }

    public void setReqMove(moveType reqMove) {
        this.reqMove = reqMove;
    }

    public TeleportTunnel(int x1,int y1,int x2,int y2,moveType reqMove){
        from = new Point(x1,y1);
        to = new Point(x2,y2);
        this.reqMove = reqMove;
    }
}
