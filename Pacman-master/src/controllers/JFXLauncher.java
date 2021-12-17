package controllers;


import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import views.StartWindow;

public class JFXLauncher extends Application {
	
	@FXML
	private Button btnStart;
	
	@FXML 
	private AnchorPane mainPane;
	
	private Stage stage;
//	private String password;

    @FXML
    private Button Start;
    @FXML
    private AnchorPane MainPanel;
    MediaPlayer mp;
    AnchorPane root;
	public static void main(String[] args) {
		launch(args);

	}
	
    @FXML
    void startGame(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginScreen.fxml"));
		LoadScreen(loader);
		return;
    }
	
	public void start(Stage primaryStage) throws Exception {
		try {
//			Image image = new Image("file:1.png");
//			iv.setImage(image);
//			Group root = new Group();
//			root.getChildren().addAll(iv);
//			Scene s = new Scene(root, 500, 300);
//			primaryStage.setScene(s);
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/Welcome.fxml"));
			//Parent startpage = FXMLLoader.load(getClass().getResource("/views/LoginScreen.fxml"));
			Scene scene = new Scene (root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			File temp = new File("");
			String abPath = temp.getAbsolutePath();
			String path = new File(abPath + "/Pacman-master/src/media/tutorial.mp4").getAbsolutePath();
			mp = new MediaPlayer(new Media(new File(path).toURI().toString()));
			mv = new MediaView(mp);
			mp.setVolume(0);
			mv.setFitHeight(primaryStage.getHeight());
			mv.setFitWidth(primaryStage.getWidth());
			mp.play();
			mp.setCycleCount(MediaPlayer.INDEFINITE);
			mv.getMediaPlayer().audioSpectrumIntervalProperty();
			root.getChildren().add(mv);
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
					@Override
					public void handle(WindowEvent event)
					{
				         Platform.exit();
				         System.exit(0);
					}
			});
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
		
	
	public void openGame(ActionEvent e) throws Exception { 
		
		stage = (Stage) mainPane.getScene().getWindow();
		new StartWindow();
		stage.close();
		
	}

//	    @FXML
//	    private Button LoginButton;


//	    @FXML
//	    private PasswordField loginPassword;

	    @FXML
	    private MediaView mv;

//	    @FXML
//	    private TextField passwordText;
//
//	    @FXML
//	    private RadioButton showPassword;
//
//	    @FXML
//	    private TextField username;
	    
//	    private HashMap<String, String> nicknamesAndPasswords = new HashMap<>();
//
//		
//
//		public HashMap<String, String> getNicknamesAndPasswords() {
//			return nicknamesAndPasswords;
//		}
//
//		public void setNicknamesAndPasswords(HashMap<String, String> nicknamesAndPasswords) {
//			this.nicknamesAndPasswords = nicknamesAndPasswords;
//		}
//		private void showFailAlert(AlertType type, String title, String header, String text) {
//			Alert alert = new Alert(type);
//
//			alert.setHeight(Region.BASELINE_OFFSET_SAME_AS_HEIGHT);
//			alert.setWidth(Region.BASELINE_OFFSET_SAME_AS_HEIGHT);
//			alert.setResizable(true);
//			alert.setTitle(title);
//			alert.setHeaderText(header);
//			alert.setContentText(text);
//			alert.showAndWait();
//		}
//	    @FXML
//	    void showThePassword(ActionEvent event) {
//	    	if (showPassword.isSelected()) {
//				password = loginPassword.getText();
//				passwordText.setText(password);
//				passwordText.setVisible(true);
//				loginPassword.setVisible(false);
//				loginPassword.setText(passwordText.getText());
//				return;
//			}
//
//			loginPassword.setText(passwordText.getText());
//			loginPassword.setVisible(true);
//			passwordText.setVisible(false);
//	    }
//	    @FXML
//	    void EnterTheMenu(ActionEvent event) {
//	    	try {
//	    		if(username.getText().equals("admin")
//	    				&& (loginPassword.getText().equals("admin") || passwordText.getText().equals("admin"))) {
//	    			showAlert(AlertType.INFORMATION, "Enter The System",
//							LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + "  "
//									+ LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue() + "/"
//									+ LocalDate.now().getYear() + "\nWelcome Back Admin!",
//							"");
//	    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
//					LoadScreen(loader);
//					return;
//	    		}
//	    		if (username.getText().equals("admin") && !loginPassword.getText().equals("admin")) {
//	    			showFailAlert(AlertType.WARNING, "Error", "Sorry, cannot enter the system", "Oops... Are you sure you're the admin?"); 
//	    		}
//	    		if (username.getText().isEmpty() || (loginPassword.getText().isEmpty() && passwordText.getText().isEmpty())) {
//	    			showFailAlert(AlertType.WARNING, "Empty Fields", "You must fill all the fields", ""); 
//	    		}
//	    		if (!username.getText().equals("admin")) {
//	    			if(nicknamesAndPasswords.containsKey(username.getText())){
//	    				if(nicknamesAndPasswords.get(username.getText()).equals(passwordText.getText())) {
//	    					showAlert(AlertType.INFORMATION, "Enter The System",
//	    							LocalTime.now().getHour() + ":" + LocalTime.now().getMinute() + "  "
//	    									+ LocalDate.now().getDayOfMonth() + "/" + LocalDate.now().getMonthValue() + "/"
//	    									+ LocalDate.now().getYear() + "\nWelcome Back " + username.getText() + "!",
//	    							"");
//	    	    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
//	    					LoadScreen(loader);
//	    					return;
//	    				}
//	    				System.out.println(passwordText.getText());
//						if (nicknamesAndPasswords.containsKey(username.getText())
//								&& (!(nicknamesAndPasswords.get(username.getText()).equals(passwordText.getText())))) {
//							showFailAlert(AlertType.INFORMATION, "Incorrect Password", null,
//									"Sorry " + username.getText() + ", You entered a wrong password...");
//							System.out.println("wrong password");
//							return;
//						}
//	    			}
//	    			else if(!(nicknamesAndPasswords.containsKey(username.getText())) && !username.getText().isEmpty()){
//	    				showFailAlert(AlertType.ERROR, "Incorrect NickName", null,
//								"Sorry " + username.getText() + ", You entered a wrong nickname...");
//	    				System.out.println("no such username");
//						return;
//	    			}
//
//	    		}
//
//	    	}
//	    		catch(Exception e) {
//	    			e.getMessage();
//	    		}
//	    }
	    
//	    private void inputDialog() {
//
//			Stage s = new Stage();
//			// set title for the stage
//			s.setTitle("User Confirmation");
//
//			// create a text input dialog
//			TextInputDialog td = new TextInputDialog("");
//
//			// setHeaderText
//			td.setHeaderText("Enter your nickname:");
//			td.showAndWait();
//
//			String result = td.getResult();
//			if(result == null) {
//				showFailAlert(AlertType.ERROR, "No nickname entered", "Please do not leave this field empty", "");
//			}
//			if (result != null) {
//				for (String nickname : getNicknamesAndPasswords().keySet()) {
//					if (nickname.equals(result)) {
//						showAlert(AlertType.INFORMATION, "Success", "Your password is: " + getNicknamesAndPasswords().get(result), "");
//						return;
//					}
//
//				}
//				showFailAlert(AlertType.INFORMATION, "Failed", "Sorry, could not find you in the system...", "");
//				return;
//			} else {
//				td.close();
//				return;
//			}
//
//		}
	    
	    @FXML
	    void NewPlayer(ActionEvent event) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/NewPlayer.fxml"));
			LoadScreen(loader);
			return;
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

	    @FXML
	    void forgotPassword(ActionEvent event) {

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


		// text input dialog which asks the user to enter his nickname in order to
		// search him in the database

	}

}
