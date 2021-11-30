import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import modules.Answer;
import modules.Question;
import modules.SysData;

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
        questions.add(new Question(1, "TT", 2, answers, 2, "tt"));
        questions.add(new Question(2, "asdf", 1, answers, 3, "ss"));
        questions.add(new Question(3, "123", 3, answers, 4, "ff"));
        questions.add(new Question(4, "test", 4, answers, 1, "gg"));
    }
	
	@Test
	public void testReadQuestionsJSON() throws Exception {
		 assertNotNull(sd.readQuestionsJSON(),"Successfully read the JSON file");
	}

	@Test
	public void testAddQuestionToJSON() throws FileNotFoundException {
		assertTrue(sd.addQuestionToJSON(questions));
	}

}
