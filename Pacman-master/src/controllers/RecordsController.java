package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.SysData;

// controls the records screen
public class RecordsController {
	
		// screen's components
	   @FXML
	    private AnchorPane MainPanel;

	    @FXML
	    private Label WeAreTheChampions;

	    @FXML
	    private TableView<RecordWinner> allRecords;

	    @FXML
	    private ImageView goBack;

	    @FXML
	    private TableColumn<RecordWinner, String> nickname;

	    @FXML
	    private TableColumn<RecordWinner, Integer> score;

	    @FXML
	    private TableColumn<RecordWinner, Double> time;

	    @FXML
	    private TableColumn<RecordWinner, Boolean> trophy;

		
	    @FXML
	    void goToPageBefore(MouseEvent event) {
	    	FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Menu.fxml"));
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
	    
	    
	  
	    
	   
	    
	    
	    ObservableList<RecordWinner> tableList = FXCollections.observableArrayList();

	    
	    private ArrayList<RecordWinner> TopTenWinnersAL = new ArrayList<RecordWinner>();
	    
	 
		@FXML
		public void initialize() {
			try {
				TopTenWinnersAL= SysData.initializeTopTen();
				tableList.clear();
	
				tableList.addAll(TopTenWinnersAL);
	
				 trophy.setCellValueFactory(new PropertyValueFactory<RecordWinner, Boolean>("did_Earn_Trophy"));
				 nickname.setCellValueFactory(new PropertyValueFactory<RecordWinner, String>("userName"));
				 score.setCellValueFactory(new PropertyValueFactory<RecordWinner, Integer>("points"));
				 time.setCellValueFactory(new PropertyValueFactory<RecordWinner, Double>("time"));
				 allRecords.setItems(tableList);
			}
			catch(NullPointerException n) {
				allRecords.setVisible(true);
			}
			catch(Exception e) {
				allRecords.setVisible(true);
			}
			
		}
		
		


}
