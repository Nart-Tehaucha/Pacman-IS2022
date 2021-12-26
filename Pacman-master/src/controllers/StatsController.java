package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.Question;

public class StatsController {

    @FXML
    private AnchorPane MainPanel;
    @FXML
    private NumberAxis Percentage;
    @FXML
    private CategoryAxis question;
    @FXML
    private BarChart<String, Double> stats;
    
    @FXML
    private ImageView goBack;

    @FXML
    void goToPageBefore(MouseEvent event) {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/AdminMenu.fxml"));
		LoadScreen(loader);
		return;
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
    
	@FXML
	void initialize() {
		try {
			ArrayList<Question> allQuestions = SysData.readQuestionsJSON();
			
			XYChart.Series<String, Double> series3 = new Series<String, Double>();
			series3.setName("Easy");
			ArrayList<Question> easyQuestions = new ArrayList<>();
			for(Question q: allQuestions) {
				if(q.getDifficulty().equals("Easy")) {
					easyQuestions.add(q);
				}
			}

			for(Question q: easyQuestions) {
				double percentage = 0.0;
				if(q.getNumOfPeopleAnswered() != 0)
					percentage = (((double)q.getAnsweredCorrectly()/q.getNumOfPeopleAnswered())*100);
				series3.getData().add(new Data<String, Double>(String.valueOf(q.getQuestionID()), percentage));
			}
			
			XYChart.Series<String, Double> series2 = new Series<String, Double>();
			series2.setName("Medium");
			ArrayList<Question> mediumQuestions = new ArrayList<>();
			for(Question q: allQuestions) {
				if(q.getDifficulty().equals("Medium")) {
					mediumQuestions.add(q);
				}
			}
			for(Question q: mediumQuestions) {
				double percentage = 0.0;
				if(q.getNumOfPeopleAnswered() != 0)
					percentage =  (((double)q.getAnsweredCorrectly()/q.getNumOfPeopleAnswered())*100);
				series2.getData().add(new Data<String, Double>(String.valueOf(q.getQuestionID()), percentage));
			}
			
			XYChart.Series<String, Double> series1 = new Series<String, Double>();
			series1.setName("Hard");
			ArrayList<Question> hardQuestions = new ArrayList<>();
			for(Question q: allQuestions) {
				if(q.getDifficulty().equals("Hard")) {
					hardQuestions.add(q);
				}
			}
			for(Question q: hardQuestions) {
				double percentage = 0;
				if(q.getNumOfPeopleAnswered() != 0)
					percentage = (((double)q.getAnsweredCorrectly()/q.getNumOfPeopleAnswered())*100);
				series1.getData().add(new Data<String, Double>(String.valueOf(q.getQuestionID()), percentage));
			}
			Percentage.setAutoRanging(false);
			Percentage.setLowerBound(0);
			Percentage.setUpperBound(103);
			stats.getData().addAll(series1, series2, series3);
		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
