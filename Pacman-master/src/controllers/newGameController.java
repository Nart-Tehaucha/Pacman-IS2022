package controllers;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import models.Pacman;
import controllers.PacBoard;
import views.PacWindow;
import controllers.SysData;

public class newGameController {
	private Stage stage;

	private String username;
	private static String PacMode;
	private static String GameMode;


	@FXML
	private AnchorPane MainPanel;

	@FXML
	private RadioButton pacman;
	@FXML
	private RadioButton missPac;

	@FXML
	private ImageView logOut;
	@FXML
	private ToggleGroup character;

    @FXML
    private Label labelChoosePlayer;
    @FXML
    private Label labelChooseGameMode;

    @FXML
    private RadioButton RegularID;

    @FXML
    private ToggleGroup gameMode;

    @FXML
    private RadioButton zombie;

    @FXML
    private RadioButton christmasID;

    @FXML
    private RadioButton covidID;
    @FXML
    private Button startPlay;




//	@FXML
//	void start(ActionEvent event) {
//
//	}
//
//	@FXML
//	void startMissPac(ActionEvent event) {
//
//	}
	private void showFailAlert(AlertType type, String title, String header, String text) {
		Alert alert = new Alert(type);

		alert.setHeight(Region.BASELINE_OFFSET_SAME_AS_HEIGHT);
		alert.setWidth(Region.BASELINE_OFFSET_SAME_AS_HEIGHT);
		alert.setResizable(true);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(text);
		DialogPane dialogPane = alert.getDialogPane();
		dialogPane.setStyle(
				"-fx-background-image: url('/views/sad_pacman.jpg'); -fx-background-size: cover; -fx-font-weight: bold; -fx-font-size: 12px;");

		String path = new File(SysData.correctedPath + "/Pacman-master/src/media/fail.mp3").getAbsolutePath();
		MediaPlayer sound = new MediaPlayer(new Media(new File(path).toURI().toString()));
		sound.play();
		
		alert.showAndWait();
	}

	@FXML
	void logOutOfSystem(MouseEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
		LoadScreen(loader);
		return;

	}

	
	  @FXML 
	  void start(ActionEvent event) { 
		  if(PacMode!= null && GameMode!=null) {
			stage = (Stage) MainPanel.getScene().getWindow();
			username =SysData.getThisUser();
			new PacWindow(username);
			stage.close();
		  }
		  else {
			  showFailAlert(AlertType.WARNING, "Error", "Sorry, cannot enter the Game", "Please choose a game mode and a character"); 
		  }
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


	@FXML
	void modeMissPac(ActionEvent event) {
		PacMode ="missPac";
		SysData.setPacMode(1);
	
	}
	
	@FXML
	void modePacman(ActionEvent event) {
		PacMode ="pacman";
		SysData.setPacMode(0);
	
	}
	
	
	@FXML
	void RegularBtm(ActionEvent event) {
		SysData.setGameMode(0);
	
	}
	
	@FXML
	void christmas(ActionEvent event) {
		SysData.setGameMode(3);
	
	}
	
	@FXML
	void covid(ActionEvent event) {
		SysData.setGameMode(2);
	
	}
	
	@FXML
	void zombie(ActionEvent event) {
		SysData.setGameMode(1);
	
	}
	public static String getPacMode() {
		return PacMode;
	}
	
	public void setPacMode(String pacMode) {
		PacMode = pacMode;
	}
	
	
	public static String getGameMode() {
		return GameMode;
	}
	
	
	public static void setGameMode(String gameMode) {
		GameMode = gameMode;
	}
}
