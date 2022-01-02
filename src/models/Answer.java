package models;

public class Answer {
	// -------------------------------Class Members------------------------------
	transient static int idCounter = 1;
	private int answerID;
	private int questionID;
	private String content;

	// -------------------------------Constructors-------------------------------
	public Answer(int answerID, int questionID, String content) {
		super();
		this.answerID = answerID;
		this.questionID = questionID;
		this.content = content;
	}
	
	public Answer(int questionID, String content) {
		super();
		this.answerID = idCounter;
		this.questionID = questionID;
		this.content = content;
	}

	// -------------------------------Getters And Setters-------------------------
	public int getAnswerID() {
		return answerID;
	}

	public void setAnswerID(int answerID) {
		this.answerID = answerID;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getQuestionID() {
		return questionID;
	}

	public void setQuestionID(int questionID) {
		this.questionID = questionID;
	}

	@Override
	public String toString() {
		return content;
	}

}
