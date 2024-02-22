package controller;


import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import entity.Salle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.SalleService;
import javafx.scene.input.MouseEvent;

public class Stat {
    @FXML
    private Button btnhome;

    @FXML
    private Button btnstat;
    @FXML
    private PieChart pie;
    @FXML
    private Label lbltotale;
    @FXML
    private Label lbldispo;



        public Label title;
        SalleService ss=new SalleService();

        Salle salle=new Salle();




        @FXML
        void initialize() {
            try {
                assert btnhome != null : "fx:id=\"btnhome\" was not injected: check your FXML file 'Stat.fxml'.";
                assert btnstat != null : "fx:id=\"btnstat\" was not injected: check your FXML file 'Stat.fxml'.";
                int totalSalles = ss.NbrDeSalleTotale();
                lbltotale.setText(String.valueOf(ss.NbrDeSalleTotale()));
                lbldispo.setText(String.valueOf(ss.NbrDeSalleDispo()));



            }catch (Exception e){
                throw new RuntimeException(e);
            }

        }




        private void showAlert(String title, String content, Alert.AlertType alertType) {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setContentText(content);
            alert.showAndWait();
        }

        public void home(ActionEvent actionEvent) {

                try {
                    Parent root =FXMLLoader.load(getClass().getResource("/Afficher.fxml"));
                    btnhome.getScene().setRoot(root);


                }catch (Exception e)
                {
                    System.out.println(e.getMessage());
                }

        }


    public void stat(ActionEvent actionEvent) {
    }

    public void statis(MouseEvent mouseEvent) {
        System.out.println("statis method triggered");
        SalleService salleService = new SalleService();
        List<Salle> salleList = salleService.readAll();

        // Collect the statistics based on "discipline"
        Map<String, Long> disciplineStats = salleList.stream()
                .collect(Collectors.groupingBy(Salle::getDiscipline, Collectors.counting()));

        // Create PieChart.Data from the statistics
        List<PieChart.Data> pieChartData = disciplineStats.entrySet().stream()
                .map(entry -> new PieChart.Data(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());

        // Set the data to the PieChart
        pie.setData(FXCollections.observableArrayList(pieChartData));

    }




}


