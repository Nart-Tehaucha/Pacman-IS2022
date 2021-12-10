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
    	new PacWindow("Nart");
    	//Test.main(args);
    	//System.out.println(new SysData().readQuestionsJSON().toString());
    }


}
