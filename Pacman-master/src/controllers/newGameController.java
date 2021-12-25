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
		private static String PacMode;

		private Pacman pacman;

	      
		

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
	    	PacMode ="regular";
			stage = (Stage) MainPanel.getScene().getWindow();
			username =SysData.getThisUser();
			new PacWindow(username);
			stage.close();

	    }
	    @FXML
	    void startCovid(ActionEvent event) {
//	    	pacman = new Pacman();
//	    	pacman.setCovidPac(true); //covidPac mode
	    	PacMode ="covidPac";
	    	stage = (Stage) MainPanel.getScene().getWindow();
			username =SysData.getThisUser();
			new PacWindow(username);
			stage.close();

	    }

	    @FXML
	    void startMissPac(ActionEvent event) {
//	    	pacman = new Pacman();
//	    	pacman.setMissPac(true);//missPac mode
	    	PacMode ="missPac";
	    	//System.out.println(" misspac " +pacman.isMissPac());
	    	stage = (Stage) MainPanel.getScene().getWindow();
			username =SysData.getThisUser();
			new PacWindow(username);
			stage.close();
	    }

	    @FXML
	    void startZombie(ActionEvent event) {
//	    	pacman = new Pacman();
//	    	pacman.setZombiePac(true);// zombiePac mode
	    	PacMode ="zombiePac";
	     	stage = (Stage) MainPanel.getScene().getWindow();
			username =SysData.getThisUser();
			new PacWindow(username);
			stage.close();

	    }
	    @FXML
	    void startChristmas(ActionEvent event) {
	    	PacMode ="christmasPac";
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

		public static String getPacMode() {
			return PacMode;
		}

		public void setPacMode(String pacMode) {
			PacMode = pacMode;
		}

	


}
