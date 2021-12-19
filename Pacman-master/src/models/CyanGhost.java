package models;

import controllers.*;
import views.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

// Class for the Cyan Ghost. Inherits from Ghost.
public class CyanGhost extends Ghost {

	// Constructor
    public CyanGhost(int x, int y,PacBoard pb){
    	// (x,y) position, PacBoard, ghost speed, and GhostType
        super(x,y,pb,12,3);
    }

    // Load Ghost sprites
    @Override
    public void loadImages(){
        ghostR = new Image[2];
        ghostL = new Image[2];
        ghostU = new Image[2];
        ghostD = new Image[2];
        try {
            ghostR[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/1.png"));
            ghostR[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/3.png"));
            ghostL[0] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/1.png")));
            ghostL[1] = ImageHelper.flipHor(ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/3.png")));
            ghostU[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/4.png"));
            ghostU[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/5.png"));
            ghostD[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/6.png"));
            ghostD[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/cyan/7.png"));
        }catch(IOException e){
            System.err.println("Cannot Read Images !");
        }
    }

    moveType lastCMove;
    moveType pendMove = moveType.UP;

    // Get the next move for the ghost
    // The Cyan Ghost moves around randomly and doesn't chase the Pacman
    @Override
    public moveType getMoveAI(){
        if(isDead) {
            return baseReturner.getMove(logicalPosition.x,logicalPosition.y, parentBoard.getGhostBase().x,parentBoard.getGhostBase().y);
        }else {
            ArrayList<moveType> pm = getPossibleMoves();
            int i = ThreadLocalRandom.current().nextInt(pm.size());
            lastCMove = pm.get(i);
            return lastCMove;
        }
    }
}
