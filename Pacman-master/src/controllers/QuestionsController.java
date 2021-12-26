package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import models.Answer;
import models.Question;

// controls the questions manager
public class QuestionsController {
	
		// screen's components
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

	    // table's columns- shows data of Question class
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
	    
	    ObservableList<Question> tableList = FXCollections.observableArrayList();
	    
	    static int chosenQuesId;
	    @FXML
	    private Button statsBtn;

	    
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
	    void addAQuestion(ActionEvent event) {
	    	FXMLLoader Loader = new FXMLLoader(getClass().getResource("/views/AddQuestion.fxml"));
			LoadScreen(Loader);
			return;
	    }

		// ********************************* pop-up message ********************************* //
		private void showAlert(AlertType type, String title, String header, String text) {
			Alert alert = new Alert(type);
			alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
			alert.setResizable(true);
			alert.setTitle(title);
			alert.setHeaderText(header);
			alert.setContentText(text);
			alert.showAndWait();
		}
	    
	    @FXML
	    void deleteQuestion(ActionEvent event) {
	    	try {
		    	ArrayList<Question> selectedQuestions = new ArrayList<>();
		    	selectedQuestions.addAll(questionsTable.getSelectionModel().getSelectedItems());
		    	tableList.addAll(SysData.readQuestionsJSON());
				for (Question q : SysData.readQuestionsJSON()) {
					if (selectedQuestions.get(0).getQuestionID() == q.getQuestionID()) {
						if(SysData.deleteQuestionFromJSONByID(q.getQuestionID())) {
							showAlert(AlertType.INFORMATION, "Success", "You have successfully deleted the question", null);
							tableList.removeAll(selectedQuestions);
							questionsTable.setItems(null);
							questionsTable.setItems(tableList);
							FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ManageQuestions.fxml"));
							LoadScreen(loader);
							return;
						}
						else {
							showAlert(AlertType.WARNING, "Not Deleted", "Sorry...Something has prevented the deletion", null);
			       			return;
						}
						
					}
				}
	    	}
	    	catch(Exception e) {
       			showAlert(AlertType.WARNING, "Oops", "It looks like no question has been chosen", null);
       			return;
	    	}

	    }
	    // go to edit an existing question screen with the question's id
	    @FXML
	    void editQuestion(ActionEvent event) {
	       	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/EditQuestions.fxml"));
       		if(questionsTable.getSelectionModel().getSelectedItem() != null) {
		       	chosenQuesId = questionsTable.getSelectionModel().getSelectedItem().getQuestionID();
       		}
       		else {
       			showAlert(AlertType.WARNING, "Oops", "It looks like no question has been chosen", null);
       			return;
       		}
			LoadScreen(loader);
			return;
	    }

	    // go back to the menu
	    @FXML
	    void goToPageBefore(MouseEvent event) {
	       	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminMenu.fxml"));
			LoadScreen(loader);
			return;
	    }
	    
	    @FXML
	    void goToStats(ActionEvent event) {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/QuestionsStatistics.fxml"));
			LoadScreen(loader);
			return;
	    }
	    
	    // initialize the screen
		@FXML
		void initialize() {
			try {
				quesId.setCellValueFactory(new PropertyValueFactory<Question, Integer>("questionID"));
				quesContent.setCellValueFactory(new PropertyValueFactory<Question, String>("content"));
				quesDifficulty.setCellValueFactory(new PropertyValueFactory<Question, String>("difficulty"));
				quesAnswers.setCellValueFactory(new PropertyValueFactory<Question, ArrayList<Answer>>("answers"));
				quesCorrectAns.setCellValueFactory(new PropertyValueFactory<Question, Integer>("correct_ans"));
				questionsTable.setItems(allQuestions);

			} catch (NullPointerException p) {
				questionsTable.setVisible(true);
			}
		}




}
