package Controller;

import Entity.User;
import Service.UserService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Utiils.SessionManager;

public class MyProfileController {
    @FXML
    private Button btn_ajout;

    @FXML
    private ComboBox<String> cmbRole_modif;

    @FXML
    private TextField tfadress_modif;

    @FXML
    private TextField tfemail_modif;

    @FXML
    private TextField tfnom_modif;

    @FXML
    private TextField tfnumero_modif;

    @FXML
    private TextField tfprenom_modif;
    @FXML
    private TextField tfid_modif;


    @FXML
    public void initialize() {
        cmbRole_modif.getItems().addAll("Coach", "adherant");
        cmbRole_modif.setValue("Role");
        setText(User.getCurentUser());
    }

    @FXML
    void modifier(ActionEvent event ) {
        UserService rec= new UserService();
        User user ;

//        validateFields();


        Integer id = Integer.parseInt(tfid_modif.getText());
        Integer numero=Integer.parseInt(tfnumero_modif.getText());
        String nom= tfnom_modif.getText();
        String adresse= tfadress_modif.getText();
        String email= tfemail_modif.getText();
        String prenom= tfprenom_modif.getText();
        String role= cmbRole_modif.getValue();

        User R;
        R = new User(id,nom,prenom,email,role,numero,adresse);
        rec.update(R);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("SporTuni :: Success Message");
        alert.setHeaderText(null);
        alert.setContentText("Utilsateur modifié");
        alert.showAndWait();


    }
    public String getSelectedValue() {
        return cmbRole_modif.getValue();
    }

    public void setText(User user)
    {
        user.getCurentUser();
        String id =String.valueOf(user.Current_User.getId_user());
        tfnumero_modif.setText(id);
        String numero =String.valueOf(user.Current_User.getNumero());
        tfnumero_modif.setText(numero);
        tfnom_modif.setText(user.Current_User.getNom());
        tfadress_modif.setText(user.Current_User.getAdresse());
        tfemail_modif.setText(user.Current_User.getEmail());
        tfprenom_modif.setText(user.Current_User.getPrenom());
        cmbRole_modif.setValue(user.Current_User.getRole());
    }
    public void setTextFields(User R){
        String id =String.valueOf(R.getId_user());
        tfid_modif.setText(id);
        String numero =String.valueOf(R.getNumero());
        tfnumero_modif.setText(numero);
        tfnom_modif.setText(R.getNom());
        tfadress_modif.setText(R.getAdresse());
        tfemail_modif.setText(R.getEmail());
        tfprenom_modif.setText(R.getPrenom());
    }
}
