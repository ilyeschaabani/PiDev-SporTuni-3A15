package Controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import Entity.Dispo;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Service.DispoService;
import Utiils.DataSource;
import Entity.Salle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import Service.SalleService;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import javafx.scene.chart.PieChart;
import javafx.stage.Stage;

public class SalleDashbordController {
    SalleService ss=new Service.SalleService();
    DispoService ds=new DispoService();

    @FXML
    private Button btnadd1;

    @FXML
    private TextField tfcapa1;

    @FXML
    private TextField tfdisc1;
    @FXML
    private Button ref;

    @FXML
    private TextField tfdispo1;

    @FXML
    private TextField tfnom1;

    @FXML
    private TextField tfsurf1;
    @FXML
    private TableView<Salle> tvsalle;

    @FXML
    private TableColumn<Salle, Integer> clcapa;

    @FXML
    private TableColumn<Salle, String> cldisc;

    @FXML
    private TableColumn<?, ?> cldated;

    @FXML
    private TableColumn<?, ?> cldatef;

    @FXML
    private TableColumn<Salle, String> clnom;

    @FXML
    private TableColumn<Salle, Integer> clsurf;
    @FXML
    private TableColumn<?, ?> clrating;
    @FXML
    private PieChart pi_chart;

    @FXML
    private TextField searchField;
    @FXML
    private Label lbldispo;

    @FXML
    private Label lbltotale;

    private Salle salle1;
    @FXML
    void initialize() {
        try {
//            LocalDateTime currentDateTime = LocalDateTime.now();
//
//            List<Salle> salleList = ss.readAllWithJoin().stream().filter(s->s.getDateF()!=null && s.getDateD()!=null).filter(s->s.getDateF().isBefore(currentDateTime)).toList();
//            List<Salle> salleList2 = new java.util.ArrayList<>(ss.readAllWithJoin().stream().filter(s -> s.getDateF() == null && s.getDateD() == null).toList());
//            salleList2.addAll(salleList);
            int totalSalles = ss.NbrDeSalleTotale();

            // Convert List<Salle> to ObservableList<Salle>
            ObservableList<Salle> observableList = FXCollections.observableList(ss.readAllWithJoin());

            // Set cell value factories for each column
            clnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            clsurf.setCellValueFactory(new PropertyValueFactory<>("surface"));
            clcapa.setCellValueFactory(new PropertyValueFactory<>("capacite"));
            cldisc.setCellValueFactory(new PropertyValueFactory<>("discipline"));
            cldated.setCellValueFactory(new PropertyValueFactory<>("dateD"));
            cldatef.setCellValueFactory(new PropertyValueFactory<>("dateF"));
            clrating.setCellValueFactory(new PropertyValueFactory<>("rate"));

            ds.deleteExpiredDispos();
            // Set the items in the TableView
            tvsalle.setItems(observableList);

            //stat
            LocalDateTime currentDateTime = LocalDateTime.now();

            List<Salle> salleList = ss.readAllWithJoin().stream().filter(s->s.getDateF()!=null && s.getDateD()!=null).filter(s->s.getDateF().isBefore(currentDateTime)).toList();
            List<Salle> salleList2 = new java.util.ArrayList<>(ss.readAllWithJoin().stream().filter(s -> s.getDateF() == null && s.getDateD() == null).toList());
            salleList2.addAll(salleList);
            List<String> salleList3=salleList2.stream().map(s->s.getNom()).distinct().toList();
            lbltotale.setText(String.valueOf(totalSalles));
            lbldispo.setText(String.valueOf(salleList3.size()));

            statPi();


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void mouseClicked1(MouseEvent mouseEvent) {
        try{
            Salle s=tvsalle.getSelectionModel().getSelectedItem();
            System.out.println(s);
            if(s !=null) {
                s = new Salle(s.getId(), s.getNom(), s.getSurface(), s.getCapacite(), s.getDiscipline());
                tfnom1.setText(s.getNom());
                tfsurf1.setText(String.valueOf(s.getSurface()));
                tfcapa1.setText(String.valueOf(s.getCapacite()));
                tfdisc1.setText(String.valueOf(s.getDiscipline()));
               salle1 = s;

            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    private boolean validateInput() {
        validateAndShowError(tfnom1, "Nom");
        validateAndShowError(tfsurf1, "Surface");
        validateAndShowError(tfcapa1, "Capacite");
        validateAndShowNumericError(tfsurf1, "Surface");
        validateAndShowNumericError(tfcapa1, "Capacite");
        validateAndShowDisciplineError(tfdisc1, "Discipline");


        return !tfnom1.getText().isEmpty()
                && !tfsurf1.getText().isEmpty()
                && !tfcapa1.getText().isEmpty()
                && isNumeric(tfsurf1.getText())
                && isNumeric(tfcapa1.getText())
                && isValidDiscipline(tfdisc1.getText());
    }
    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    private boolean isValidDiscipline(String discipline) {
        return discipline.matches("[a-zA-Z]+");//vérification si la chaîne ne contient que des caractères alphabétiques
    }
    private void validateAndShowError(TextInputControl inputControl, String fieldName) {
        if (inputControl.getText().isEmpty()) {
            showAlert("Error", fieldName + " is required", Alert.AlertType.ERROR);
        }
    }

    private void validateAndShowNumericError(TextField textField, String fieldName) {
        if (!isNumeric(textField.getText())) {
            showAlert("Error", fieldName + " must be a number", Alert.AlertType.ERROR);
        }
    }

    private void validateAndShowDisciplineError(TextField textField, String fieldName) {
        if (!isValidDiscipline(textField.getText())) {
            showAlert("Error", "Invalid " + fieldName, Alert.AlertType.ERROR);
        }
    }
    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
    private boolean showDeleteConfirmationDialog() {
        Alert confirmationDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationDialog.setTitle("Confirmation");
        confirmationDialog.setHeaderText(null);
        confirmationDialog.setContentText("Are you sure you want to delete this item?");

        Optional<ButtonType> result = confirmationDialog.showAndWait();

        return result.isPresent() && result.get() == ButtonType.OK;
    }
    private void showNotification(String title, String message) {
        if (SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();
                Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\ilyes\\d-lab\\PiDev-SporTuni-3A15\\src\\main\\resources\\notif.png");

                TrayIcon trayIcon = new TrayIcon(icon, "Notification");
                trayIcon.setImageAutoSize(true);

                trayIcon.addActionListener(e -> {
                    // Handle the tray icon click event if needed
                });

                tray.add(trayIcon);
                trayIcon.displayMessage(title,message,TrayIcon.MessageType.INFO);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }


    @FXML
    void add(ActionEvent event) {
        try {
            if (validateInput()) {
                ss.add(new Salle(tfnom1.getText(),Integer.parseInt(tfsurf1.getText()), Integer.parseInt(tfcapa1.getText()), tfdisc1.getText()));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                initialize();
                showAlert("Information", "Salle ajoutée", Alert.AlertType.INFORMATION);
            } else {
//                showAlert("Error", "Veuillez remplir tous les champs correctement.", Alert.AlertType.ERROR);
                showNotification("Error", "Veuillez remplir tous les champs correctement.");

            }
        } catch(Exception e){
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);}

    }
    public void delete(ActionEvent actionEvent) {
        try {
            SalleService ss = new SalleService();
            if (salle1!= null) {
                // Show confirmation dialog
                if (showDeleteConfirmationDialog()) {
                    ss.delete(salle1.getId());
                    DispoService ds=new DispoService();
                    initialize();
                    showAlert("Information", "Salle supprimée", Alert.AlertType.INFORMATION);
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);;
        }
    }
    public void update(ActionEvent actionEvent) {
        try {
            if(validateInput()) {
                SalleService ss = new SalleService();
                System.out.println(salle1);
                salle1 = new Salle(salle1.getId(), tfnom1.getText(), Integer.parseInt(tfsurf1.getText()), Integer.parseInt(tfcapa1.getText()), tfdisc1.getText());
                ss.updato(salle1);
                initialize();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                showAlert("Information", "Salle modifiée", Alert.AlertType.INFORMATION);
            }else {
//                showAlert("Error", "Veuillez remplir tous les champs correctement.", Alert.AlertType.ERROR);
                showNotification("Error", "Veuillez remplir tous les champs correctement.");
            }
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
    @FXML
    void recherche(ActionEvent actionEvent) {
        try {
            ObservableList<Salle> observableList = FXCollections.observableList(ss.readAllWithJoin());
            FilteredList<Salle> filteredList = new FilteredList<>(observableList, p -> true);

            // Bind the search field text to the filter predicate
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(salle -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Show all items when the filter is empty
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    // Check if any property of the Salle contains the filter text
                    return salle.getNom().toLowerCase().contains(lowerCaseFilter)
                            || String.valueOf(salle.getSurface()).contains(lowerCaseFilter)
                            || String.valueOf(salle.getCapacite()).contains(lowerCaseFilter)
                            || salle.getDiscipline().toLowerCase().contains(lowerCaseFilter)
                            || (salle.getDateD() != null && salle.getDateD().toString().toLowerCase().contains(lowerCaseFilter))
                            || (salle.getDateF() != null && salle.getDateF().toString().toLowerCase().contains(lowerCaseFilter));
                });
            });

            SortedList<Salle> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tvsalle.comparatorProperty());

            tvsalle.setItems(sortedList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
//    @FXML
//    void refresh(ActionEvent event) {
//        initialize();
//
//    }
    public void statPi(){
        ObservableList<Salle> observableList = FXCollections.observableList(ss.readAll());

        List<String> disciplines = observableList.stream()
                .map(Salle::getDiscipline)
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
        pi_chart.setData(pieChartData);
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


}