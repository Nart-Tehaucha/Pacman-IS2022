package models;

import controllers.*;
import views.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

//Class for the Red Ghost. Inherits from Ghost.
// The red ghost chases after the Pacman by calculating a path using BFS. (Explained in class controllers.BFSFinder)
public class RedGhost extends Ghost {

    public BFSFinder bfs;
	// (x,y) position, PacBoard, ghost speed, and GhostType
    public RedGhost(int x, int y,PacBoard pb, int gameMode){
    	//12
        super(x,y,pb,12,1,gameMode);
    }

    // Load Ghost sprites
    @Override
    public void loadImages(int gameMode){
        ghostR = new Image[4];
        ghostL = new Image[4];
        ghostU = new Image[4];
        ghostD = new Image[4];
        try {
        	switch(gameMode) {
        	case 0:
                ghostR[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/1.png"));
                ghostR[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/3.png"));
                ghostL[0] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/1.png")));
                ghostL[1] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/3.png")));
                ghostU[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/4.png"));
                ghostU[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/5.png"));
                ghostD[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/6.png"));
                ghostD[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/7.png"));
                break;
        	case 1:
            	ghostR[0] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_b/r1.png"));
            	ghostR[1] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_b/r2.png"));
            	ghostR[2] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_b/r3.png"));
            	ghostL[0] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_b/l1.png"));
            	ghostL[1] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_b/l2.png"));
            	ghostL[2] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_b/l3.png"));
            	ghostU[0] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_b/u1.png"));
            	ghostU[1] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_b/u2.png"));
            	ghostU[2] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_b/u3.png"));
            	ghostD[0] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_b/d1.png"));
            	ghostD[1] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_b/d2.png"));
            	ghostD[2] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_b/d3.png"));
            	break;
        	case 2:
        		System.out.println("GAME MODE: " + gameMode);
            	ghostR[0] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/0.png"));
            	ghostR[1] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/1.png"));
            	ghostR[2] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/2.png"));
            	ghostR[3] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/3.png"));
            	ghostL[0] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/0.png"));
            	ghostL[1] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/1.png"));
            	ghostL[2] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/2.png"));
            	ghostL[3] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/3.png"));
            	ghostU[0] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/0.png"));
            	ghostU[1] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/1.png"));
            	ghostU[2] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/2.png"));
            	ghostU[3] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/3.png"));
            	ghostD[0] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/0.png"));
            	ghostD[1] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/1.png"));
            	ghostD[2] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/2.png"));
            	ghostD[3] = ImageIO.read(this.getClass().getResource("/resources/images/corona_b/3.png"));
            	break;
        		
        	default:
                ghostR[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/1.png"));
                ghostR[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/3.png"));
                ghostL[0] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/1.png")));
                ghostL[1] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/3.png")));
                ghostU[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/4.png"));
                ghostU[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/5.png"));
                ghostD[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/6.png"));
                ghostD[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/red/7.png"));
        		
        	}
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
