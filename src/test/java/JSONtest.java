import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import controllers.PacBoard;
import models.Answer;
import models.Question;
import controllers.SysData;
import views.PacWindow;

public class JSONtest {

	SysData sd = SysData.getInstance();
	ArrayList<Question> questions = new ArrayList<Question>();
	
	@BeforeEach
    void setUp() {
        Answer a1 = new Answer(1, 1, "A1");
        Answer a2 = new Answer(2, 1, "A2");
        Answer a3 = new Answer(3, 1, "A3");
        Answer a4 = new Answer(4, 1, "A4");
        ArrayList<Answer> answers = new ArrayList<Answer>();
        answers.add(a1);
        answers.add(a2);
        answers.add(a3);
        answers.add(a4);
        questions.add(new Question(1, "TT", "Easy", answers, 2,0,0,0,0,0,0));
        questions.add(new Question(2, "asdf", "Hard", answers, 3,0,0,0,0,0,0));
        questions.add(new Question(3, "123", "Easy", answers, 4,0,0,0,0,0,0));
        questions.add(new Question(4, "test", "Hard", answers, 1,0,0,0,0,0,0));
    }
	
	@Test
	public void testReadQuestionsJSON() throws Exception {
		 assertNotNull(sd.readQuestionsJSON(),"Successfully read the JSON file");
	}

	@Test
	public void testAddQuestionToJSON() throws FileNotFoundException {
		assertTrue(sd.addQuestionToJSON(questions.get(0)));
	}

	// test whether the score is initialized to 0 
	@Test 
	public void testScoreInit() { 
<<<<<<< HEAD
		PacWindow pw = new PacWindow(1,0,3,"Test"); 
=======
		PacWindow pw = new PacWindow(1, 0, 3, "Test"); 
>>>>>>> refs/remotes/origin/Maven-Branch
		PacBoard pb = pw.getPacBoard();
		int score = pb.getScore(); 
		assertEquals(score, 0); 
		} 
	
	@Test
	public void testNumOfGhost() {
		PacWindow pw = new PacWindow(1,0,3,"Test"); 
		PacBoard pb = pw.getPacBoard();
		assertEquals(pb.getGhosts().size(), 3); 
	}
	
	@Test
	public void isScoreIncreased() {
		PacWindow pw = new PacWindow(1,0,3,"Test");
		PacBoard pb = pw.getPacBoard();
		int scoreBefore = pb.getScore();
		pb.addScore(1);
		int scoreAfter = pb.getScore();
		assertTrue(scoreBefore + 1 == scoreAfter);
	}
}
