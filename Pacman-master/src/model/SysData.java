package model;

import controllers.*;
import views.*;

import java.util.LinkedList;
import java.util.TreeMap;

// Class for handling all the information in the system.
// Saves history of all games
// Saves all questions
public class SysData {
	public LinkedList<PacBoard> games;
	public LinkedList<Question> questions;
	private TreeMap<String, Integer> scoresByNickname;
	private TreeMap<String, Integer> timesByNickname;
	
	public SysData() {
		this.games = new LinkedList<PacBoard>();
		this.questions = new LinkedList<Question>();
		scoresByNickname = new TreeMap<>();
		timesByNickname = new TreeMap<>();
	};
	
	public void initiate() {};
	public void loadData() {};
	public void writeData() {};
	public void loadQuestions() {};
	public void writeQuestions() {};
	public void addQuestion(Question q) {};
	public void removeQuestion(Question q) {};
	public void addGame(PacBoard g) {};
	public void popQuestion() {};
	
	
	
}
