package controllers;

import views.*;
import java.awt.*;
import java.util.*;

import models.*;

//Finds Path Between two Maze Points Using Breadth-First Search (BFS)
//This is used to calculate how the ghosts navigate the maze
public class BFSFinder {

    int[][] map;
    int mx;
    int my;

    // Constructor
    public BFSFinder(PacBoard pb){
        this.mx = pb.getM_x();
        this.my = pb.getM_y();
        // Initializes BFS map
        // Creates a map with the same size as the real map, and assigns 1 to where there are walls, and 0 to the rest
        map = new int[pb.getM_x()][pb.getM_y()];
        for(int ii=0;ii<pb.getM_y();ii++){
            for(int jj=0;jj<pb.getM_x();jj++){
                if(pb.getMap()[jj][ii]>0 && pb.getMap()[jj][ii]<26){
                    map[jj][ii] = 1;
                }else{
                    map[jj][ii] = 0;
                }
            }
        }
    }

    // Represents a single cell on the map.
    private class MazeCell {
        int x;
        int y;

        public MazeCell(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public String toString() {
            return "x = " + x + " y = " + y;
        }
    }

    // Method for checking if a certain point on the map is valid to go to (Not a wall or outside of the map)
    private boolean isValid(int i,int j,boolean[][] markMat) {
        return (i>=0 && i<mx && j>=0 && j<my && map[i][j]==0 && !markMat[i][j]);
    }

    // Returns the next move the ghost has to make to get closer to point (tx,ty)
    // Parameters: 
    // x,y - The ghost's position
    // tx,ty - Pacman's position (where the ghost wants to go)
    public moveType getMove(int x, int y,int tx,int ty) {

        //already reached
        if(x==tx && y==ty){
            return moveType.NONE;
        }

        // mazeCellTable - a 2D table that represents all valid points (x,y) on the map. 
        // markMat - a 2D "Cross-section" of the map that contains all the cells from Pacman's position, to the ghost's position
        // markMat is used to save on space and computation time, so the ghost only has to check cells that are between him and the pacman.
        // To see how markMat and mazeCellTable look like, you can uncomment the for-loops below and run the game.
        MazeCell[][] mazeCellTable = new MazeCell[mx][my];
        Point[][] parentTable = new Point[mx][my];
        boolean[][] markMat = new boolean[mx][my];

        for (int ii = 0; ii < mx; ii++) {
            for (int jj = 0; jj < my; jj++) {
                markMat[ii][jj] = false;
            }
        }

        MazeCell[] Q = new MazeCell[2000];
        int size = 1;

        MazeCell start = new MazeCell(x, y);
        mazeCellTable[x][y] = start;
        Q[0] = start;
        markMat[x][y] = true;

        // Generate markMat and mazeCellTable
        for (int k = 0; k < size; k++) {
            int i = Q[k].x;
            int j = Q[k].y;
            //RIGHT
            if (isValid(i + 1, j, markMat)) {
                MazeCell m = new MazeCell(i + 1, j);
                mazeCellTable[i + 1][j] = m;
                Q[size] = m;
                size++;
                markMat[i + 1][j] = true;
                parentTable[i + 1][j] = new Point(i, j);
            }
            //LEFT
            if (isValid(i - 1, j, markMat)) {
                MazeCell m = new MazeCell(i - 1, j);
                mazeCellTable[i - 1][j] = m;
                Q[size] = m;
                size++;
                markMat[i - 1][j] = true;
                parentTable[i - 1][j] = new Point(i, j);
            }
            //UP
            if (isValid(i, j - 1, markMat)) {
                MazeCell m = new MazeCell(i, j - 1);
                mazeCellTable[i][j - 1] = m;
                Q[size] = m;
                size++;
                markMat[i][j - 1] = true;
                parentTable[i][j - 1] = new Point(i, j);
            }
            //DOWN
            if (isValid(i, j + 1, markMat)) {
                MazeCell m = new MazeCell(i, j + 1);
                mazeCellTable[i][j + 1] = m;
                Q[size] = m;
                size++;
                markMat[i][j + 1] = true;
                parentTable[i][j + 1] = new Point(i, j);
            }
        }
        
      // MARKMAT PRINTOUT  
      /*  
      System.out.println("====================================================================");
      for (int ii = 0; ii < x; ii++) {
          for (int jj = 0; jj < y; jj++) {
              if(markMat[ii][jj]) {
              	System.out.print(String.format("%2d",0));
              } else {
          	System.out.print(String.format("%2d",1));
              }
          }
          System.out.println("");
      }
      System.out.println("====================================================================");
      */
	/*
	System.out.println("====================================================================");
	for (int ii = 0; ii < mx; ii++) {
	    for (int jj = 0; jj < my; jj++) {
	        if(mazeCellTable[ii][jj] == null) {
	        	System.out.print(String.format("%5s",""));
	        } else {
	    	System.out.print(String.format("%5s",mazeCellTable[ii][jj].x + "," + mazeCellTable[ii][jj].y));
	        }
	    }
	    System.out.println("");
	}
	System.out.println("====================================================================");
	*/

        // This code gets the current position of the Ghost
        int ttx = tx;
        int tty = ty;
        MazeCell t = mazeCellTable[ttx][tty];
        MazeCell tl = null;
        while (t != start) {
            Point tp = parentTable[ttx][tty];
            ttx = tp.x;
            tty = tp.y;
            tl = t;
            t = mazeCellTable[ttx][tty];
        }

        // This code tells the ghost which direction to go to get closer to the Pacman
        if (x == tl.x - 1 && y == tl.y) {
            return moveType.RIGHT;
        }
        if (x == tl.x + 1 && y == tl.y) {
            return moveType.LEFT;
        }
        if (x == tl.x && y == tl.y - 1) {
            return moveType.DOWN;
        }
        if (x == tl.x && y == tl.y + 1) {
            return moveType.UP;
        }
        return moveType.NONE;
        
    }

}