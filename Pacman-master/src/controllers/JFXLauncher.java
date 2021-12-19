package controllers;

import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class JFXLauncher extends Application {
	
	@FXML
	private Button btnStart;
	
	@FXML 
	private AnchorPane mainPane;
	
	private Stage stage;

    @FXML
    private Button Start;
   
    @FXML
    private AnchorPane MainPanel;
   
    MediaPlayer mp;
    
    @FXML
    private MediaView mv;
    
    AnchorPane root;
	
    public static void main(String[] args) {
		launch(args);
	}
	
    // go to the Login screen where you enter to the system
    @FXML
    void startGame(ActionEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/LoginScreen.fxml"));
		LoadScreen(loader);
		return;
    }
	
    // the first screen that comes up- contains a video
	public void start(Stage primaryStage) throws Exception {
		try {
			root = (AnchorPane) FXMLLoader.load(getClass().getResource("/views/Welcome.fxml"));
			Scene scene = new Scene (root);
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.setTitle("Pacman IS-21");
			primaryStage.getIcons().add(new Image("/views/images/sad_pacman.jpg"));
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
