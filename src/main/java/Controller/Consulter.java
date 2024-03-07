package Controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.controlsfx.control.Rating;
import Service.SalleService;
import Entity.Salle;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Consulter {
    @FXML
    public AnchorPane events_pane;
    @FXML
    private Label event_name;

    @FXML
    private Label event_date;
    @FXML
    private Button cht;

    @FXML
    private Label event_place;

    @FXML
    private Label event_goal;

    @FXML
    private Label event_aa;

    @FXML
    private Label event_id;
    @FXML
    private TextField ratingID;
    @FXML
    private Rating rating;
    private SalleService salleService;
    private Salle currentSalle;


    @FXML
    private void initialize() {
  /*      try {


            // Call your service to get data
            SalleService salleService = new SalleService();
            List<Salle> salles = salleService.readAll();

            // Assuming you want to display the first salle's data
            if (!salles.isEmpty()) {
                Salle firstSalle = salles.get(0);
                setEventData(firstSalle);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Handle the exception according to your application's needs
        }*/
       /* rating.ratingProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                ratingID.setText("" + t1 + "/5");
            }
        });*/
        salleService = new SalleService();

        // Set up the rating listener
        rating.ratingProperty().addListener((observableValue, number, t1) -> {
            ratingID.setText(String.format("%.1f/5", t1.doubleValue()));
            updateRatingInDatabase(t1.doubleValue());
        });

    }

    private void updateRatingInDatabase(double newRating) {
        // Check if there is a valid Salle and ratingID is not empty
        if (currentSalle != null && !ratingID.getText().isEmpty()) {
            try {
                int salleId = currentSalle.getId();
                salleService.updateRating(salleId, newRating);
            } catch (Exception e) {
                e.printStackTrace(); // Handle the exception according to your application's needs
            }
        }
    }


    public void setEventData(Salle event) {
        if (event != null) {
            event_name.setText(event.getNom());
            currentSalle = event;

            // Assuming getDateD() returns LocalDateTime
            LocalDateTime localDateTime = event.getDateD();
            // event_date.setText(formatLocalDateTime(localDateTime));

            //event_place.setText(String.valueOf(event.getSurface()));
            event_goal.setText(event.getDiscipline());

            event_id.setText(String.valueOf(event.getId()));
        }
    }

    private String formatLocalDateTime(LocalDateTime localDateTime) {
        try {
            if (localDateTime != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                return localDateTime.format(formatter);
            } else {
                return ""; // or some default value
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    @FXML
    void chatbot(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/ChatBot.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

