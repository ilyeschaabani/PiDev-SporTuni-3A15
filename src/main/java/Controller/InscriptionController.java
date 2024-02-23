package Controller;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import service.UserService;
import Entity.User;

import java.net.URL;
import java.util.ResourceBundle;

public class InscriptionController {
    service.UserService us = new service.UserService();
    @FXML
    private ComboBox<String> Roles;

    @FXML
    private Button btn;

    @FXML
    private TextField tf_Motdepasse;

    @FXML
    private TextField tf_Nom;

    @FXML
    private TextField tf_adrdess;

    @FXML
    private TextField tf_email;

    @FXML
    private TextField tf_numero;

    @FXML
    private TextField tf_prenom;

    @FXML
    void add(ActionEvent event) {
        try {
            User u = new User(tf_Nom.getText(), tf_prenom.getText(), tf_email.getText(), tf_Motdepasse.getText(), Roles.getValue(), Integer.parseInt(tf_numero.getText()), tf_adrdess.getText());
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
    }
    public void initialize(URL url, ResourceBundle rb) {
        Roles.setItems(FXCollections.observableArrayList("coach","adherant","arbitre"));
    }


}
