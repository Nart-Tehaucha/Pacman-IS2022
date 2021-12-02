package controllers;

import model.Record;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class RecordsController {
		@FXML
	    private AnchorPane MainPanel;

	    @FXML
	    private Label WeAreTheChampions;

	    @FXML
	    private TableView<Record> allRecords;

	    @FXML
	    private Button backToMenu;

	    @FXML
	    private TableColumn<Record, String> nicknames;

	    @FXML
	    private TableColumn<Record, Integer> places;

	    @FXML
	    private Button playAgain;

	    @FXML
	    private TableColumn<Record, Integer> scores;

	    @FXML
	    private TableColumn<Record, String> times;

		public RecordsController(AnchorPane mainPanel, Label weAreTheChampions, TableView<Record> allRecords,
				Button backToMenu, TableColumn<Record, String> nicknames, TableColumn<Record, Integer> places,
				Button playAgain, TableColumn<Record, Integer> scores, TableColumn<Record, String> times) {
			super();
			MainPanel = mainPanel;
			WeAreTheChampions = weAreTheChampions;
			this.allRecords = allRecords;
			this.backToMenu = backToMenu;
			this.nicknames = nicknames;
			this.places = places;
			this.playAgain = playAgain;
			this.scores = scores;
			this.times = times;
		}

		public AnchorPane getMainPanel() {
			return MainPanel;
		}

		public void setMainPanel(AnchorPane mainPanel) {
			MainPanel = mainPanel;
		}

		public Label getWeAreTheChampions() {
			return WeAreTheChampions;
		}

		public void setWeAreTheChampions(Label weAreTheChampions) {
			WeAreTheChampions = weAreTheChampions;
		}

		public TableView<Record> getAllRecords() {
			return allRecords;
		}

		public void setAllRecords(TableView<Record> allRecords) {
			this.allRecords = allRecords;
		}

		public Button getBackToMenu() {
			return backToMenu;
		}

		public void setBackToMenu(Button backToMenu) {
			this.backToMenu = backToMenu;
		}

		public TableColumn<Record, String> getNicknames() {
			return nicknames;
		}

		public void setNicknames(TableColumn<Record, String> nicknames) {
			this.nicknames = nicknames;
		}

		public TableColumn<Record, Integer> getPlaces() {
			return places;
		}

		public void setPlaces(TableColumn<Record, Integer> places) {
			this.places = places;
		}

		public Button getPlayAgain() {
			return playAgain;
		}

		public void setPlayAgain(Button playAgain) {
			this.playAgain = playAgain;
		}

		public TableColumn<Record, Integer> getScores() {
			return scores;
		}

		public void setScores(TableColumn<Record, Integer> scores) {
			this.scores = scores;
		}

		public TableColumn<Record, String> getTimes() {
			return times;
		}

		public void setTimes(TableColumn<Record, String> times) {
			this.times = times;
		}
	    
	    
}
