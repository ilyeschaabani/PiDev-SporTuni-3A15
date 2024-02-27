package controller;

import entity.Cour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import service.CourService;
import service.DisciplineService;

import java.sql.Date;
import java.time.LocalDate;

public class CourAffichage {
    private CourService cs = new CourService();
    private Cour cour;

    @FXML
    private Button btnadd2;

    @FXML
    private Button btnmodif2;

    @FXML
    private Button btnsupp2;

    @FXML
    private TableColumn<Cour, String> clNom;

    @FXML
    private TableColumn<Cour, String> clDiscipline;

    @FXML
    private TableColumn<Cour, Date> clDate;

    @FXML
    private TableColumn<Cour, String> clHeureDebut;

    @FXML
    private TableColumn<Cour, String> clHeureFin;

    @FXML
    private TableColumn<Cour, String> clNbParticipants;

    @FXML
    private TableColumn<Cour, String> clNomSalle;

    @FXML
    private TextField searchField;

    @FXML
    private DatePicker tfdate;

    @FXML
    private TextField tfdesc2;

    @FXML
    private TextField tfhd;

    @FXML
    private TextField tfhf;

    @FXML
    private TextField tfnom2;

    @FXML
    private TextField tfnbm;

    @FXML
    private TextField tfns;

    @FXML
    private TableView<Cour> tvCour;

    @FXML
    void initialize() {
        try {
            ObservableList<Cour> observableList = FXCollections.observableList(cs.readAll());
            tvCour.setItems(observableList);
            clNom.setCellValueFactory(new PropertyValueFactory<>("nom_cour"));
            clDiscipline.setCellValueFactory(new PropertyValueFactory<>("nom_discipline"));
            clDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            clHeureDebut.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));
            clHeureFin.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));
            clNbParticipants.setCellValueFactory(new PropertyValueFactory<>("nb_max"));
            clNomSalle.setCellValueFactory(new PropertyValueFactory<>("nom_salle"));
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    void add2(ActionEvent event) {
        try {
            // Récupérer les valeurs des champs depuis l'interface utilisateur
            String nomCours = tfnom2.getText().trim();
            String nomDiscipline = tfdesc2.getText().trim();
            String heureDebut = tfhd.getText().trim();
            String heureFin = tfhf.getText().trim();
            String nomSalle = tfns.getText().trim();
            String nb_max = tfnbm.getText().trim();

            // Vérifier si tous les champs sont remplis
            if (nomCours.isEmpty() || nomDiscipline.isEmpty() || heureDebut.isEmpty() || heureFin.isEmpty() || nomSalle.isEmpty() || nb_max.isEmpty()) {
                showAlert("Error", "Veuillez remplir tous les champs", Alert.AlertType.ERROR);
                return;
            }

            // Récupérer la date depuis le DatePicker
            LocalDate localDate = tfdate.getValue();
            if (localDate == null) {
                showAlert("Error", "Veuillez sélectionner une date", Alert.AlertType.ERROR);
                return;
            }
            Date date = Date.valueOf(localDate);

            // Créer un nouvel objet Cour avec les données saisies
            Cour nouveauCour = new Cour(nomCours, date, heureDebut, heureFin, nomSalle, nb_max, nomDiscipline);

            // Utiliser le service CourService pour ajouter le nouveau cours à la base de données
            cs.add(nouveauCour);

            // Actualiser l'affichage de la table
            initialize();

            // Afficher une notification de succès
            showAlert("Information", "Cours ajouté avec succès", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            // En cas d'erreur, afficher un message d'erreur
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    void delete2(ActionEvent event) {
        try {
            if (cour != null) {
                cs.delete(cour.getId_cour());
                initialize();
                showAlert("Information", "Cours supprimé", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Veuillez sélectionner un cours à supprimer", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void mouseClicked2(MouseEvent mouseEvent) {
        try {
            Cour s = tvCour.getSelectionModel().getSelectedItem();
            if (s != null) {
                cour = s;
                tfnom2.setText(cour.getNom_cour());
                tfdesc2.setText(cour.getNom_discipline());
                tfdate.setValue(cour.getDate().toLocalDate());
                tfhd.setText(cour.getHeure_debut());
                tfhf.setText(cour.getHeure_fin());
                tfns.setText(cour.getNom_salle());
                tfnbm.setText(cour.getNb_max());
            }
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    void recherche2(ActionEvent event) {
        try {
            ObservableList<Cour> observableList = FXCollections.observableList(cs.readAll());
            FilteredList<Cour> filteredList = new FilteredList<>(observableList, p -> true);

            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(cour -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true;
                    }
                    String lowerCaseFilter = newValue.toLowerCase();
                    return cour.getNom_cour().toLowerCase().contains(lowerCaseFilter)
                            || cour.getNom_discipline().toLowerCase().contains(lowerCaseFilter)
                            || cour.getNom_salle().toLowerCase().contains(lowerCaseFilter)
                            || cour.getHeure_debut().toLowerCase().contains(lowerCaseFilter);
                });
            });

            SortedList<Cour> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tvCour.comparatorProperty());
            tvCour.setItems(sortedList);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML

    void update2(ActionEvent event) {
        try {
            if (cour != null) {
                // Récupérer les valeurs des champs depuis l'interface utilisateur
                String nomCours = tfnom2.getText().trim();
                String nomDiscipline = tfdesc2.getText().trim();
                String heureDebut = tfhd.getText().trim();
                String heureFin = tfhf.getText().trim();
                String nomSalle = tfns.getText().trim();
                String nb_max = tfnbm.getText().trim();

                // Vérifier si tous les champs sont remplis
                if (nomCours.isEmpty() || nomDiscipline.isEmpty() || heureDebut.isEmpty() || heureFin.isEmpty() || nomSalle.isEmpty() || nb_max.isEmpty()) {
                    showAlert("Error", "Veuillez remplir tous les champs", Alert.AlertType.ERROR);
                    return;
                }

                // Récupérer la date depuis le DatePicker
                LocalDate localDate = tfdate.getValue();
                if (localDate == null) {
                    showAlert("Error", "Veuillez sélectionner une date", Alert.AlertType.ERROR);
                    return;
                }
                Date date = Date.valueOf(localDate);

                // Mettre à jour les informations du cours
                cour.setNom_cour(nomCours);
                cour.setNom_discipline(nomDiscipline);
                cour.setDate(date);
                cour.setHeure_debut(heureDebut);
                cour.setHeure_fin(heureFin);
                cour.setNom_salle(nomSalle);
                cour.setNb_max(nb_max);

                // Utiliser le service CourService pour mettre à jour le cours dans la base de données
                cs.updato(cour);

                // Actualiser l'affichage de la table
                initialize();

                // Afficher une notification de succès
                showAlert("Information", "Cours modifié avec succès", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Veuillez sélectionner un cours à modifier", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            // En cas d'erreur, afficher un message d'erreur
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

}
