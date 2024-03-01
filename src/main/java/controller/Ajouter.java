/*package controller;

import java.net.URL;
import java.util.ResourceBundle;

import entity.Discipline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import service.DisciplineService;



public class Ajouter {
    DisciplineService ss =new DisciplineService();
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnafficher;

    @FXML
    private Button btnajouter;

    @FXML
    private TextArea tfdescription;

    @FXML
    private TextField tfnom;


    @FXML
    void add(ActionEvent event) {
        try {
            ss.add(new Discipline(tfnom.getText(), tfdescription.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("success");
            alert.setContentText("description ajoutée");
            alert.showAndWait();
        } catch(Exception e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("erreur");
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
        assert tfdescription != null : "fx:id=\"tfdescription\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert tfnom != null : "fx:id=\"tfnom\" was not injected: check your FXML file 'Ajouter.fxml'.";
        }

}*/
