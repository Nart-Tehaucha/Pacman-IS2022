package controllers;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.effect.Reflection;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

// controller of the instructions screen with all the details of how to play the game
public class InstructionsController {

	// the screen's components
    @FXML
    private AnchorPane MainPanel;

    @FXML
    private ImageView goBack;
    
    @FXML
    private MediaView mv;
    
    @FXML
    private Button showButton;

    
	// when the button is clicked the video is shown/taken down
	@FXML
	void videoPlayer(ActionEvent event) {
		if(!mv.isVisible()) {
			mv.setVisible(true);
			File temp = new File("");
			String abPath = temp.getAbsolutePath();
			String path = new File(abPath + "/Pacman-master/src/media/demo.mp4").getAbsolutePath();
			MediaPlayer mp = new MediaPlayer(new Media(new File(path).toURI().toString()));
			mv = new MediaView(mp);
			Reflection r = new Reflection();
			r.setBottomOpacity(0.9);
			mv.setEffect(r);
			mv.setX(70);
			mv.setY(70);
			mp.setVolume(0);
			mp.play();
			mp.setCycleCount(MediaPlayer.INDEFINITE);
			mv.getMediaPlayer().audioSpectrumIntervalProperty();
			MainPanel.getChildren().add(mv);
		}
		else {
			mv.setVisible(false);
			MainPanel.getChildren().remove(mv);
		}
		
	}


    // go back to the menu screen
    @FXML
    void goToPageBefore(MouseEvent event) {
    	if(SysData.getThisUser().equals("admin")) {
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminMenu.fxml"));
			LoadScreen(loader);
			return;
    	}
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
		LoadScreen(loader);
		return;
    }
    
    // load another fxml document
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
