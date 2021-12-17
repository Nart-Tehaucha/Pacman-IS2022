package controllers;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Player;
import models.SysData;
import views.PacWindow;
import views.StartWindow;

public class MenuController {
	
	

    @FXML
    private Button InstructionsBtn;

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private Button ManageQuesBtn;

    @FXML
    private Button RecordsBtn;

    public Button getRecordsBtn() {
		return RecordsBtn;
	}

	public void setRecordsBtn(Button recordsBtn) {
		RecordsBtn = recordsBtn;
	}

	public String getUsername() {
		return username;
	}

	@FXML
    private Button StartPlayBtn;
    
	private Stage stage;
	
	private String username;
	
	private ObservableList<Player> player = FXCollections.observableArrayList();
    @FXML
    void start(ActionEvent event) {
//    	player.add(new Player(typedText, score));
    	player.add(new Player("tal", "0"));
		stage = (Stage) MainPanel.getScene().getWindow();
		username =SysData.getThisUser();
		new PacWindow(username);
		stage.close();
    }
    
    @FXML
    void instructions(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Instructions.fxml"));
		LoadScreen(loader);
		return;
    }
    
    @FXML
	void LoadScreen(FXMLLoader loader) {
		try {
			System.out.println("I use this method");
			AnchorPane pane = loader.load();
			MainPanel.getChildren().clear();
			MainPanel.getChildren().add(pane);

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @FXML
    void OpenRecordsScreen(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/RecordsTable.fxml"));
		LoadScreen(loader);
		return;
    }
    

    @FXML
    void OpenQuestionManager(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ManageQuestions.fxml"));
		LoadScreen(loader);
		return;
    }

    public void setUsername(String username) {
    	this.username = username;
    }
}