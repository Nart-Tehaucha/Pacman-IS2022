package controllers;

import java.awt.Point;

import models.Question;
import models.QuestionIcon;

public class QuestionFactory { 
  
	
    // Returns a random question from the questions ArrayList
    public static Question getRandomQuestion(PacBoard pb) {
    	int randIndex = (int)(Math.random() * pb.questions.size());
    	return pb.questions.get(randIndex);
    }
	
	public static void generateQuestionByDifficutly(String difficulty, MapData md_backup, PacBoard pb) {
		if(pb.questions.size() < 3) return;
		
		//Generate a new QuestionIcon in a random position on the map.
		int randIndex = (int)(Math.random() * md_backup.getFoodPositions().size());
		int randType = (int)(Math.random() * 3);
		Point pointOfNewQuestion = md_backup.getFoodPositions().get(randIndex).position; 
		int qType;
		QuestionIcon newQuestionIcon; 
		Question newQuestion;
		
		// Get a new question that isn't already on the map and has the desired difficulty
		do {
			newQuestion = getRandomQuestion(pb);
		}while(pb.questionPoints.containsValue(newQuestion) || !(newQuestion.getDifficulty().equalsIgnoreCase(difficulty)));
		
		// Remove pac point and replace it with a QuestionIcon
    	md_backup.getFoodPositions().remove(randIndex);
    	switch (newQuestion.getDifficulty()) {
    	case "Easy":
    		qType = 0;
    		break;
    	case "Medium":
    		qType = 1;
    		break;
    	case "Hard":
    		qType = 2;
    		break;
    	default:
    		qType = 0;
    	}
    	
    	newQuestionIcon = new QuestionIcon(pointOfNewQuestion.x,pointOfNewQuestion.y,qType); 
        pb.questionIcons.add(newQuestionIcon);
        
        // Put new Question & QuestionIcon pair in the hashmap
        pb.questionPoints.put(newQuestionIcon, newQuestion);
		
	}
	
	public static void generateQuestionIcon(QuestionIcon questionIcontToEat, MapData md_backup, PacBoard pb) {
	if(pb.questions.size() < 3) return;
		
	//Generate a new QuestionIcon in a random position on the map.
	int randIndex = (int)(Math.random() * md_backup.getFoodPositions().size());
	Point pointOfNewQuestion = md_backup.getFoodPositions().get(randIndex).position; 
	int qType;
	QuestionIcon newQuestionIcon; 
	Question newQuestion;
	Question oldQuestion = pb.questionPoints.get(questionIcontToEat);
	
	if(questionIcontToEat == null) {
		// Get a random question that isn't already on the map
		do {
			newQuestion = getRandomQuestion(pb);
		}while(pb.questionPoints.containsValue(newQuestion));
		
		// Remove pac point and replace it with a QuestionIcon
    	md_backup.getFoodPositions().remove(randIndex);
    	switch (newQuestion.getDifficulty()) {
    	case "Easy":
    		qType = 0;
    		break;
    	case "Medium":
    		qType = 1;
    		break;
    	case "Hard":
    		qType = 2;
    		break;
    	default:
    		qType = 0;
    	}
    	
    	newQuestionIcon = new QuestionIcon(pointOfNewQuestion.x,pointOfNewQuestion.y,qType); 
        pb.questionIcons.add(newQuestionIcon);
        
        // Put new Question & QuestionIcon pair in the hashmap
        pb.questionPoints.put(newQuestionIcon, newQuestion);
	}
	else {
		
		// Get a random question that isn't already on the map, and is different from the question we just ate
		do {
			newQuestion = getRandomQuestion(pb);
		}while(pb.questionPoints.containsValue(newQuestion) || newQuestion.equals(oldQuestion));
		
		// Remove pac point and replace it with a QuestionIcon
    	md_backup.getFoodPositions().remove(randIndex);
    	newQuestionIcon = new QuestionIcon(pointOfNewQuestion.x,pointOfNewQuestion.y,questionIcontToEat.type);
    	pb.questionIcons.remove(questionIcontToEat);
    	pb.questionIcons.add(newQuestionIcon);
        
        // Put new Question & QuestionIcon pair in the hashmap, remove the one we ate.
        pb.questionPoints.remove(questionIcontToEat);
        pb.questionPoints.put(newQuestionIcon, newQuestion);
	}
}

}
