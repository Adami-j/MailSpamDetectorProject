

package fr.til.projetfilrouge.mailspamdetectorproject;

        import javafx.event.ActionEvent;
        import javafx.fxml.FXML;
        import javafx.scene.control.Button;
        import javafx.scene.control.PasswordField;
        import javafx.scene.control.TextField;
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
     */
    @FXML
    private void connection() {
        System.out.println("connection");
        // Ajoutez ici la logique pour se connecter
    }
}
