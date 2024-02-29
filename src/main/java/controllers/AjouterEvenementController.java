package controllers;

import entities.Evenement;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import services.EvenementService;
import services.SalleService;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class AjouterEvenementController {

    public Button hiddenButton;
    public Button btModif;
    @FXML
    private TextField nom_e;

    @FXML
    private ComboBox<Salle> nom_salle;

    @FXML
    private DatePicker dateDebut;

    @FXML
    private TextField nom_discipline;

    @FXML
    private DatePicker dateFin;

    @FXML
    private TextField nbr_max;

    private int selectedid;
    @FXML
    private TextField description;

    @FXML
    private TableView<Evenement> tv_evenements;

    @FXML
    private TableColumn<Evenement, String> clnom_e;

    @FXML
    private TableColumn<Evenement, String> clnom_salle;

    @FXML
    private TableColumn<Evenement, Date> cldateDebut;

    @FXML
    private TableColumn<Evenement, String> clnom_discipline;

    @FXML
    private TableColumn<Evenement, String> cldescription;

    @FXML
    private TableColumn<Evenement, Integer> clnbr_max;

    @FXML
    private TableColumn<Evenement, Date> cldateFin;

    private EvenementService evenementService;
    private SalleService salleService;
    private void initTable() {
        clnom_e.setCellValueFactory(new PropertyValueFactory<>("nom_e"));
        clnom_salle.setCellValueFactory(new PropertyValueFactory<>("nom_salle"));
        cldateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
        clnom_discipline.setCellValueFactory(new PropertyValueFactory<>("nom_discipline"));
        cldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        clnbr_max.setCellValueFactory(new PropertyValueFactory<>("nbr_max"));
        cldateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));

        // Set the items to the TableView
        tv_evenements.setItems(getEvenementData());
    }

    private ObservableList<Evenement> getEvenementData() {
        // Initialize your EvenementService
        evenementService = new EvenementService();

        // Retrieve the list of evenements from the service
        List<Evenement> evenements = evenementService.readAll();

        // Return the list as an ObservableList
        return FXCollections.observableArrayList(evenements);
    }

    public AjouterEvenementController() {
        evenementService = new EvenementService();
        salleService = new SalleService();
    }

    @FXML
    public void initialize() {
        initComboBox();
        initTable();
        populateTable();
    }

    private void initComboBox() {
        List<Salle> salleList = salleService.readAll();
        ObservableList<Salle> observableSalleList = FXCollections.observableArrayList(salleList);
        nom_salle.setItems(observableSalleList);
    }



    private void populateTable() {
        List<Evenement> evenementList = evenementService.readAll();
        ObservableList<Evenement> data = FXCollections.observableArrayList(evenementList);
        tv_evenements.setItems(data);
    }

    @FXML
    void AjouterEvenement(ActionEvent event) {
        String nomE = nom_e.getText();
        Salle selectedSalle = nom_salle.getValue();
        LocalDate dateDebutVal = dateDebut.getValue();
        String nomDiscipline = nom_discipline.getText();
        String descriptionVal = description.getText();
        int nbrMax ;
        LocalDate dateFinVal = dateFin.getValue();
        if (nbr_max.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez saisir un nombre maximum.");
            return;
        } else {
            nbrMax = Integer.parseInt(nbr_max.getText());
        }
        if (nomE.isEmpty() || selectedSalle == null || dateDebutVal == null || nomDiscipline.isEmpty() || descriptionVal.isEmpty() || nbrMax == 0 || dateFinVal == null) {
            showAlert("Erreur", "Veuillez remplir tous les champs.");
            return;
        }

        Evenement evenement = new Evenement(nomE, selectedSalle.getNom(), Date.valueOf(dateDebutVal), nomDiscipline, descriptionVal, nbrMax, Date.valueOf(dateFinVal));
        evenementService.add(evenement);
        clearFields();
        populateTable();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    void modifierEvenement(ActionEvent event) {
        Evenement selectedEvenement = tv_evenements.getSelectionModel().getSelectedItem();
        if (selectedEvenement == null) {
            showAlert("Erreur", "Tu dois selectionner une evenement.");
            return;
        }
        selectedid = selectedEvenement.getId_e();

        nom_e.setText(selectedEvenement.getNom_e());
        SalleService ss=new SalleService();

        Salle selectedSalle = ss.findbynom_salle(selectedEvenement.getNom_salle());

        System.out.println("Nom salle="+selectedEvenement.getNom_salle());
        initComboBox();

        for (Salle salle : nom_salle.getItems()) {
            if (salle.getNom().equals(selectedSalle.getNom())) {
                nom_salle.setValue(salle);
                break;
            }
        }
        dateDebut.setValue(selectedEvenement.getDateDebut().toLocalDate());
        nom_discipline.setText(selectedEvenement.getNom_discipline());
        description.setText(selectedEvenement.getDescription());
        nbr_max.setText(String.valueOf(selectedEvenement.getNbr_max()));
        dateFin.setValue(selectedEvenement.getDateFin().toLocalDate());
        /*
        String nomE = nom_e.getText();
        Salle selectedSalle = nom_salle.getValue();
        LocalDate dateDebutVal = dateDebut.getValue();
        String nomDiscipline = nom_discipline.getText();
        String descriptionVal = description.getText();
        LocalDate dateFinVal = dateFin.getValue();
        int nbrMax ;
        if (nbr_max.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez saisir un nombre maximum.");
            return;
        } else {
            nbrMax = Integer.parseInt(nbr_max.getText());
        }

        if (nomE.isEmpty() || selectedSalle == null || dateDebutVal == null || nomDiscipline.isEmpty() || descriptionVal.isEmpty() || nbrMax == 0 || dateFinVal == null) {
            showAlert("Erreur", "Remplir tout les champs.");
            return;
        }

        selectedEvenement.setNom_e(nomE);
        selectedEvenement.setNom_salle(selectedSalle.getNom());
        selectedEvenement.setDateDebut(Date.valueOf(dateDebutVal));
        selectedEvenement.setNom_discipline(nomDiscipline);
        selectedEvenement.setDescription(descriptionVal);
        selectedEvenement.setNbr_max(nbrMax);
        selectedEvenement.setDateFin(Date.valueOf(dateFinVal));

        evenementService.update(selectedEvenement);
        clearFields();*/
        populateTable();
        btModif.setVisible(false);
        hiddenButton.setVisible(true);

        }


    @FXML
    void supprimerEvenement(ActionEvent event) {
        Evenement selectedEvenement = tv_evenements.getSelectionModel().getSelectedItem();
        if (selectedEvenement == null) {
showAlert("Erreur","Tu dois selectionner qqchose");
return;
        }

        evenementService.delete(selectedEvenement);
        showSuccessAlert("Success","Event supprimée");

        populateTable();
    }

    private void clearFields() {
        nom_e.clear();
        nom_salle.getSelectionModel().clearSelection();
        dateDebut.setValue(null);
        nom_discipline.clear();
        description.clear();
        nbr_max.clear();
        dateFin.setValue(null);
    }

    @FXML
    void AfficherEvenement(ActionEvent event) {
        populateTable();
    }

    @FXML
    void validerReservation(ActionEvent event) {
        // Implement validation logic here
    }

    public void AffcherEvenement(ActionEvent actionEvent) {
    }

    public void sauvegarder(ActionEvent actionEvent) {
        Evenement selectedEvenement = new Evenement();
        selectedEvenement.setId_e(this.selectedid);
        System.out.println(selectedEvenement);
        String nomE = nom_e.getText();
        Salle selectedSalle = nom_salle.getValue();
        LocalDate dateDebutVal = dateDebut.getValue();
        String nomDiscipline = nom_discipline.getText();
        String descriptionVal = description.getText();
        LocalDate dateFinVal = dateFin.getValue();
        int nbrMax ;
        if (nbr_max.getText().isEmpty()) {
            showAlert("Erreur", "Veuillez saisir un nombre maximum.");
            return;
        } else {
            nbrMax = Integer.parseInt(nbr_max.getText());
        }

        if (nomE.isEmpty() || selectedSalle == null || dateDebutVal == null || nomDiscipline.isEmpty() || descriptionVal.isEmpty() || nbrMax == 0 || dateFinVal == null) {
            showAlert("Erreur", "Remplir tout les champs.");
            return;
        }

        selectedEvenement.setNom_e(nomE);
        selectedEvenement.setNom_salle(selectedSalle.getNom());
        selectedEvenement.setDateDebut(Date.valueOf(dateDebutVal));
        selectedEvenement.setNom_discipline(nomDiscipline);
        selectedEvenement.setDescription(descriptionVal);
        selectedEvenement.setNbr_max(nbrMax);
        selectedEvenement.setDateFin(Date.valueOf(dateFinVal));
        System.out.println(selectedEvenement);
        evenementService.update(selectedEvenement);
        showSuccessAlert("Success","Evenement modifiée avec succées");
        btModif.setVisible(true);
        hiddenButton.setVisible(false);
        clearFields();
        populateTable();
    }

    private void showSuccessAlert(String title, String msg) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
