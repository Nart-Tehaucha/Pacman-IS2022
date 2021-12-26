package controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Pacman;
import controllers.PacBoard;
import views.PacWindow;

public class newGameController {
	private Stage stage;

		private String username;

	    @FXML
	    private AnchorPane MainPanel;

	    @FXML
	    private Button MissPacBtn;

	    @FXML
	    private Button ZombieBtn;

	    @FXML
	    private Button StartPlayBtn;

	    @FXML
	    private ImageView logOut;

	    @FXML
	    private Button CovidBtn;

	    @FXML
	    void logOutOfSystem(MouseEvent event) {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
			LoadScreen(loader);
			return;

	    }

	    @FXML
	    void start(ActionEvent event) {
	    	// Normal Mode
	    	SysData.setGameMode(0);
			stage = (Stage) MainPanel.getScene().getWindow();
			username =SysData.getThisUser();
			new PacWindow(username);
			stage.close();

	    }
	    @FXML
    	// Zombie mode
	    void startCovid(ActionEvent event) {
	    	SysData.setGameMode(1);
	    	stage = (Stage) MainPanel.getScene().getWindow();
			username =SysData.getThisUser();
			new PacWindow(username);
			stage.close();

	    }

	    @FXML
	    void startZombie(ActionEvent event) {
		    // Corona Mode
	    	SysData.setGameMode(2);
	     	stage = (Stage) MainPanel.getScene().getWindow();
			username =SysData.getThisUser();
			new PacWindow(username);
			stage.close();

	    }
	    @FXML
	    void startChristmas(ActionEvent event) {
	    	// Christmas Modde
	    	SysData.setGameMode(3);
	     	stage = (Stage) MainPanel.getScene().getWindow();
			username =SysData.getThisUser();
			new PacWindow(username);
			stage.close();
	    }
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
