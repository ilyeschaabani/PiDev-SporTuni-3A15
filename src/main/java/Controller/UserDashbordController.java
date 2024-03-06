package Controller;

import Entity.Salle;
import Entity.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import Service.UserService;
import utils.DataSource;

public class UserDashbordController {
    UserService us = new UserService();

    @FXML
    public Button btn_ajout;
    @FXML
    public Button btn_modif;
    @FXML
    public Button btn_Supp;

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
    private TextField tfid_modif;

    @FXML
    private TableView<User> tableviewUser;
    @FXML
    private TableColumn<User,Integer> col_iD;
    @FXML
    private TableColumn<User, String> col_adress;

    @FXML
    private TableColumn<User, String> col_email;

    @FXML
    private TableColumn<User, String> col_nom;

    @FXML
    private TableColumn<User, Integer> col_numero;

    @FXML
    private TableColumn<User, String> col_prenom;

    @FXML
    private TableColumn<User, String> col_role;
    @FXML
    private TextField recherche_user;
    @FXML
    private Button btn_Trie;
    @FXML
    private PieChart pi_chart_user;
    @FXML
    private Label lbltotale_adherant;

    @FXML
    private Label lbltotale_coach;

    private Connection cnx;
    User user = null ;


    @FXML
    void add(ActionEvent event) {
        try {
            if (!validateFields()) {
                User u = new User(tfnom_ajout.getText(), tfprenom_ajout.getText(), tfemail_ajout.getText(), tfpwd_ajout.getText(), getSelectedValue(), Integer.parseInt(tfnumero_ajout.getText()), tfadress_ajout.getText());
                us.add(u);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("User added successfully");
                alert.showAndWait();
                refresh();
            }
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
        int totalSalles = us.NbrDeSalleTotale();
        cmbRole_ajout.getItems().addAll("Coach", "adherant");
        cmbRole_ajout.setValue("Role");
        ArrayList UserList2 = new ArrayList<>(us.readAll().stream().filter(s -> s.getRole().equals("Coach")).toList());
        lbltotale_adherant.setText(String.valueOf(totalSalles));
        lbltotale_coach.setText(String.valueOf(UserList2.size()));
        showRec();
        searchRec();
        statPi();
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
                        rs.getString(6),
                        rs.getInt(8),
                        rs.getString(9)
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

        col_iD.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        col_numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        col_adress.setCellValueFactory(new PropertyValueFactory<>("adresse"));

        tableviewUser.setItems(list);
    }
    private void refresh(){
        ObservableList<User> list = getUserList();


        col_iD.setCellValueFactory(new PropertyValueFactory<>("id_user"));
        col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
        col_role.setCellValueFactory(new PropertyValueFactory<>("role"));
        col_numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
        col_adress.setCellValueFactory(new PropertyValueFactory<>("adresse"));
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
    }
    private boolean validateFields() {
        if (tfadress_ajout.getText().isEmpty()) {
            showAlert("Error", "ADDRESS field is required.");
            return false;
        }
        if (tfpwd_ajout.getText().isEmpty()) {
            showAlert("Error", "PASS WORD   field is required.");
            return false;
        }
        if (tfnumero_ajout.getText().isEmpty()) {
            showAlert("Error", "Numero field is required.");
            return false;
        } else if (!tfnumero_ajout.getText().matches("\\d+(\\.\\d+)?")) {
            showAlert("Error", "Numero must be a number.");
            return false;
        }
        if (tfemail_ajout.getText().isEmpty()) {
            showAlert("Error", "Email field is required.");
            return false;
        }else if (!tfemail_ajout.getText().matches("^(.+)@(.+)$")) {
            showAlert("Error", "Email is not valid.");
            return false;
        }
        if (tfprenom_ajout.getText().isEmpty()) {
            showAlert("Error", "Prenom field is required.");
            return false;
        }else if (!tfprenom_ajout.getText().matches("^[a-zA-Z]*$")) {
            showAlert("Error", "Prenom must contain only letters.");
            return false;
        }
        if (tfnom_ajout.getText().isEmpty()) {
            showAlert("Error", "Nom field is required.");
            return false;
        }else if (!tfnom_ajout.getText().matches("^[a-zA-Z]*$")) {
            showAlert("Error", "Nom must contain only letters.");
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

            col_iD.setCellValueFactory(new PropertyValueFactory<>("id_user"));
            col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
            col_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            col_role.setCellValueFactory(new PropertyValueFactory<>("role"));
            col_numero.setCellValueFactory(new PropertyValueFactory<>("numero"));
            col_adress.setCellValueFactory(new PropertyValueFactory<>("adresse"));
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
    @FXML
    void Trie(ActionEvent event) {
        col_nom.setSortType(TableColumn.SortType.ASCENDING);
        tableviewUser.getSortOrder().add(col_nom);
        tableviewUser.sort();

    }
    @FXML
    void Return(ActionEvent event) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/Menu.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    public void statPi(){
        ObservableList<User> observableList = FXCollections.observableList(us.readAll());

        List<String> disciplines = observableList.stream()
                .map(User::getRole)
                .collect(Collectors.toList());

        // Count the occurrences of each discipline
        Map<String, Long> disciplineCounts = disciplines.stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        // Create PieChart Data
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        disciplineCounts.forEach((discipline, count) -> {
            PieChart.Data data = new PieChart.Data(discipline, count);
            pieChartData.add(data);
        });

        // Set PieChart data
     pi_chart_user.setData(pieChartData);
    }


}