package org.example;


import controllers.AjouterCompetitionController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/AjouterCompetition.fxml"));
        Parent root = fxmlLoader.load();
        AjouterCompetitionController controller = fxmlLoader.getController();

        Scene scene = new Scene(root, 800, 600); // Taille personnalisée de la scène
        stage.setTitle("competition");
        stage.setScene(scene);
        stage.show();

        // Ajouter des écouteurs pour le contrôle de saisie
        //controller.setupValidation();
    }


}
