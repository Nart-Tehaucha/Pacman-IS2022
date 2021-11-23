package controllers;


import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import views.StartWindow;

public class JFXLauncher extends Application {
	
	@FXML
	private Button btnStart;
	
	@FXML 
	private AnchorPane mainPane;
	
	Stage stage;
	
	public static void main(String[] args) {
		System.out.println("test");
		launch();

	}
	
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent startpage = FXMLLoader.load(getClass().getResource("/views/HomeScreen.fxml"));
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
