package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

public class AddQuestionController {

    @FXML
    private AnchorPane popUp;
    
    @FXML
  		void PopUpLoadScreen(FXMLLoader loader) {
  			try {
  				AnchorPane pane = loader.load();
  				popUp.getChildren().clear();
  				popUp.getChildren().add(pane);

  			} catch (IOException e) {
  				e.printStackTrace();
  			}
  	    }
}
