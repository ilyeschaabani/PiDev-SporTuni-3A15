package Controller;

import Entity.Cour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.stage.Stage;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import Service.CourService;
import Service.DisciplineService;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

import javafx.scene.chart.AreaChart;

public class CourDashbordController  {
    private CourService cs = new CourService();
    private Cour cour;

    @FXML
    private Button btnadd2;
    @FXML
    private Button dash;
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
    private Button btnexel;
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
    private AreaChart<String, Number> area_chart;
    @FXML
    private Label lblTotalCoursValue;

    @FXML
    private Label lblTotalDisciplinesValue;

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
            clNomSalle.setCellValueFactory(new PropertyValueFactory<>("nom_salle"));
            clNbParticipants.setCellValueFactory(new PropertyValueFactory<>("nb_max"));
            statArea();
            afficherNombreTotalCours();
            afficherNombreTotalDisciplines();

        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }

    }
    private DisciplineService disciplineService = new DisciplineService();
    public void afficherNombreTotalDisciplines() {
        int totalDisciplines = disciplineService.getNombreTotalDisciplines(); // Utilisation du service pour récupérer le total des disciplines
        lblTotalDisciplinesValue.setText(Integer.toString(totalDisciplines)); // Affichage du total des disciplines dans le Label correspondant
    }
    public void afficherNombreTotalCours() {
        int totalCours = cs.getNombreTotalCours(); // Méthode à implémenter dans votre service CoursService
        lblTotalCoursValue.setText(Integer.toString(totalCours));
    }
    @FXML
    void handleGeneratePDF(ActionEvent actionEvent) {
        try {
            ObservableList<Cour> cours = tvCour.getItems();
            Workbook wb = new XSSFWorkbook();
            Sheet sheet = wb.createSheet("Cours");

            // Créer l'en-tête du tableau Excel
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Nom du cours");
            headerRow.createCell(1).setCellValue("Discipline");
            headerRow.createCell(2).setCellValue("Date");
            headerRow.createCell(3).setCellValue("Heure début");
            headerRow.createCell(4).setCellValue("Heure fin");
            headerRow.createCell(5).setCellValue("Nom de la salle");
            headerRow.createCell(6).setCellValue("Nombre maximal de participants");

            // Remplir les données
            int rowNum = 1;
            for (Cour cour : cours) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(cour.getNom_cour());
                row.createCell(1).setCellValue(cour.getNom_discipline());
                row.createCell(2).setCellValue(cour.getDate().toString());
                row.createCell(3).setCellValue(cour.getHeure_debut());
                row.createCell(4).setCellValue(cour.getHeure_fin());
                row.createCell(5).setCellValue(cour.getNom_salle());
                row.createCell(6).setCellValue(cour.getNb_max());
            }

            // Écrire le classeur dans le fichier
            try (OutputStream fileOut = new FileOutputStream(new File("cours_data.xlsx"))) {
                wb.write(fileOut);
            }

            showAlert("Information", "Fichier Excel généré avec succès.", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Erreur", "Une erreur s'est produite lors de la génération du fichier Excel.", Alert.AlertType.ERROR);
            e.printStackTrace();
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

            if (!Character.isUpperCase(nomCours.charAt(0))) {
                showAlert("Error", "Le nom du cours doit commencer par une majuscule", Alert.AlertType.ERROR);
                return;
            }

            if (nomCours.isEmpty() || nomDiscipline.isEmpty() || heureDebut.isEmpty() || heureFin.isEmpty() || nomSalle.isEmpty() || nb_max.isEmpty()) {
                showAlert("Error", "Veuillez remplir tous les champs", Alert.AlertType.ERROR);
                return;
            }

            // Vérifier la validité de la date
            LocalDate localDate = tfdate.getValue();
            if (localDate == null) {
                showAlert("Error", "Veuillez sélectionner une date", Alert.AlertType.ERROR);
                return;
            }
            LocalDate today = LocalDate.now();
            if (localDate.isBefore(today)) {
                showAlert("Error", "La date doit être dans le futur", Alert.AlertType.ERROR);
                return;
            }

            LocalTime heureDebutTime = LocalTime.parse(heureDebut);
            LocalTime heureFinTime = LocalTime.parse(heureFin);

// Vérifier si l'heure de début est avant l'heure de fin
            // Vérifier le format de l'heure de début
            if (!heureDebut.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                showAlert("Error", "Le format de l'heure de début est incorrect (HH:mm)", Alert.AlertType.ERROR);
                return;
            }

            // Vérifier le format de l'heure de fin
            if (!heureFin.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                showAlert("Error", "Le format de l'heure de fin est incorrect (HH:mm)", Alert.AlertType.ERROR);
                return;
            }


            // Vérifier si l'heure de début est avant l'heure de fin
            if (heureDebutTime.isAfter(heureFinTime)) {
                showAlert("Error", "L'heure de début doit être avant l'heure de fin", Alert.AlertType.ERROR);
                return;
            }

            // Vérifier si l'heure de début est après l'heure actuelle (ou une autre condition si nécessaire)
          /*  LocalTime currentTime = LocalTime.now();
            if (heureDebutTime.isBefore(currentTime)) {
                showAlert("Error", "L'heure de début doit être dans le futur", Alert.AlertType.ERROR);
                return;
            }*/
            int nbParticipants = Integer.parseInt(nb_max);
            if (nbParticipants <= 0) {
                showAlert("Error", "Le nombre maximal de participants doit être supérieur à zéro", Alert.AlertType.ERROR);
                return;
            }

         /*   if (!isSalleDisponible(nomSalle, localDate, heureDebut, heureFin)) {
                showAlert("Error", "La salle n'est pas disponible à cette heure", Alert.AlertType.ERROR);
                return;
            }*/
            // Créer un nouvel objet Cour avec les données saisies
            Cour nouveauCour = new Cour(nomCours, Date.valueOf(localDate), heureDebut, heureFin, nomSalle, nb_max, nomDiscipline);

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
            FilteredList<Cour> filteredList = new FilteredList<>(tvCour.getItems(), p -> true);

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
    void Trie(ActionEvent event) {
        try {
            ObservableList<Cour> observableList = FXCollections.observableList(cs.readAll());

            // Définir le comparateur pour trier par nom de cours
            Comparator<Cour> comparator = Comparator.comparing(Cour::getNom_cour);

            // Trier la liste observable
            observableList.sort(comparator);

            // Mettre à jour TableView avec la liste triée
            tvCour.setItems(observableList);
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

                // Vérifier si le nom commence par une majuscule
                if (!Character.isUpperCase(nomCours.charAt(0))) {
                    showAlert("Error", "Le nom du cours doit commencer par une majuscule", Alert.AlertType.ERROR);
                    return;
                }

                // Vérifier si tous les champs sont remplis
                if (nomCours.isEmpty() || nomDiscipline.isEmpty() || heureDebut.isEmpty() || heureFin.isEmpty() || nomSalle.isEmpty() || nb_max.isEmpty()) {
                    showAlert("Error", "Veuillez remplir tous les champs", Alert.AlertType.ERROR);
                    return;
                }

                // Vérifier la validité de la date
                LocalDate localDate = tfdate.getValue();
                if (localDate == null) {
                    showAlert("Error", "Veuillez sélectionner une date", Alert.AlertType.ERROR);
                    return;
                }
                LocalDate today = LocalDate.now();
                if (localDate.isBefore(today)) {
                    showAlert("Error", "La date doit être dans le futur", Alert.AlertType.ERROR);
                    return;
                }

                // Vérifier le format de l'heure de début
                if (!heureDebut.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                    showAlert("Error", "Le format de l'heure de début est incorrect (HH:mm)", Alert.AlertType.ERROR);
                    return;
                }

                // Vérifier le format de l'heure de fin
                if (!heureFin.matches("^([01]?[0-9]|2[0-3]):[0-5][0-9]$")) {
                    showAlert("Error", "Le format de l'heure de fin est incorrect (HH:mm)", Alert.AlertType.ERROR);
                    return;
                }

               /* LocalTime heureDebutTime = LocalTime.parse(heureDebut);
                LocalTime heureFinTime = LocalTime.parse(heureFin);

                // Vérifier si l'heure de début est avant l'heure de fin
                if (heureDebutTime.isAfter(heureFinTime)) {
                    showAlert("Error", "L'heure de début doit être avant l'heure de fin", Alert.AlertType.ERROR);
                    return;
                }

                // Vérifier si l'heure de début est après l'heure actuelle (ou une autre condition si nécessaire)
                LocalTime currentTime = LocalTime.now();
                if (heureDebutTime.isBefore(currentTime)) {
                    showAlert("Error", "L'heure de début doit être dans le futur", Alert.AlertType.ERROR);
                    return;
                }*/
                int nbParticipants = Integer.parseInt(nb_max);
                if (nbParticipants <= 0) {
                    showAlert("Error", "Le nombre maximal de participants doit être supérieur à zéro", Alert.AlertType.ERROR);
                    return;
                }

                // Mettre à jour les informations du cours
                cour.setNom_cour(nomCours);
                cour.setNom_discipline(nomDiscipline);
                cour.setDate(Date.valueOf(localDate));
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

    public void dashboard(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Statistique.fxml"));
            dash.getScene().setRoot(root);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public void statArea() {
        ObservableList<Cour> courList = FXCollections.observableList(cs.readAll());

        // Mettre en œuvre la logique pour collecter les données nécessaires pour votre AreaChart


        Map<String, Long> disciplineCounts = courList.stream()
                .collect(Collectors.groupingBy(Cour::getNom_discipline, Collectors.counting()));

        // Utilisez les données collectées pour créer des séries pour votre AreaChart
        // Exemple:
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        disciplineCounts.forEach((discipline, count) -> {
            series.getData().add(new XYChart.Data<>(discipline, count));
        });

        // Ajouter la série à votre AreaChart
        area_chart.getData().add(series);
    }
    @FXML
    private Button btn_discipline;

    @FXML
    void GoToDiscipline(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/DisciplineDashbord.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    @FXML
    void Return(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Menu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


}