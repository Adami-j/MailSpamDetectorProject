

package fr.til.projetfilrouge.mailspamdetectorproject.View;

        import fr.til.projetfilrouge.mailspamdetectorproject.Controller.ConnexionController;
        import fr.til.projetfilrouge.mailspamdetectorproject.HelloApplication;
        import fr.til.projetfilrouge.mailspamdetectorproject.Model.UserModel;
        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.fxml.FXMLLoader;
        import javafx.scene.Parent;
        import javafx.scene.Scene;
        import javafx.scene.control.Alert;
        import javafx.scene.control.Button;
        import javafx.scene.control.PasswordField;
        import javafx.scene.control.TextField;
        import javafx.scene.layout.StackPane;
        import javafx.stage.Stage;

        import javax.mail.MessagingException;
        import java.io.IOException;

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

    private Stage primaryStage;

    /**
     * Initialise la vue de connexion.
     * Cette méthode est appelée automatiquement par JavaFX lors du chargement de la vue.
     */
    @FXML
    private void initialize() {
        /*try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("connect-vue.fxml"));
            loader.setController(this);
            AnchorPane pane = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }
    /**
     * Réinitialise les champs d'identification.
     * Cette méthode est appelée lorsque l'utilisateur appuie sur le bouton "Réinitialiser".
     */
    @FXML
    public  void resetFields() {
        System.out.println("reset");
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
        //paramètres pour test
        login = "equipedetectorspmtest@outlook.fr";
        password="SpamTestBaiern12..";
        System.out.println(login+password);
        UserModel userModel = UserModel.getInstance();
        userModel.setLogin(login);
        userModel.setPassword(password);
        try {
            ConnexionController connexionController = ConnexionController.getInstance(userModel);
            ouverturePageVisualisationMail();
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
    public void ouverturePageVisualisationMail() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("visualisation-mail.fxml"));
        Parent root = fxmlLoader.load();

        Stage secondStage = new Stage();
        StackPane rootParent = new StackPane();
        Scene scene = new Scene(root, 300, 250);
        secondStage.setScene(scene);
        secondStage.show();
        this.primaryStage.close();
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
