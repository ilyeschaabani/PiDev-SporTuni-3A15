package controllers;

import entities.Competition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import service.CompetitionService;

public class AfficherController {

    CompetitionService cs = new CompetitionService();
    @FXML
    private TreeTableView<Competition> tvc;

    @FXML
    private TreeTableColumn<Competition, Integer> id_comp;

    @FXML
    private TreeTableColumn<Competition, String> NomCompetition;

    @FXML
    private TreeTableColumn<Competition, String> Lieu;

    @FXML
    private TreeTableColumn<Competition, java.sql.Date> Datetvc;

    @FXML
    private TreeTableColumn<Competition, String> Discipline;

    @FXML
    private TreeTableColumn<Competition, Integer> IDsalle;

    @FXML
    void initialize() {
        // Définir les cellules de la table pour chaque colonne
        id_comp.setCellValueFactory(new TreeItemPropertyValueFactory<>("id_comp"));
        NomCompetition.setCellValueFactory(new TreeItemPropertyValueFactory<>("nom_comp"));
        Lieu.setCellValueFactory(new TreeItemPropertyValueFactory<>("lieu"));
        Datetvc.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
        Discipline.setCellValueFactory(new TreeItemPropertyValueFactory<>("discipline"));
        IDsalle.setCellValueFactory(new TreeItemPropertyValueFactory<>("id_salle"));
    }

    public void afficherCompetition() {
        try {
            // Récupérer les données depuis la base de données
            ObservableList<Competition> competitions = FXCollections.observableList(cs.readAll());

            // Créer la racine de l'arbre
            TreeItem<Competition> root = new TreeItem<>(null);
            root.setExpanded(true); // Définir la racine comme étant toujours étendue

            // Créer un TreeItem pour chaque compétition et les ajouter à la racine
            for (Competition competition : competitions) {
                TreeItem<Competition> item = new TreeItem<>(competition);
                root.getChildren().add(item);
            }

            // Définir la racine de l'arbre dans le TreeTableView
            tvc.setRoot(root);
            tvc.setShowRoot(false); // Masquer la racine

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
