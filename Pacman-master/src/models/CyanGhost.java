package models;

import controllers.*;
import views.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

// Class for the Cyan Ghost. Inherits from Ghost.
// The Cyan Ghost moves in a direction until it hits a wall, and chooses a new direction
public class CyanGhost extends Ghost {

	// Constructor
    public CyanGhost(int x, int y,PacBoard pb, int gameMode){
    	// (x,y) position, PacBoard, ghost speed, and GhostType
        super(x,y,pb,12,3,gameMode);
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
                ghostR[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/1.png"));
                ghostR[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/3.png"));
                ghostL[0] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/1.png")));
                ghostL[1] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/3.png")));
                ghostU[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/4.png"));
                ghostU[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/5.png"));
                ghostD[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/6.png"));
                ghostD[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/7.png"));
                break;
        	case 1:
            	ghostR[0] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_g/r1.png"));
            	ghostR[1] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_g/r2.png"));
            	ghostR[2] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_g/r3.png"));
            	ghostL[0] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_g/l1.png"));
            	ghostL[1] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_g/l2.png"));
            	ghostL[2] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_g/l3.png"));
            	ghostU[0] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_g/u1.png"));
            	ghostU[1] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_g/u2.png"));
            	ghostU[2] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_g/u3.png"));
            	ghostD[0] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_g/d1.png"));
            	ghostD[1] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_g/d2.png"));
            	ghostD[2] = ImageIO.read(this.getClass().getResource("/resources/images/zombie_g/d3.png"));
            	break;
        	case 2:
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
        	case 3:
                ghostR[0] = ImageIO.read(this.getClass().getResource("/resources/images/xmas_ghost/cyan/1.png"));
                ghostR[1] = ImageIO.read(this.getClass().getResource("/resources/images/xmas_ghost/cyan/3.png"));
                ghostL[0] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/xmas_ghost/cyan/1.png")));
                ghostL[1] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/xmas_ghost/cyan/3.png")));
                ghostU[0] = ImageIO.read(this.getClass().getResource("/resources/images/xmas_ghost/cyan/4.png"));
                ghostU[1] = ImageIO.read(this.getClass().getResource("/resources/images/xmas_ghost/cyan/5.png"));
                ghostD[0] = ImageIO.read(this.getClass().getResource("/resources/images/xmas_ghost/cyan/6.png"));
                ghostD[1] = ImageIO.read(this.getClass().getResource("/resources/images/xmas_ghost/cyan/7.png"));
                break;
        	default:
                ghostR[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/1.png"));
                ghostR[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/3.png"));
                ghostL[0] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/1.png")));
                ghostL[1] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/3.png")));
                ghostU[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/4.png"));
                ghostU[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/5.png"));
                ghostD[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/6.png"));
                ghostD[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/7.png"));
        		
        	}
        }catch(IOException e){
            System.err.println("Cannot Read Images !");
        }
    }

    moveType lastCMove;
    moveType pendMove = moveType.UP;

    // Get the next move for the ghost
    // The Cyan Ghost moves in a direction until it hits a wall, and chooses a new direction
    @Override
    public moveType getMoveAI(){
        if(isDead) {
            return baseReturner.getMove(logicalPosition.x,logicalPosition.y, parentBoard.getGhostBase().x,parentBoard.getGhostBase().y);
        }else {
            if (lastCMove == null || isStuck) {
                ArrayList<moveType> pm = getPossibleMoves();
                int i = ThreadLocalRandom.current().nextInt(pm.size());
                lastCMove = pm.get(i);
                return lastCMove;
            } else {
                return lastCMove;
            }
        }
    }
}
