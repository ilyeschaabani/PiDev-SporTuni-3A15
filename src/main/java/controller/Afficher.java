package controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;
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
    private TableColumn<Salle, Boolean> cldispo;

    @FXML
    private TableColumn<Salle, String> clnom;

    @FXML
    private TableColumn<Salle, Integer> clsurf;
    @FXML
    private TextField searchField;


    @FXML
    private TextField tfcapa1;

    @FXML
    private TextField tfdisc1;

    @FXML
    private TextField tfdispo1;

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
            cldispo.setCellValueFactory(new PropertyValueFactory<>("dispo"));

        }catch (Exception e){
        throw new RuntimeException(e);
        }

    }

    public void mouseClicked1(MouseEvent mouseEvent) {
        try{
            Salle s=tvsalle.getSelectionModel().getSelectedItem();
            System.out.println(s);
            if(s !=null) {

                salle = new Salle(s.getId(), s.getNom(), s.getSurface(), s.getCapacite(), s.getDiscipline(), s.getDispo());
                tfnom1.setText(salle.getNom());
                tfsurf1.setText(String.valueOf(salle.getSurface()));
                tfcapa1.setText(String.valueOf(salle.getCapacite()));
                tfdisc1.setText(String.valueOf(salle.getDiscipline()));
                tfdispo1.setText(String.valueOf(salle.getDispo()));

            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public void delete(ActionEvent actionEvent) {
        try {
            SalleService ss = new SalleService();
            if (salle != null) {
                ss.delete(salle.getId());
                initialize();
                showAlert("Information", "Salle supprimée", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);;
        }
    }

    public void update(ActionEvent actionEvent) {
        try {

             SalleService ss = new SalleService();
            String dispoText = tfdispo1.getText();
            boolean dispoValue = Boolean.parseBoolean(dispoText);
            salle = new Salle(salle.getId(), tfnom1.getText(), Integer.parseInt(tfsurf1.getText()), Integer.parseInt(tfcapa1.getText()), tfdisc1.getText(), dispoValue);
            ss.updato(salle);
            initialize();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            showAlert("Information", "Salle modifiée", Alert.AlertType.INFORMATION);

        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);;
        }
    }

    public void add(ActionEvent actionEvent) {
        try {
            if (validateInput()) {
            ss.add(new Salle(tfnom1.getText(),Integer.parseInt(tfsurf1.getText()), Integer.parseInt(tfcapa1.getText()), tfdisc1.getText(), Boolean.parseBoolean(tfdispo1.getText())));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            initialize();
            showAlert("Information", "Salle ajoutée", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Veuillez remplir tous les champs correctement.", Alert.AlertType.ERROR);
            }
        } catch(Exception e){
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);}
    }
    private boolean validateInput() {
        return !tfnom1.getText().isEmpty()
                && !tfsurf1.getText().isEmpty()
                && !tfcapa1.getText().isEmpty()
                && isNumeric(tfsurf1.getText()) // Validation numérique pour la surface
                && isNumeric(tfcapa1.getText()) // Validation numérique pour la capacité
                && isValidDiscipline(tfdisc1.getText()) // Validation pour l'attribut discipline
                && !tfdispo1.getText().isEmpty();
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
                            || salle.getDiscipline().toLowerCase().contains(lowerCaseFilter)
                            || String.valueOf(salle.getDispo()).toLowerCase().contains(lowerCaseFilter);
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

    public void home(ActionEvent actionEvent) {
        try {
            Parent root =FXMLLoader.load(getClass().getResource("/Afficher.fxml"));
            tfnom1.getScene().setRoot(root);

        }catch (Exception e)
        {
            System.out.println(e.getMessage());
        }

    }

}
