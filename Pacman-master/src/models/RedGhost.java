package models;

import controllers.*;
import views.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

//Class for the Red Ghost. Inherits from Ghost.
public class RedGhost extends Ghost {

    public BFSFinder bfs;
	// (x,y) position, PacBoard, ghost speed, and GhostType
    public RedGhost(int x, int y,PacBoard pb){
    	//12
        super(x,y,pb,12,1);
    }

    // Load Ghost sprites
    @Override
    public void loadImages(){
        ghostR = new Image[2];
        ghostL = new Image[2];
        ghostU = new Image[2];
        ghostD = new Image[2];
        try {
            ghostR[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/1.png"));
            ghostR[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/3.png"));
            ghostL[0] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/1.png")));
            ghostL[1] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/3.png")));
            ghostU[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/4.png"));
            ghostU[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/5.png"));
            ghostD[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/6.png"));
            ghostD[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/7.png"));
        }catch(IOException e){
            System.err.println("Cannot Read Images !");
        }
    }

    moveType pendMove = moveType.UP;

    // Get the next move for the ghost
    // The red ghost chases after the Pacman by calculating a path using BFS. (Explained in class controllers.BFSFinder)
    @Override
    public moveType getMoveAI(){
        if(bfs==null)
            bfs = new BFSFinder(parentBoard);
        if(isDead) {
            return baseReturner.getMove(logicalPosition.x,logicalPosition.y, parentBoard.getGhostBase().x,parentBoard.getGhostBase().y);
        }else{
            return bfs.getMove(logicalPosition.x,logicalPosition.y,parentBoard.getPacman().getLogicalPosition().x,parentBoard.getPacman().getLogicalPosition().y);

        }
    }


}
