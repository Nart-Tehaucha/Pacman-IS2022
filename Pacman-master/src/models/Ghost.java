package models;

import controllers.*;
import views.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

// Generic class for Ghost. Handles ghost position, status, and movement.
public abstract class Ghost {
    //Anim Vars
    public Timer animTimer;
    public ActionListener animAL;

    //Pending Vars
    public Timer pendingTimer;
    public ActionListener pendingAL;

    //Move Vars
    public Timer moveTimer;
    public ActionListener moveAL;
    public moveType activeMove;
    protected boolean isStuck = true;
    boolean isPending = false;

    public Timer unWeakenTimer1;
    public Timer unWeakenTimer2;
    public ActionListener unweak1;
    public ActionListener unweak2;
    int unweakBlinks;
    boolean isWhite = false;
    int timeToSleep = 5;
    private int ghostSpeed;

    protected boolean isWeak = false;
    protected boolean isDead = false;
    protected boolean disappear = false;

    public boolean isWeak() {
        return isWeak;
    }

    public boolean isDead() {
        return isDead;
    }


    public int getGhostSpeed() {
		return ghostSpeed;
	}

	public void setGhostSpeed(int ghostSpeed) {
		this.ghostSpeed = ghostSpeed;
	}


	//Image[] pac;
    Image ghostImg;
    int activeImage = 0;
    int addFactor = 1;

    public Point pixelPosition;
    public Point logicalPosition;

    // Sprite of the ghost for every direction (up, down, left, right)
    Image[] ghostR;
    Image[] ghostL;
    Image[] ghostU;
    Image[] ghostD;

    Image[] ghostW; // Sprite for "weak" ghost, after player eats bomb
    Image[] ghostWW; // Sprite for "weak" ghost, after player eats bomb
    Image ghostEye;

    int ghostNormalDelay;
    int ghostWeakDelay = 5;
    int ghostDeadDelay = 0;

    // Calculates the ghost's path to the base
    BFSFinder baseReturner;

    protected PacBoard parentBoard;

    // Constructor
//---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public Ghost (int x, int y,PacBoard pb,int ghostDelay) {

        logicalPosition = new Point(x,y);
        pixelPosition = new Point(28*x,28*y);

        parentBoard = pb;

        activeMove = moveType.RIGHT;

        ghostNormalDelay = ghostDelay;

        loadImages();
        
        this.ghostSpeed = 1;

        //load weak Image
        ghostW = new Image[2];
        try {
            ghostW[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/blue/1.png"));
            ghostW[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/blue/3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        ghostWW = new Image[2];
        try {
            ghostWW[0] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/white/1.png"));
            ghostWW[1] = ImageIO.read(this.getClass().getResource("/resources/images/ghost/white/3.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            ghostEye = ImageIO.read(this.getClass().getResource("/resources/images/eye.png")); // invis
        } catch (IOException e) {
            e.printStackTrace();
        }

        //animation timer
        animAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                activeImage = (activeImage + 1) % 2;
            }
        };
        //tal you changed from 100 to 200
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

                    //animTimer.stop();
                    //System.out.println("LOGICAL POS :" + logicalPosition.x + " , " + logicalPosition.y);
                    //if(todoMove != moveType.NONE) {
                    //    activeMove = todoMove;
                    //    todoMove = moveType.NONE;
                    //}
                }else{
                    isStuck = false;
                    //animTimer.start();
                }
                // }
                //TODO : fix ghost movements
                switch(activeMove){
                    case RIGHT:
                        if(pixelPosition.x >= (parentBoard.m_x-1) * 28){
                            return;
                        }
                        if((logicalPosition.x+1 < parentBoard.m_x) && (parentBoard.map[logicalPosition.x+1][logicalPosition.y]>0) && ((parentBoard.map[logicalPosition.x+1][logicalPosition.y]<26)||isPending)){
                            return;
                        }
                        pixelPosition.x ++;
                        break;
                    case LEFT:
                        if(pixelPosition.x <= 0){
                            return;
                        }
                        if((logicalPosition.x-1 >= 0) && (parentBoard.map[logicalPosition.x-1][logicalPosition.y]>0) && ((parentBoard.map[logicalPosition.x-1][logicalPosition.y]<26)||isPending)){
                            return;
                        }
                        pixelPosition.x --;
                        break;
                    case UP:
                        if(pixelPosition.y <= 0){
                            return;
                        }
                        if((logicalPosition.y-1 >= 0) && (parentBoard.map[logicalPosition.x][logicalPosition.y-1]>0) && ((parentBoard.map[logicalPosition.x][logicalPosition.y-1]<26)||isPending)){
                            return;
                        }
                        pixelPosition.y--;
                        break;
                    case DOWN:
                        if(pixelPosition.y >= (parentBoard.m_y-1) * 28){
                            return;
                        }
                        if((logicalPosition.y+1 < parentBoard.m_y) && (parentBoard.map[logicalPosition.x][logicalPosition.y+1]>0) && ((parentBoard.map[logicalPosition.x][logicalPosition.y+1]<26)||isPending)){
                            return;
                        }
                        pixelPosition.y ++;
                        break;
                }

                parentBoard.dispatchEvent(new ActionEvent(this,Messages.COLTEST,null));
            }
        };
        moveTimer = new Timer(ghostDelay,moveAL);
        moveTimer.start();

        unweak1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                unWeakenTimer2.start();
                unWeakenTimer1.stop();
            }
        };
        unWeakenTimer1 = new Timer(7000,unweak1);
        
        unweak2 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(unweakBlinks == 10){
                    unweaken();
                    unWeakenTimer2.stop();
                }
                if(unweakBlinks % 2 == 0){
                    isWhite = true;
                }else{
                    isWhite = false;
                }
                unweakBlinks++;
            }
        };
        unWeakenTimer2 = new Timer(0,unweak2);

        pendingAL = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                isPending = false;
                pendingTimer.stop();
            }
        };
        pendingTimer = new Timer(0,pendingAL);

        baseReturner = new BFSFinder(pb);
        //start AI
        activeMove = getMoveAI();

    }
    
 

	// ---END OF CONSTRUCTOR---
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------//
    public int getGhostNormalDelay() {
		return ghostNormalDelay;
	}

	public void setGhostNormalDelay(int ghostNormalDelay) {
		this.ghostNormalDelay = ghostNormalDelay;
	}
	//load Images from Resource
    public abstract void loadImages();

    //get Move Based on AI
    public abstract moveType getMoveAI();

    //get possible Moves
    public ArrayList<moveType> getPossibleMoves(){
        ArrayList<moveType> possibleMoves = new ArrayList<>();

        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
            //System.out.println(this.toString());
            if (!(parentBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0)) {
                possibleMoves.add(moveType.RIGHT);
            }

            if (!(parentBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0)) {
                possibleMoves.add(moveType.LEFT);
            }

            if(!(parentBoard.map[logicalPosition.x][logicalPosition.y-1]>0)){
                possibleMoves.add(moveType.UP);
            }

            if(!(parentBoard.map[logicalPosition.x][logicalPosition.y+1]>0)){
                possibleMoves.add(moveType.DOWN);
            }
        }

        return possibleMoves;
    }
    
    // Gets the sprite of the ghost according to it's direction (up, down, left, right)
    public Image getGhostImage(){
        if(!isDead) {
        	if(isPending) {
        		return ghostEye;
        	}
            if (!isWeak) {
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
            } else {
                if (isWhite) {
                    return ghostWW[activeImage];
                } else {
                    return ghostW[activeImage];
                }
            }
        }else{
            return ghostEye;
        }
    }

    // Makes ghost "weak" (after player eats bomb)
    public void weaken(){
    	this.die();
    }
    public void ghostDisappear(){
        disappear = true;
        this.die();


       // this.die();
//        moveTimer.setDelay(ghostWeakDelay);
////        unweakBlinks = 0;
////        isWhite = false;
//        unWeakenTimer1.start();

//        Timer timer = new Timer();
//         timer.schedule(, 0, 5000);
//        try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}


    }
    // Makes ghost normal.
    public void unweaken(){
        isWeak = false;
        moveTimer.setDelay(ghostNormalDelay);
    }
    // Kills ghost
    public void die(){
        isDead = true;
        
        moveTimer.setDelay(ghostDeadDelay);
    }
    // Respwans ghost
    public void undie(){
    	
        //Shift Left Or Right
        int r = ThreadLocalRandom.current().nextInt(3);
        if (r == 0) {
            //Do nothing
        }
        if(r==1){
            logicalPosition.x += 1;
            pixelPosition.x += 28;
        }
        if(r==2){
            logicalPosition.x -= 1;
            pixelPosition.x -= 28;
        }
        isPending = true;
        pendingTimer.start();

        isDead = false;
        isWeak = false;
        moveTimer.setDelay(ghostNormalDelay);
    }
    

}