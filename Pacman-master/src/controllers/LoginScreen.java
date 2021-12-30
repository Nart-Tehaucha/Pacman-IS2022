package controllers;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;

import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;

import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import models.Player;

// controls the login screen- when a user wants to enter the system
public class LoginScreen{

	// save the username
	static String lastUserToLogIn;

	// the Login screen components
	@FXML
    private AnchorPane MainPanel;

	@FXML
	private TextField username;

	@FXML
	private RadioButton showPassword;

	@FXML
	private PasswordField loginPassword;

	@FXML
	private TextField passwordText;

	@FXML
	private Button LoginButton;

	private String password;
	
    @FXML
    private MediaView mv;
    
	private HashMap<String, String> nicknamesAndPasswords = new HashMap<>();

	// constructor
	public LoginScreen() {
		this.setNicknamesAndPasswords();
	}
	
	public HashMap<String, String> getNicknamesAndPasswords() {
		return nicknamesAndPasswords;
	}

	
	public void setNicknamesAndPasswords() {
		try {
			for(Player p : PlayersController.readPlayersFromJSON()) {
				nicknamesAndPasswords.put(p.getNickname(), p.getPassword());
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	// check the user who tries to enter the system- his nickname and password
    @FXML
    void EnterTheMenu(ActionEvent event) {
    	try {
    		if(username.getText().equals("admin")
    				&& (loginPassword.getText().equals("admin") || passwordText.getText().equals("admin"))) {
    			SysData.setThisUser(username.getText());
    			String path = new File(SysData.correctedPath + "/Pacman-master/src/media/success.mp3").getAbsolutePath();
    			MediaPlayer sound = new MediaPlayer(new Media(new File(path).toURI().toString()));
    			sound.play();
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminMenu.fxml"));
				LoadScreen(loader, "Admin");
				return;
    		}
    		if (username.getText().equals("admin") && !loginPassword.getText().equals("admin")) {
    			showFailAlert(AlertType.WARNING, "Error", "Sorry, cannot enter the system", "Oops... Are you sure you're the admin?"); 
    		}
    		if (username.getText().isEmpty() || (loginPassword.getText().isEmpty() && passwordText.getText().isEmpty())) {
    			showFailAlert(AlertType.WARNING, "Empty Fields", "You must fill all the fields", ""); 
    		}
    		if (!username.getText().equals("admin")) {
    			
    			// Successful login
    			if(nicknamesAndPasswords.containsKey(username.getText())){
    				if(nicknamesAndPasswords.get(username.getText()).equals(loginPassword.getText())) {
    					SysData.setThisUser(username.getText());
    					lastUserToLogIn= username.getText();
    	    			String path = new File(SysData.correctedPath + "/Pacman-master/src/media/success.mp3").getAbsolutePath();
    	    			MediaPlayer sound = new MediaPlayer(new Media(new File(path).toURI().toString()));
    	    			sound.play();
    	    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
    					LoadScreen(loader, username.getText());
    					return;
    				}
    				
    				// Wrong password
					if (nicknamesAndPasswords.containsKey(username.getText())
							&& (!(nicknamesAndPasswords.get(username.getText()).equals(loginPassword.getText())))) {
						showFailAlert(AlertType.INFORMATION, "Incorrect Password", null,
								"Sorry " + username.getText() + ", You entered a wrong password...");
						return;
					}
    			}
    			
    			// Wrong username
    			else if(!(nicknamesAndPasswords.containsKey(username.getText())) && !username.getText().isEmpty()){
    				showFailAlert(AlertType.ERROR, "Incorrect NickName", null,
							"Sorry " + username.getText() + ", You entered a wrong nickname...");
					return;
    			}
    		}
    	}
    		catch(Exception e) {
    			e.getMessage();
    		}
    	}		
	
	// if the 'forgot password' button is pressed then sends a text input dialog to
	// the user
	@FXML
	void forgotPassword(ActionEvent event) {
		inputDialog();
	}

	// show the password
	@FXML
	void showThePassword(ActionEvent event) {

		if (showPassword.isSelected()) {
			password = loginPassword.getText();
			passwordText.setText(password);
			passwordText.setVisible(true);
			loginPassword.setVisible(false);
			loginPassword.setText(passwordText.getText());
			return;
		}

		loginPassword.setText(passwordText.getText());
		loginPassword.setVisible(true);
		passwordText.setVisible(false);
	}

	@FXML
	public void LoadScreen(FXMLLoader loader, String username) {
		try {

			AnchorPane pane = loader.load();
			MainPanel.getChildren().clear();
			MainPanel.getChildren().add(pane);
			MenuController mc = loader.<MenuController>getController();
			mc.setUsername(username);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
    @FXML
	void LoadScreen2(FXMLLoader loader) {
		try {

			AnchorPane pane = loader.load();
			MainPanel.getChildren().clear();
			MainPanel.getChildren().add(pane);

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
// in case of a success
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
// in case of a failure
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

	// text input dialog which asks the user to enter his nickname in order to
	// search him in the database
	private void inputDialog() {

		Stage s = new Stage();
		// set title for the stage
		s.setTitle("User Confirmation");

		// create a text input dialog
		TextInputDialog td = new TextInputDialog("");

		// setHeaderText
		td.setHeaderText("Enter your nickname:");
		td.showAndWait();

		String result = td.getResult();
		if(result == null) {
			showFailAlert(AlertType.ERROR, "No nickname entered", "Please do not leave this field empty", "");
		}
		if (result != null) {
			for (String nickname : getNicknamesAndPasswords().keySet()) {
				if (nickname.equals(result)) {
					showAlert(AlertType.INFORMATION, "Success", "Your password is: " + getNicknamesAndPasswords().get(result), "");
					return;
				}

			}
			showFailAlert(AlertType.INFORMATION, "Failed", "Sorry, could not find you in the system...", "");
			return;
		} else {
			td.close();
			return;
		}

	}
	
	// if the user is not registered yet than he can register in this screen
    @FXML
    void NewPlayer(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NewPlayer.fxml"));
		LoadScreen2(loader);
		return;
    }

}


