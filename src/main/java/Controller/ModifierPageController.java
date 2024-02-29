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
    public void initialize() {
        cmbRole_modif.getItems().addAll("Coach", "adherant","eli tohkem");
        cmbRole_modif.setValue("Role");
    }

    @FXML
    void modifier(ActionEvent event) {
        UserService us= new UserService();
        try {
            User u = new User(tfnom_modif.getText(), tfprenom_modif.getText(), tfemail_modif.getText(), getSelectedValue(), Integer.parseInt(tfnumero_modif.getText()), tfadress_modif.getText());
            us.update(u);
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
    public String getSelectedValue() {
        return cmbRole_modif.getValue();
    }

    public void setText(User user)
    {

        String numero =String.valueOf(user.getNumero());
        tfnumero_modif.setText(numero);
        tfnom_modif.setText(user.getNom());
        tfadress_modif.setText(user.getAdresse());
        tfemail_modif.setText(user.getEmail());
        tfprenom_modif.setText(user.getPrenom());

    }
    public void setTextFields(User R){
        String numero =String.valueOf(R.getNumero());
        tfnumero_modif.setText(numero);
        tfnom_modif.setText(R.getNom());
        tfadress_modif.setText(R.getAdresse());
        tfemail_modif.setText(R.getEmail());
        tfprenom_modif.setText(R.getPrenom());
    }



}
