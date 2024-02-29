package controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFx extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader=new FXMLLoader(getClass().getResource("/Statistique.fxml"));

        Screen screen = Screen.getPrimary();
        double screenWidth = screen.getBounds().getWidth();
        double screenHeight = screen.getBounds().getHeight();

        System.out.println(screenHeight);
        Scene scene = new Scene(fxmlLoader.load(), screenWidth, screenHeight);
        stage.setTitle("Discipline");
        stage.setScene(scene);
        stage.show();
    }
}
