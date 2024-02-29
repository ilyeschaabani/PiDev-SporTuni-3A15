package Controller;

import Entity.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import Service.UserService;
public class ModifierPageController  {

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
    private TextField tfid_modif1;


    @FXML
    public void initialize() {
        cmbRole_modif.getItems().addAll("Coach", "adherant");
        cmbRole_modif.setValue("Role");
    }

    @FXML
    void modifier(ActionEvent event ) {
        UserService rec= new UserService();
        User user ;


        Integer id = Integer.parseInt(tfid_modif1.getText());
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
        alert.setTitle("Travel Me :: Success Message");
        alert.setHeaderText(null);
        alert.setContentText("Utilsateur modifié");
        alert.showAndWait();

    }
    public String getSelectedValue() {
        return cmbRole_modif.getValue();
    }

    public void setText(User user)
    {
        String id =String.valueOf(user.getId_user());
        tfnumero_modif.setText(id);
        String numero =String.valueOf(user.getNumero());
        tfnumero_modif.setText(numero);
        tfnom_modif.setText(user.getNom());
        tfadress_modif.setText(user.getAdresse());
        tfemail_modif.setText(user.getEmail());
        tfprenom_modif.setText(user.getPrenom());
        cmbRole_modif.setValue(user.getRole());
    }
    public void setTextFields(User R){
        String id =String.valueOf(R.getId_user());
        tfid_modif1.setText(id);
        String numero =String.valueOf(R.getNumero());
        tfnumero_modif.setText(numero);
        tfnom_modif.setText(R.getNom());
        tfadress_modif.setText(R.getAdresse());
        tfemail_modif.setText(R.getEmail());
        tfprenom_modif.setText(R.getPrenom());
    }



}
