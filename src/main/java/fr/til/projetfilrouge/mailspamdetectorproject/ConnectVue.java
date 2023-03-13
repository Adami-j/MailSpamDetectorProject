package fr.til.projetfilrouge.mailspamdetectorproject;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

/**
 * la classe Connect vue est le controleur du fxml connect-vue
 */
public class ConnectVue {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;


    /**
     * Initialise la vue de connexion.
     * Cette méthode est appelée automatiquement par JavaFX lors du chargement de la vue.
     */
    @FXML
    private void initialize() {

        // Ajoutez ici tout ce qui doit être initialisé au lancement de la vue
    }
    /**
     * Réinitialise les champs d'identification.
     * Cette méthode est appelée lorsque l'utilisateur appuie sur le bouton "Réinitialiser".
     */
    @FXML
    private void resetFields() {
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

