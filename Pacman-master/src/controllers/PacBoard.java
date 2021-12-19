package controllers;


import views.*;
import javax.imageio.ImageIO;
import javax.swing.*;


import models.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

// This is the main class for running the game. It handles all the logic of the player, ghosts, score, and time.
public class PacBoard extends JPanel{

	private boolean mflag = false; // This flag is used to stop the current game from updating after it ends. (for restarts and going up a level)
	
	
    private Timer redrawTimer; // Timer for redrawing all graphical elements on the map
	private ActionListener redrawAL; // AL for the redrawTimer

    private int[][] map; // Contains a copy of the map with each cell converted to a number between 0 - 26. This is used to generate images of different types of walls.
    
    // Images for all the different walls
    private Image[] blue_mapSegments; 
    private Image[] pink_mapSegments;
    private Image[] babyBlue_mapSegments;
    private Image[] green_mapSegments;
    
    // Images for food
    private Image foodImage;
    private Image[] pfoodImage;
    private Image[] questionIconImage;

    // Game over and Victory images
    private Image goImage;
    private Image vicImage;

    private Pacman pacman;
    private ArrayList<Food> foods; // Regular foods (pac points)
    private ArrayList<PowerUpFood> pufoods; // Power Up foods (bombs, special fruit)
    private ArrayList<Ghost> ghosts; // All the ghosts that are currently alive
    private ArrayList<TeleportTunnel> teleports; // Teleports = Passages
    private ArrayList<QuestionIcon> questionIcons;// Icons on the map representing questions
    private ArrayList<Question> questions; // Questions
    private HashMap<QuestionIcon, Question> questionPoints; //Pairs of Questions and their QuestionIcon on the map
    private ArrayList<Timer> foodRespawnTimers; // Contains timers for every eaten pacpoint. Each timer activates after 30 seconds and respawns the pacpoint

    private boolean isCustom = false; // Is it a custom made map?
    private boolean isGameOver = false;
    private boolean isWin = false;
    private boolean drawScore = false; // Used to signal to the program when to draw score (score that shows up below the pacman after eating Fruit)
    private boolean drawQuestionScore = false; // Used to signal to the program when to draw score (score that shows up below the pacman after answering questions)
    private boolean clearScore = false; // Resets the score that is to be given to the player
    private int scoreToAdd = 0; // Score to be given to the player
    private int pacLives; // Number of lives left

    private int score; // Current score
    private int level; 
    private int scoreToNextLevel; // Score needed to reach the next level
    private JLabel scoreboard; //JLabel for the score 
    
    // Sounds that play throughout the game *Disabled for Iteration 3*
    private LoopPlayer siren;
    private boolean mustReactivateSiren = false;
    private LoopPlayer pac6;

    private Point ghostBase; // The base of the ghosts

    // Dimensions of the map
    private int m_x; 
    private int m_y;

    private MapData md_backup; // MapData containing all initial info of the map
    private PacWindow windowParent; // Parent window that contains the game
    
    private String username;
	private ArrayList<Ghost> ghostsToRemove; // Used to signal to the program which ghosts to remove (die)
    
   
    

    // Constructor
    public PacBoard(JLabel scoreboard, int level, int score, int pacLives, MapData md, PacWindow pw){
    	
    	SysData.initializeTopTen(); 
    	this.username = pw.getUsername();
        this.level = level;
        this.score = score;
        this.pacLives = pacLives;
    	this.scoreboard = scoreboard;
    	
        this.setDoubleBuffered(true);
        md_backup = md;
        windowParent = pw;

        m_x = md.getX();
        m_y = md.getY();
        this.map = md.getMap();

        this.isCustom = md.isCustom();
        this.ghostBase = md.getGhostBasePosition();
        pacman = new Pacman(md.getPacmanPosition().x,md.getPacmanPosition().y,this);
        addKeyListener(pacman);
        foods = new ArrayList<>(); // Regular foods (pac points)
        pufoods = new ArrayList<>(); // Power Up foods (bombs, special fruit)
        ghosts = new ArrayList<>();
        teleports = new ArrayList<>(); // Teleports = Passages
        questionIcons = new ArrayList<>(); //Objects on the map representing questions that can be eaten
        questionPoints = new HashMap<>(); // Pairs every Question with a QuestionIcon that's on the map
        foodRespawnTimers = new ArrayList<>(); // Contains timers for every eaten pacpoint. Each timer activates after 30 seconds and respawns the pacpoint
        ghostsToRemove = new ArrayList<>(); // Contains all ghosts that will be die
        
        // Load questions from JSON file to arraylist
        try {
			questions = SysData.readQuestionsJSON();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
        
        // Add all pac points on the map
        if(!isCustom) {
            for (int i = 0; i < m_x; i++) {
                for (int j = 0; j < m_y; j++) {
                    if (map[i][j] == 0)
                        foods.add(new Food(i, j));
                }
            }
        }else{
            foods = md.getFoodPositions();
        }

        pufoods = md.getPufoodPositions();
        questionIcons = md.getquestionIconsPositions();

        // Add all ghosts
        ghosts = new ArrayList<>();
        for(GhostData gd : md.getGhostsData()){
            switch(gd.getType()) {
                case RED:
                    ghosts.add(new RedGhost(gd.getX(), gd.getY(), this));
                    break;
                case PINK:
                    ghosts.add(new PinkGhost(gd.getX(), gd.getY(), this));
                    break;
                case CYAN:
                    ghosts.add(new CyanGhost(gd.getX(), gd.getY(), this));
                    break;
            }
        }
        // Add Teleports
        teleports.add(new TeleportTunnel(1, 9, 25, 9, moveType.LEFT));
        teleports.add(new TeleportTunnel(25, 9, 1, 9, moveType.RIGHT));
        // Add all timers to an arraylist in SysData.
        // This is done so that all timers can be stopped when the current game ends (to threading problems)
        SysData.allTimers.add(pacman.getAnimTimer());
        SysData.allTimers.add(pacman.getMoveTimer());
        for(Ghost g : ghosts) {
        	SysData.allTimers.add(g.animTimer);
        	SysData.allTimers.add(g.moveTimer);
        }
        
        // Set layout of the map (size, background color)
        setLayout(null);
        setSize(20*m_x,20*m_y);

        setBackground(Color.black);

        // Load blue images for all segments of the map
        blue_mapSegments = new Image[28];
        blue_mapSegments[0] = null;
        for(int ms=1;ms<28;ms++){
            try {
                blue_mapSegments[ms] = ImageIO.read(this.getClass().getResource("/resources/images/blue_map segments/"+ms+".png"));
            }catch(Exception e){}
        }
        
        // Load pink images for all segments of the map
        pink_mapSegments = new Image[28];
        pink_mapSegments[0] = null;
        for(int ms=1;ms<28;ms++){
            try {
                pink_mapSegments[ms] = ImageIO.read(this.getClass().getResource("/resources/images/pink_map segments/"+ms+".png"));
            }catch(Exception e){}
        }
        
        
        // Load green images for all segments of the map
        green_mapSegments = new Image[28];
        green_mapSegments[0] = null;
        for(int ms=1;ms<28;ms++){
            try {
                green_mapSegments[ms] = ImageIO.read(this.getClass().getResource("/resources/images/green_map segments/"+ms+".png"));
            }catch(Exception e){}
        }
        
        // Load babyBlue images for all segments of the map
        babyBlue_mapSegments = new Image[28];
        babyBlue_mapSegments[0] = null;
        for(int ms=1;ms<28;ms++){
            try {
                babyBlue_mapSegments[ms] = ImageIO.read(this.getClass().getResource("/resources/images/babyBlue_map segments/"+ms+".png"));
            }catch(Exception e){}
        }
        
        // Load images for Fruits
        pfoodImage = new Image[5];
        for(int ms=0 ;ms<5;ms++){
            try {
                pfoodImage[ms] = ImageIO.read(this.getClass().getResource("/resources/images/food/"+ms+".png"));
            }catch(Exception e){}
        }
        
        // Insert the question's icons into array of images
        questionIconImage = new Image[3];
        for(int ms=0 ;ms<3;ms++){
        	try {
            	questionIconImage[ms] = ImageIO.read(this.getClass().getResource("/resources/images/questionIcons/"+ms+".png"));
         	}catch(Exception e){}
        }
        
        // Load images for pac points, game over, and victory
        try{
            foodImage = ImageIO.read(this.getClass().getResource("/resources/images/food.png"));
            goImage = ImageIO.read(this.getClass().getResource("/resources/images/gameover.png"));
            vicImage = ImageIO.read(this.getClass().getResource("/resources/images/victory.png"));
            //pfoodImage = ImageIO.read(this.getClass().getResource("/images/pfood.png"));
        }catch(Exception e){}


        // Re-Draws the entire board periodically, updating all grahpics.
        redrawAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Draw Board
                repaint();
            }
        };
        redrawTimer = new Timer(16,redrawAL);
        redrawTimer .start();
        
        // Generate the first 3 questions and place them randomly on the map
        putQuestionOnMap(QuestionFactory.generateQuestionByDifficutly("Easy", md_backup, this));
        putQuestionOnMap(QuestionFactory.generateQuestionByDifficutly("Medium", md_backup, this));
        putQuestionOnMap(QuestionFactory.generateQuestionByDifficutly("Hard", md_backup, this));

        // Set score, player speed, and ghost speed for each level
        switch(level) {
    	case 1:
    		scoreToNextLevel = 51;
    		break;
    	case 2:
    		scoreToNextLevel = 101;
    		pacman.setGameSpeed(pacman.getGameSpeed() * 2);
    		break;
    	case 3:
    		scoreToNextLevel = 151;
    		pacman.setGameSpeed(pacman.getGameSpeed() * 2);
    		break;
    	case 4:
    		pacman.setGameSpeed(pacman.getGameSpeed() * 2);
    		for (Ghost g1 : ghosts) {	
    			g1.setGhostSpeed(4);
    		}
    		scoreToNextLevel = 200;
    		break;
    	default:
    		scoreToNextLevel = 51;
    	}
    }

    // ==============================================================================
    // ============================= END OF CONSTRUCTOR =============================
    // ==============================================================================
    
    // Checks if the player collided with a ghost
    public void collisionTest(){
        Rectangle pr = new Rectangle(pacman.getPixelPosition().x+13,pacman.getPixelPosition().y+13,2,2);
        for(Ghost g : ghosts){
            Rectangle gr = new Rectangle(g.pixelPosition.x,g.pixelPosition.y,28,28);

            if(pr.intersects(gr)){
                if(!g.isDead()) {
                	if(pacLives > 1) {
                		// Remove 1 life
                		pause();
                        pacLives--;
                		restart(level, score, pacLives, username);
                	}
                	else {
                		// Game Over
                		SysData.addToTopTen(this.username,this.score,0.0);
                        pause();
                        isGameOver = true;
                        scoreboard.setText("    Press R to try again !");	
                	}
                    
                    break;
                }
            }
        }
    }

    // Updates the state of the map (checks if food has been eaten, if a ghost died, if player passed through a passage)
    public void update(){
        Food foodToEat = null;
        //Check food eat
        for(Food f : foods){
            if(pacman.getLogicalPosition().x == f.position.x && pacman.getLogicalPosition().y == f.position.y)
                foodToEat = f;
        }
        
        // Eat food
        if(foodToEat!=null) {
            foods.remove(foodToEat);
            respawnFood(foodToEat.position);
            this.addScore(1);
        }
        
        // Eat Question
        QuestionIcon questionIcontToEat = null;
        for(QuestionIcon question : questionIcons){
            if(pacman.getLogicalPosition().x == question.position.x && pacman.getLogicalPosition().y == question.position.y)
                questionIcontToEat = question;
        }
        if(questionIcontToEat!=null) {
        	questionPopup(questionIcontToEat);
        	putQuestionOnMap(QuestionFactory.generateQuestionIcon(questionIcontToEat, md_backup, this));
        	respawnFood(questionIcontToEat.position);
        	questionIcontToEat=null;
            scoreToAdd = 0;
        }
        
        
        // Eat Fruit or Bomb
        PowerUpFood puFoodToEat = null;
        
        for(PowerUpFood puf : pufoods){
            if(pacman.getLogicalPosition().x == puf.getPosition().x && pacman.getLogicalPosition().y == puf.getPosition().y)
                puFoodToEat = puf;
        }
        if(puFoodToEat!=null) {
            switch(puFoodToEat.getType()) {
                // Eat Bomb
            	case 0:
                    pufoods.remove(puFoodToEat);
                    pacman.setStrong(true);
                    pacman.setInLocation(true);
                    break;
                // Eat Fruit
                default:
                    pufoods.remove(puFoodToEat);
                    if(score != 200) {
                    	scoreToAdd = 1;
                    	drawScore = true;
                    }
                    else
                    	drawScore = false;
            }
        }
        
        // Kill ghosts
        if(pacman.getIsStrong() &&pacman.isEnterPressed()) {	
	    	for (Ghost g : ghosts) {	
	        	for(int i=-3 ;i<=3; i++) {	
	        		for(int j=-3; j<=3; j++) {	

	        			// Check if Ghost is in radius 3 of Pacman
	        			if(pacman.getLogicalPosition().x == g.logicalPosition.x+i&&	
	                	    pacman.getLogicalPosition().y == g.logicalPosition.y+j) {	
	                		g.moveTimer.stop();
	                		g.animTimer.stop();
							ghostsToRemove.add(g);
					        // Action Listener for respawning ghosts after they die
							ActionListener respawnAL = new ActionListener() {
					            public void actionPerformed(ActionEvent evt) {
					                spawnNewGhost(g.ghostType);
					            }
					        };
					        Timer ghostRespawnTimer = new Timer(5000,respawnAL);
					        ghostRespawnTimer.setRepeats(false);
					        SysData.allTimers.add(ghostRespawnTimer);
					        ghostRespawnTimer.start();
	                		pacman.setStrong(false);	
	                		pacman.setEnterPreesed(false);
	        			}	
	            		
	            	}	
	        	}	
	        }	
        }
        
        ArrayList<Ghost> aux = (ArrayList<Ghost>) ghostsToRemove.clone();
        for(Ghost g : aux) {
        	ghosts.remove(g);
        	ghostsToRemove.remove(g);
        }
        aux = null;

        //Check Teleport
        for(TeleportTunnel tp : teleports) {
            if (pacman.getLogicalPosition().x == tp.getFrom().x && pacman.getLogicalPosition().y == tp.getFrom().y && pacman.getActiveMove() == tp.getReqMove()) {
                //System.out.println("TELE !");
                pacman.setLogicalPosition(tp.getTo());
                pacman.getPixelPosition().x = pacman.getLogicalPosition().x * 28;
                pacman.getPixelPosition().y = pacman.getLogicalPosition().y * 28;
            }
        }
    }
    
    // Respawns a ghost that just died
	private void spawnNewGhost(int ghostType) {
		System.out.println("SPAWNED");
        switch(ghostType) {
        case 1:
            ghosts.add(new RedGhost(ghostBase.x, ghostBase.y, this));
            break;
        case 2:
            ghosts.add(new PinkGhost(ghostBase.x, ghostBase.y, this));
            break;
        case 3:
            ghosts.add(new CyanGhost(ghostBase.x, ghostBase.y, this));
            break;
    }
		
	}

	// Method for adding score
	public void addScore(int amount) {
    	
        if(score >= scoreToNextLevel){
            //siren.stop();
            //pac6.stop();
            //SoundPlayer.play("pacman_intermission.wav");
            if(level != 4) {
            	scoreToNextLevel += 50;
            	nextLevel();
            } else {
                isWin = true;
                SysData.addToTopTen(this.username,this.score,0.0);
                pause();
            }
        } else if (score + amount >= 200) {
        	score = 200;
        } else {
        	score += amount;
        }
        scoreboard.setText("    Score : "+score);
    } 
	
	// Score answer according to it's difficulty (minus points if guessed wrong)
    public void scoreAnswer(String difficulty, boolean correct) {
    	//easy question
    	if(difficulty.equalsIgnoreCase("Easy")) {
    		//Right answer
    		if(correct) {
    		  	scoreToAdd = 1;
    		}
    		//Wrong answer
    		else {
    			if(score>=10) {
    				scoreToAdd = -10;
    			}
    			else {
    				scoreToAdd = -score;
    			}
    		}
    	}
    	//medium  question
    	else if(difficulty.equalsIgnoreCase("Medium")) {
    		//Right answer
    		if(correct) {
    		  	scoreToAdd = 2;
    		}
    		//Wrong answer
    		else {
    			if(score>=20) {
    				scoreToAdd = -20;
    			}
    			else {
    				scoreToAdd = -score;
    			}
    		}
    	}
    	//Hard question
    	else if(difficulty.equalsIgnoreCase("Hard")) {
    		//Right answer
    		if(correct) {
    		  	scoreToAdd = 3;
    			
    		}
    		//Wrong answer
    		else {
    			if(score>=30) {
    				scoreToAdd = -30;
    			}
    			else {
    				scoreToAdd = -score;
    			}
    		}
    	}

	  	drawQuestionScore = true;
    }

    
    // Draws all objects on the map
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        //DEBUG ONLY !
        /*for(int ii=0;ii<=m_x;ii++){
            g.drawLine(ii*28+10,10,ii*28+10,m_y*28+10);
        }
        for(int ii=0;ii<=m_y;ii++){
            g.drawLine(10,ii*28+10,m_x*28+10,ii*28+10);
        }*/

        switch(level) {
    	case 1:
    		//Draw Walls
            g.setColor(Color.blue);
            for(int i=0;i<m_x;i++){
                for(int j=0;j<m_y;j++){
                    if(map[i][j]>0){
                        //g.drawImage(10+i*28,10+j*28,28,28);
                        g.drawImage(blue_mapSegments[map[i][j]],10+i*28,10+j*28,null);
                    }
                }
            }
    		break;
    	case 2:
    		//Draw Walls
            g.setColor(Color.blue);
            for(int i=0;i<m_x;i++){
                for(int j=0;j<m_y;j++){
                    if(map[i][j]>0){
                        g.drawImage(pink_mapSegments[map[i][j]],10+i*28,10+j*28,null);
                    }
                }
            }
    		break;
    	case 3:
    		//Draw Walls
            g.setColor(Color.blue);
            for(int i=0;i<m_x;i++){
                for(int j=0;j<m_y;j++){
                    if(map[i][j]>0){
                        g.drawImage(babyBlue_mapSegments[map[i][j]],10+i*28,10+j*28,null);
                    }
                }
            }
    		break;
    		
    	case 4:
    		//Draw Walls
            g.setColor(Color.blue);
            for(int i=0;i<m_x;i++){
                for(int j=0;j<m_y;j++){
                    if(map[i][j]>0){
                        g.drawImage(green_mapSegments[map[i][j]],10+i*28,10+j*28,null);
                    }
                }
            }
    		break;
    	default:
    		//Draw Walls
            g.setColor(Color.blue);
            for(int i=0;i<m_x;i++){
                for(int j=0;j<m_y;j++){
                    if(map[i][j]>0){
                        g.drawImage(blue_mapSegments[map[i][j]],10+i*28,10+j*28,null);
                    }
                }
            }
    	}

        //Draw Food
        g.setColor(new Color(204, 122, 122));
        for(Food f : foods){
            g.drawImage(foodImage,10+f.position.x*28,10+f.position.y*28,null);
        }

        //Draw PowerUpFoods
        g.setColor(new Color(204, 174, 168));
        for(PowerUpFood f : pufoods){
            g.drawImage(pfoodImage[f.getType()],10+f.getPosition().x*28,10+f.getPosition().y*28,null);
        }
        
        //Draw QuestionIcons
        for(QuestionIcon f : questionIcons){
            g.drawImage(questionIconImage[f.type],10+f.position.x*28,10+f.position.y*28,null);
            
        }

        //Draw Pacman
        switch(pacman.getActiveMove()){
            case NONE:
            case RIGHT:
                g.drawImage(pacman.getPacmanImage(),10+pacman.getPixelPosition().x,10+pacman.getPixelPosition().y,null);
                break;
            case LEFT:
                g.drawImage(ImageHelper.flipHor(pacman.getPacmanImage()),10+pacman.getPixelPosition().x,10+pacman.getPixelPosition().y,null);
                break;
            case DOWN:
                g.drawImage(ImageHelper.rotate90(pacman.getPacmanImage()),10+pacman.getPixelPosition().x,10+pacman.getPixelPosition().y,null);
                break;
            case UP:
                g.drawImage(ImageHelper.flipVer(ImageHelper.rotate90(pacman.getPacmanImage())),10+pacman.getPixelPosition().x,10+pacman.getPixelPosition().y,null);
                break;
        }

        //Draw Ghosts
        for(Ghost gh : ghosts){
            g.drawImage(gh.getGhostImage(),10+gh.pixelPosition.x,10+gh.pixelPosition.y,null);
        }
        	
        // Clear the little score below pacman after 1 second
        if(clearScore){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawScore = false;
            drawQuestionScore = false;
            clearScore =false;
        }
        
        // Draw the gained score below pacman after eating fruit
        if(drawScore) {
            g.setFont(new Font("Arial",Font.BOLD,15));
            g.setColor(Color.yellow);
            Integer s = scoreToAdd*20;
            g.drawString(s.toString(), pacman.getPixelPosition().x + 13, pacman.getPixelPosition().y + 50);
            //drawScore = false;
            addScore(s);
            if(score > 200) {
            	score = 200;
            }
            scoreboard.setText("    Score : "+ score);
            clearScore = true;

        }
        
        // Draw the gained/lost score below pacman after answering question
        if(drawQuestionScore) {
            g.setFont(new Font("Arial",Font.BOLD,15));
            g.setColor(Color.yellow);
            Integer s = scoreToAdd;
            g.drawString(s.toString(), pacman.getPixelPosition().x + 13, pacman.getPixelPosition().y + 50);
            //drawScore = false;
            addScore(s);
            scoreboard.setText("    Score : "+score);
            clearScore = true;
        }
        
        // Draw game over screen
        if(isGameOver){
        	g.drawImage(goImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
            
        }
        
        // Draw victory screen
        if(isWin){
            g.drawImage(vicImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
        }


    }

    
    // Recieves event, checks what type it is (UPDATE, COLLISION, RESET), and proccesses it accordingly.
    @Override
    public void processEvent(AWTEvent ae){

        if(ae.getID()== Messages.UPDATE) {
        	if(mflag) {
        		return;
        	}
            update();
        }else if(ae.getID()== Messages.COLTEST) {
            if (!isGameOver) {
                collisionTest();
            }
        }else if(ae.getID()== Messages.RESET){
            if(isGameOver)
                restart(1,0,3,username);
        }else {
            super.processEvent(ae);
        }
    }
    
    // Respawns eaten pacpoint after 30 seconds
    public void respawnFood(Point position) {
        //animation timer
        ActionListener respawnAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                foods.add(new Food(position.x, position.y));
            }
        };
        Timer respawnTimer = new Timer(30000,respawnAL);
        respawnTimer.setRepeats(false);
        foodRespawnTimers.add(respawnTimer);
        //System.out.println(foodRespawnTimers.size());
        //SysData.allTimers.add(respawnTimer);
        respawnTimer.start();
    }

    
    // Opens the Question pop-up window
    public void questionPopup(QuestionIcon qi) {
    	// Stop all movement and animation
    	pause();
        
        Question q = questionPoints.get(qi);
        
        // Load the question window
    	new QuestionWindow(windowParent, q, this);
    }
    
    // Restarts the game.
    public void nextLevel(){
    	mflag = true;
    	stop();
    	
        if(score > 200) score = 200;
        windowParent.dispose();
        new PacWindow(level+1, score, pacLives,username);
        
    }
    
    // Pauses the game
    public void pause() {
    	pacman.getMoveTimer().stop();
        pacman.getAnimTimer().stop();
        for(Ghost g : ghosts){
            g.moveTimer.stop();
            g.animTimer.stop();
        }
    }
    
    // Stops the game (stops all the timers in the program)
    public void stop() {
    	pause();
    	redrawTimer.stop();
    	for(Timer t : foodRespawnTimers) {
    		if(t != null) {
    			t.stop();
    		}
    	}
    	for(Timer t : SysData.allTimers) {
    		if(t != null) {
    			t.stop();
    		}
    		
    	}
    }
    
    // Resumes the game
    public void resume() {
    	pacman.getMoveTimer().start();
        pacman.getAnimTimer().start();
        for(Ghost g : ghosts){
            g.moveTimer.start();
            g.animTimer.start();
        }
    }
    
    // Restarts the level with the same score, minus one life
    public void restart(int level, int score, int pacLives, String userName) {
    	//siren.stop();
    	//pac6.stop();
    	mflag = true;
    	
    	stop();
    	
    	windowParent.dispose();
    	if(score > 200) score = 200;
    	new PacWindow(level, score, pacLives, userName);
    	
    }


	// Checks if answer is correct, if yes, returns true and adds to score.
	public boolean checkAnswer(Question q, String ans) {
		boolean correct = false;
		for(Answer a : q.getAnswers()) {
			if(a.getContent().equals(ans)) {
				if(a.getAnswerID() == q.getCorrect_ans()) {
					correct = true;
				}
			}
		}
		scoreAnswer(q.getDifficulty(), correct);
		return correct;
	}
	
	// Puts a question on the map that is generated by QuestionFactory class
	public void putQuestionOnMap(ArrayList<Object> questionIconPair) {
		if(questionIconPair == null) return;
        questionPoints.put((QuestionIcon)questionIconPair.get(0),(Question)questionIconPair.get(1));        
        questionIcons.add((QuestionIcon)questionIconPair.get(0));
	}
	
	//=================================== GETTER SETTERS ===================================
	public int[][] getMap() {
		return map;
	}


	public void setMap(int[][] map) {
		this.map = map;
	}


	public Pacman getPacman() {
		return pacman;
	}


	public void setPacman(Pacman pacman) {
		this.pacman = pacman;
	}


	public ArrayList<Food> getFoods() {
		return foods;
	}


	public void setFoods(ArrayList<Food> foods) {
		this.foods = foods;
	}


	public ArrayList<PowerUpFood> getPufoods() {
		return pufoods;
	}


	public void setPufoods(ArrayList<PowerUpFood> pufoods) {
		this.pufoods = pufoods;
	}


	public ArrayList<Ghost> getGhosts() {
		return ghosts;
	}


	public void setGhosts(ArrayList<Ghost> ghosts) {
		this.ghosts = ghosts;
	}


	public ArrayList<TeleportTunnel> getTeleports() {
		return teleports;
	}


	public void setTeleports(ArrayList<TeleportTunnel> teleports) {
		this.teleports = teleports;
	}


	public ArrayList<QuestionIcon> getQuestionIcons() {
		return questionIcons;
	}


	public void setQuestionIcons(ArrayList<QuestionIcon> questionIcons) {
		this.questionIcons = questionIcons;
	}


	public ArrayList<Question> getQuestions() {
		return questions;
	}


	public void setQuestions(ArrayList<Question> questions) {
		this.questions = questions;
	}


	public HashMap<QuestionIcon, Question> getQuestionPoints() {
		return questionPoints;
	}


	public void setQuestionPoints(HashMap<QuestionIcon, Question> questionPoints) {
		this.questionPoints = questionPoints;
	}


	public ArrayList<Timer> getFoodRespawnTimers() {
		return foodRespawnTimers;
	}


	public void setFoodRespawnTimers(ArrayList<Timer> foodRespawnTimers) {
		this.foodRespawnTimers = foodRespawnTimers;
	}


	public boolean isCustom() {
		return isCustom;
	}


	public void setCustom(boolean isCustom) {
		this.isCustom = isCustom;
	}


	public boolean isGameOver() {
		return isGameOver;
	}


	public void setGameOver(boolean isGameOver) {
		this.isGameOver = isGameOver;
	}


	public boolean isWin() {
		return isWin;
	}


	public void setWin(boolean isWin) {
		this.isWin = isWin;
	}


	public int getScore() {
		return score;
	}


	public void setScore(int score) {
		this.score = score;
	}


	public int getLevel() {
		return level;
	}


	public void setLevel(int level) {
		this.level = level;
	}


	public Point getGhostBase() {
		return ghostBase;
	}


	public void setGhostBase(Point ghostBase) {
		this.ghostBase = ghostBase;
	}


	public int getM_x() {
		return m_x;
	}


	public void setM_x(int m_x) {
		this.m_x = m_x;
	}


	public int getM_y() {
		return m_y;
	}


	public void setM_y(int m_y) {
		this.m_y = m_y;
	}


	public MapData getMd_backup() {
		return md_backup;
	}


	public void setMd_backup(MapData md_backup) {
		this.md_backup = md_backup;
	}


	public PacWindow getWindowParent() {
		return windowParent;
	}


	public void setWindowParent(PacWindow windowParent) {
		this.windowParent = windowParent;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public ArrayList<Ghost> getGhostToRemove() {
		return ghostsToRemove;
	}


	public void setGhostToRemove(ArrayList<Ghost> ghostToRemove) {
		this.ghostsToRemove = ghostsToRemove;
	}
    

}
