package Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controllers.PacBoard;
import views.PacWindow;

public class JUnit {
// test whether the score is initialized to 0	
	@Test
	void testScoreInit() {
		PacBoard pb = new PacBoard();
		int score = pb.score;
		assertEquals(score, 0);
	}
	
	
	
//	@Test
//	void testNumOfGhosts() {
//		PacWindow pw = new PacWindow();
//		int desiredNum = 3;
//		int realNum = pw.
//		int score = pb.score;
//		assertEquals(score, 0);
//	}
	
//	// test whether the array contains the correct num of ghosts
//	@Test
//	void testNumOfGhosts() {
//		PacBoard pb = new PacBoard();
//		int requiredNum = 3;
//		System.out.println(pb.ghosts.size());
//		assertTrue(pb.ghosts.size() == requiredNum);
//	}
	
	// test whether the score is being calculated correctly
//	@Test
//	void testScoreCalc() {
//		PacBoard pb = new PacBoard();
//		int score = pb.score;
//	}
}
