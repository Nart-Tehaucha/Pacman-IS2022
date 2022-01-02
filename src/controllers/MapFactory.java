package controllers;

import java.awt.Point;
import java.util.concurrent.ThreadLocalRandom;

import models.Food;
import models.GhostData;
import models.MapData;
import models.PowerUpFood;
import models.QuestionIcon;
import models.ghostType;

// This class reads a map from a text file and converts it to a real map containing objects
// Returns a MapData object containing all the objects on the map (Pacman, Ghosts, Food, etc.)
public class MapFactory {

	//Reads the text file from the path "input"
    public static MapData compileMap(String input){
    	// Get dimensions of the map
        int mx = input.indexOf('\n');
        int my = StringHelper.countLines(input);

        MapData customMap = new MapData(mx,my);
        customMap.setCustom(true);
        int[][] map = new int[mx][my]; // Contains a copy of the map with each cell converted to a number between 0 - 26. This is used to generate images of different types of walls.

        // Goes through the text file, and checks every "cell" on the map.
        // Each cell contains a number, representing a certain object.
        // For each type of cell, create an object corresponding to it if needed
        // Update the map[][] array with a number corresponding to the cell type
        int i=0;
        int j=0;
        for(char c : input.toCharArray()){
        	// 1 - Red Ghost
            if(c == '1'){
                map[i][j] = 0;
                customMap.getGhostsData().add(new GhostData(i,j,ghostType.RED));
                customMap.getFoodPositions().add(new Food(i,j));
            }
            // 2 - Pink Ghost
            if(c == '2'){
                map[i][j] = 0;
                customMap.getGhostsData().add(new GhostData(i,j,ghostType.PINK));
                customMap.getFoodPositions().add(new Food(i,j));
            }
            // 3 - Cyan Ghost
            if(c == '3'){
                map[i][j] = 0;
                customMap.getGhostsData().add(new GhostData(i,j,ghostType.CYAN));
                customMap.getFoodPositions().add(new Food(i,j));
            }
            // P - Pacman
            if(c == 'P'){
                map[i][j] = 0;
                customMap.setPacmanPosition(new Point(i,j));
            }
            // X - Wall
            if(c == 'X'){
                map[i][j] = 23;
            }
            // Y - Door of the Ghost base
            if(c == 'Y'){
                map[i][j] = 26;
            }
            // _ - Pac point
            if(c == '_'){
                map[i][j] = 0;
                customMap.getFoodPositions().add(new Food(i,j));
            }
            // = - Empty space
            if(c == '='){
                map[i][j] = 0;
            }
            // O - Bomb
            if(c == 'O'){
                map[i][j] = 0;
                customMap.getPufoodPositions().add(new PowerUpFood(i,j,0));
            }
            // F - Fruit
            if(c == 'F'){
                map[i][j] = 0;
                customMap.getPufoodPositions().add(new PowerUpFood(i,j, ThreadLocalRandom.current().nextInt(4)+1));
            }
            // B - Center of the Ghost base
            if(c == 'B'){
                map[i][j] = 0;
                customMap.setGhostBasePosition(new Point(i,j));
            }
            
            i++;
            if(c == '\n'){
                j++;
                i=0;
            }
        }
        
        // return the generate MapData
        customMap.setMap(map);
        customMap.setCustom(true);
        return customMap;
    }

}
