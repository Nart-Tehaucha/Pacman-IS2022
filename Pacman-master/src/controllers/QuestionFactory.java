package controllers;

import java.awt.Point;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import models.Question;
import models.QuestionIcon;

public class QuestionFactory { 
  
	
    // Returns a random question from the questions ArrayList
    public static Question getRandomQuestion(PacBoard pb) {
    	int randIndex = (int)(Math.random() * pb.getQuestions().size());
    	return pb.getQuestions().get(randIndex);
    }
	
	public static ArrayList<Object> generateQuestionByDifficutly(String difficulty, MapData md_backup, PacBoard pb) {
		if(pb.getQuestions().size() < 3) return null;
		int numberOfTries = 0; // Used to prevent infinite loops
		//Generate a new QuestionIcon in a random position on the map.
		int randIndex = (int)(Math.random() * md_backup.getFoodPositions().size());
		Point pointOfNewQuestion = md_backup.getFoodPositions().get(randIndex).position; 
		QuestionIcon newQuestionIcon; 
		Question newQuestion;
		ArrayList<Object>newQuestionArray = new ArrayList<>();
		
		// Get a new question that isn't already on the map and has the desired difficulty
		do {
			newQuestion = getRandomQuestion(pb);
			numberOfTries++;
		}while((pb.getQuestionPoints().containsValue(newQuestion) || !(newQuestion.getDifficulty().equalsIgnoreCase(difficulty))) && numberOfTries <= 999);
		
		if(numberOfTries >= 9) return null;
		
		// Remove pac point and replace it with a QuestionIcon
    	md_backup.getFoodPositions().remove(randIndex);
    	int qType;
    	if(difficulty.equalsIgnoreCase("Easy")) qType = 0;
    	else if(difficulty.equalsIgnoreCase("Medium")) qType = 1;
    	else qType = 2;
    	
    	newQuestionIcon = new QuestionIcon(pointOfNewQuestion.x,pointOfNewQuestion.y, qType); 
    	newQuestionArray.add(newQuestionIcon);
    	newQuestionArray.add(newQuestion);
    	
		return newQuestionArray;
		
	}
	
	public static ArrayList<Object> generateQuestionIcon(QuestionIcon questionIcontToEat, MapData md_backup, PacBoard pb) {
	if(pb.getQuestions().size() < 3) return null;
	if(questionIcontToEat == null) return null;
	
	int numberOfTries = 0; // Used to prevent infinite loops

	//Generate a new QuestionIcon in a random position on the map.
	int randIndex = (int)(Math.random() * md_backup.getFoodPositions().size());
	Point pointOfNewQuestion = md_backup.getFoodPositions().get(randIndex).position; 
	int qType;
	QuestionIcon newQuestionIcon; 
	Question newQuestion;
	Question oldQuestion = pb.getQuestionPoints().get(questionIcontToEat);
	ArrayList<Object>newQuestionArray = new ArrayList<>();	
		
	// Get a random question that isn't already on the map, and is different from the question we just ate
	do {
		newQuestion = getRandomQuestion(pb);
		numberOfTries++;
	}while((pb.getQuestionPoints().containsValue(newQuestion) || newQuestion.equals(oldQuestion))  && numberOfTries <= 999);
	
	if(numberOfTries >= 99) return null;
	
	// Remove pac point and replace it with a QuestionIcon
	md_backup.getFoodPositions().remove(randIndex);
	pb.getQuestionIcons().remove(questionIcontToEat);
	
	newQuestionIcon = new QuestionIcon(pointOfNewQuestion.x,pointOfNewQuestion.y,questionIcontToEat.type);
	
	newQuestionArray.add(newQuestionIcon);
	newQuestionArray.add(newQuestion);
	
	// Put new Question & QuestionIcon pair in the hashmap, remove the one we ate.
	pb.getQuestionPoints().remove(questionIcontToEat);
	
  	newQuestionArray.add(newQuestionIcon);
	newQuestionArray.add(newQuestion);
	return newQuestionArray;
}

}
