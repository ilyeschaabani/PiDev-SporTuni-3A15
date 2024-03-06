package Controller;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import Entity.Discipline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Service.DisciplineService;
import javafx.scene.input.MouseEvent;

public class DisciplineDashbordController {
    public Label title;
    DisciplineService ss=new DisciplineService();
    @FXML
    private Button btnadd1;
    @FXML
    private Button dashb;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnmodif;

    @FXML
    private Button btnsupp;

    @FXML
    private TableColumn<Discipline, String> cldesc;

    @FXML
    private TableColumn<Discipline, String> clnom;

    @FXML
    private TextField searchField;


    @FXML
    private TextField tfdesc1;

    @FXML
    private TextField tfnom1;

    @FXML
    private TableView<Discipline> tvdiscipline;
    private Discipline discipline;


    @FXML
    void initialize() {
        try {
            ObservableList<Discipline> observableList = FXCollections.observableList(ss.readAll());
            tvdiscipline.setItems(observableList);
            clnom.setCellValueFactory(new PropertyValueFactory<>("nom_discipline"));
            cldesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public void mouseClicked1(MouseEvent mouseEvent) {
        try{
            Discipline s=tvdiscipline.getSelectionModel().getSelectedItem();
            System.out.println(s);
            if(s !=null) {

                discipline = new Discipline(s.getId_discipline(), s.getNom_discipline(),  s.getDescription());
                tfnom1.setText(discipline.getNom_discipline());
                tfdesc1.setText(String.valueOf(discipline.getDescription()));

            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }
    private void showNotification(String title, String message) {
        if (SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();
                Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\rawen\\Documents\\GitHub\\PiDev-SporTuni-3A15\\src\\main\\resources\\Images\\small_tick");

                TrayIcon trayIcon = new TrayIcon(icon, "Notification");
                trayIcon.setImageAutoSize(true);

                trayIcon.addActionListener(e -> {
                    // Gérer l'événement de clic sur l'icône de la barre d'état si nécessaire
                });

                tray.add(trayIcon);
                trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    public void delete(ActionEvent actionEvent) {
        try {
            DisciplineService ss = new DisciplineService();
            if (discipline != null) {
                ss.delete(discipline.getId_discipline());
                initialize();
                showNotification("Suppression", "Discipline supprimée avec succès");
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }



    public void update(ActionEvent actionEvent) {
        try {
            if (discipline != null) {
                DisciplineService ss = new DisciplineService();
                // Mettre à jour les informations de la discipline
                discipline.setNom_discipline(tfnom1.getText());
                discipline.setDescription(tfdesc1.getText());
                ss.updato(discipline);
                initialize(); // Rafraîchir la TableView
                showAlert("Information", "discipline modifiée", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Veuillez sélectionner une discipline à modifier", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void add(ActionEvent actionEvent) {
        try {
            String nom = tfnom1.getText().trim();
            String desc = tfdesc1.getText().trim();

            // Validation de la saisie
            if (nom.isEmpty() || desc.isEmpty()) {
                showAlert("Error", "Veuillez remplir tous les champs.", Alert.AlertType.ERROR);
                return;
            }

            if (nom.length() < 3 || nom.length() > 50) {
                showAlert("Error", "Le nom doit contenir entre 3 et 50 caractères.", Alert.AlertType.ERROR);
                return;
            }

            if (!nom.matches("^[A-Z][a-zA-Z]*$")) {
                showAlert("Error", "Le nom doit commencer par une majuscule et ne doit contenir que des lettres.", Alert.AlertType.ERROR);
                return;
            }

            if (desc.length() < 3 || desc.length() > 200) {
                showAlert("Error", "La description doit contenir entre 10 et 200 caractères.", Alert.AlertType.ERROR);
                return;
            }
            ss.add(new Discipline(nom, desc));
            initialize();
            showAlert("Information", "Discipline ajoutée", Alert.AlertType.INFORMATION);
        } catch(Exception e){
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void generatePDF(ActionEvent actionEvent) {
        // Récupérer toutes les disciplines affichées dans la table view
        ObservableList<Discipline> disciplines = tvdiscipline.getItems();

        // Vérifier si la liste des disciplines est vide
        if (disciplines == null || disciplines.isEmpty()) {
            showAlert("Information", "La liste des disciplines est vide.", Alert.AlertType.INFORMATION);
            return;
        }

        // Générer le PDF
        PDFGenerator pdfGenerator = new PDFGenerator();
        pdfGenerator.generatePDF("disciplines.pdf", disciplines);

        showAlert("Information", "PDF généré avec succès.", Alert.AlertType.INFORMATION);
    }

    public void recherche(ActionEvent actionEvent) {
        try {
            // Récupérer toutes les disciplines
            ObservableList<Discipline> observableList = FXCollections.observableList(ss.readAll());

            // Créer un FilteredList lié à la liste observable
            FilteredList<Discipline> filteredList = new FilteredList<>(observableList, p -> true);

            // Ajouter un écouteur sur le champ de recherche
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(discipline -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Afficher toutes les disciplines quand le champ de recherche est vide
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    // Vérifier si le nom de la discipline ou la description contient le texte de recherche
                    return discipline.getNom_discipline().toLowerCase().contains(lowerCaseFilter)
                            || discipline.getDescription().toLowerCase().contains(lowerCaseFilter);
                });
            });

            // Créer une SortedList liée au FilteredList et triée selon le comparateur de la table
            SortedList<Discipline> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tvdiscipline.comparatorProperty());

            // Mettre à jour les données de la table avec la liste triée et filtrée
            tvdiscipline.setItems(sortedList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void dash2(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Statistique.fxml"));
            dashb.getScene().setRoot(root);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

}