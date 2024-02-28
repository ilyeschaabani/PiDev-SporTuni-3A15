package controllers;

import java.net.URL;
import java.util.ResourceBundle;
import enities.Product;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.When;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import services.ServiceProduct;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class Afficher {
    public Label title;
    ServiceProduct ss = new ServiceProduct();
    @FXML
    private Button btnadd1;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Product, Integer> clprix;

    @FXML
    private TableColumn<Product, String> clnom;

    @FXML
    private TableColumn<Product, Integer> clcateg;
    @FXML
    private TextField searchField;


    @FXML
    private TextField tfprix;

    @FXML
    private TextField tfnom1;

    @FXML
    private TextField tfcateg;

    @FXML
    private TableView<Product> tvProduct;
    private Product product;
    @FXML
    private Button btnmodif;

    @FXML
    private Button btnsupp;
    @FXML
    private Button btnstat;

    @FXML
    private void initialize() {
        try {
            ObservableList<Product> observableList = FXCollections.observableList(ss.readAll());
            tvProduct.setItems(observableList);
            clnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            clcateg.setCellValueFactory(new PropertyValueFactory<>("category"));
            clprix.setCellValueFactory(new PropertyValueFactory<>("prix"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void mouseClicked1(MouseEvent mouseEvent) {
        try {
            // Check if the TextField objects have been initialized
            if (tfnom1 == null || tfcateg == null || tfprix == null) {
                return;
            }

            Product s = tvProduct.getSelectionModel().getSelectedItem();
            if (s != null) {
                tfnom1.setText(s.getNom());
                tfcateg.setText(s.getCategory());
                tfprix.setText(String.valueOf(s.getPrix()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void delete(ActionEvent actionEvent) {
        try {
            Product selectedProduct = tvProduct.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                // Call delete method from the service class
                ss.delete(ss.getid(selectedProduct));

                // Refresh the TableView
                initialize();

                showAlert("Information", "Product deleted", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "No product selected for deletion.", Alert.AlertType.ERROR);
            }
        } catch (SQLException e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    @FXML
    public void update(ActionEvent actionEvent) {
        try {
            Product selectedProduct = tvProduct.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                ServiceProduct ss = new ServiceProduct();
                // Mise à jour des propriétés du produit sélectionné
                selectedProduct.setId_Product(ss.getid(selectedProduct));
                selectedProduct.setNom(tfnom1.getText());
                selectedProduct.setCategory(tfcateg.getText());
                selectedProduct.setPrix(Double.parseDouble(tfprix.getText()));

                // Appel de la méthode de service pour mettre à jour le produit
                ss.update(selectedProduct);

                // Réinitialisation de la TableView pour refléter les changements
                initialize();

                // Affichage d'une alerte pour confirmer la mise à jour
                showAlert("Information", "Product updated", Alert.AlertType.INFORMATION);
            } else {
                // Affichage d'une alerte si aucun produit n'est sélectionné
                showAlert("Error", "No product selected for update.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            // Affichage d'une alerte en cas d'erreur
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void add(ActionEvent actionEvent) {
        try {
            // Create a new Product object using the values from the text fields
            Product newProduct = new Product(
                    tfnom1.getText(),
                    tfcateg.getText(),
                    Double.parseDouble(tfprix.getText())
            );

            // Add the new product using the service
            ss.add(newProduct);

            // Refresh the UI
            initialize();

            // Show a success message
            showAlert("Information", "Produit ajoutée", Alert.AlertType.INFORMATION);
        } catch(Exception e) {
            // Show an error message if an exception occurs
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    public void recherche(ActionEvent actionEvent) {
        try {
            // Get the list of all products
            ObservableList<Product> observableList = FXCollections.observableList(ss.readAll());

            // Create a filtered list
            FilteredList<Product> filteredList = new FilteredList<>(observableList);

            // Set the filter predicate to filter by nom, category, or prix
            filteredList.predicateProperty().bind(Bindings.createObjectBinding(() -> {
                String searchText = searchField.getText();
                if (searchText == null || searchText.isEmpty()) {
                    return p -> true;
                } else {
                    return p -> p.getNom().contains(searchText)
                            || p.getCategory().contains(searchText)
                            || String.valueOf(p.getPrix()).contains(searchText);
                }
            }, searchField.textProperty()));

            // Create a sorted list
            SortedList<Product> sortedList = new SortedList<>(filteredList);

            // Bind the sorted list to the table view
            sortedList.comparatorProperty().bind(tvProduct.comparatorProperty());

            // Set the items in the table view
            tvProduct.setItems(sortedList);

        } catch (Exception e) {
            // Handle any exceptions
            throw new RuntimeException(e);
        }
    }
    @FXML
    void stat(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/statistique.fxml"));
            tfnom1.getScene().setRoot(root);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void home(ActionEvent actionEvent) {
    }




}