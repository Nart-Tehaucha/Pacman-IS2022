package modules;

import java.util.HashMap;

// Class for Questions.
public class Question {
	public int id;
	public String teamName;
	public String content;
	public int difficulty; // 0 - Easy, 1 - Medium, 2 - Hard
	public boolean isMultipleChoice;
	public HashMap<Question, String> answers;
	public char tag;
	
	public Question() {};
}
