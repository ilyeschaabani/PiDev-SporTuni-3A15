package controllers;

import entities.Evenement;
import entities.Salle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import facebook4j.FacebookException;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.EvenementService;
import services.SalleService;

import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
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
        LocalDate dateActuelle = LocalDate.now();
        if (dateDebutVal.isBefore(dateActuelle)) {
            showAlert("Erreur", "Veuillez choisir une date de début dans le futur.");
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

    public void goToStats(ActionEvent event) {
        FXMLLoader innerLoader = new FXMLLoader(getClass().getResource("/StatsEvents.fxml"));
        try {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = innerLoader.load();
            Scene scene = new Scene(root,stage.getWidth(), stage.getHeight() );

            stage.setTitle("Scene One");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void goToPdf(ActionEvent event) throws SQLException {
        // Afficher la boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            // Générer le fichier PDF avec l'emplacement de sauvegarde sélectionné
            // Récupérer la liste des visites médicales
            List<Evenement> evenements = evenementService.readAll();

            try {
                // Créer le document PDF
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
                document.open();

                // Titre du document
                Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 32, Font.BOLD, BaseColor.DARK_GRAY);
                Paragraph title = new Paragraph("Liste des evenements", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20);
                document.add(title);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10);

                // Ajouter les en-têtes de colonnes
                String[] headers = {"ID", "description", "date début", "Nom Salle", "Nombre de places maximale"};
                Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Paragraph(header, headerFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }

                // Ajouter les données des visites médicales
                for (Evenement e : evenements) {
                    table.addCell(String.valueOf(e.getId_e()));
                    table.addCell(String.valueOf(e.getDescription()));
                    table.addCell(String.valueOf(e.getDateDebut()));
                    table.addCell(e.getNom_salle());
                    table.addCell(String.valueOf(e.getNbr_max()));
                }

                document.add(table);
                document.close();

                System.out.println("Le fichier PDF a été généré avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void goToEvents(ActionEvent event) {
        FXMLLoader innerLoader = new FXMLLoader(getClass().getResource("/AjouterEvenement.fxml"));
        try {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = innerLoader.load();
            Scene scene = new Scene(root,stage.getWidth(), stage.getHeight() );

            stage.setTitle("Scene One");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void postFacebook(MouseEvent mouseEvent) {
        EvenementService e=new EvenementService();
        Evenement event = e.getLastInsertedEvenement();
        System.out.println(event.getId_e() + event.getDescription() + event.getNom_salle());
        String appId = "232528662540085";
        String appSecret = "60988e9928012f06c205e07717bb4196";
        String accessTokenString = "EAADTe8xUrzUBO3mGRL9ZBcnXcIL7SCf9TpxvqmlxZC6xOsKJRFN1TZBEwZAw3PipeZB1vOWMiylsWG8iBBOXZCIZAjUcE4ZAFJH275ZCcXeIZANH5ubkyxa0Av4K779KilKsUwbNHvXCMkv4TZCSZBFtNxkkMCb8g6ToFt335CtcUdjYcLVCbF5jTTpeYSbbDkZB8OUOb8dZAYtu0fMCB0HKpOtxZBDluF8";
        // BADEL ACCESSTOKENSTRING AKAHAW 9BAL VALIDATION ###############

        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(appId, appSecret);
        facebook.setOAuthAccessToken(new AccessToken(accessTokenString, null));

        String msg = "Rejoinez notre Event! "
                + "\n"
                + event.getDescription()
                + "starting on at"
                + event.getDateDebut()+".Book your application now via our desktop app.";

        try {
            facebook.postStatusMessage(msg);
            System.out.println("Post shared successfully.");
        } catch (FacebookException e2) {
            throw new RuntimeException(e2);
        }


    }
}
