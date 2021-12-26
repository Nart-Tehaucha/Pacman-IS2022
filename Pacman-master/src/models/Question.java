package models;

import java.util.ArrayList;


public class Question {
	// -------------------------------Class Members------------------------------
	transient static int idCounter = 1;
	private int questionID;
	private String content;
	private String difficulty;
	private ArrayList<Answer> answers;
	private int correct_ans;
	private int numOfPeopleAnswered;
	private int answeredCorrectly;

	// -------------------------------Constructors-------------------------------

	public Question(int questionID, String content, String difficulty, ArrayList<Answer> answers, int correct_ans,int numOfPeopleAnswered, int answeredCorrectly ) {
		super();
		this.questionID = questionID;
		this.content = content;
		this.difficulty = difficulty;
		this.answers = answers;
		this.correct_ans = correct_ans;
		this.numOfPeopleAnswered = numOfPeopleAnswered;
		this.answeredCorrectly = answeredCorrectly;
	}
	
	public Question(String content, String difficulty, ArrayList<Answer> answers, int correct_ans) {
		super();
		this.questionID = idCounter++;
		this.content = content;
		this.difficulty = difficulty;
		this.answers = answers;
		this.correct_ans = correct_ans;
	}

	// -------------------------------Getters And Setters-------------------------
	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public ArrayList<Answer> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<Answer> answers) {
		this.answers = answers;
	}

	public int getCorrect_ans() {
		return correct_ans;
	}

	public void setCorrect_ans(int correct_ans) {
		this.correct_ans = correct_ans;
	}
	
	public int getNumOfPeopleAnswered() {
		return numOfPeopleAnswered;
	}

	public void setNumOfPeopleAnswered(int numOfPeopleAnswered) {
		this.numOfPeopleAnswered = numOfPeopleAnswered;
	}
	public int getAnsweredCorrectly() {
		return answeredCorrectly;
	}

	public void setAnsweredCorrectly(int answeredCorrectly) {
		this.answeredCorrectly = answeredCorrectly;
	}
	

	// -------------------------------Methods------------------------------------

	//Delete an answer
	public boolean deleteAnswer(Answer answer) {
		if (answer != null) {
			answers.remove(answers.indexOf(answer));
			return true;
		}
		return false;
	}




}
