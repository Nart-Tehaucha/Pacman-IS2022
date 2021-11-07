package modules;

import controllers.*;
import views.*;
import java.util.LinkedList;
public class SysData {
	public LinkedList<PacBoard> games;
	public LinkedList<Question> questions;
	
	
	public SysData() {
		this.games = new LinkedList<PacBoard>();
		this.questions = new LinkedList<Question>();
		
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
