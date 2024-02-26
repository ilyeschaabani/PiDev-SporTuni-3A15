package controllers;
import entities.Evenement;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import  services.EvenementService;

import java.sql.SQLException;
import java.util.Objects;


public class AjouterEvenementController {
       EvenementService es;


        @FXML
        private TextField dateDebut;

        @FXML
        private TextField dateFin;

        @FXML
        private TextField description;

        @FXML
        private TextField nbr_max;

        @FXML
        private TextField nom_discipline;

        @FXML
        private TextField nom_e;

        @FXML
        private TextField nom_salle;



        @FXML
        void AffcherEvenement(ActionEvent event) {
            try {
                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherEvenement.fxml")));
                nom_e.getScene().setRoot(root);

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }





    @FXML

    void AjouterEvenement(ActionEvent event) throws SQLException {
        es.add(new Evenement(dateDebut.getText(), dateFin.getText(), description.getText(), Integer.parseInt(nbr_max.getText()), nom_discipline.getText(), nom_e.getText(), nom_salle.getText()
        ));
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Success");
        alert.setContentText("evenement ajoute");
        alert.showAndWait();

    }
    }


