package Controller;


import Entity.Competition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.control.TreeTableView;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import Service.CompetitionService;
import javafx.stage.Stage;

import java.io.IOException;

public class UserinterfaceController {


    CompetitionService cs = new CompetitionService();
    @FXML
    private TreeTableView<Competition> tvc1;

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

        initializetreetableviewbutton();
        // Appeler la méthode pour afficher les compétitions
        afficherCompetitions();

        tvc1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Récupérez l'objet Competition correspondant à la ligne sélectionnée
                Competition selectedCompetition = newSelection.getValue();
                // Activez l'édition des cellules pour cet objet

            }
        });


    }


    public void afficherCompetitions() {
        try {
            ObservableList<Competition> observableList = FXCollections.observableList(cs.readAll());

            TreeItem<Competition> root = new TreeItem<>(null);
            root.setExpanded(true);

            for (Competition competition : observableList) {
                TreeItem<Competition> item = new TreeItem<>(competition);
                root.getChildren().add(item);
            }

            tvc1.setRoot(root);
            tvc1.setShowRoot(false);

            id_comp.setCellValueFactory(new TreeItemPropertyValueFactory<>("id_comp"));
            NomCompetition.setCellValueFactory(new TreeItemPropertyValueFactory<>("nom_comp"));
            Lieu.setCellValueFactory(new TreeItemPropertyValueFactory<>("lieu_comp"));
            Datetvc.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
            Discipline.setCellValueFactory(new TreeItemPropertyValueFactory<>("discipline"));
            IDsalle.setCellValueFactory(new TreeItemPropertyValueFactory<>("id_salle"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private void initializetreetableviewbutton() {

        TreeTableColumn<Competition, Void> actionColumn = new TreeTableColumn<>("Actions");
        actionColumn.setCellFactory(new ButtonCellFactoryUser(tvc1, this::oninscriptionButtonClick, this::oninscriptionButtonClick));

// Ajouter la colonne d'actions à votre TreeTableView
        tvc1.getColumns().add(actionColumn);


    }

    public Void oninscriptionButtonClick(Competition item) {
        TreeItem<Competition> selectedTreeItem = tvc1.getSelectionModel().getSelectedItem();
        if (selectedTreeItem != null) {
            Competition selectedCompetition = selectedTreeItem.getValue();
            int competitionId = selectedCompetition.getId_comp(); // Supposons que getId() retourne l'ID de la compétition
            try {
                // Charger l'interface inscriptionuser.fxml
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/InterfaceInscriptionUserComp.fxml"));
                Parent root = loader.load();

                // Accéder au contrôleur de l'interface inscriptionuser.fxml
                InscriptionUserConroller inscriptionUserController = loader.getController();

                // Mettre à jour la label id_comp avec l'ID de la compétition sélectionnée
                inscriptionUserController.setIdCompLabel(Integer.parseInt(String.valueOf(competitionId)));

                // Changer la scène pour afficher l'interface inscriptionuser.fxml
                tvc1.getScene().setRoot(root);

                System.out.println("ID de la compétition sélectionnée : " + competitionId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Aucune ligne sélectionnée, afficher un message d'erreur ou prendre d'autres mesures
            System.err.println("Aucune ligne sélectionnée.");
        }
        return null;
    }
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
