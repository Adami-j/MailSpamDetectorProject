package fr.til.projetfilrouge.mailspamdetectorproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VisualisationMailController {

    @FXML
    private Button update;
    private Message[] listeMessages;
    private ControllerBayesian controller;


    public VisualisationMailController() {
       this.listeMessages = this.getListeMessages();
    }

    @FXML
    private ListView<Message> nonSpamListView;

    @FXML
    private ListView<Message> spamListView;


    /**
     * @Author Julien ADAMI
     * fonction qui déplace le mail sélectionné de la liste des mails spam à la liste des mails de non spam
     * @param actionEvent
     */
    public void moveToNonSpam(ActionEvent actionEvent) {

        ObservableList<Message> selectedMessages = spamListView.getSelectionModel().getSelectedItems();

        // Ajouter les mails sélectionnés à la liste des mails de non spam
        nonSpamListView.getItems().addAll(selectedMessages);

        // Supprimer les mails sélectionnés de la liste des mails de spam
        spamListView.getItems().removeAll(selectedMessages);
        setSpamList(spamListView.getItems(), spamListView);
        setSpamList(nonSpamListView.getItems(), nonSpamListView);
    }

    /**
     * @Author Julien ADAMI
     * fonction qui déplace le mail sélectionné de la liste des mails non spam à la liste des mails de spam
     * @param actionEvent
     */
    public void moveToSpam(ActionEvent actionEvent) {
        ObservableList<Message> selectedMessages = nonSpamListView.getSelectionModel().getSelectedItems();

        // Ajouter les mails sélectionnés à la liste des mails de spam
        spamListView.getItems().addAll(selectedMessages);

        // Supprimer les mails sélectionnés de la liste des mails de non spam
        nonSpamListView.getItems().removeAll(selectedMessages);
        setSpamList(spamListView.getItems(), spamListView);
        setSpamList(nonSpamListView.getItems(), nonSpamListView);

    }


    /**
     * @Author Julien ADAMI
     * Affiche la liste des mails de spam dans la liste spamListView
     * @param nonSpamList
     * @param listView
     */

    public void setSpamList(List<Message> nonSpamList, ListView<Message> listView) {
        listView.setCellFactory(param -> new ListCell<Message>() {
            @Override
            protected void updateItem(Message message, boolean empty) {
                super.updateItem(message, empty);

                if (empty || message == null) {
                    setText(null);

                } else {
                    try {
                        setText(message.getSubject());

                    } catch (MessagingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });

        listView.setItems(FXCollections.observableArrayList(nonSpamList));
    }


    public Message[] getListeMessages() {
        return listeMessages;
    }

    public void setListeMessages(Message[] listeMessages) {
        this.listeMessages = listeMessages;
    }


    /**
     * Affiche le contenu du mail sélectionné dans une fenetre d'alerte informative
     * en doublecliquant sur une des deux listes spam ou non spam
     * @author Julien ADAMI
     * @param actionEvent
     */
    public void onClickUpdate(ActionEvent actionEvent) throws MessagingException, IOException {
        System.out.println("update");
        controller = new ControllerBayesian();
        String spamFolder = "src/main/resources/file/SPAM";
        String noSpamFolder = "src/main/resources/file/HAM";
        List<Message> listeMessagesSpam = new ArrayList<>();
        List<Message> listeMessagesNonSpam = new ArrayList<>();


        controller.train(spamFolder, noSpamFolder);
        this.listeMessages = this.getListeMessages();
        for(Message message : this.listeMessages){
            String messageTexte = ConnexionController.getBodyMessage(message);

            if(controller.isSpam(message.getSubject()+" "+messageTexte)){
                spamListView.getItems().add(message);
                listeMessagesSpam.add(message);

            }else {
                nonSpamListView.getItems().add(message);
                listeMessagesNonSpam.add(message);


            }
        }
        setSpamList(listeMessagesNonSpam, nonSpamListView);
        setSpamList(listeMessagesSpam, spamListView);
        setOnMouseClickedExtract(nonSpamListView);
        setOnMouseClickedExtract(spamListView);
        
        System.out.println("spam : "+listeMessagesSpam.size());
        System.out.println("non spam : "+listeMessagesNonSpam.size());


    }

    public void setOnMouseClickedExtract(ListView<Message> basicListView) {
        basicListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Message message = basicListView.getSelectionModel().getSelectedItem();
                if (message != null) {
                    try {
                        String content = message.getContent().toString();
                        alerteInfoMessage(content, message.getSubject());
                    } catch (MessagingException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void alerteInfoMessage(String content, String subject){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(subject);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
