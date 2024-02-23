package controllers;

import enities.Product;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import services.ServiceProduct;

import java.sql.SQLException;

public class AddProduitController {

        ServiceProduct sp=new ServiceProduct();
        @FXML
        private TextField tf_details;

        @FXML
        private TextField tf_more;

        @FXML
        private TextField tf_nom;
    private Object AlertType;

    @FXML
        void Ajouter_au_Panier(ActionEvent event) throws SQLException {
            ServiceProduct.ajouter(new Product(Integer.parseInt(tf_nom.getText()), tf_details.getText(), tf_more.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("saha");
            alert.setContentText("merci pour passer la commande ");
            alert.showAndWait();
        }


    @FXML
    private HBox cardlayout;
    }