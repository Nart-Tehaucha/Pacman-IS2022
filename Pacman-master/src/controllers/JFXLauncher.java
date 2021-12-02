package controllers;


import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import views.StartWindow;

public class JFXLauncher extends Application {
	
	@FXML
	private Button btnStart;
	
	@FXML 
	private AnchorPane mainPane;
	
	private Stage stage;
	
	
	public static void main(String[] args) {
		launch(args);

	}
	
	public void start(Stage primaryStage) throws Exception {
		try {
//			Image image = new Image("file:1.png");
//			iv.setImage(image);
//			Group root = new Group();
//			root.getChildren().addAll(iv);
//			Scene s = new Scene(root, 500, 300);
//			primaryStage.setScene(s);
			Parent startpage = FXMLLoader.load(getClass().getResource("/views/LoginScreen.fxml"));
			Scene scene = new Scene (startpage);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
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
	
	
}
