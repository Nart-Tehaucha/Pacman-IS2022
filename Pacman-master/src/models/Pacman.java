package models;

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

// Class for handling Pacman. 
public class Pacman implements KeyListener{
	private int gameSpeed; // variable that decides the movement speed of Pacman.
   
	private Timer moveTimer; // Timer for the movement of Pacman
    private ActionListener moveAL;
    private moveType activeMove; // Current active move
    private  moveType todoMove; // Next move to do when available
    private boolean isStuck = true;
    private boolean isInLocation = false; // Checks if the pacman is in radius 3 of a ghost. Used to calculate killing ghosts with bomb
    private boolean isEnterPressed =false; // Checks if enter has been pressed (to explode bomb)
    
	private boolean isStrong = false; // Checks if pacman is holding a bomb

    //Animation Vars
	private Timer animTimer; // Timer for the animation of Pacman
	private ActionListener animAL;
	private Image[] pac; // Images for normal pacman
	private Image[] pacStrong; // Images for when pacman is holding a bomb
	private int activeImage = 0;
	private int addFactor = 1;
	private Point pixelPosition; // The actual position of Pacman
	private Point logicalPosition; // The graphical position of Pacman (28 * pixelPosition)
	private PacBoard parentBoard;

    // Constructor
//-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public Pacman (int x, int y,PacBoard pb) {

        logicalPosition = new Point(x,y);
        pixelPosition = new Point(28*x,28*y);

        parentBoard = pb;

        pac = new Image[5];
        pacStrong = new Image[5];

        activeMove = moveType.NONE;
        todoMove = moveType.NONE;
        gameSpeed = 2;

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
        try {
            pacStrong[0] = ImageIO.read(this.getClass().getResource("/resources/images/pac/pacStrong0.png"));
            pacStrong[1] = ImageIO.read(this.getClass().getResource("/resources/images/pac/pacStrong1.png"));
            pacStrong[2] = ImageIO.read(this.getClass().getResource("/resources/images/pac/pacStrong2.png"));
            pacStrong[3] = ImageIO.read(this.getClass().getResource("/resources/images/pac/pacStrong3.png"));
            pacStrong[4] = ImageIO.read(this.getClass().getResource("/resources/images/pac/pacStrong4.png"));
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
        animTimer = new Timer(40 / gameSpeed,animAL);
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
                        if((pixelPosition.x >= (parentBoard.getM_x()-1) * 28)&&parentBoard.isCustom()){
                            return;
                        }
                        /*if((logicalPosition.x+1 < parentBoard.getM_x()) && (parentBoard.map[logicalPosition.x+1][logicalPosition.y]>0)){
                            return;
                        }*/
                        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.getM_x()-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.getM_y()-1 ) {
                            if (parentBoard.getMap()[logicalPosition.x + 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x += gameSpeed;
                        break;
                    case LEFT:
                        if((pixelPosition.x <= 0)&&parentBoard.isCustom()){
                            return;
                        }
                        /*if((logicalPosition.x-1 >= 0) && (parentBoard.map[logicalPosition.x-1][logicalPosition.y]>0)){
                            return;
                        }*/
                        if(logicalPosition.x > 0 && logicalPosition.x < parentBoard.getM_x()-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.getM_y()-1 ) {
                            if (parentBoard.getMap()[logicalPosition.x - 1][logicalPosition.y] > 0) {
                                return;
                            }
                        }
                        pixelPosition.x -= gameSpeed;
                        break;
                    case UP:
                        if((pixelPosition.y <= 0)&&parentBoard.isCustom()){
                            return;
                        }
                        /*if((logicalPosition.y-1 >= 0) && (parentBoard.map[logicalPosition.x][logicalPosition.y-1]>0)){
                            return;
                        }*/
                        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.getM_x()-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.getM_y()-1 ) {
                            if(parentBoard.getMap()[logicalPosition.x][logicalPosition.y-1]>0){
                                return;
                            }
                        }
                        pixelPosition.y -= gameSpeed;
                        break;
                    case DOWN:
                    	
                        if((pixelPosition.y >= (parentBoard.getM_y()-1) * 28)&&parentBoard.isCustom()){
                            return;
                        }
                        /*if((logicalPosition.y+1 < parentBoard.getM_y()) && (parentBoard.map[logicalPosition.x][logicalPosition.y+1]>0)){
                            return;
                        }*/
                        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.getM_x()-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.getM_y()-1 ) {
                            if(parentBoard.getMap()[logicalPosition.x][logicalPosition.y+1]>0){
                                return;
                            }
                        }
                        pixelPosition.y += gameSpeed;
                        break;
                }

                //send Messege to PacBoard to check collision
                parentBoard.dispatchEvent(new ActionEvent(this,Messages.COLTEST,null));

            }
        };
        moveTimer = new Timer(9,moveAL);
        moveTimer.start();
    }      

	//--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    public Timer getMoveTimer() {
		return moveTimer;
	}
	public void setMoveTimer(Timer moveTimer) {
		this.moveTimer = moveTimer;
	}   
    public boolean changeColor(){
    	isStrong =true; 
        return isStrong;
    }  
    // Check if a move is possible
    public boolean isPossibleMove(moveType move){
        if(logicalPosition.x >= 0 && logicalPosition.x < parentBoard.getM_x()-1 && logicalPosition.y >= 0 && logicalPosition.y < parentBoard.getM_y()-1 ) {
            switch(move){
                case RIGHT:
                    return !(parentBoard.getMap()[logicalPosition.x + 1][logicalPosition.y] > 0);
                case LEFT:
                    return !(parentBoard.getMap()[logicalPosition.x - 1][logicalPosition.y] > 0);
                case UP:
                    return !(parentBoard.getMap()[logicalPosition.x][logicalPosition.y - 1] > 0);
                case DOWN:
                    return !(parentBoard.getMap()[logicalPosition.x][logicalPosition.y+1] > 0);
            }
        }
        return false;
    }

    public Image getPacmanImage(){
    	if(!isStrong) {
        return pac[activeImage];
    	}
    	else {
    		 return pacStrong[activeImage];
    		// newColor.setDelay();
    	}
    }

    @Override
    public void keyReleased(KeyEvent ke){
        //
    }

    @Override
    public void keyTyped(KeyEvent ke){
        //
    }

    //Handle Keyboard presses
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
            case KeyEvent.VK_ENTER:            	
            	isEnterPressed =true;    	
                break;
        }
     
    }
	public int getGameSpeed() {
		return gameSpeed;
	}
	public void setGameSpeed(int gameSpeed) {
		this.gameSpeed = gameSpeed;
	}
	public void setGameSpeedForLevel2(int newGameSpeed, int level) {
		if(level == 2) {
		this.gameSpeed = newGameSpeed;
		}
		else 
			this.gameSpeed =4;
	}

	
	public void setNewPosition(int x, int y) {
        logicalPosition = new Point(x,y);
        pixelPosition = new Point(28*x,28*y);
	}
	
	//=================================== GETTER SETTERS ===================================

   public boolean isEnterPressed() {
		return isEnterPressed;
	}
	public void setEnterPreesed(boolean isEnterPreesed) {
		this.isEnterPressed = isEnterPreesed;
	}
	public boolean isInLocation() {
		return isInLocation;
	}
	public void setInLocation(boolean isInLocation) {
		this.isInLocation = isInLocation;
	}
	public boolean getIsStrong() {
		return isStrong;
	}
	public void setStrong(boolean isStrong) {
		this.isStrong = isStrong;
	}

	public ActionListener getMoveAL() {
		return moveAL;
	}

	public void setMoveAL(ActionListener moveAL) {
		this.moveAL = moveAL;
	}

	public moveType getActiveMove() {
		return activeMove;
	}

	public void setActiveMove(moveType activeMove) {
		this.activeMove = activeMove;
	}

	public moveType getTodoMove() {
		return todoMove;
	}

	public void setTodoMove(moveType todoMove) {
		this.todoMove = todoMove;
	}

	public boolean isStuck() {
		return isStuck;
	}

	public void setStuck(boolean isStuck) {
		this.isStuck = isStuck;
	}

	public Timer getAnimTimer() {
		return animTimer;
	}

	public void setAnimTimer(Timer animTimer) {
		this.animTimer = animTimer;
	}

	public ActionListener getAnimAL() {
		return animAL;
	}

	public void setAnimAL(ActionListener animAL) {
		this.animAL = animAL;
	}

	public Image[] getPac() {
		return pac;
	}

	public void setPac(Image[] pac) {
		this.pac = pac;
	}

	public Image[] getPacStrong() {
		return pacStrong;
	}

	public void setPacStrong(Image[] pacStrong) {
		this.pacStrong = pacStrong;
	}

	public int getActiveImage() {
		return activeImage;
	}

	public void setActiveImage(int activeImage) {
		this.activeImage = activeImage;
	}

	public int getAddFactor() {
		return addFactor;
	}

	public void setAddFactor(int addFactor) {
		this.addFactor = addFactor;
	}

	public Point getPixelPosition() {
		return pixelPosition;
	}

	public void setPixelPosition(Point pixelPosition) {
		this.pixelPosition = pixelPosition;
	}

	public Point getLogicalPosition() {
		return logicalPosition;
	}

	public void setLogicalPosition(Point logicalPosition) {
		this.logicalPosition = logicalPosition;
	}

	public PacBoard getParentBoard() {
		return parentBoard;
	}

	public void setParentBoard(PacBoard parentBoard) {
		this.parentBoard = parentBoard;
	}

	public void setEnterPressed(boolean isEnterPressed) {
		this.isEnterPressed = isEnterPressed;
	}
}
