package models;

import controllers.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import java.util.concurrent.ThreadLocalRandom;


// Generic class for Ghost. Handles ghost position, status, and movement.
public abstract class Ghost {
    // Timer for the animation of the ghost
    public Timer animTimer;
    public ActionListener animAL;
    // Timer for the movement of the ghost
    public Timer moveTimer;
    public ActionListener moveAL;
    
    public moveType activeMove; // Current move that the ghost is doing
    protected boolean isStuck = true; // Is the Ghost stuck?

    private int ghostSpeed; // Speed of the ghost
    protected boolean isDead = false; // Is the ghost dead?
    
    // Images of the ghost
    Image ghostImg;
    int activeImage = 0;
    int addFactor = 1;
    
    public Point pixelPosition; // The actual position of the ghost
    public Point logicalPosition; // The graphical position of the ghost (28 * pixelPosition)
    public int ghostType; // Type (Red, Pink, Cyan)

    // Sprite of the ghost for every direction (up, down, left, right)
    Image[] ghostR;
    Image[] ghostL;
    Image[] ghostU;
    Image[] ghostD;

    // Decides the delay between the ghost's movements
    int ghostNormalDelay;
    
    // Number of frames per animation cycle.
    private int framesInCycle;

    // Calculates the ghost's path to the base
    BFSFinder baseReturner;

    protected PacBoard parentBoard;

    // Constructor
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public Ghost (int x, int y,PacBoard pb,int ghostDelay, int ghostType, int gameMode) {
    	this.ghostType = ghostType;
    	
        logicalPosition = new Point(x,y);
        pixelPosition = new Point(28*x,28*y);

        parentBoard = pb;

        activeMove = moveType.RIGHT;

        ghostNormalDelay = ghostDelay;

        loadImages(gameMode);
        
        this.ghostSpeed = 1;
        
        // Set the number of frames in the enemy's walk cycle
        switch(gameMode) {
        case 0:
        	framesInCycle = 2;
        case 1:
        	framesInCycle = 3;
        case 2:
        	framesInCycle = 4;
    	default:
    		framesInCycle = 2;
        }
        
        //animation timer
        animAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                activeImage = (activeImage + 1) % framesInCycle;
            }
        };
        animTimer = new Timer(200,animAL);
        animTimer.start();
        
        // Handles the movement of the Ghosts around the map.
        moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                if((pixelPosition.x % 28 == 0) && (pixelPosition.y % 28 == 0)){
                    if(!isStuck) {
                        switch (activeMove) {
                            case RIGHT:
                                logicalPosition.x++;
                                break;
                            case LEFT:
                                logicalPosition.x--;
                                break;
                            case UP:
                                logicalPosition.y--;
                                break;
                            case DOWN:
                                logicalPosition.y++;
                                break;
                        }
                        parentBoard.dispatchEvent(new ActionEvent(this,Messages.UPDATE,null));
                    }
                    
                    activeMove = getMoveAI();
                    isStuck = true;
                }else{
                    isStuck = false;
                }
                switch(activeMove){
                case RIGHT:
                    if(pixelPosition.x >= (parentBoard.getM_x()-1) * 28){
                        return;
                    }
                    if((logicalPosition.x+1 < parentBoard.getM_x()) && (parentBoard.getMap()[logicalPosition.x+1][logicalPosition.y]>0) && ((parentBoard.getMap()[logicalPosition.x+1][logicalPosition.y]<26))){
                        return;
                    }
                    pixelPosition.x += getGhostSpeed();
                    break;
                case LEFT:
                    if(pixelPosition.x <= 0){
                        return;
                    }
                    if((logicalPosition.x-1 >= 0) && (parentBoard.getMap()[logicalPosition.x-1][logicalPosition.y]>0) && ((parentBoard.getMap()[logicalPosition.x-1][logicalPosition.y]<26))){
                        return;
                    }
                    pixelPosition.x -= getGhostSpeed();
                    break;
                case UP:
                    if(pixelPosition.y <= 0){
                        return;
                    }
                    if((logicalPosition.y-1 >= 0) && (parentBoard.getMap()[logicalPosition.x][logicalPosition.y-1]>0) && ((parentBoard.getMap()[logicalPosition.x][logicalPosition.y-1]<26))){
                        return;
                    }
                    pixelPosition.y -= getGhostSpeed();
                    break;
                case DOWN:
                    if(pixelPosition.y >= (parentBoard.getM_y()-1) * 28){
                        return;
                    }
                    if((logicalPosition.y+1 < parentBoard.getM_y()) && (parentBoard.getMap()[logicalPosition.x][logicalPosition.y+1]>0) && ((parentBoard.getMap()[logicalPosition.x][logicalPosition.y+1]<26))){
                        return;
                    }
                    pixelPosition.y += getGhostSpeed();
                    break;
            }
            
            parentBoard.dispatchEvent(new ActionEvent(this,Messages.COLTEST,null));
        }
    };
        moveTimer = new Timer(ghostDelay,moveAL);
        moveTimer.start();

        baseReturner = new BFSFinder(pb);
        //start AI
        activeMove = getMoveAI();

    }
    

	                            // ---END OF CONSTRUCTOR---\\
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//

	//load Images from Resource
    public abstract void loadImages(int gameMode);

    //get Move Based on AI
    public abstract moveType getMoveAI();

    //get possible Moves
    public ArrayList<moveType> getPossibleMoves(){
        ArrayList<moveType> possibleMoves = new ArrayList<>();

        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.getM_x()-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.getM_y()-1 ) {
            if (!(parentBoard.getMap()[logicalPosition.x + 1][logicalPosition.y] > 0)) {
                possibleMoves.add(moveType.RIGHT);
            }

            if (!(parentBoard.getMap()[logicalPosition.x - 1][logicalPosition.y] > 0)) {
                possibleMoves.add(moveType.LEFT);
            }

            if(!(parentBoard.getMap()[logicalPosition.x][logicalPosition.y-1]>0)){
                possibleMoves.add(moveType.UP);
            }

            if(!(parentBoard.getMap()[logicalPosition.x][logicalPosition.y+1]>0)){
                possibleMoves.add(moveType.DOWN);
            }
        }

        return possibleMoves;
    }
    
    // Gets the sprite of the ghost according to it's direction (up, down, left, right)
    public Image getGhostImage(){
		switch (activeMove) {
		    case RIGHT:
		        return ghostR[activeImage];
		    case LEFT:
		        return ghostL[activeImage];
		    case UP:
		        return ghostU[activeImage];
		    case DOWN:
		        return ghostD[activeImage];
		}
		return ghostR[activeImage];   
    }

    //=================================== GETTER SETTERS ===================================
    
    public boolean isDead() {
        return isDead;
    }

    public int getGhostSpeed() {
		return ghostSpeed;
	}

	public void setGhostSpeed(int ghostSpeed) {
		this.ghostSpeed = ghostSpeed;
	}

    public int getGhostNormalDelay() {
		return ghostNormalDelay;
	}

	public void setGhostNormalDelay(int ghostNormalDelay) {
		this.ghostNormalDelay = ghostNormalDelay;
	}

    

}