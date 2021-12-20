package controllers;

import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import models.Player;
import views.PacWindow;


// controls the menu screen 
public class MenuController {

	
	// the menu's components
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

	@FXML
    private Button StartPlayBtn;
    
	private Stage stage;
	

	
	private ObservableList<Player> player = FXCollections.observableArrayList();

	private String username;


    @FXML
    private ImageView logOut;

    public void setUsername(String username) {
    	this.username = username;
    }
    
    public String getUsername() {
    	return username;
    }

    // log out
    @FXML
    void logOutOfSystem(MouseEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginScreen.fxml"));
		LoadScreen(loader);
		return;
    }

    // start the game
    @FXML
    void start(ActionEvent event) {
		stage = (Stage) MainPanel.getScene().getWindow();
		username =SysData.getThisUser();
		new PacWindow(username);
		stage.close();
    }
    
    // go to instructions screen
    @FXML
    void instructions(ActionEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Instructions.fxml"));
		LoadScreen(loader);
		return;
    }
    
    // go to records table
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