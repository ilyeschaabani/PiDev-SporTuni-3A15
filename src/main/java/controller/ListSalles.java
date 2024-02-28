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
        assert event_container != null : "fx:id=\"event_container\" was not injected: check your FXML file 'ListSalles.fxml'.";
        loadEvents(); // Call the method to load events
    }

    private void loadEvents() {
        // Suppose readAll is a method to fetch salle data from the service.
        SalleService ss = new SalleService();
        List<Salle> salles = ss.readAll();

        // Clear existing content.
        event_container.getChildren().clear();

        for (Salle salle : salles) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Consulter.fxml"));
                AnchorPane eventCell = fxmlLoader.load();

                // Set Salle data to the cell's controllers here.
                Consulter controller = fxmlLoader.getController();
                controller.setEventData(salle);

                // Add the cell to the container.
                event_container.getChildren().add(eventCell);
            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            } catch (Exception e) {
                e.printStackTrace();
                // Handle other exceptions
            }
        }

    }
        }


