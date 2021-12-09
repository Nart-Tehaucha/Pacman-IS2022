package controllers;

import models.SysData;
import views.StartWindow;

public class Main {
	

	
	// Main class, opens the main screen
    public static void main(String[] args) throws Exception {
        //new StartWindow();
    	PlayersController.createJSON();
    	//JFXLauncher.main(args);
    	//Test.main(args);
    	//System.out.println(new SysData().readQuestionsJSON().toString());
    }


}
