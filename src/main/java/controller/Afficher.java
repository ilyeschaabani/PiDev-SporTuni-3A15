package controller;

import java.awt.*;
import java.io.InputStream;
import java.net.URL;
import java.sql.PreparedStatement;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.List;

import entity.Dispo;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import util.DataSource;
import entity.Salle;
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
import service.SalleService;
import javafx.scene.input.MouseEvent;

public class Afficher {
    public Label title;
    SalleService ss=new SalleService();
    private List<Dispo> dispoList;
    @FXML
    private Button btnadd1;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Salle, Integer> clcapa;

    @FXML
    private TableColumn<Salle, String> cldisc;



    @FXML
    private TableColumn<Salle, String> clnom;

    @FXML
    private TableColumn<Salle, Integer> clsurf;
    @FXML
    private TableColumn<Salle, String> cldispo;
    @FXML
    private TextField searchField;


    @FXML
    private TextField tfcapa1;

    @FXML
    private TextField tfdisc1;



    @FXML
    private TextField tfnom1;

    @FXML
    private TextField tfsurf1;

    @FXML
    private TableView<Salle> tvsalle;
    private Salle salle;
    @FXML
    private Button btnstat;

    @FXML
    private Button btnmodif;

    @FXML
    private Button btnsupp;


    @FXML
    void initialize() {
        try {
            ObservableList<Salle> observableList = FXCollections.observableList(ss.readAll());
            tvsalle.setItems(observableList);
            clnom.setCellValueFactory(new PropertyValueFactory<>("nom"));
            clsurf.setCellValueFactory(new PropertyValueFactory<>("surface"));
            clcapa.setCellValueFactory(new PropertyValueFactory<>("capacite"));
            cldisc.setCellValueFactory(new PropertyValueFactory<>("discipline"));
            cldispo.setCellValueFactory(cellData -> new SimpleStringProperty(getAvailabilityInterval((Salle)cellData.getValue())));
            cldispo.setResizable(false);
            cldispo.setSortable(false);

        }catch (Exception e){
        throw new RuntimeException(e);
        }

    }

    public void mouseClicked1(MouseEvent mouseEvent) {
        try{
            Salle s=tvsalle.getSelectionModel().getSelectedItem();
            System.out.println(s);
            if(s !=null) {

                salle = new Salle(s.getId(), s.getNom(), s.getSurface(), s.getCapacite(), s.getDiscipline());
                tfnom1.setText(salle.getNom());
                tfsurf1.setText(String.valueOf(salle.getSurface()));
                tfcapa1.setText(String.valueOf(salle.getCapacite()));
                tfdisc1.setText(String.valueOf(salle.getDiscipline()));

            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public void delete(ActionEvent actionEvent) {
        try {
            SalleService ss = new SalleService();
            if (salle != null) {
                // Show confirmation dialog
                if (showDeleteConfirmationDialog()) {
                    ss.delete(salle.getId());
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

             SalleService ss = new SalleService();
            salle = new Salle(salle.getId(), tfnom1.getText(), Integer.parseInt(tfsurf1.getText()), Integer.parseInt(tfcapa1.getText()), tfdisc1.getText());
            ss.updato(salle);
            initialize();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            showAlert("Information", "Salle modifiée", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void add(ActionEvent actionEvent) {
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

    public void recherche(ActionEvent actionEvent) {
        try {
            ObservableList<Salle> observableList = FXCollections.observableList(ss.readAll());
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
                            || salle.getDiscipline().toLowerCase().contains(lowerCaseFilter);
                });
            });

            SortedList<Salle> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tvsalle.comparatorProperty());

            tvsalle.setItems(sortedList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void stat(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Stat.fxml"));
            tfnom1.getScene().setRoot(root);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
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

    public void home(ActionEvent actionEvent) {
        try {
            Parent root =FXMLLoader.load(getClass().getResource("/Afficher.fxml"));
            tfnom1.getScene().setRoot(root);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }
   private void showNotification(String title, String message) {
       if (SystemTray.isSupported()) {
           try {
               SystemTray tray = SystemTray.getSystemTray();
               Image icon = Toolkit.getDefaultToolkit().getImage("C:\\Users\\zarro\\IdeaProjects\\GestionSalles\\src\\main\\resources\\Images\\notif.png");

               TrayIcon trayIcon = new TrayIcon(icon, "Notification");
               trayIcon.setImageAutoSize(true);

               trayIcon.addActionListener(e -> {
                   // Handle the tray icon click event if needed
               });

               tray.add(trayIcon);
               trayIcon.displayMessage(title, message, TrayIcon.MessageType.INFO);
           } catch (AWTException e) {
               e.printStackTrace();
           }
       }
   }
    public String getAvailabilityInterval(Salle salle) {
        if (salle.getDispoList() != null && !salle.getDispoList().isEmpty()) {
            StringBuilder intervalStringBuilder = new StringBuilder();

            for (Dispo dispo : salle.getDispoList()) {
                LocalDateTime dateD = dispo.getDateD();
                LocalDateTime dateF = dispo.getDateF();

                String interval = calculateInterval(dateD, dateF);
                intervalStringBuilder.append(interval).append(", ");
            }

            return intervalStringBuilder.toString().replaceAll(", $", "");
        } else {
            return "No intervals";
        }
    }

    private String calculateInterval(LocalDateTime dateD, LocalDateTime dateF) {
        long hours = java.time.Duration.between(dateD, dateF).toHours();
        return hours + " hours";
    }

    // Placeholder for the getDispoList() method
    private List<Dispo> getDispoList() {
        return null;
    }



}



