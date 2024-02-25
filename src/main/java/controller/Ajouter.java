package controller;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import entity.Salle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import service.SalleService;



public class Ajouter {
    SalleService ss =new SalleService();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnafficher;

    @FXML
    private Button btnajouter;

    @FXML
    private TextField tfcapacite;

    @FXML
    private TextField tfdiscipline;

    @FXML
    private TextField tfdispo;

    @FXML
    private TextField tfnom;

    @FXML
    private TextField tfsurface;

    @FXML
    void add(ActionEvent event) {
        try {
            ss.add(new Salle(tfnom.getText(),Integer.parseInt(tfsurface.getText()), Integer.parseInt(tfcapacite.getText()), tfdiscipline.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("success");
            alert.setContentText("salle ajoutée");
            alert.showAndWait();
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("fail");
            alert.setContentText(e.getMessage());
            alert.showAndWait();}

    }



    @FXML
    void read(ActionEvent event) {
        try {
            Parent root =FXMLLoader.load(getClass().getResource("/Afficher.fxml"));
            tfnom.getScene().setRoot(root);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

    @FXML
    void initialize() {
        assert btnafficher != null : "fx:id=\"btnafficher\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert btnajouter != null : "fx:id=\"btnajouter\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert tfcapacite != null : "fx:id=\"tfcapacite\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert tfdiscipline != null : "fx:id=\"tfdiscipline\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert tfdispo != null : "fx:id=\"tfdispo\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert tfnom != null : "fx:id=\"tfnom\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert tfsurface != null : "fx:id=\"tfsurface\" was not injected: check your FXML file 'Ajouter.fxml'.";

    }

}
