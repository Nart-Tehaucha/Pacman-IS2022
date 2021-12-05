package controllers;


import views.*;
import javax.imageio.ImageIO;
import javax.swing.*;
import models.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Scanner;

// This is the main class for running the game. It handles all the logic of the player, ghosts, score, and time.
public class PacBoard extends JPanel{


    public Timer redrawTimer;
    public ActionListener redrawAL;

    public int[][] map;
    public Image[] mapSegments;

    public Image foodImage;
    public Image[] pfoodImage;
    public Image[] questionIconImage;

    public Image goImage;
    public Image vicImage;

    public Pacman pacman;
    public ArrayList<Food> foods; // Regular foods (pac points)
    public ArrayList<PowerUpFood> pufoods; // Power Up foods (bombs, special fruit)
    public ArrayList<QuestionIcon> questionIcons;// question icons (easy, medium, hard)
    public ArrayList<Ghost> ghosts;
    public ArrayList<TeleportTunnel> teleports; // Teleports = Passages

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

    // Constructor
    public PacBoard(JLabel scoreboard, int level, int score, int pacLives, MapData md, PacWindow pw){
        this.level = level;
        this.score = score;
        this.pacLives = pacLives;
    	this.scoreboard = scoreboard;
    	
    	switch(level) {
    	case 1:
    		scoreToNextLevel = 51;
    		break;
    	case 2:
    		scoreToNextLevel = 101;
    		break;
    	case 3:
    		scoreToNextLevel = 151;
    		break;
    	case 4:
    		scoreToNextLevel = 200;
    		break;
    	default:
    		scoreToNextLevel = 51;
    	}
    	
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
        questionIcons = new ArrayList<>(); //Questions icons (easy, medium, hard)

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

        // Load images for all segments of the map
        mapSegments = new Image[28];
        mapSegments[0] = null;
        for(int ms=1;ms<28;ms++){
            try {
                mapSegments[ms] = ImageIO.read(this.getClass().getResource("/resources/images/map segments/"+ms+".png"));
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

        // Start playing sounds
        //SoundPlayer.play("pacman_start.wav");
        //siren = new LoopPlayer("siren.wav");
        //pac6 = new LoopPlayer("pac6.wav");
        //siren.start();
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
                    		restart(level, score, pacLives);
                    	}
                    	else {
                    		//Game Over
                            //siren.stop();
                            //SoundPlayer.play("pacman_lose.wav");
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
                        scoreToAdd++;
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
        	int index;
        	Point pointOfNewQuestion;
        	QuestionIcon qi;
            switch(questionIcontToEat.type) {
                case 0:
                    //OPEN EASY QUESTION WINDOW
     
                	questionIcons.remove(questionIcontToEat);
                	index = (int)(Math.random() * md_backup.getFoodPositions().size());
                	pointOfNewQuestion = md_backup.getFoodPositions().get(index).position; 
                	md_backup.getFoodPositions().remove(index);
                    qi = new QuestionIcon(pointOfNewQuestion.x,pointOfNewQuestion.y,0);
                    questionIcons.add(qi);
                	questionIcontToEat=null;
                    scoreToAdd = 0;
                    break;
                case 1:
                    //OPEN MEDIUM QUESTION WINDOW
                	questionIcons.remove(questionIcontToEat);
                	index = (int)(Math.random() * md_backup.getFoodPositions().size());
                	
                	pointOfNewQuestion = md_backup.getFoodPositions().get(index).position;        
                	md_backup.getFoodPositions().remove(index);
                    qi = new QuestionIcon(pointOfNewQuestion.x,pointOfNewQuestion.y,1);
                    questionIcons.add(qi);
                	questionIcontToEat=null;
                    scoreToAdd = 0;
                    break;
                    
                case 2: 
                    //OPEN HARD QUESTION WINDOW
                	questionIcons.remove(questionIcontToEat);
                	index = (int)(Math.random() * md_backup.getFoodPositions().size());
                	pointOfNewQuestion = md_backup.getFoodPositions().get(index).position;
                	md_backup.getFoodPositions().remove(index);
                    qi = new QuestionIcon(pointOfNewQuestion.x,pointOfNewQuestion.y,2);
                    questionIcons.add(qi);
                	questionIcontToEat=null;
                    scoreToAdd = 0;
                    break;
            }
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
                    //siren.stop();
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
                    scoreToAdd = 1;
                    drawScore = true;
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
                //siren.start();
            }

        
        
        }
    }



    


    public void addScore() {
    	score ++;
        scoreboard.setText("    Score : "+score);

        if(score >= scoreToNextLevel){
            //siren.stop();
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

        //Draw Walls
        g.setColor(Color.blue);
        for(int i=0;i<m_x;i++){
            for(int j=0;j<m_y;j++){
                if(map[i][j]>0){
                    //g.drawImage(10+i*28,10+j*28,28,28);
                    g.drawImage(mapSegments[map[i][j]],10+i*28,10+j*28,null);
                }
            }
        }

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
            scoreboard.setText("    Score : "+score);
            clearScore = true;

        }

        if(isGameOver){
            g.drawImage(goImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
        }

        if(isWin){
            g.drawImage(vicImage,this.getSize().width/2-315,this.getSize().height/2-75,null);
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
                restart(1,0,3);
        }else {
            super.processEvent(ae);
        }
    }

    
    // Restarts the game.
    public void nextLevel(){

        //siren.stop();
//pac6.stop();
     
//        
//        try {
//            Thread.sleep(5000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        windowParent.dispose();
        
        new PacWindow(level+1, score, pacLives);
    }
    
    
    public void restart(int level, int score, int pacLives) {
    	//siren.stop();
    	//pac6.stop();
    	
    	windowParent.dispose();
    	new PacWindow(level, score, pacLives);
    	
    }
    

}
