package controller;

import entity.Cour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import service.CourService;
import service.DisciplineService;
import javafx.event.ActionEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import java.io.File;
import java.io.OutputStream;
import java.nio.file.Files;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
public class Statistique {

    @FXML
    private PieChart pi_chart;
    @FXML
    private Button btnCour;
    @FXML
    private Button btnpdf;
    @FXML
    private Label lblTotalCoursValue;
    @FXML
    private Label lblTotalDisciplinesValue;
    private CourService courService = new CourService();// Assurez-vous d'initialiser le service approprié pour gérer les cours

    private DisciplineService disciplineService = new DisciplineService(); // Ajout du service pour gérer les disciplines

    @FXML
    private AreaChart<String, Number> area_chart;

    @FXML
    void initialize() {
        try {
            assert btnCour != null : "fx:id=\"btnCour\" was not injected: check your FXML file 'Statistique.fxml'.";
            statPi();
            statArea();
            afficherNombreTotalCours();
            afficherNombreTotalDisciplines(); // Appel de la méthode pour afficher le total des disciplines

        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }





    public void statPi() {
        ObservableList<Cour> courList = FXCollections.observableList(courService.readAll());

        Map<String, Long> disciplineCounts = courList.stream()
                .collect(Collectors.groupingBy(Cour::getNom_discipline, Collectors.counting()));

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        disciplineCounts.forEach((discipline, count) -> {
            PieChart.Data data = new PieChart.Data(discipline, count);
            pieChartData.add(data);
        });

        pi_chart.setData(pieChartData);
    }



    public void statArea() {
        ObservableList<Cour> courList = FXCollections.observableList(courService.readAll());

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

    public void afficherNombreTotalCours() {
        int totalCours = courService.getNombreTotalCours(); // Méthode à implémenter dans votre service CoursService
        lblTotalCoursValue.setText(Integer.toString(totalCours));
    }
    public void afficherNombreTotalDisciplines() {
        int totalDisciplines = disciplineService.getNombreTotalDisciplines(); // Utilisation du service pour récupérer le total des disciplines
        lblTotalDisciplinesValue.setText(Integer.toString(totalDisciplines)); // Affichage du total des disciplines dans le Label correspondant
    }

    public void homeC(javafx.event.ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/CourAffichage.fxml"));
            btnCour.getScene().setRoot(root);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    public void homeD(javafx.event.ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Afficher.fxml"));
            btnCour.getScene().setRoot(root);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}