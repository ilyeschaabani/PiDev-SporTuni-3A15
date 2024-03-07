package Controller;

import Entity.Evenement;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import Service.EvenementService;

import javafx.fxml.FXML;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {

    public GridPane eventsListContainer;
    @FXML
    private GridPane eventsListController;
    public GridPane gridPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Call your service to get the list of events
        EvenementService es=new EvenementService();
        List<Evenement> evenements = es.readAll();

        // Populate the GridPane with the events data
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < evenements.size(); i++) {

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/OneEventCard.fxml"));
                VBox oneEventCard = fxmlLoader.load();
                OneEventCardController productCardController = fxmlLoader.getController();
                productCardController.setEventData(evenements.get(i));

                if (column == 3) {
                    column = 0;
                    ++row;
                }
                eventsListContainer.add(oneEventCard, column++, row);
                // GridPane.setMargin(oneProductCard, new Insets(10));
                GridPane.setMargin(oneEventCard, new Insets(0, 20, 20, 10));
                // oneProductCard.setStyle("-fx-effect: dropshadow(three-pass-box,
                // rgba(0,0,0,0.09), 25, 0.1, 0, 0);");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
