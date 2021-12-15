package controllers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import models.Answer;
import models.Question;
import models.SysData;

public class QuestionsController {
	
	    @FXML
	    private Button Add;
	    
		@FXML
	    private Button Delete;

	    @FXML
	    private Button Edit;

	    @FXML
	    private AnchorPane MainPanel;

	    @FXML
	    private ImageView goBack;

	    @FXML
	    private TableColumn<Question, ArrayList<Answer>> quesAnswers;

	    @FXML
	    private TableColumn<Question, String> quesContent;

	    @FXML
	    private TableColumn<Question, Integer> quesCorrectAns;

	    @FXML
	    private TableColumn<Question, String> quesDifficulty;

	    @FXML
	    private TableColumn<Question, Integer> quesId;

	    @FXML
	    private TableView<Question> questionsTable;

	    @FXML
	    private AnchorPane popUp;
	    
	    ObservableList<Question> tableList = FXCollections.observableArrayList();
	    
	    static int chosenQuesId;
	    
	    ObservableList<Question> allQuestions = FXCollections.observableArrayList(SysData.readQuestionsJSON());
	    ObservableList<Integer> nums = FXCollections.observableArrayList(1,2,3,4);
	   
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
	    
	    @FXML
		void PopUpLoadScreen(FXMLLoader loader) {
			try {
				AnchorPane pane = loader.load();
				popUp.getChildren().clear();
				popUp.getChildren().add(pane);

			} catch (IOException e) {
				e.printStackTrace();
			}
	    }
	    
	    @FXML
	    void addAQuestion(ActionEvent event) {
	    	//inputDialog();
	    	FXMLLoader popUpLoader = new FXMLLoader(getClass().getResource("/views/AddQuestion.fxml"));
			PopUpLoadScreen(popUpLoader);
			return;
	    }
	    private void inputDialog() {

			Stage s = new Stage();
			// set title for the stage
			s.setTitle("Add A New Question");

			// create a text input dialog
			TextInputDialog content = new TextInputDialog("");

			// setHeaderText
			content.setHeaderText("Enter the question:");
			content.showAndWait();
			// create a text input dialog
			TextInputDialog difficulty = new TextInputDialog("");
			// setHeaderText
			difficulty.setHeaderText("Difficulty:");
			difficulty.showAndWait();
			TextInputDialog ans1 = new TextInputDialog("");
			// setHeaderText
			ans1.setHeaderText("Answer 1:");
			ans1.showAndWait();
			TextInputDialog ans2 = new TextInputDialog("");
			// setHeaderText
			ans2.setHeaderText("Answer 2:");
			ans2.showAndWait();
			TextInputDialog ans3 = new TextInputDialog("");
			// setHeaderText
			ans3.setHeaderText("Answer 3:");
			ans3.showAndWait();
			TextInputDialog ans4 = new TextInputDialog("");
			// setHeaderText
			ans4.setHeaderText("Answer 4:");
			ans4.showAndWait();
			TextInputDialog correct = new TextInputDialog("");
			// setHeaderText
			correct.setHeaderText("Correct answer:");
			correct.showAndWait();
			
			ArrayList<Answer> answers = new ArrayList<>();
			String contentResult = content.getResult();
			String diffResult = difficulty.getResult();
			int correctResult = Integer.parseInt(correct.getResult());
			Question newQuestion = new Question(contentResult, diffResult, null, correctResult);
			String ans1Result = ans1.getResult();
			answers.add(new Answer(newQuestion.getQuestionID(), ans1Result));
			String ans2Result = ans2.getResult();
			answers.add(new Answer(newQuestion.getQuestionID(), ans2Result));
			String ans3Result = ans3.getResult();
			answers.add(new Answer(newQuestion.getQuestionID(), ans3Result));
			String ans4Result = ans4.getResult();
			answers.add(new Answer(newQuestion.getQuestionID(), ans4Result));
			newQuestion.setAnswers(answers);
			try {
				if(SysData.addQuestionToJSON(newQuestion)) {
					showAlert(AlertType.INFORMATION, "Success", "You successfully added a new question", "");
					return;
					}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
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
	    void deleteQuestion(ActionEvent event) {
	    	ArrayList<Question> selectedQuestions = new ArrayList<>();
	    	selectedQuestions.addAll(questionsTable.getSelectionModel().getSelectedItems());
	    	tableList.addAll(SysData.readQuestionsJSON());
	    	System.out.println("The question- " + selectedQuestions.get(0).getDifficulty() + selectedQuestions.get(0).getQuestionID());
	    	System.out.println("questions before: " + SysData.readQuestionsJSON());
			for (Question q : SysData.readQuestionsJSON()) {
				System.out.println("In the for");
				if (selectedQuestions.get(0).getQuestionID() == q.getQuestionID()) {
					System.out.println("after the if");
					SysData.deleteQuestionFromJSONByID(q.getQuestionID());
					System.out.println("The ques i chose- " + questionsTable.getSelectionModel().getSelectedItems().get(0).getQuestionID());
				}
			}

			System.out.println("questions after: " + SysData.readQuestionsJSON());
			tableList.removeAll(selectedQuestions);
			questionsTable.setItems(null);
			questionsTable.setItems(tableList);

	    }

	    @FXML
	    void editQuestion(ActionEvent event) {
	       	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditQuestions.fxml"));
	       	chosenQuesId = questionsTable.getSelectionModel().getSelectedItem().getQuestionID();
	       	System.out.println(chosenQuesId);
			LoadScreen(loader);
			return;
	    }

	    @FXML
	    void goToPageBefore(MouseEvent event) {
	       	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
			LoadScreen(loader);
			return;
	    }
	    
		@FXML
		void initialize() {
			System.out.println(allQuestions);
			try {
				//questionsTable.setVisible(true);
				quesId.setCellValueFactory(new PropertyValueFactory<Question, Integer>("questionID"));
				quesContent.setCellValueFactory(new PropertyValueFactory<Question, String>("content"));
				quesDifficulty.setCellValueFactory(new PropertyValueFactory<Question, String>("difficulty"));
				quesAnswers.setCellValueFactory(new PropertyValueFactory<Question, ArrayList<Answer>>("answers"));
				quesCorrectAns.setCellValueFactory(new PropertyValueFactory<Question, Integer>("correct_ans"));
				questionsTable.setItems(allQuestions);

			} catch (NullPointerException p) {
				//questionsTable.setVisible(true);
			}
		}




}
