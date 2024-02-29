package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Service.UserService;
import Entity.User;

import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class InscriptionController {
    UserService us = new UserService();
    @FXML
    private Button btn_isncri;

    @FXML
    private ComboBox <String> cmbRole;

    @FXML
    private TextField tfadress;

    @FXML
    private TextField tfemail;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfnumero;

    @FXML
    private TextField tfprenom;

    @FXML
    private TextField tfpwd;

    private Connection cnx;
    @FXML
    void add(ActionEvent event) {
        try {
            User u = new User(tfnom.getText(), tfprenom.getText(), tfemail.getText(), tfpwd.getText(), getSelectedValue(), Integer.parseInt(tfnumero.getText()), tfadress.getText());
            us.add(u);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("User added successfully");
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error adding user");
            alert.showAndWait();
        }
    };

    public void initialize() {
//        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "Client");
//        cmbRole.setItems(roles);
        cmbRole.getItems().addAll("Coach", "adherant");
        cmbRole.setValue("Role");

    }

    // Method to get the selected value
    public String getSelectedValue() {
        return cmbRole.getValue();
    }


}
