package org.example;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.stage.Stage;




public class MainFX extends Application {


    @Override
    public void start(Stage Stage) throws Exception {

        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/ReserverEvenement.fxml"));
        Parent root= fxmlLoader.load();
        Scene scene=new Scene(root);
        Stage.setScene(scene);
        Stage.setTitle("Gerer evenements");
        Stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}



