package controllers;

import java.awt.Point;

import models.Question;
import models.QuestionIcon;

public class QuestionFactory { 
  
	public static void generateQuestionIcon(QuestionIcon questionIcontToEat, MapData md_backup, PacBoard pb) {
	//Generate a new QuestionIcon in a random position on the map.
	int randIndex = (int)(Math.random() * md_backup.getFoodPositions().size());
	int randType = (int)(Math.random() * 3);
	Point pointOfNewQuestion = md_backup.getFoodPositions().get(randIndex).position; 
	QuestionIcon newQuestionIcon; 
	Question newQuestion;
	
	if(questionIcontToEat == null) {
		// Get a random question that isn't already on the map
		do {
			newQuestion =pb.getRandomQuestion();
		}while(pb.questionPoints.containsValue(newQuestion));
		
		// Remove pac point and replace it with a QuestionIcon
    	md_backup.getFoodPositions().remove(randIndex);
    	newQuestionIcon = new QuestionIcon(pointOfNewQuestion.x,pointOfNewQuestion.y,randType); 
        pb.questionIcons.add(newQuestionIcon);
        
        // Put new Question & QuestionIcon pair in the hashmap
        pb.questionPoints.put(newQuestionIcon, newQuestion);
	}
	else {
		// Get the question that we just ate
		Question previousQuestion = pb.questionPoints.get(questionIcontToEat);
		
		// Get a random question that isn't already on the map, and is different from the question we just ate
		do {
			newQuestion = pb.getRandomQuestion();
		}while(pb.questionPoints.containsValue(newQuestion));
		
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
