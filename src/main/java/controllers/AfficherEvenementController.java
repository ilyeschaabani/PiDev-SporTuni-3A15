package controllers;


import entities.Evenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import services.EvenementService;

import java.util.List;

public class AfficherEvenementController {

    @FXML
    private Button btAjout;

    @FXML
    private Button btModif;

    @FXML
    private TableColumn<Evenement, String> cldateDebut;

    @FXML
    private TableColumn<Evenement, String> cldateFin;

    @FXML
    private TableColumn<Evenement, String> cldescription;

    @FXML
    private TableColumn<Evenement, Integer> clnbr_max;

    @FXML
    private TableColumn<Evenement, String> clnom_discipline;

    @FXML
    private TableColumn<Evenement, String> clnom_e;

    @FXML
    private TableColumn<Evenement, String> clnom_salle;

    @FXML
    private TableView<Evenement> tv_evenements;

    @FXML
    void initialize() {
        try {
            //EvenementService es = new EvenementService();
            //ObservableList<Evenement> observableList = FXCollections.observableList(es.readAll());
           // tv_evenements.setItems(observableList);
            clnom_e.setCellValueFactory(new PropertyValueFactory<>("nom_e"));
            clnom_salle.setCellValueFactory(new PropertyValueFactory<>("nom_salle"));
            cldateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
            clnom_discipline.setCellValueFactory(new PropertyValueFactory<>("nom_discipline"));
            cldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            clnbr_max.setCellValueFactory(new PropertyValueFactory<>("nbr_max"));
            cldateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
            loadEvents();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public void loadEvents(){
        EvenementService es =new EvenementService();
        List <Evenement> events =es.readAll();
        ObservableList<Evenement> observableList = FXCollections.observableList(events);
        tv_evenements.setItems(observableList);
    }
}

