package controllers;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import models.Answer;
import models.Question;
import models.SysData;

public class EditQuestionsController {

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
    private Button submit;
 

	ObservableList<Integer> nums = FXCollections.observableArrayList(1,2,3,4);
	ObservableList<String> difficulties = FXCollections.observableArrayList("Easy", "Medium", "Hard");

    @FXML
    void submitChange(ActionEvent event) {

    }

    @FXML
	void initialize() {
    	correct.setItems(nums);
    	difficulty.setItems(difficulties);
    	String chosenQuesContent = null;
    	String diff = null;
    	int correctQues = 0;
    	ArrayList<Answer> answersOfChosenQues = new ArrayList<>();
    
    	for(Question q: SysData.readQuestionsJSON()) {
    		if(QuestionsController.chosenQuesId == q.getQuestionID()) {
    			answersOfChosenQues.addAll(q.getAnswers());
    			chosenQuesContent = q.getContent();
    			diff = q.getDifficulty();
    			correctQues = q.getCorrect_ans();
    		}
    	}
    	System.out.println(QuestionsController.chosenQuesId);
    	System.out.println(answersOfChosenQues);
    	int i = 0;
    	ans1.setText(answersOfChosenQues.get(i++).getContent());
    	ans2.setText(answersOfChosenQues.get(i++).getContent());
    	ans3.setText(answersOfChosenQues.get(i++).getContent());
    	ans4.setText(answersOfChosenQues.get(i++).getContent());
    	content.setText(chosenQuesContent);
    	difficulty.setValue(diff);
    	correct.setValue(correctQues);
    }
}
