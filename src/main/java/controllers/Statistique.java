package controllers;
import enities.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import services.ServiceProduct;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistique {

    @FXML
    private PieChart pi_chart;

    @FXML
    private Label lblTotalProductValue;

    private ServiceProduct productService = new ServiceProduct();

    @FXML
    void initialize() {
        try {
            statPi();
            afficherNombreTotalProduct();
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
        ObservableList<Product> productList = FXCollections.observableList(productService.readAll());

        Map<String, Long> categoryCounts = productList.stream()
                .collect(Collectors.groupingBy(Product::getCategory, Collectors.counting()));

        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        categoryCounts.forEach((category, count) -> {
            PieChart.Data data = new PieChart.Data(category, count);
            pieChartData.add(data);
        });

        pi_chart.setData(pieChartData);
    }
    @FXML
    void navigateToProducts(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Products.fxml"));
            pi_chart.getScene().setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void afficherNombreTotalProduct() {
        int totalProducts = productService.getNombreTotalProduct();
        lblTotalProductValue.setText(Integer.toString(totalProducts));
    }
}
