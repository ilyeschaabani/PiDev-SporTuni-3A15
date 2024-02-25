package controller;

import java.net.URL;
import java.util.ResourceBundle;

import entity.Discipline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import service.DisciplineService;
import javafx.scene.input.MouseEvent;

public class Afficher {
    public Label title;
    DisciplineService ss=new DisciplineService();
    @FXML
    private Button btnadd1;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;



    @FXML
    private Button btnmodif;

    @FXML
    private Button btnsupp;

    @FXML
    private TableColumn<Discipline, String> cldesc;

    @FXML
    private TableColumn<Discipline, String> clnom;

    @FXML
    private TextField searchField;


    @FXML
    private TextField tfdesc1;

    @FXML
    private TextField tfnom1;

    @FXML
    private TableView<Discipline> tvdiscipline;
    private Discipline discipline;


    @FXML
    void initialize() {
        try {
            ObservableList<Discipline> observableList = FXCollections.observableList(ss.readAll());
            tvdiscipline.setItems(observableList);
            clnom.setCellValueFactory(new PropertyValueFactory<>("nom_discipline"));
            cldesc.setCellValueFactory(new PropertyValueFactory<>("description"));

        }catch (Exception e){
        throw new RuntimeException(e);
        }

    }

    public void mouseClicked1(MouseEvent mouseEvent) {
        try{
            Discipline s=tvdiscipline.getSelectionModel().getSelectedItem();
            System.out.println(s);
            if(s !=null) {

                discipline = new Discipline(s.getId_discipline(), s.getNom_discipline(),  s.getDescription());
                tfnom1.setText(discipline.getNom_discipline());
                tfdesc1.setText(String.valueOf(discipline.getDescription()));

            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }


    public void delete(ActionEvent actionEvent) {
        try {
            DisciplineService ss = new DisciplineService();
            if (discipline != null) {
                ss.delete(discipline.getId_discipline());
                initialize();
                showAlert("Information", "discipline supprimée", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);;
        }
    }

    public void update(ActionEvent actionEvent) {
        try {
            if (discipline != null) {
                DisciplineService ss = new DisciplineService();
                discipline = new Discipline(discipline.getId_discipline(), tfnom1.getText(), tfdesc1.getText());
                ss.updato(discipline);
                initialize();
                showAlert("Information", "discipline modifiée", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Veuillez sélectionner une discipline à modifier", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    public void add(ActionEvent actionEvent) {
        try {
            ss.add(new Discipline(tfnom1.getText(), tfdesc1.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            initialize();
            showAlert("Information", "Discipline ajoutée", Alert.AlertType.INFORMATION);
        } catch(Exception e){
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);}
    }

    public void recherche(ActionEvent actionEvent) {
        try {
            // Récupérer toutes les disciplines
            ObservableList<Discipline> observableList = FXCollections.observableList(ss.readAll());

            // Créer un FilteredList lié à la liste observable
            FilteredList<Discipline> filteredList = new FilteredList<>(observableList, p -> true);

            // Ajouter un écouteur sur le champ de recherche
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(discipline -> {
                    if (newValue == null || newValue.isEmpty()) {
                        return true; // Afficher toutes les disciplines quand le champ de recherche est vide
                    }

                    String lowerCaseFilter = newValue.toLowerCase();

                    // Vérifier si le nom de la discipline ou la description contient le texte de recherche
                    return discipline.getNom_discipline().toLowerCase().contains(lowerCaseFilter)
                            || discipline.getDescription().toLowerCase().contains(lowerCaseFilter);
                });
            });

            // Créer une SortedList liée au FilteredList et triée selon le comparateur de la table
            SortedList<Discipline> sortedList = new SortedList<>(filteredList);
            sortedList.comparatorProperty().bind(tvdiscipline.comparatorProperty());

            // Mettre à jour les données de la table avec la liste triée et filtrée
            tvdiscipline.setItems(sortedList);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }





    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
