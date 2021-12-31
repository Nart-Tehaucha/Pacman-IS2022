package models;

import controllers.*;
import views.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

//Class for the Pink Ghost. Inherits from Ghost.
// The Pink Ghost moves in a direction until it hits a wall, and chooses a new direction
public class PinkGhost extends Ghost {
	
    public PinkGhost(int x, int y,PacBoard pb){
    	// (x,y) position, PacBoard, ghost speed, and GhostType
        super(x,y,pb,12,2);
    }

    // Load Ghost sprites
    @Override
    public void loadImages(){
        ghostR = new Image[2];
        ghostL = new Image[2];
        ghostU = new Image[2];
        ghostD = new Image[2];
        try {
                ghostR[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/pink/1.png"));
                ghostR[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/pink/3.png"));
                ghostL[0] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/pink/1.png")));
                ghostL[1] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/pink/3.png")));
                ghostU[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/pink/4.png"));
                ghostU[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/pink/5.png"));
                ghostD[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/pink/6.png"));
                ghostD[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/pink/7.png"));
        }catch(IOException e){
            System.err.println("Cannot Read Images !");
        }
    }

    moveType lastCMove;

    moveType pendMove = moveType.UP;

    // Get the next move for the ghost
    // The Pink Ghost moves in a direction until it hits a wall, and chooses a new direction
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
