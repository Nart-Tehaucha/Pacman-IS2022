package controllers;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import models.RecordWinner;

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
	    private Button allTheRecords;

	    @FXML
	    private Button myRecords;

	    @FXML
	    void showAllRecords(ActionEvent event) {
	    	initialize();
	    	
	    }

	    @FXML
	    void showMyRecords(ActionEvent event) {
	    	ArrayList<RecordWinner> allThePlayers = SysData.initializeTopTen();
	    	ArrayList<RecordWinner> myRecordsList = new ArrayList<>();
	    	for(RecordWinner rw: allThePlayers) {
	    		if(rw.getUserName().equals(SysData.getThisUser())) {
	    			myRecordsList.add(rw);
	    		}
	    	}
	    	try {
				tableList.clear();
	
				tableList.addAll(myRecordsList);
	
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
