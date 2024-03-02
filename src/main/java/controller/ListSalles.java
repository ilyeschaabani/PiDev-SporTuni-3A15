package controller;

import entity.Salle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import service.SalleService;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ListSalles {

    @FXML
    private VBox event_container;

    @FXML
    void initialize() {
        loadEvents(); // Call the method to load events
    }

    private void loadEvents() {

        List<Salle> salles = SalleService.getInstance().readAll();

        for (Salle salle : salles) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Consulter.fxml"));
                AnchorPane eventCell = fxmlLoader.load();

                Consulter controller = fxmlLoader.getController();
                controller.setEventData(salle);

                // Add the cell to the container.
                event_container.getChildren().addAll(eventCell);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }}

}



