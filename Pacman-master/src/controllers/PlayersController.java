package controllers;

import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import views.LoginScreen;

public class PlayersController {


    @FXML
    private ToggleGroup allowNotifications;
    
    @FXML
    private AnchorPane MainPanel;

    @FXML
    private TextField email;

    @FXML
    private TextField nickname;

    @FXML
    private RadioButton no;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField password2;

    @FXML
    private Button saveButton;

    @FXML
    private RadioButton yes;

    @FXML
    private Label enterEmail;

    @FXML
    void Notifications(MouseEvent event) {
    	email.setVisible(true);
    	enterEmail.setVisible(true);
    }
    
    @FXML
    void NoNotif(ActionEvent event) {
    	email.setVisible(false);
    	enterEmail.setVisible(false);
    }
   

    @FXML
    void saveData(ActionEvent event) {
    	if(nickname.getText().isEmpty() || password.getText().isEmpty() || password2.getText().isEmpty()
    			|| (!yes.isSelected() && !no.isSelected())){
    		showFailAlert(AlertType.ERROR, "Empty Fields", "Please fill all the fields", null);
    	}
    	if(!password.getText().equals(password2.getText())) {
    		showFailAlert(AlertType.ERROR, "Password don't match!", "Please retype your password", null);
    	}
    	 LoginScreen lc = new LoginScreen();

    	if (!nickname.getText().isEmpty() && !password.getText().isEmpty() && !password2.getText().isEmpty()
			&& (!yes.isSelected() || !no.isSelected()) && (password.getText().equals(password2.getText()))){
    			if(!lc.getNicknamesAndPasswords().containsKey(nickname.getText())) {
    				lc.getNicknamesAndPasswords().put(nickname.getText(), password.getText());
    		    	for(String s: lc.getNicknamesAndPasswords().values()) {
    		    		System.out.println(s);
    		    	}
    				showAlert(AlertType.CONFIRMATION, "Successfully Registered", "You have been registerd!", "WELCOME TO PACMAN IS-21");
    				FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HomeScreen.fxml"));
					LoadScreen(loader);
					return;
    			}
    			else
    				showFailAlert(AlertType.ERROR, "Nickname already exists!", "Oops... it seems like this exact nickname has been taken", null);
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

	private void showAlert(AlertType type, String title, String header, String text) {
		Alert alert = new Alert(type);

		alert.setHeight(Region.USE_PREF_SIZE);
		alert.setWidth(Region.USE_PREF_SIZE);
		alert.setResizable(true);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(text);

		
		alert.showAndWait();
	}

	private void showFailAlert(AlertType type, String title, String header, String text) {
		Alert alert = new Alert(type);

		alert.setHeight(Region.BASELINE_OFFSET_SAME_AS_HEIGHT);
		alert.setWidth(Region.BASELINE_OFFSET_SAME_AS_HEIGHT);
		alert.setResizable(true);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(text);
		alert.showAndWait();
	}

}
