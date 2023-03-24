package fr.til.projetfilrouge.mailspamdetectorproject.Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;

import javafx.scene.control.*;

import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class VisualisationMailController {

    @FXML
    private Button update;
    private Message[] listeMessages;

    @FXML
    private Label nbNonSpam;
    @FXML
    private Label nbSpam;

    @FXML
    private Label proucentageNonSpam;
    @FXML
    private Label proucentageSpam;



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
     *
     */
    public void moveToNonSpam() {

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
     *
     */
    public void moveToSpam() {
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
                        throw new RuntimeException("Problème lors de l'affichage de la liste des mails");
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
     */
    public void onClickUpdate() throws MessagingException, IOException {
        ControllerBayesian controllerBayesian;
        controllerBayesian = new ControllerBayesian();
        String spamFolder = "src/main/resources/file/SPAM";
        String noSpamFolder = "src/main/resources/file/HAM";
        List<Message> listeMessagesSpam = new ArrayList<>();
        List<Message> listeMessagesNonSpam = new ArrayList<>();

        controllerBayesian.train(spamFolder, noSpamFolder);
        this.listeMessages = this.getListeMessages();
        for(Message message : this.listeMessages){
            String messageTexte = ConnexionController.getBodyMessage(message);

            if(controllerBayesian.isSpam(message.getSubject()+" "+messageTexte)){
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

        nbNonSpam.setText("Non spam : "+listeMessagesNonSpam.size());
        nbSpam.setText("Spam : "+listeMessagesSpam.size());

        double pourcentageSpam = ((double)listeMessagesSpam.size() / (listeMessagesNonSpam.size() + listeMessagesSpam.size())) * 100;
        proucentageSpam.setText(String.format("%.2f", pourcentageSpam)+" %");

        double pourcentageNonSpam = ((double)listeMessagesNonSpam.size() / (listeMessagesNonSpam.size() + listeMessagesSpam.size())) * 100;
        proucentageNonSpam.setText(String.format("%.2f", pourcentageNonSpam)+" % ");


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
