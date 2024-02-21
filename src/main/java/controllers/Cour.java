package controllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import service.CourService;

import static java.lang.Integer.parseInt;

public class Cour {
    CourService ss= new CourService();




        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private Button btnClear;

        @FXML
        private Button btnSave;

        @FXML
        private TextField tclasse_c;

    @FXML
    private TextField theure_c;
        @FXML
        private VBox tcour;

        @FXML
        private TextField tdate_c;

        @FXML
        private TextField tdiscipline_c;

        @FXML
        private TextField tduree_c;

        @FXML
        private TextField tid_c;

        @FXML
        private Label title;



        @FXML
        private TextField tparticipant_c;
    @FXML
    private TextField tnom_c;

    public Cour(int i) {
    }

    @FXML
        void Afficher(ActionEvent event) {

        }

    void ajout(ActionEvent event) {
        try {
        CourService.ajout( new Cour(
                tnom_c.getText(),
                parseInt(tparticipant_c.getText()),
                parseInt(tdiscipline_c.getText()),
                parseInt(tdate_c.getText()),
                parseInt(theure_c.getText()),
                parseInt(tduree_c.getText()),
                parseInt(tclasse_c.getText())
        ));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("success");
            alert.setContentText("cours ajoutée avec succés");
            alert.showAndWait();
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setContentText(e.getMessage());
            alert.showAndWait();}


    }



    @FXML
    void read(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AfficherC.fxml"));
            tnom_c.getScene().setRoot(root);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    @FXML
        void initialize() {
            assert btnClear != null : "fx:id=\"btnClear\" was not injected: check your FXML file 'Cour.fxml'.";
            assert btnSave != null : "fx:id=\"btnSave\" was not injected: check your FXML file 'Cour.fxml'.";
            assert tclasse_c != null : "fx:id=\"tclasse_c\" was not injected: check your FXML file 'Cour.fxml'.";
            assert tcour != null : "fx:id=\"tcour\" was not injected: check your FXML file 'Cour.fxml'.";
            assert tdate_c != null : "fx:id=\"tdate_c\" was not injected: check your FXML file 'Cour.fxml'.";
            assert tdiscipline_c != null : "fx:id=\"tdiscipline_c\" was not injected: check your FXML file 'Cour.fxml'.";
            assert tduree_c != null : "fx:id=\"tduree_c\" was not injected: check your FXML file 'Cour.fxml'.";
            assert tid_c != null : "fx:id=\"tid_c\" was not injected: check your FXML file 'Cour.fxml'.";
            assert title != null : "fx:id=\"title\" was not injected: check your FXML file 'Cour.fxml'.";
            assert tnom_c != null : "fx:id=\"tnom_c\" was not injected: check your FXML file 'Cour.fxml'.";
            assert tparticipant_c != null : "fx:id=\"tparticipant_c\" was not injected: check your FXML file 'Cour.fxml'.";

        }

    }

}