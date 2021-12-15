package controllers;

import java.util.ArrayList;

import models.Answer;
import models.Player;
import models.Question;
import models.SysData;
import views.PacWindow;
import views.StartWindow;



public class Main {
	

	
	// Main class, opens the main screen
    public static void main(String[] args) throws Exception {
        //new StartWindow();
    	//PlayersController.createJSON();
    	//JFXLauncher.main(args);
    	//Question q = new Question(6, "ttstt", "Hard", null, 3);
    	//SysData.deleteQuestionFromJSONByID(6);
    	
    	new PacWindow("Nart");
    	//Test.main(args);
    	//System.out.println(new SysData().readQuestionsJSON().toString());

    }
    
   


}
