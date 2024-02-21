package controllers;


import java.net.URL;
import java.util.ResourceBundle;

import entities.cour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import service.CourService;

public class AfficherC {

    CourService ss=new CourService();



        @FXML
        private ResourceBundle resources;

        @FXML
        private URL location;

        @FXML
        private TableColumn<Cour, String> col_date;

        @FXML
        private TableColumn<Cour, String> col_discipline;

        @FXML
        private TableColumn<Cour, Integer> col_duree;

        @FXML
        private TableColumn<Cour, Integer> col_heure;

        @FXML
        private TableColumn<Cour, String> col_nom;

        @FXML
        private TableColumn<Cour, String> col_participant;

        @FXML
        private TableColumn<Cour, String> col_salle;
    @FXML
    private TableView<cour> tv_cour;
    @FXML
    void initialize() {
        try {
            ObservableList<cour> observableList = FXCollections.observableList(ss.readAll());
            tv_cour.setItems(observableList);
            col_nom.setCellValueFactory(new PropertyValueFactory<>("nom cour"));
            col_discipline.setCellValueFactory(new PropertyValueFactory<>("discipline"));
            col_salle.setCellValueFactory(new PropertyValueFactory<>("nom salle"));
            col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
            col_duree.setCellValueFactory(new PropertyValueFactory<>("duree"));
            col_heure.setCellValueFactory(new PropertyValueFactory<>("heure"));
            col_participant.setCellValueFactory(new PropertyValueFactory<>("nb participant"));

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

}
