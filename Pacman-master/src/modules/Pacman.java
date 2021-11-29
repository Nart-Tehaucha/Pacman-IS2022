package modules;

import controllers.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.util.ArrayList;

// Class for handling the Pacman. 
public class Pacman implements KeyListener{
	static final int GAMESPEED = 2; // Static variable that decides the movement speed of Pacman.
    //Move Vars
    public Timer moveTimer;
    public ActionListener moveAL;
    public moveType activeMove;
    public moveType todoMove;
    boolean isStuck = true;

    //Animation Vars
    public Timer animTimer;
    public ActionListener animAL;
    public Image[] pac;
    public int activeImage = 0;
    public int addFactor = 1;

    public Point pixelPosition;
    public Point logicalPosition;

    public PacBoard parentBoard;

    // Constructor
    public Pacman (int x, int y,PacBoard pb) {

        logicalPosition = new Point(x,y);
        pixelPosition = new Point(28*x,28*y);

        parentBoard = pb;

        pac = new Image[5];

        activeMove = moveType.NONE;
        todoMove = moveType.NONE;

        // Load Pacman's sprites
        try {
            pac[0] = ImageIO.read(this.getClass().getResource("/resources/images/pac/pac0.png"));
            pac[1] = ImageIO.read(this.getClass().getResource("/resources/images/pac/pac1.png"));
            pac[2] = ImageIO.read(this.getClass().getResource("/resources/images/pac/pac2.png"));
            pac[3] = ImageIO.read(this.getClass().getResource("/resources/images/pac/pac3.png"));
            pac[4] = ImageIO.read(this.getClass().getResource("/resources/images/pac/pac4.png"));
        }catch(IOException e){
            System.err.println("Cannot Read Images !");
        }

        //animation timer
        animAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                activeImage = activeImage + addFactor;
                if(activeImage==4 || activeImage==0){
                    addFactor *= -1;
                }
            }
        };
        animTimer = new Timer(40 / GAMESPEED,animAL);
        animTimer.start();

        // Handles the movement of the Pacman around the map.
        moveAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {

                //update logical position
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
                        //send update message
                        parentBoard.dispatchEvent(new ActionEvent(this,Messages.UPDATE,null));
                        //System.out.println(logicalPosition);
                    }
                    isStuck = true;
                    animTimer.stop();

                    if(todoMove != moveType.NONE && isPossibleMove(todoMove) ) {
                        activeMove = todoMove;
                        todoMove = moveType.NONE;
                    }
                }else{
                    isStuck = false;
                    animTimer.start();
                }

                switch(activeMove){
                    case RIGHT:
                        if((pixelPosition.x >= (parentBoard.m_x-1) * 28)&&parentBoard.isCustom){
                            return;
                        }
                        /*if((logicalPosition.x+1 < parentBoard.m_x) && (parentBoard.map[logicalPosition.x+1][logicalPosition.y]>0)){
                            return;
                        }*/
                        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
                            if (parentBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x += GAMESPEED;
                        break;
                    case LEFT:
                        if((pixelPosition.x <= 0)&&parentBoard.isCustom){
                            return;
                        }
                        /*if((logicalPosition.x-1 >= 0) && (parentBoard.map[logicalPosition.x-1][logicalPosition.y]>0)){
                            return;
                        }*/
                        if(logicalPosition.x > 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
                            if (parentBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x -= GAMESPEED;
                        break;
                    case UP:
                        if((pixelPosition.y <= 0)&&parentBoard.isCustom){
                            return;
                        }
                        /*if((logicalPosition.y-1 >= 0) && (parentBoard.map[logicalPosition.x][logicalPosition.y-1]>0)){
                            return;
                        }*/
                        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
                            if(parentBoard.map[logicalPosition.x][logicalPosition.y-1]>0){
                                return;
                            }
                        }
                        pixelPosition.y -= GAMESPEED;
                        break;
                    case DOWN:
                        if((pixelPosition.y >= (parentBoard.m_y-1) * 28)&&parentBoard.isCustom){
                            return;
                        }
                        /*if((logicalPosition.y+1 < parentBoard.m_y) && (parentBoard.map[logicalPosition.x][logicalPosition.y+1]>0)){
                            return;
                        }*/
                        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
                            if(parentBoard.map[logicalPosition.x][logicalPosition.y+1]>0){
                                return;
                            }
                        }
                        pixelPosition.y += GAMESPEED;
                        break;
                }

                //send Messege to PacBoard to check collision
                parentBoard.dispatchEvent(new ActionEvent(this,Messages.COLTEST,null));

            }
        };
        moveTimer = new Timer(9,moveAL);
        moveTimer.start();

    }
    // Check if a move is possible
    public boolean isPossibleMove(moveType move){
        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.m_x-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.m_y-1 ) {
            switch(move){
                case RIGHT:
                    return !(parentBoard.map[logicalPosition.x + 1][logicalPosition.y] > 0);
                case LEFT:
                    return !(parentBoard.map[logicalPosition.x - 1][logicalPosition.y] > 0);
                case UP:
                    return !(parentBoard.map[logicalPosition.x][logicalPosition.y - 1] > 0);
                case DOWN:
                    return !(parentBoard.map[logicalPosition.x][logicalPosition.y+1] > 0);
            }
        }
        return false;
    }

    public Image getPacmanImage(){
        return pac[activeImage];
    }

    @Override
    public void keyReleased(KeyEvent ke){
        //
    }

    @Override
    public void keyTyped(KeyEvent ke){
        //
    }

    //Handle Arrow Keys
    @Override
    public void keyPressed(KeyEvent ke){
        switch(ke.getKeyCode()){
            case 37:
                todoMove = moveType.LEFT;
                break;
            case 38:
                todoMove = moveType.UP;
                break;
            case 39:
                todoMove = moveType.RIGHT;
                break;
            case 40:
                todoMove = moveType.DOWN;
                break;
            case 82:
                parentBoard.dispatchEvent(new ActionEvent(this,Messages.RESET,null));
                break;
        }
        //System.out.println(ke.getKeyCode());
    }


}
