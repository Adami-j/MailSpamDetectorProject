package fr.til.projetfilrouge.mailspamdetectorproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import javax.mail.Message;

public class VisualisationMailController {

    @FXML
    private ListView<Message> nonSpamListView;

    @FXML
    private ListView<Message> spamListView;

    public void moveToNonSpam(ActionEvent actionEvent) {
        
    }

    public void moveToSpam(ActionEvent actionEvent) {

    }
}
