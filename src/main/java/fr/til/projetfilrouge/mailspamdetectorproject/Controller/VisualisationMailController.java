package fr.til.projetfilrouge.mailspamdetectorproject.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import javax.mail.Message;

public class VisualisationMailController {

    private Message[] listeMessages;

    public VisualisationMailController() {
       this.listeMessages = this.getListeMessages();
    }

    @FXML
    private ListView<Message> nonSpamListView;

    @FXML
    private ListView<Message> spamListView;

    public void moveToNonSpam(ActionEvent actionEvent) {

    }

    public void moveToSpam(ActionEvent actionEvent) {

    }

    public Message[] getListeMessages() {
        return listeMessages;
    }

    public void setListeMessages(Message[] listeMessages) {
        this.listeMessages = listeMessages;
    }


}
