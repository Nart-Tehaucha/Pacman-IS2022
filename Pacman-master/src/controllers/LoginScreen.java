package controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import javafx.application.Application;
import javafx.event.ActionEvent;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;


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

public class LoginScreen{


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
    
//    @Override
//	public void start(Stage primaryStage) {
//    	System.out.println("hi");
//	    File temp = new File("");
//		String abPath = temp.getAbsolutePath();
//		String path = new File(abPath + "/Pacman-master/src/media/ourZoo.mp4").getAbsolutePath();
//		System.out.println(path);
//		MediaPlayer mp = new MediaPlayer(new Media(new File(path).toURI().toString()));
//		System.out.println(mp);
//		mv = new MediaView(mp);
//		mp.setVolume(0);
//		mv.setFitHeight(primaryStage.getHeight());
//		mv.setFitWidth(primaryStage.getWidth());
//		mp.play();
//		mp.setCycleCount(MediaPlayer.INDEFINITE);
//		mv.getMediaPlayer().audioSpectrumIntervalProperty();
//		MainPanel.getChildren().add(mv);
//    }

    @FXML
    void EnterTheMenu(ActionEvent event) {
    	try {
    		if(username.getText().equals("admin")
    				&& (loginPassword.getText().equals("admin") || passwordText.getText().equals("admin"))) {
    			showAlert(AlertType.INFORMATION, "Enter The System",
						LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + "  "
								+ LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue() + "/"
								+ LocalDate.now().getYear() + "\nWelcome Back Admin!",
						"");
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HomeScreen.fxml"));
				LoadScreen(loader);
				return;
    		}
    		if (username.getText().equals("admin") && !loginPassword.getText().equals("admin")) {
    			showFailAlert(AlertType.WARNING, "Error", "Sorry, cannot enter the system", "Oops... Are you sure you're the admin?"); 
    		}
    		if (username.getText().isEmpty() || (loginPassword.getText().isEmpty() && passwordText.getText().isEmpty())) {
    			showFailAlert(AlertType.WARNING, "Empty Fields", "You must fill all the fields", ""); 
    		}
    		if (!username.getText().equals("admin")) {
    			if(nicknamesAndPasswords.containsKey(username.getText())){
    				if(nicknamesAndPasswords.get(username.getText()).equals(passwordText.getText())) {
    					showAlert(AlertType.INFORMATION, "Enter The System",
    							LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + "  "
    									+ LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue() + "/"
    									+ LocalDate.now().getYear() + "\nWelcome Back " + username.getText() + "!",
    							"");
    	    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/HomeScreen.fxml"));
    					LoadScreen(loader);
    					return;
    				}
    				System.out.println(passwordText.getText());
					if (nicknamesAndPasswords.containsKey(username.getText())
							&& (!(nicknamesAndPasswords.get(username.getText()).equals(passwordText.getText())))) {
						showFailAlert(AlertType.INFORMATION, "Incorrect Password", null,
								"Sorry " + username.getText() + ", You entered a wrong password...");
						System.out.println("wrong password");
						return;
					}
    			}
    			else if(!(nicknamesAndPasswords.containsKey(username.getText())) && !username.getText().isEmpty()){
    				showFailAlert(AlertType.ERROR, "Incorrect NickName", null,
							"Sorry " + username.getText() + ", You entered a wrong nickname...");
    				System.out.println("no such username");
					return;
    			}

    		}

    	}
    		catch(Exception e) {
    			e.getMessage();
    		}
    	}		
    
	
	private HashMap<String, String> nicknamesAndPasswords = new HashMap<>();

	

	public HashMap<String, String> getNicknamesAndPasswords() {
		return nicknamesAndPasswords;
	}

	public void setNicknamesAndPasswords(HashMap<String, String> nicknamesAndPasswords) {
		this.nicknamesAndPasswords = nicknamesAndPasswords;
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
	
    @FXML
    void NewPlayer(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NewPlayer.fxml"));
		LoadScreen(loader);
		return;
    }

}


