package controller;


import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.function.Function;
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
    private PieChart pi_chart;
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

                statPi();

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

public void statPi(){
    ObservableList<Salle> observableList = FXCollections.observableList(ss.readAll());

    List<String> disciplines = observableList.stream()
            .map(Salle::getDiscipline)
            .collect(Collectors.toList());

    // Count the occurrences of each discipline
    Map<String, Long> disciplineCounts = disciplines.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

    // Create PieChart Data
    ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
    disciplineCounts.forEach((discipline, count) -> {
        PieChart.Data data = new PieChart.Data(discipline, count);
        pieChartData.add(data);
    });

    // Set PieChart data
    pi_chart.setData(pieChartData);
}



}


