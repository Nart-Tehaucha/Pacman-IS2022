package views;

import controllers.*;
import models.*;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Scanner;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

// Main window of the game screen.
public class PacWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private PacBoard pb;
	private String username;
	
	
	// ============================== Constructors =============================
	
	// Default Constructor. Initializes the game screen.
    public PacWindow(String username){
        
    	//Assign user name field
    	this.username = username;
        setTitle("IS 2022 Pacman Game"); // Title of the game
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Setup the game window and scoreboard
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.black);

        //setSize(794,918);
        setSize(794,646);
        setLocationRelativeTo(null);
        
        JPanel topBar = new JPanel();
        topBar.setBackground(Color.black);
        
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Color.black);
        
        JLabel scoreboard = new JLabel("    Score : 0");
        scoreboard.setForeground(new Color(255, 243, 36));
        
        JLabel level = new JLabel("    Level : 1");
        level.setForeground(new Color(255, 243, 36));
        
        JLabel lbLives = new JLabel("    Lives : 3");
        lbLives.setForeground(new Color(255, 243, 36));
        
        JLabel lbUsername = new JLabel("Hello, " + username + "!");
        lbUsername.setForeground(new Color(255, 243, 36));

        // Load the default map layout
        MapData map1 = getMapFromResource("/resources/maps/map1_c.txt");
        adjustMap(map1);

        // Create a new game object.
        pb = new PacBoard(scoreboard,1,0,3,map1,this);

        pb.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new LineBorder(Color.BLUE)));
        addKeyListener(pb.getPacman());

        this.getContentPane().add(bottomBar,BorderLayout.SOUTH);
        this.getContentPane().add(topBar,BorderLayout.NORTH);
        bottomBar.add(scoreboard);
        bottomBar.add(level);
        bottomBar.add(lbLives);
        topBar.add(lbUsername);
        this.getContentPane().add(pb);
        
        setVisible(true);
        
        // Event listner for when the player tries to close the game

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            	pb.pause();
            	int result = JOptionPane.showConfirmDialog(null,
                  "All your progress will be lost!\nAre you sure you want to Exit ?", "Before You Exit: ",
                  JOptionPane.YES_NO_OPTION);
            	if (result == JOptionPane.YES_OPTION)
            		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	else if (result == JOptionPane.NO_OPTION) {
            		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            		pb.resume();
            	}
            }
        });
    }
    
    // Second constructor, gets MapData as an argument
    public PacWindow(int level, int score, int pacLives, String userName){
    	this.username = userName;
        setTitle("IS 2022 getPacman() Game"); // Title
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Setup the game window and lbScore
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.black);

        //setSize(794,884);
        //setSize(794,918);--upd
        setSize(794,646);
        setLocationRelativeTo(null);
        
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Color.black);
        
        JPanel topBar = new JPanel();
        topBar.setBackground(Color.black);

        JLabel lbUsername = new JLabel("Hello, " + username + "!");
        lbUsername.setForeground(new Color(255, 243, 36));

        topBar.add(lbUsername);
        if(score > 200) score = 200;
        JLabel lbScore = new JLabel("    Score : " + score);
        lbScore.setForeground(new Color(255, 243, 36));
        
        JLabel lbLevel = new JLabel();
        lbLevel.setForeground(new Color(255, 243, 36));
        
        JLabel lbLives = new JLabel("    Lives : " + pacLives);
        lbLives.setForeground(new Color(255, 243, 36));
        MapData map;
        switch(level) {
        case 1: 
        	map = getMapFromResource("/resources/maps/map1_c.txt");
        	lbLevel.setText("    Level : 1");
        	lbScore.setText("    Score : 0");
        	break;
        case 2: 
        	map = getMapFromResource("/resources/maps/map2_c.txt");
        	map.getTeleports().add(new TeleportTunnel(1,14,25,14,moveType.LEFT));
            map.getTeleports().add(new TeleportTunnel(25,14,1,14,moveType.RIGHT));
        	lbLevel.setText("    Level : 2");
        	break;
        case 3: 
        	map = getMapFromResource("/resources/maps/map3_c.txt");
        	lbLevel.setText("    Level : 3");
        	break;
        case 4: 
        	map = getMapFromResource("/resources/maps/map4_c.txt");
        	lbLevel.setText("    Level : 4");
        	break;
        default:
        	map = getMapFromResource("/resources/maps/map1_c.txt");
        	lbLevel.setText("    Level : 1");
        	lbScore.setText("    Score : 0");
        }
        
        adjustMap(map);
        
        // Load the custom map layout
    
        PacBoard pb = new PacBoard(lbScore,level, score, pacLives,map,this);
        pb.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new LineBorder(Color.BLUE)));
        addKeyListener(pb.getPacman());
        
     
        
        this.getContentPane().add(bottomBar,BorderLayout.SOUTH);
        this.getContentPane().add(topBar,BorderLayout.NORTH);
        bottomBar.add(lbScore);
        bottomBar.add(lbLevel);
        bottomBar.add(lbLives);
        this.getContentPane().add(pb);
        setVisible(true);
        
        // Event listner for when the player tries to close the game
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            	pb.pause();
            	int result = JOptionPane.showConfirmDialog(null,
                  "All your progress will be lost!\nAre you sure you want to Exit ?", "Before You Exit: ",
                  JOptionPane.YES_NO_OPTION);
            	if (result == JOptionPane.YES_OPTION)
            		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	else if (result == JOptionPane.NO_OPTION) {
            		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            		pb.resume();
            	}
            }
        });
        

        
        
    }


    	public void windowAfterWinning() {
//        	pb.pause();
//        	int result = JOptionPane.showConfirmDialog(null,
//              "You won!\n	Would you like to play again ?", "You won! ",
//              JOptionPane.YES_NO_OPTION);
//        	if (result == JOptionPane.YES_OPTION)
//        		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        	else if (result == JOptionPane.NO_OPTION) {
//        		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
//        		pb.resume();
//        }
    		


//            }
        }
        
    // Third constructor, gets MapData as an argument
    public PacWindow(MapData md){
        setTitle("IS 2022 getPacman() Game"); // Title
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        
        // Setup the game window and scoreboard
        getContentPane().setLayout(new BorderLayout());
        getContentPane().setBackground(Color.black);

        //setSize(794,884);
        //setSize(794,918);
        setSize(794,646);
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
              int result = JOptionPane.showConfirmDialog(null,
                  "All your progress will be lost!\nAre you sure you want to Exit ?", "Before You Exit: ",
                  JOptionPane.YES_NO_OPTION);
              if (result == JOptionPane.YES_OPTION)
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              else if (result == JOptionPane.NO_OPTION)
                setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            }
          });
        
        JPanel bottomBar = new JPanel();
        bottomBar.setBackground(Color.black);
        
        JLabel scoreboard = new JLabel("    Score : 0");
        scoreboard.setForeground(new Color(255, 243, 36));
        
        JLabel level = new JLabel("    Level : 1");
        level.setForeground(new Color(255, 243, 36));
        
        JLabel lbLives = new JLabel("    Lives : 3");
        lbLives.setForeground(new Color(255, 243, 36));

        //int[][] mapLoaded = loadMap(27,29,"/maps/map1.txt");
        
        // Load the custom map layout
        adjustMap(md);
        PacBoard pb = new PacBoard(scoreboard,1,0,3,md,this);
        pb.setBorder(new CompoundBorder(new EmptyBorder(10,10,10,10),new LineBorder(Color.BLUE)));
        addKeyListener(pb.getPacman());

        this.getContentPane().add(bottomBar,BorderLayout.SOUTH);
        bottomBar.add(scoreboard);
        bottomBar.add(level);
        bottomBar.add(lbLives);
        this.getContentPane().add(pb);
        setVisible(true);
        
        // Event listner for when the player tries to close the game
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
            	pb.pause();
            	int result = JOptionPane.showConfirmDialog(null,
                  "All your progress will be lost!\nAre you sure you want to Exit ?", "Before You Exit: ",
                  JOptionPane.YES_NO_OPTION);
            	if (result == JOptionPane.YES_OPTION)
            		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            	else if (result == JOptionPane.NO_OPTION) {
            		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            		pb.resume();
            	}
            }
        });
    }

    
    // ============================== Methods =============================
    
    // Compiles a map from reading a text file, returns it as an int 2D array
    public int[][] loadMap(int mx,int my,String relPath){
        try {
            Scanner scn = new Scanner(this.getClass().getResourceAsStream(relPath));
            int[][] map;
            map = new int[mx][my];
            for(int y=0;y<my;y++){
                for(int x=0;x<mx;x++){
                    map[x][y]=scn.nextInt();
                }
            }
            return map;
        }catch(Exception e){
            System.err.println("Error Reading Map File !");
        }
        return null;
    }
    

    // Compiles a map from reading a text file, returns it as a MapData object
    public MapData getMapFromResource(String relPath){
        String mapStr = "";
        try {
            Scanner scn = new Scanner(this.getClass().getResourceAsStream(relPath));
            StringBuilder sb = new StringBuilder();
            String line;
            while(scn.hasNextLine()){
                line = scn.nextLine();
                sb.append(line).append('\n');
            }
            mapStr = sb.toString();
        }catch(Exception e){
            System.err.println("Error Reading Map File !");
        }
        if("".equals(mapStr)){
            System.err.println("Map is Empty !");
        }
        return MapFactory.compileMap(mapStr);
    }

    //Classifies all the walls on the map and gives each one a number representing it's type
    // For example:
    // Horizontal wall = 20
    // Vertical wall = 24
    // Corner walls = 10, 11, 12, 13 (depending on which corner)
    // This is done for the purpose of loading a different image for each type of wall.
    public void adjustMap(MapData mapd){
        int[][] map = mapd.getMap();
        int mx=mapd.getX();
        int my=mapd.getY();
        for(int y=0;y<my;y++){
            for(int x=0;x<mx;x++){
                boolean l = false;
                boolean r = false;
                boolean t = false;
                boolean b = false;
                boolean tl = false;
                boolean tr = false;
                boolean bl = false;
                boolean br = false;
                

                if(map[x][y]>0 && map[x][y]<26) {
                    int mustSet = 0;
                    //LEFT
                    if (x > 0 && map[x - 1][y] > 0 && map[x-1][y]<26) {
                        l = true;
                    }
                    //RIGHT
                    if (x < mx - 1 && map[x + 1][y] > 0 && map[x+1][y]<26) {
                        r = true;
                    }
                    //TOP
                    if (y > 0 && map[x][y - 1] > 0 && map[x][y-1]<26) {
                        t = true;
                    }
                    //Bottom
                    if (y < my - 1 && map[x][y + 1] > 0 && map[x][y+1]<26) {
                        b = true;
                    }
                    //TOP LEFT
                    if (x > 0 && y > 0 && map[x - 1][y - 1] > 0 && map[x-1][y-1]<26) {
                        tl = true;
                    }
                    //TOP RIGHT
                    if (x < mx - 1 && y > 0 && map[x + 1][y - 1] > 0 && map[x+1][y-1]<26) {
                        tr = true;
                    }
                    //Bottom LEFT
                    if (x > 0 && y < my - 1 && map[x - 1][y + 1] > 0 && map[x-1][y+1]<26) {
                        bl = true;
                    }
                    //Bottom RIGHT
                    if (x < mx - 1 && y < my - 1 && map[x + 1][y + 1] > 0 && map[x+1][y+1]<26) {
                        br = true;
                    }

                    //Decide Image to View
                    if (!r && !l && !t && !b) {
                        mustSet = 23;
                    }
                    if (r && !l && !t && !b) {
                        mustSet = 22;
                    }
                    if (!r && l && !t && !b) {
                        mustSet = 25;
                    }
                    if (!r && !l && t && !b) {
                        mustSet = 21;
                    }
                    if (!r && !l && !t && b) {
                        mustSet = 19;
                    }
                    if (r && l && !t && !b) {
                        mustSet = 24;
                    }
                    if (!r && !l && t && b) {
                        mustSet = 20;
                    }
                    if (r && !l && t && !b && !tr) {
                        mustSet = 11;
                    }
                    if (r && !l && t && !b && tr) {
                        mustSet = 2;
                    }
                    if (!r && l && t && !b && !tl) {
                        mustSet = 12;
                    }
                    if (!r && l && t && !b && tl) {
                        mustSet = 3;
                    }
                    if (r && !l && !t && b && br) {
                        mustSet = 1;
                    }
                    if (r && !l && !t && b && !br) {
                        mustSet = 10;
                    }
                    if (!r && l && !t && b && bl) {
                        mustSet = 4;
                    }
                    if (r && !l && t && b && !tr) {
                        mustSet = 15;
                    }
                    if (r && !l && t && b && tr) {
                        mustSet = 6;
                    }
                    if (!r && l && t && b && !tl) {
                        mustSet = 17;
                    }
                    if (!r && l && t && b && tl) {
                        mustSet = 8;
                    }
                    if (r && l && !t && b && !br) {
                        mustSet = 14;
                    }
                    if (r && l && !t && b && br) {
                        mustSet = 5;
                    }
                    if (r && l && t && !b && !tr) {
                        mustSet = 16;
                    }
                    if (r && l && t && !b && tr) {
                        mustSet = 7;
                    }
                    if (!r && l && !t && b && !bl) {
                        mustSet = 13;
                    }
                    if (r && l && t && b && br && tl) {
                        mustSet = 9;
                    }
                    if (r && l && t && b && !br && !tl) {
                        mustSet = 18;
                    }

                    map[x][y] = mustSet;
                }
                
            }
        }
        mapd.setMap(map);
    }

    public PacBoard getPacBoard() {
    	return this.pb;
    }

	public PacBoard getPb() {
		return pb;
	}

	public void setPb(PacBoard pb) {
		this.pb = pb;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
    
	


}