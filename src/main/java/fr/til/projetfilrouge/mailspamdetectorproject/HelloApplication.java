package fr.til.projetfilrouge.mailspamdetectorproject;

import fr.til.projetfilrouge.mailspamdetectorproject.View.ConnectVueController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        //FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("connect-vue.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 300, 375);
        stage.setResizable(false);
        ConnectVueController controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);
        stage.setResizable(false);
        stage.setTitle("Spam detector");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}