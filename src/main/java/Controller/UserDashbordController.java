package Controller;

import Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Service.UserService;
import utils.DataSource;

public class UserDashbordController {
    UserService us = new UserService();

    @FXML
    private ComboBox<String> cmbRole_ajout;

    @FXML
    private TextField tfadress_ajout;

    @FXML
    private TextField tfemail_ajout;

    @FXML
    private TextField tfnom_ajout;

    @FXML
    private TextField tfnumero_ajout;

    @FXML
    private TextField tfprenom_ajout;

    @FXML
    private TextField tfpwd_ajout;
    @FXML
    private TableView<User> tableviewUser;
    @FXML
    private TableColumn<User, String> col_iadress;

    @FXML
    private TableColumn<User, String> col_iemail;

    @FXML
    private TableColumn<User, String> col_inom;

    @FXML
    private TableColumn<User, Integer> col_inumero;

    @FXML
    private TableColumn<User, String> col_iprenom;

    @FXML
    private TableColumn<User, String> col_irole;
    @FXML
    private TextField recherche_user;

    private Connection cnx;
    User user = null ;


    @FXML
    void add(ActionEvent event) {
        try {
            validateFields();
            User u = new User(tfnom_ajout.getText(), tfprenom_ajout.getText(), tfemail_ajout.getText(), tfpwd_ajout.getText(), getSelectedValue(), Integer.parseInt(tfnumero_ajout.getText()), tfadress_ajout.getText());
            us.add(u);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("User added successfully");
            alert.showAndWait();
            refresh();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("Error adding user");
            alert.showAndWait();
        }


    }
    @FXML
    void Supprimer(ActionEvent event) {
        User user = (User) tableviewUser.getSelectionModel().getSelectedItem();
        us.delete(user.getId_user());
        refresh();
    }
    @FXML
    public void initialize() {
        cmbRole_ajout.getItems().addAll("Coach", "adherant","eli tohkem");
        cmbRole_ajout.setValue("Role");
        showRec();
        searchRec();
    }


    public String getSelectedValue() {
        return cmbRole_ajout.getValue();
    }
    public ObservableList<User> getUserList() {
        cnx = DataSource.getInstance().getCnx();

        ObservableList<User> UserList = FXCollections.observableArrayList();
        try {
            String query2="SELECT * FROM  user ";
            PreparedStatement smt = cnx.prepareStatement(query2);
            User user;
            ResultSet rs= smt.executeQuery();
            while(rs.next()){
                user=new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(8),
                        rs.getString(7)
                );

                UserList.add(user);
            }
            System.out.println(UserList);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

        return UserList;

    }
    public void showRec(){

        ObservableList<User> list = getUserList();

        col_inom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_iprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_iemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_irole.setCellValueFactory(new PropertyValueFactory<>("role"));
        col_inumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        col_iadress.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        tableviewUser.setItems(list);

    }
    private void refresh(){
        ObservableList<User> list = getUserList();

        col_inom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_iprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_iemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_irole.setCellValueFactory(new PropertyValueFactory<>("role"));
        col_inumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        col_iadress.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        tableviewUser.setItems(list);

    }


    @FXML
    void modifer(ActionEvent event) {
        user = tableviewUser.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader ();
        loader.setLocation(getClass().getResource("/ModifierPage.fxml"));
        try {
            loader.load();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }

        ModifierPageController muc = loader.getController();
        muc.setTextFields(user);
        Parent parent = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(parent));
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        showRec();

    }
    private boolean validateFields() {
        if (tfadress_ajout.getText().isEmpty()) {
            showAlert("Error", "Description field is required.");
            return false;
        }
        if (tfpwd_ajout.getText().isEmpty()) {
            showAlert("Error", "Titre field is required.");
            return false;
        }
        if (tfnumero_ajout.getText().isEmpty()) {
            showAlert("Error", "Numero field is required.");
            return false;
        } else if (!tfnumero_ajout.getText().matches("\\d+(\\.\\d+)?")) {
            showAlert("Error", "Numero must be a number.");
            return false;
        }

        return true;
    }
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

        private void searchRec() {
        col_inom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_iprenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_iemail.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_irole.setCellValueFactory(new PropertyValueFactory<>("role"));
        col_inumero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        col_iadress.setCellValueFactory(new PropertyValueFactory<>("adresse"));
        ObservableList<User> list = getUserList();
        tableviewUser.setItems(list);
        FilteredList<User> filteredData = new FilteredList<>(list, b->true);
            recherche_user.textProperty().addListener((observable,oldValue,newValue)-> {
            filteredData.setPredicate(rec-> {
                if (newValue == null || newValue.isEmpty()){
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (rec.getEmail().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                    return true;
                }else if (rec.getNom().toLowerCase().indexOf(lowerCaseFilter)!= -1){
                    return true;
                }
                else
                    return false ;

            });
        });
        SortedList<User> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(tableviewUser.comparatorProperty());
        tableviewUser.setItems(sortedData);
    }
}