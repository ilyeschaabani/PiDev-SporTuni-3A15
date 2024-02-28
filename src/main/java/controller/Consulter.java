/*package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;



import entity.Salle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Consulter {
    private Salle evnt;

    public Salle getEvnt() {
        return evnt;
    }

    public void setEvnt(Salle evnt) {
        this.evnt = evnt;
    }

    @FXML
    public AnchorPane events_pane;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnpart;

    @FXML
    public Label event_date;

    @FXML
    private Label event_goal;

    @FXML
    private Label event_name;

    @FXML
    private Label event_id;

    @FXML
    private Label event_per;

    @FXML
    private Label event_place;

    public ResourceBundle getResources() {
        return resources;
    }

    public void setResources(ResourceBundle resources) {
        this.resources = resources;
    }

    public URL getLocation() {
        return location;
    }

    public void setLocation(URL location) {
        this.location = location;
    }

    public Button getBtnpart() {
        return btnpart;
    }

    public void setBtnpart(Button btnpart) {
        this.btnpart = btnpart;
    }

    public Label getEvent_date() {
        return event_date;
    }

    public void setEvent_date(Label event_date) {
        this.event_date = event_date;
    }

    public Label getEvent_goal() {
        return event_goal;
    }

    public void setEvent_goal(Label event_goal) {
        this.event_goal = event_goal;
    }

    public Label getEvent_name() {
        return event_name;
    }

    public void setEvent_name(Label event_name) {
        this.event_name = event_name;
    }

    public Label getEvent_per() {
        return event_per;
    }

    public void setEvent_per(Label event_per) {
        this.event_per = event_per;
    }

    public Label getEvent_place() {
        return event_place;
    }

    public void setEvent_place(Label event_place) {
        this.event_place = event_place;
    }


    public Label getEvent_id() {
        return event_id;
    }

    public void setEvent_id(Label event_id) {
        this.event_id = event_id;
    }


    private Salle eventData;


    @FXML
    void initialize() {
        btnpart.setOnAction(e -> participer_event());

    }

    @FXML
    void participer_event() {
        try {
            // Load the FXML file for AjouterDon interface
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/finfolio/User/Evennement/faire-un-don.fxml"));
            Parent root = loader.load();
            Ajouter adc = loader.getController();
            String id = event_id.getText();
            adc.(id);

            // Create a new stage for the AjouterDon interface
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Faire un don");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void setEventData(Salle event) {
        event_name.setText(event.getNom());
        event_id.setText(String.valueOf(event.getId()));

    }


}
*/