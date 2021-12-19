package controllers;

import java.awt.*;
import java.util.ArrayList;

import models.*;


// Data about the map and the positions of the objects.
// All data that is read from the map text file is stored here.
public class MapData {

    private int x;
    private int y;
    private int[][] map; // Contains a copy of the map with each cell converted to a number between 0 - 26. This is used to generate images of different types of walls.
    private Point pacmanPosition;
    private Point ghostBasePosition;
    private boolean isCustom; // Is the map standard or custom
    private ArrayList<Food> foodPositions; // Contains all the pac points (food) on the map
    private ArrayList<PowerUpFood> pufoodPositions; // Contains all "powerup" foods (Bombs and fruit)
    private ArrayList<QuestionIcon> questionIconsPositions; // Contains all questions on the map
    private ArrayList<TeleportTunnel> teleports; // Contains all the passages (teleports)
    private ArrayList<GhostData> ghostsData; // Contains data for all the ghosts on the map.

    // Default Constructor
    public MapData(){
        foodPositions = new ArrayList<>();
        pufoodPositions = new ArrayList<>();
        questionIconsPositions = new ArrayList<>();
        teleports = new ArrayList<>();
        ghostsData = new ArrayList<>();
    }

    // 2nd Constructor, parameters are the width and height of the map
    public MapData(int x,int y){
        this.x = x;
        this.y = y;

        foodPositions = new ArrayList<>();
        pufoodPositions = new ArrayList<>();
        questionIconsPositions = new ArrayList<>();
        teleports = new ArrayList<>();
        ghostsData = new ArrayList<>();
    }

    // 3rd Constructor, parameters are width, height, map, and position of Pacman
    public MapData(int x, int y,int[][] map,Point pacPosition){
        this.x = x;
        this.y = y;
        this.map = map;
        pacmanPosition = pacPosition;

        foodPositions = new ArrayList<>();
        pufoodPositions = new ArrayList<>();
        questionIconsPositions = new ArrayList<>();
        teleports = new ArrayList<>();
        ghostsData = new ArrayList<>();
    }

    //=================================== GETTER SETTERS ===================================
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int[][] getMap() {
        return map;
    }

    public void setMap(int[][] map) {
        this.map = map;
    }

    public Point getPacmanPosition() {
        return pacmanPosition;
    }

    public void setPacmanPosition(Point pacmanPosition) {
        this.pacmanPosition = pacmanPosition;
    }

    public Point getGhostBasePosition() {
        return ghostBasePosition;
    }

    public void setGhostBasePosition(Point ghostBasePosition) {
        this.ghostBasePosition = ghostBasePosition;
    }

    public ArrayList<Food> getFoodPositions() {
        return foodPositions;
    }

    public ArrayList<PowerUpFood> getPufoodPositions() {
        return pufoodPositions;
    }
    
    public ArrayList<QuestionIcon> getquestionIconsPositions() {
        return questionIconsPositions;
    }

    public ArrayList<TeleportTunnel> getTeleports() {
        return teleports;
    }

    public ArrayList<GhostData> getGhostsData() {
        return ghostsData;
    }

    public boolean isCustom() {
        return isCustom;
    }

    public void setCustom(boolean custom) {
        isCustom = custom;
    }
}
