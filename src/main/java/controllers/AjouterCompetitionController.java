package controllers;

import entities.Competition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import service.CompetitionService;

import java.sql.Date;
import java.util.List;


public class AjouterCompetitionController {


    CompetitionService cs = new CompetitionService();
    AfficherController see = new AfficherController();
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private DatePicker date;
    @FXML
    private TextField nom_comp;
    @FXML
    private TextField lieu;
    @FXML
    private TextField discipline;
    @FXML
    private Label datteee;
    @FXML
    private ComboBox<Integer> id_salle;
    @FXML
    private Label nomCompErrorLabel;
    @FXML
    private Label lieuErrorLabel;
    @FXML
    private Label disciplineErrorLabel;
    @FXML
    private Label dateErrorLabel;
    @FXML
    private Label idSalleErrorLabel;


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
    void AddComp(ActionEvent event) {
        // Vérifiez si les champs requis sont vides
        if (nom_comp.getText().isEmpty() || lieu.getText().isEmpty() || discipline.getText().isEmpty() || id_salle.getValue() == null || date.getValue() == null) {
            setupValidation();
            return;
        }

        // Obtenez l'ID de salle sélectionné
        int idSalle = id_salle.getValue();

        // Date
        Date currentDate = Date.valueOf(date.getValue());

        // Créez une nouvelle instance de la compétition avec les données fournies
        Competition competition = new Competition(nom_comp.getText(), lieu.getText(), currentDate, discipline.getText(), idSalle);

        // Ajoutez la compétition à la base de données
        cs.add(competition);

        // Affichez une alerte de succès
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Compétition ajoutée.");
        alert.showAndWait();

        afficherCompetitions();

    }


    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        assert nom_comp != null : "fx:id=\"nom_comp\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert lieu != null : "fx:id=\"lieu\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert date != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert discipline != null : "fx:id=\"discipline\" was not injected: check your FXML file 'Ajouter.fxml'.";

        loadSalleIds();

        // Appeler la méthode pour afficher les compétitions
        afficherCompetitions();
        //preparation des buttons
        initializetreetableviewbutton();
        // Ajoutez un écouteur d'événements à la sélection de la TreeTableView
        tvc.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Récupérez l'objet Competition correspondant à la ligne sélectionnée
                Competition selectedCompetition = newSelection.getValue();
                // Activez l'édition des cellules pour cet objet
                activation_des_cellules(selectedCompetition);
                //update selected object
                //onEditButtonClick(selectedCompetition);
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

            tvc.setRoot(root);
            tvc.setShowRoot(false);

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

    @FXML
    void selectdate(ActionEvent event) {
        datteee.setText(date.getValue().toString());
    }

    // Méthode pour charger les ID de salle depuis la base de données et les ajouter au ComboBox
    @FXML
    private void loadSalleIds() {
        // Récupérez les ID de salle depuis la base de données
        List<Integer> salleIds = cs.getAvailableSalleIdsFromDatabase();

        // Effacez le ComboBox avant d'ajouter de nouvelles valeurs
        id_salle.getItems().clear();

        // Ajoutez les ID de salle au ComboBox
        id_salle.getItems().addAll(salleIds);
    }

    public void setupValidation() {
        // Validation pour le nom de la compétition
        if (nom_comp.getText().isEmpty()) {
            nom_comp.getStyleClass().add("empty-field");
            nomCompErrorLabel.setText("Veuillez donner le nom de la compétition");
            nomCompErrorLabel.setTextFill(Color.RED);
        } else {
            nom_comp.getStyleClass().remove("empty-field");
            nomCompErrorLabel.setText("Validé !");
            nomCompErrorLabel.setTextFill(Color.GREEN);
        }

        // Validation pour le lieu de la compétition
        if (lieu.getText().isEmpty()) {
            lieu.getStyleClass().add("empty-field");
            lieuErrorLabel.setText("Veuillez donner le lieu de la compétition");
            lieuErrorLabel.setTextFill(Color.RED);
        } else {
            lieu.getStyleClass().remove("empty-field");
            lieuErrorLabel.setText("Validé !");
            lieuErrorLabel.setTextFill(Color.GREEN);
        }

        // Validation pour la date de la compétition
        if (date.getValue() == null) {
            date.getStyleClass().add("empty-field");
            dateErrorLabel.setText("Veuillez sélectionner une date");
            dateErrorLabel.setTextFill(Color.RED);
        } else {
            date.getStyleClass().remove("empty-field");
            dateErrorLabel.setText("Validé !");
            dateErrorLabel.setTextFill(Color.GREEN);
        }

        // Validation pour la discipline de la compétition
        if (discipline.getText().isEmpty()) {
            discipline.getStyleClass().add("empty-field");
            disciplineErrorLabel.setText("Veuillez donner la discipline de la compétition");
            disciplineErrorLabel.setTextFill(Color.RED);
        } else {
            discipline.getStyleClass().remove("empty-field");
            disciplineErrorLabel.setText("Validé !");
            disciplineErrorLabel.setTextFill(Color.GREEN);
        }

        // Validation pour l'ID de la salle
        if (id_salle.getValue() == null) {
            id_salle.getStyleClass().add("empty-field");
            idSalleErrorLabel.setText("Veuillez sélectionner une salle");
            idSalleErrorLabel.setTextFill(Color.RED);
        } else {
            id_salle.getStyleClass().remove("empty-field");
            idSalleErrorLabel.setText("Validé !");
            idSalleErrorLabel.setTextFill(Color.GREEN);
        }
    }


    // Méthode pour gérer le clic sur le bouton Supprimer
    public Void onDeleteButtonClick(Competition item) {
        cs.delete(item.getId_comp());
        System.out.println("Supprimer: " + item);
        afficherCompetitions();
        return null;
    }


    public void activation_des_cellules(Competition item) {
        // Activer l'édition des cellules dans chaque colonne
        NomCompetition.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        Lieu.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        Discipline.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        IDsalle.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new IntegerStringConverter()));
// Configuration de la colonne Datetvc pour utiliser TextFieldTreeTableCell
        Datetvc.setCellFactory(column -> {
            TextFieldTreeTableCell<Competition, Date> cell = new TextFieldTreeTableCell<>();
            cell.setConverter(new StringConverter<Date>() {
                @Override
                public String toString(Date object) {
                    return object != null ? object.toString() : "";
                }

                @Override
                public Date fromString(String string) {
                    return string != null && !string.isEmpty() ? Date.valueOf(string) : null;
                }
            });
            return cell;
        });

// Conversion java.sql.Date en String et vice versa
        Datetvc.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));


        // Ajouter des écouteurs pour détecter les modifications


        NomCompetition.setOnEditCommit(event -> {
            item.setNom_comp(event.getNewValue());
        });

        Lieu.setOnEditCommit(event -> {
            item.setLieu_comp(event.getNewValue());
        });

        Datetvc.setOnEditCommit(event -> {
            item.setDate(event.getNewValue());
        });

        Discipline.setOnEditCommit(event -> {
            item.setDiscipline(event.getNewValue());
        });

        IDsalle.setOnEditCommit(event -> {
            item.setId_salle(event.getNewValue());
        });
    }


    // Méthode pour gérer le clic sur le bouton Modifier
    public Void onEditButtonClick(Competition item) {
        TreeItem<Competition> selectedTreeItem = tvc.getSelectionModel().getSelectedItem();
        if (selectedTreeItem != null) {
            Competition selectedCompetition = selectedTreeItem.getValue();
            try {
                // Mettre à jour l'objet Competition dans la base de données
                cs.update(selectedCompetition);
                System.out.println("Modifier: " + selectedCompetition);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Aucune ligne sélectionnée, afficher un message d'erreur ou prendre d'autres mesures
            System.err.println("Aucune ligne sélectionnée.");
        }
        return null;
    }


    // Méthode pour charger les données dans le TableView
    private void initializetreetableviewbutton() {

        TreeTableColumn<Competition, Void> actionColumn = new TreeTableColumn<>("Actions");
        actionColumn.setCellFactory(new ButtonCellFactory(tvc, this::onDeleteButtonClick, this::onEditButtonClick));

// Ajouter la colonne d'actions à votre TreeTableView
        tvc.getColumns().add(actionColumn);


    }
}