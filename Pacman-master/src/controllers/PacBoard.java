package controllers;


import views.*;
import javax.imageio.ImageIO;
import javax.swing.*;


import models.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


// This is the main class for running the game. It handles all the logic of the player, ghosts, score, and time.
public class PacBoard extends JPanel{

	
	
	
	
    public Timer redrawTimer;
    public ActionListener redrawAL;

    public int[][] map;
    public Image[] blue_mapSegments;
    public Image[] pink_mapSegments;
    public Image[] babyBlue_mapSegments;
    public Image[] green_mapSegments;
    
    public Image foodImage;
    public Image[] pfoodImage;
    public Image[] questionIconImage;

    public Image goImage;
    public Image vicImage;

    public Pacman pacman;
    public ArrayList<Food> foods; // Regular foods (pac points)
    public ArrayList<PowerUpFood> pufoods; // Power Up foods (bombs, special fruit)
    public ArrayList<Ghost> ghosts;
    public ArrayList<TeleportTunnel> teleports; // Teleports = Passages
    public ArrayList<QuestionIcon> questionIcons;// Icons on the map representing questions
    public ArrayList<Question> questions; // Questions
    public HashMap<QuestionIcon, Question> questionPoints; //Pairs of Questions and their QuestionIcon on the map

    public boolean isCustom = false;
    public boolean isGameOver = false;
    public boolean isWin = false;
    public boolean drawScore = false;
    public boolean clearScore = false;
    public int scoreToAdd = 0;
    public int pacLives;

    public int score;
    public int level;
    public int scoreToNextLevel;
    public JLabel scoreboard;

    public LoopPlayer siren;
    public boolean mustReactivateSiren = false;
    public LoopPlayer pac6;

    public Point ghostBase;

    public int m_x;
    public int m_y;

    public MapData md_backup;
    public PacWindow windowParent;
    
    //shahar
    private String username;
    
   
    

    // Constructor
    public PacBoard(JLabel scoreboard, int level, int score, int pacLives, MapData md, PacWindow pw){
    	
    	//SysData.initializeTopTen();
    	System.out.println("THIS IS USER NAME1: " + this.username);
    	//shahar 
    	this.username = pw.getUsername();
    	System.out.println("THIS IS USER NAME2: " + this.username);
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
    		scoreToNextLevel = 200;
    		break;
    	default:
    		scoreToNextLevel = 51;
    	}
        
        foods = new ArrayList<>(); // Regular foods (pac points)
        pufoods = new ArrayList<>(); // Power Up foods (bombs, special fruit)
        ghosts = new ArrayList<>();
        teleports = new ArrayList<>(); // Teleports = Passages
        questionIcons = new ArrayList<>(); //Objects on the map representing questions that can be eaten
        questionPoints = new HashMap<>(); // Pairs every Question with a QuestionIcon that's on the map

        // Load questions from JSON file to arraylist
        try {
			questions = SysData.readQuestionsJSON();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        
        //TODO : read food from mapData (Map 1)

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
        teleports = md.getTeleports();

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
        } System.out.println("this is pink_mapSegments: "+ pink_mapSegments.toString());
        
        
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
        

        pfoodImage = new Image[5];
        for(int ms=0 ;ms<5;ms++){
            try {
                pfoodImage[ms] = ImageIO.read(this.getClass().getResource("/resources/images/food/"+ms+".png"));
            }catch(Exception e){}
        }
        
        //Insert the question's icons into array of images
        questionIconImage = new Image[3];
        for(int ms=0 ;ms<3;ms++){
        	try {
            	questionIconImage[ms] = ImageIO.read(this.getClass().getResource("/resources/images/questionIcons/"+ms+".png"));
         	}catch(Exception e){}
        }
        
        try{
            foodImage = ImageIO.read(this.getClass().getResource("/resources/images/food.png"));
            goImage = ImageIO.read(this.getClass().getResource("/resources/images/gameover.png"));
            vicImage = ImageIO.read(this.getClass().getResource("/resources/images/victory.png"));
            //pfoodImage = ImageIO.read(this.getClass().getResource("/images/pfood.png"));
        }catch(Exception e){}


        // Draw the board
        redrawAL = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                //Draw Board
                repaint();
            }
        };
        redrawTimer = new Timer(16,redrawAL);
        redrawTimer .start();
        // Generates a new question on the map
        QuestionFactory.generateQuestionIcon(null, md_backup, this);
        QuestionFactory.generateQuestionIcon(null, md_backup, this);
        QuestionFactory.generateQuestionIcon(null, md_backup, this);

        // Start playing sounds
        SoundPlayer.play("/Pacman-master/src/media/tutorial.mp4");
        siren = new LoopPlayer("/Pacman-master/src/media/tutorial.mp4");
        //pac6 = new LoopPlayer("pac6.wav");
        siren.start();
    }

    
    // Checks if the player colided with a ghost
    public void collisionTest(){
        Rectangle pr = new Rectangle(pacman.pixelPosition.x+13,pacman.pixelPosition.y+13,2,2);
        Ghost ghostToRemove = null;
        for(Ghost g : ghosts){
            Rectangle gr = new Rectangle(g.pixelPosition.x,g.pixelPosition.y,28,28);

            if(pr.intersects(gr)){
                if(!g.isDead()) {
                    if (!g.isWeak()) {
                    	if(pacLives > 1) {
                    		pacman.moveTimer.stop();
                            pacman.animTimer.stop();
                            g.moveTimer.stop();
                            isGameOver = true;
                            pacLives--;
                    		restart(level, score, pacLives, username);
                    	}
                    	else {
                    		//Game Over
                            siren.stop();
                            //SoundPlayer.play("pacman_lose.wav");
                    		//Shahar
                    		//get player score into top 10 if relevant
                    		SysData.addToTopTen(this.username,this.score,0.0);
                            pacman.moveTimer.stop();
                            pacman.animTimer.stop();
                            g.moveTimer.stop();
                            isGameOver = true;
                            scoreboard.setText("    Press R to try again !");	
                    	}
                        
                        break;
                    } else {
                        //Eat Ghost
                        //SoundPlayer.play("pacman_eatghost.wav");
                        //getGraphics().setFont(new Font("Arial",Font.BOLD,20));
                        drawScore = true;
                        if(score != 200) {
                        	scoreToAdd++;
                        }
                        if(ghostBase!=null)
                            g.die();
                        else
                            ghostToRemove = g;
                    }
                }
            }
        }

        if(ghostToRemove!= null){
            ghosts.remove(ghostToRemove);
        }
    }

    // Updates the state of the map (checks if food has been eaten, if a ghost died, if player passed through a passage)
    public void update(){
        Food foodToEat = null;
        //Check food eat
        for(Food f : foods){
            if(pacman.logicalPosition.x == f.position.x && pacman.logicalPosition.y == f.position.y)
                foodToEat = f;
        }
        if(foodToEat!=null) {
            //SoundPlayer.play("pacman_eat.wav");
            foods.remove(foodToEat);
            this.addScore();
        }
        
        
        QuestionIcon questionIcontToEat = null;
        for(QuestionIcon question : questionIcons){
            if(pacman.logicalPosition.x == question.position.x && pacman.logicalPosition.y == question.position.y)
                questionIcontToEat = question;
        }
        
        
        //Shahar- This function m
        if(questionIcontToEat!=null) {
            //SoundPlayer.play("pacman_eat.wav");
        	questionPopup(questionIcontToEat);
        	QuestionFactory.generateQuestionIcon(questionIcontToEat, md_backup, this);
        	questionIcontToEat=null;
            scoreToAdd = 0;
        }
        
        

        PowerUpFood puFoodToEat = null;
        //Check pu food eat
        for(PowerUpFood puf : pufoods){
            if(pacman.logicalPosition.x == puf.position.x && pacman.logicalPosition.y == puf.position.y)
                puFoodToEat = puf;
        }
        if(puFoodToEat!=null) {
            //SoundPlayer.play("pacman_eat.wav");
            switch(puFoodToEat.type) {
                case 0:
                    //PACMAN 6
                    pufoods.remove(puFoodToEat);
                    siren.stop();
                    mustReactivateSiren = true;
                    //pac6.start();
                    pacman.setStrong(true);
                    pacman.setInLocation(true);
//                    if(pacman.isEnterPressed()) {
//                    	for (Ghost g : ghosts) {
//                        	for(int i=-3 ;i<=3; i++) {
//                        		for(int j=-3; j<=3; j++) {
//                        			if(pacman.logicalPosition.x == g.logicalPosition.x+i&&
//             	                    	   pacman.logicalPosition.y == g.logicalPosition.y+j) {
//             	                    		g.ghostDisappear();
//             	                    		g.logicalPosition.x = 12;
//             	                    		g.logicalPosition.y = 13;
//             	                    		g.pixelPosition.x = 13 * 28;
//             	                    		g.pixelPosition.y = 13 * 28;
             	                    		//g.logicalPosition =  this.ghostBase;
             	                    		
//                        			}
//    	                    	
//    	                    	}
//                        	}
//                        }
//                    }
                    
                    scoreToAdd = 0;
                    pacman.setEnterPreesed(false);
                    pacman.setInLocation(false);
                    break;
                default:
                    //SoundPlayer.play("pacman_eatfruit.wav");
                    pufoods.remove(puFoodToEat);
                    if(score != 200) {
                    	scoreToAdd = 1;
                    	drawScore = true;
                    }
                    else
                    	drawScore = false;
            }
            //score ++;
            //scoreboard.setText("    Score : "+score);
        }
        
        if(pacman.getIsStrong() &&pacman.isEnterPressed()) {	
	    	for (Ghost g : ghosts) {	
	        	for(int i=-3 ;i<=3; i++) {	
	        		for(int j=-3; j<=3; j++) {	
	        			if(pacman.logicalPosition.x == g.logicalPosition.x+i&&	
		                    	   pacman.logicalPosition.y == g.logicalPosition.y+j) {	
		                    		g.ghostDisappear();		
		                    		pacman.setStrong(false);	
		                    		pacman.setEnterPreesed(false);
	        			}	
	            		
	            	}	
	        	}	
	        }	
        }
        
        //Check Ghost Undie
        for(Ghost g:ghosts){
            if(g.isDead() && g.logicalPosition.x == ghostBase.x && g.logicalPosition.y == ghostBase.y){
                g.undie();
            }
        }

        //Check Teleport
        for(TeleportTunnel tp : teleports) {
            if (pacman.logicalPosition.x == tp.getFrom().x && pacman.logicalPosition.y == tp.getFrom().y && pacman.activeMove == tp.getReqMove()) {
                //System.out.println("TELE !");
                pacman.logicalPosition = tp.getTo();
                pacman.pixelPosition.x = pacman.logicalPosition.x * 28;
                pacman.pixelPosition.y = pacman.logicalPosition.y * 28;
            }
        }
        
        //Check isSiren
        boolean isSiren = true;
        for(Ghost g:ghosts){
            if(g.isWeak()){
                isSiren = false;
            }
        }
        if(isSiren){
            //pac6.stop();
            if(mustReactivateSiren){
                mustReactivateSiren = false;
                siren.start();
            }

        
        
        }
    }

    // Returns a random question from the questions ArrayList
    public Question getRandomQuestion() {
    	int randIndex = (int)(Math.random() * questions.size());
    	
    	return questions.get(randIndex);
    }




    public void addScore() {
    	score ++;
    	if(score > 200) score = 200;
        scoreboard.setText("    Score : "+score);

        if(score >= scoreToNextLevel){
            siren.stop();
            //pac6.stop();
            //SoundPlayer.play("pacman_intermission.wav");
            isWin = true;
            pacman.moveTimer.stop();
            for(Ghost g : ghosts){
                g.moveTimer.stop();
            }
            if(level != 4) {
            	nextLevel();
            }
//            //if he won - add to top 10
//            else {
//            	addToTopTen(oldTopTenWinnersAL);
//            }
        }
    } 
    public void addScoreAfterQuestion(Question question, int playerAnswer) {
    	//easy question
    	if(question.getDifficulty() == "Easy" ||question.getDifficulty() == "EASY" || question.getDifficulty() =="easy") {
    		//Right answer
    		if(question.getCorrect_ans() == playerAnswer) {
    		  	score ++;
    			
    		}
    		//Wrong answer
    		else {
    			if(score>=10) {
    			score =score-10;
    			}
    			else {
    				score =0;
    			}
    	       
    			
    		}
    	 scoreboard.setText("    Score : "+score);
    	}
    	//medium  question
    	if(question.getDifficulty() == "Medium" ||question.getDifficulty() == "MEDIUM" ||question.getDifficulty() == "medium") {
    		//Right answer
    		if(question.getCorrect_ans() == playerAnswer) {
    		  	score= score+2;
    			
    		}
    		//Wrong answer
    		else {
    			if(score>=20) {
    			score =score-20;
    			}
    			else {
    				score =0;
    			}
    	       
    			
    		}
    	 scoreboard.setText("    Score : "+score);
    	}
    	//Hard question
    	if(question.getDifficulty() == "Hard" ||question.getDifficulty() == "HARD" ||question.getDifficulty() == "hard") {
    		//Right answer
    		if(question.getCorrect_ans() == playerAnswer) {
    		  	score =score +3;
    			
    		}
    		//Wrong answer
    		else {
    			if(score>=30) {
    			score =score-30;
    			}
    			else {
    				score =0;
    			}
    	       
    			
    		}
    	 scoreboard.setText("    Score : "+score);
    	}
  
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
    		for (Ghost g1 : ghosts) {	
    		//
    			//g1.setGhostSpeed(7);
    		//g1.setGhostNormalDelay(int ghostNormalDelay)
    		//System.out.println(g1.getGhostSpeed());
    		g1.setGhostNormalDelay(100);
    		System.out.println(g1.getGhostNormalDelay());
    		
    		}
    		pacman.setGameSpeedForLevel2(4, level);
    		//System.out.println(pacman.getGameSpeed() + "   "+level);
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
    		//change pacman speed
    		System.out.println(pacman.getGameSpeed() + "   "+level);
    		pacman.setGameSpeedForLevel2(7, level);
    		//pacman.setGameSpeed(4);
            g.setColor(Color.blue);
            for(int i=0;i<m_x;i++){
                for(int j=0;j<m_y;j++){
                    if(map[i][j]>0){
                        //g.drawImage(10+i*28,10+j*28,28,28);
                        g.drawImage(pink_mapSegments[map[i][j]],10+i*28,10+j*28,null);
                    }
                }
            }
    		break;
    	case 3:
    		//Draw Walls
    		pacman.setGameSpeedForLevel2(4, level);
    		System.out.println(pacman.getGameSpeed() + "   "+level);
            g.setColor(Color.blue);
            for(int i=0;i<m_x;i++){
                for(int j=0;j<m_y;j++){
                    if(map[i][j]>0){
                        //g.drawImage(10+i*28,10+j*28,28,28);
                        g.drawImage(babyBlue_mapSegments[map[i][j]],10+i*28,10+j*28,null);
                    }
                }
            }
    		break;
    		
    	case 4:
    		//Draw Walls
    		pacman.setGameSpeedForLevel2(7, level);
    		System.out.println(pacman.getGameSpeed() + "   "+level);
            g.setColor(Color.blue);
            for(int i=0;i<m_x;i++){
                for(int j=0;j<m_y;j++){
                    if(map[i][j]>0){
                        //g.drawImage(10+i*28,10+j*28,28,28);
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
                        //g.drawImage(10+i*28,10+j*28,28,28);
                        g.drawImage(blue_mapSegments[map[i][j]],10+i*28,10+j*28,null);
                    }
                }
            }
    	}
//        //Draw Walls
//        g.setColor(Color.blue);
//        for(int i=0;i<m_x;i++){
//            for(int j=0;j<m_y;j++){
//                if(map[i][j]>0){
//                    //g.drawImage(10+i*28,10+j*28,28,28);
//                    g.drawImage(mapSegments[map[i][j]],10+i*28,10+j*28,null);
//                }
//            }
//        }

        //Draw Food
        g.setColor(new Color(204, 122, 122));
        for(Food f : foods){
            //g.fillOval(f.position.x*28+22,f.position.y*28+22,4,4);
            g.drawImage(foodImage,10+f.position.x*28,10+f.position.y*28,null);
        }

        //Draw PowerUpFoods
        g.setColor(new Color(204, 174, 168));
        for(PowerUpFood f : pufoods){
            //g.fillOval(f.position.x*28+20,f.position.y*28+20,8,8);
            g.drawImage(pfoodImage[f.type],10+f.position.x*28,10+f.position.y*28,null);
        }
        
        //Draw QuestionIcons
        for(QuestionIcon f : questionIcons){
            //g.fillOval(f.position.x*28+20,f.position.y*28+20,8,8);
            g.drawImage(questionIconImage[f.type],10+f.position.x*28,10+f.position.y*28,null);
            //System.out.println("This is the type:" + questionIconImage[f.type].toString());
            
        }

        //Draw Pacman
        switch(pacman.activeMove){
            case NONE:
            case RIGHT:
                g.drawImage(pacman.getPacmanImage(),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
            case LEFT:
                g.drawImage(ImageHelper.flipHor(pacman.getPacmanImage()),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
            case DOWN:
                g.drawImage(ImageHelper.rotate90(pacman.getPacmanImage()),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
            case UP:
                g.drawImage(ImageHelper.flipVer(ImageHelper.rotate90(pacman.getPacmanImage())),10+pacman.pixelPosition.x,10+pacman.pixelPosition.y,null);
                break;
        }

        //Draw Ghosts
        for(Ghost gh : ghosts){
            g.drawImage(gh.getGhostImage(),10+gh.pixelPosition.x,10+gh.pixelPosition.y,null);
        }

        if(clearScore){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            drawScore = false;
            clearScore =false;
        }

        if(drawScore) {
            g.setFont(new Font("Arial",Font.BOLD,15));
            g.setColor(Color.yellow);
            Integer s = scoreToAdd*20;
            g.drawString(s.toString(), pacman.pixelPosition.x + 13, pacman.pixelPosition.y + 50);
            //drawScore = false;
            score += s;
            if(score > 200) {
            	score = 200;
            }
            scoreboard.setText("    Score : "+ score);
            clearScore = true;

        }

        if(isGameOver){
            g.drawImage(goImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
            for(RecordWinner rw: SysData.getOldTopTenWinnersAL()) {
            	if(rw.getUserName() == username) {
            		if(rw.getPoints() < score) {
            			SysData.addToTopTen(this.username, this.score, 0.0);
            		}
            	}
            }
            
        }

        if(isWin){
            g.drawImage(vicImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
            for(RecordWinner rw: SysData.getOldTopTenWinnersAL()) {
            	if(rw.getUserName() == username) {
            		if(rw.getPoints() < score) {
            			SysData.addToTopTen(this.username, this.score, 0.0);
            		}
            	}
            }
        }


    }

    
    // Recieves event, checks what type it is (UPDATE, COLLISION, RESET), and proccesses it accordingly.
    @Override
    public void processEvent(AWTEvent ae){

        if(ae.getID()== Messages.UPDATE) {
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

    
    // Opens the Question interface
    public void questionPopup(QuestionIcon qi) {
    	// Stop all movement and animation
    	pacman.moveTimer.stop();
        pacman.animTimer.stop();
        for(Ghost g : ghosts){
            g.moveTimer.stop();
            g.animTimer.stop();
        }
        
        Question q = questionPoints.get(qi);
        
        // Load the question window
    	QuestionWindow qw = new QuestionWindow(windowParent, q, this);
    }
    
    // Restarts the game.
    public void nextLevel(){

        siren.stop();
//pac6.stop();
     
//        
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        windowParent.dispose();
        if(score > 200) score = 200;
        new PacWindow(level+1, score, pacLives,username);
        
    }
    
    public void resume() {
    	pacman.moveTimer.start();
        pacman.animTimer.start();
        for(Ghost g : ghosts){
            g.moveTimer.start();
            g.animTimer.start();
        }
    }
    
    public void restart(int level, int score, int pacLives, String userName) {
    	siren.stop();
    	//pac6.stop();
    	
    	windowParent.dispose();
    	if(score > 200) score = 200;
    	new PacWindow(level, score, pacLives, userName);
    	
    }



	

	public void checkAnswer(Question q, String text) {
		// TODO Auto-generated method stub
		
	}
    

}
