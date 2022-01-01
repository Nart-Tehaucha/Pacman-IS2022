package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import models.Answer;
import models.Question;

public class PieChartController {
	
	@FXML
    private AnchorPane MainPanel;

    @FXML
    private PieChart pie;
    
    @FXML
    private ImageView goBack;
    
    @FXML
    private Label question;
    
    @FXML
    private Label noAnswers;

    @FXML
    void goToPageBefore(MouseEvent event) {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/ManageQuestions.fxml"));
		LoadScreen(loader);
		return;
    }
    
    @FXML
    void initialize() {

    	Question chosen = null;
    	for (Question q : SysData.readQuestionsJSON()) {
			if (q.getQuestionID() == QuestionsController.chosenQuesId) {
				chosen = q;
			}
    	}
    	question.setText(chosen.getContent());
    	ArrayList<PieChart.Data> getData = new ArrayList<>();
		int quesNum = 0;

    	for(int i=0; i<chosen.getAnswers().size(); i++) {
    		Answer a = chosen.getAnswers().get(i);
			quesNum = i+1;
			switch(quesNum) {
			  case 1:
				  getData.add(new PieChart.Data(a.getContent(), chosen.getAnsweredQuesNum1()));
			    break;
			  case 2:
				  getData.add(new PieChart.Data(a.getContent(), chosen.getAnsweredQuesNum2()));
			    break;
			  case 3:
				  getData.add(new PieChart.Data(a.getContent(), chosen.getAnsweredQuesNum3()));
			    break;
			  case 4:
				  getData.add(new PieChart.Data(a.getContent(), chosen.getAnsweredQuesNum4()));
			    break;
			}
		}
		
    	ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(getData);
    	if(chosen.getNumOfPeopleAnswered() == 0) {
    		noAnswers.setVisible(true);
    	}
    	else {
    		pie.setData(pieChartData); 
			
	      pie.getData().forEach(d -> {
	        Optional<Node> opTextNode = pie.lookupAll(".chart-pie-label").stream().filter(n -> n instanceof Text && ((Text) n).getText().contains(d.getName())).findAny();
	        if (opTextNode.isPresent()) {
	          ((Text) opTextNode.get()).setText(d.getName() + " - " + (int)d.getPieValue() + " players");
	        }
	      });
    	}
    		    //super.layoutChartChildren(pc, top, left, contentWidth, contentHeight);
		  
    }
//        ObservableList<PieChart.Data> pieChartData =
//                FXCollections.observableArrayList(
//                new PieChart.Data("Grapefruit", 13),
//                new PieChart.Data("Oranges", 25),
//                new PieChart.Data("Plums", 10),
//                new PieChart.Data("Pears", 22),
//                new PieChart.Data("Apples", 30));
//        pie = new PieChart(pieChartData);
//        pie.setTitle("Imported Fruits");
//
//        pieChartData.forEach(data ->
//                data.nameProperty().bind(
//                        Bindings.concat(
//                                data.getName(), " ", data.pieValueProperty(), " Tons"
//                        )
//                )
//        );
//   }
    
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
