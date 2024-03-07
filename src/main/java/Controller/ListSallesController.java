package Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.List;
import Entity.Salle;
import javafx.stage.Stage;


public class ListSallesController {
    @FXML
    private VBox event_container;

    @FXML
    void initialize() {
        loadEvents(); // Call the method to load events
    }

    private void loadEvents() {

        List<Salle> salles = Service.SalleService.getInstance().readAll();

        for (Salle salle : salles) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/Consultet.fxml"));
                AnchorPane eventCell = fxmlLoader.load();

                Controller.Consulter controller = fxmlLoader.getController();
                controller.setEventData(salle);

                // Add the cell to the container.
                event_container.getChildren().addAll(eventCell);

            } catch (IOException e) {
                e.printStackTrace();
                // Handle the exception
            }
        }}
    @FXML
    void Return(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/MenuUser.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
