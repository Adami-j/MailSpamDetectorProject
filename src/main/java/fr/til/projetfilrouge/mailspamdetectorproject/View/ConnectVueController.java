

package fr.til.projetfilrouge.mailspamdetectorproject.View;

        import fr.til.projetfilrouge.mailspamdetectorproject.Controller.ConnexionController;
        import fr.til.projetfilrouge.mailspamdetectorproject.Controller.VisualisationMailController;
        import fr.til.projetfilrouge.mailspamdetectorproject.SpamMailDetectorApp;
        import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;
        import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModelInterface;

        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.*;
        import javafx.stage.DirectoryChooser;
        import javafx.stage.Stage;

        import javax.mail.Message;
        import javax.mail.MessagingException;
        import javax.mail.Session;
        import javax.mail.internet.MimeMessage;
        import java.io.BufferedReader;
        import java.io.File;
        import java.io.FileReader;
        import java.io.IOException;
        import java.util.ArrayList;
        import java.util.List;
        import java.util.Properties;

/**
 * la classe Connect vue est le controleur du fxml connect-vue
 */
public class ConnectVueController {

    @FXML
    public TextField usernameField;

    @FXML
    public PasswordField passwordField;

    @FXML
    Button buttonReset;

    @FXML
    Button buttonConnection;

    @FXML
    private TextField selectedDirectoryLabel;

    private Stage primaryStage;

    /**
     * Réinitialise les champs d'identification.
     * Cette méthode est appelée lorsque l'utilisateur appuie sur le bouton "Réinitialiser".
     */
    @FXML
    public  void resetFields() {
        usernameField.setText("");
        passwordField.setText("");
    }

    /**
     * Se connecte à l'application en utilisant les identifiants entrés par l'utilisateur.
     * Cette méthode est appelée lorsque l'utilisateur appuie sur le bouton "Se connecter".
     * @author Julien ADAMI
     */
    @FXML
    private void connection()  {

        String login = usernameField.getText();
        String password = passwordField.getText();
        UserModelInterface userModel = UserModelInterface.getInstance();
        userModel.setLogin(login);
        userModel.setPassword(password);
        try {
            ConnexionController connexionController = ConnexionController.getInstance((UserModel) userModel);

            ouverturePageVisualisationMail(connexionController.getMessageInbox());
        } catch (MessagingException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(String.valueOf(e));
            alert.show();
        }

    }



    /**
     * @author Julien ADAMI
     * Méthode qui crée une nouvelle fenêtre pour afficher les mails dans un premier temps
     * puis qui les tries en 3 catégories : spams, non spams, suspects
     * et qui ferme la fenêtre de connexion
     */
    public void ouverturePageVisualisationMail(Message[] messages) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SpamMailDetectorApp.class.getResource("visualisation-mail.fxml"));
        Parent root = fxmlLoader.load();
        VisualisationMailController visualisationMailController = fxmlLoader.getController();
        visualisationMailController.setListeMessages(messages);
        Stage secondStage = new Stage();
        Scene scene = new Scene(root, 600, 500);
        secondStage.setScene(scene);
        secondStage.show();

        this.primaryStage.close();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    public void selectFile() {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        File selectedDirectory = directoryChooser.showDialog(new Stage());
        if (selectedDirectory != null) {
            selectedDirectoryLabel.setText(selectedDirectory.getAbsolutePath());
        }
    }

    /**
     * @author Julien ADAMI
     * fonction qui s'active lors du click sur le bouton "valider", elle permet de récupérer les fichiers txt d'un dossier
     * et de les convertir en messages en les transmettant à la vue de visualisation des mails
     * @throws IOException
     * @throws MessagingException
     */
    public void fileConnection() throws IOException, MessagingException {
        if(selectedDirectoryLabel == null ||selectedDirectoryLabel.getText() == null ||
                getFilesFromDirectory(new File(selectedDirectoryLabel.getText())) ==null ||  getFilesFromDirectory(new File(selectedDirectoryLabel.getText())).length < 1) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Aucun fichier txt dans le dossier");
            alert.show();
        } else {
            File[] textFiles = getFilesFromDirectory(new File(selectedDirectoryLabel.getText()));
            List<Message> messages = textFilesToMessages(textFiles);
            Message[] messagesArray = messages.toArray(new Message[0]);

            ouverturePageVisualisationMail(messagesArray);

        }
    }


    //fonction qui permet de récupérer les fichiers txt d'un dossier
    public File[] getFilesFromDirectory(File directory) {
        return directory.listFiles((dir, name) -> name.toLowerCase().endsWith(".txt"));
    }

    /**
     * @author Julien ADAMI
     * Convertit un tableau de fichiers texte en tableau de messages
     * @param textFiles
     * @return
     * @throws IOException
     * @throws MessagingException
     */
    public List<Message> textFilesToMessages(File[] textFiles) throws IOException, MessagingException {
        List<Message> messages = new ArrayList<>();
        Session session = Session.getInstance(new Properties());

        for (File file : textFiles) {
            StringBuilder contentBuilder = new StringBuilder();
            MimeMessage message = new MimeMessage(session);
            String sujet = "";
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                int lineCount = 0;
                while ((line = reader.readLine()) != null) {
                    // permet de ne pas compter la première ligne comme une ligne de texte car c'est le sujet : objet du mail
                    if (lineCount!=0){
                        contentBuilder.append(line).append("\n");
                    }else {
                        sujet = line;
                    }
                    lineCount++;
                }
            }
            message.setContent(contentBuilder.toString(), "text/plain");
            message.setSubject(sujet);
            messages.add(message);
        }
        return messages;
    }





}
