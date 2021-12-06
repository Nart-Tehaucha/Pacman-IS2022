package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class QuestionsController {

    @FXML
    private AnchorPane MainPanel;

    @FXML
    private ListView<?> answers;

    @FXML
    private TextField question;
    
    @FXML
    private Button Submit;

    @FXML
    private ImageView goBack;

    @FXML
    void goToPageBefore(MouseEvent event) {

    }

}
