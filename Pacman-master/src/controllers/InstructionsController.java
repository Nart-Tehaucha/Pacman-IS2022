package controllers;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

// controller of the instructions screen with all the details of how to play the game
public class InstructionsController {

	// the screen's components
    @FXML
    private AnchorPane MainPanel;

    @FXML
    private ImageView goBack;


    // go back to the menu screen
    @FXML
    void goToPageBefore(MouseEvent event) {
    	if(SysData.getThisUser().equals("admin")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminMenu.fxml"));
			LoadScreen(loader);
			return;
    	}
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
		LoadScreen(loader);
		return;
    }
    
    // load another fxml document
    @FXML
	void LoadScreen(FXMLLoader loader) {
		try {

			AnchorPane pane = loader.load();
			MainPanel.getChildren().clear();
			MainPanel.getChildren().add(pane);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
