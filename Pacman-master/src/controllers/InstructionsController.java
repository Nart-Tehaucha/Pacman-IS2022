package controllers;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

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
