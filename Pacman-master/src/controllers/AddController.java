package controllers;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import models.Answer;
import models.Question;
import models.SysData;
public class AddController {


	    @FXML
	    private AnchorPane MainPanel;

	    @FXML
	    private TextField ans1;

	    @FXML
	    private TextField ans2;

	    @FXML
	    private TextField ans3;

	    @FXML
	    private TextField ans4;

	    @FXML
	    private TextField content;

	    @FXML
	    private ChoiceBox<Integer> correct = new ChoiceBox<>();

	    @FXML
	    private ChoiceBox<String> difficulty = new ChoiceBox<>();
	    
	    @FXML
	    private Button add;
	 
	    @FXML
	    private ImageView goBack;


		ObservableList<Integer> nums = FXCollections.observableArrayList(1,2,3,4);
		ObservableList<String> difficulties = FXCollections.observableArrayList("Easy", "Medium", "Hard");



	    @FXML
	    void goToPageBefore(MouseEvent event) {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
			LoadScreen(loader);
			return;
	    }
	    

	    @FXML
	    void submitAdd(ActionEvent event) {
			try {
		    	ArrayList<Answer> answers = new ArrayList<>();
				String contentResult = content.getText();
				String diffResult = difficulty.getValue();
				int correctResult = correct.getValue();
				Question newQuestion = new Question(contentResult, diffResult, null, correctResult);
				String ans1Result = ans1.getText();
				answers.add(new Answer(newQuestion.getQuestionID(), ans1Result));
				String ans2Result = ans2.getText();
				answers.add(new Answer(newQuestion.getQuestionID(), ans2Result));
				String ans3Result = ans3.getText();
				answers.add(new Answer(newQuestion.getQuestionID(), ans3Result));
				String ans4Result = ans4.getText();
				answers.add(new Answer(newQuestion.getQuestionID(), ans4Result));
				newQuestion.setAnswers(answers);
				if(contentResult.isBlank() || diffResult.isEmpty() || correct.getSelectionModel().isEmpty() || ans1Result.isBlank() ||
						ans2Result.isBlank() || ans3Result.isBlank() || ans4Result.isBlank() ) {
					showAlert(AlertType.WARNING, "Failed to Add", "You must fill all the fields", null);
	       			return;
				}
				try {
					if(SysData.addQuestionToJSON(newQuestion)) {
						showAlert(AlertType.INFORMATION, "Success", "You successfully added a new question", "");
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ManageQuestions.fxml"));
						LoadScreen(loader);
						return;
					}
					else {
						showAlert(AlertType.WARNING, "Failed to Add", "Sorry, something went wrong...", null);
		       			return;
					}
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}catch(NullPointerException np) {
				showAlert(AlertType.WARNING, "Failed to Add", "You must fill all the fields", null);
       			return;
			}
//		    else {
//				showFailAlert(AlertType.INFORMATION, "Failed", "Sorry, could not find you in the system...", "");
//				content.close();
//				return;
//			}
	    }

		// ********************************* pop-up message ********************************* //
		private void showAlert(AlertType type, String title, String header, String text) {
			Alert alert = new Alert(type);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setResizable(true);
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(text);
//			if(type == AlertType.ERROR) {
//		 		File temp = new File("");
//				String abPath = temp.getAbsolutePath();
//				String path = new File(abPath+"/src/Media/fail.mp3").getAbsolutePath();
//		 		MediaPlayer sound = new MediaPlayer(new Media(new File(path).toURI().toString()));
//		 		sound.play();
//	 		}
//	 		else {
//		 		File temp = new File("");
//				String abPath = temp.getAbsolutePath();
//				String path = new File(abPath+"/src/Media/success.mp3").getAbsolutePath();
//		 		MediaPlayer sound = new MediaPlayer(new Media(new File(path).toURI().toString()));
//		 		sound.play();
//	 		}
			alert.showAndWait();
		}
		
	    @FXML
		void initialize() {
	    	correct.setItems(nums);
	    	difficulty.setItems(difficulties);
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

	}


