package controllers;
import entities.Evenement;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import  services.EvenementService;

import java.sql.SQLException;
import java.util.Objects;


public class AjouterEvenementController {
   private  EvenementService es;
    public AjouterEvenementController(EvenementService es) {
        this.es = es;
    }

    @FXML
    private Button btModif;

    @FXML
    private Button btsupp;

    @FXML
    private Button btvalider;
    @FXML
    private TableColumn<Evenement, String> coldateDebut;

    @FXML
    private TableColumn<Evenement, String> coldateFin;

    @FXML
    private TableColumn<Evenement, String> coldescription;

    @FXML
    private TableColumn<Evenement, Integer> colnbr_max;

    @FXML
    private TableColumn<Evenement, String> colnom_discipline;

    @FXML
    private TableColumn<Evenement, String> colnom_e;

    @FXML
    private TableColumn<?, ?> colnom_salle;
    @FXML
    private TextField dateDebut;

    @FXML
    private TextField dateFin;

    @FXML
    private TextField description;

    @FXML
    private TextField nbr_max;

    @FXML
    private TextField nom_discipline;

    @FXML
    private TextField nom_e;

    @FXML
    private TextField nom_salle;

    @FXML
    private TableView<Evenement> tv_evenements;
    private String evenementModifié;
    private String information;
    private Alert.AlertType alertType;






    public AjouterEvenementController() {
        // Constructeur par défaut
    }
    @FXML
    void initialize() {
       /* try {
            //EvenementService es = new EvenementService();
            System.out.println("EvenementService initialisé !");
              //ObservableList<Evenement> observableList = FXCollections.observableList(es.readAll());
            colnom_e.setCellValueFactory(new PropertyValueFactory<>("nom_e"));
            colnom_salle.setCellValueFactory(new PropertyValueFactory<>("nom_salle"));
            coldateDebut.setCellValueFactory(new PropertyValueFactory<>("dateDebut"));
            colnom_discipline.setCellValueFactory(new PropertyValueFactory<>("nom_discipline"));
            coldescription.setCellValueFactory(new PropertyValueFactory<>("description"));
            colnbr_max.setCellValueFactory(new PropertyValueFactory<>("nbr_max"));
            coldateFin.setCellValueFactory(new PropertyValueFactory<>("dateFin"));
          //  tv_evenements.setItems(observableList);

        } catch (Exception e) {
            throw new RuntimeException(e);
}
}

        */
        AffcherEvenement();
    }

    public void mouseClicked1(MouseEvent mouseEvent) {
        try{

            Evenement evt =tv_evenements.getSelectionModel().getSelectedItem();
            System.out.println(evt);
            if(evt !=null) {

                Evenement evenement = new Evenement (evt.getId_e(), evt.getNom_e(), evt.getNom_salle(), evt.getDateDebut(), evt.getNom_discipline(),evt.getDescription(),evt.getNbr_max(),evt.getDateFin());
                nom_e.setText(evenement.getNom_e());
               nom_salle.setText(String.valueOf(evenement.getNom_salle()));
                dateDebut.setText(String.valueOf(evenement.getDateDebut()));
               nom_discipline.setText(String.valueOf(evenement.getNom_discipline()));
                description.setText(String.valueOf(evenement.getDescription()));
               nbr_max.setText(String.valueOf(evenement.getNbr_max()));
                dateFin.setText(String.valueOf(evenement.getDateFin()));
            }

        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    @FXML
    void AffcherEvenement() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/AfficherEvenement.fxml")));
            nom_e.getScene().setRoot(root);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @FXML
    void AjouterEvenement(ActionEvent event) throws SQLException {
        if (this.es != null) {
            es.add(new Evenement(dateDebut.getText(), dateFin.getText(), description.getText(), Integer.parseInt(nbr_max.getText()), nom_discipline.getText(), nom_e.getText(), nom_salle.getText()
            ));
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Success");
            alert.setContentText("evenement ajoute");
            alert.showAndWait();
        } else {
            // Gérer le cas où this.es est null
            System.err.println("EvenementService non initialisé !");
        }
    }



    @FXML
    void modifierEvenement(ActionEvent event) {
        try {
            Evenement evenement = new Evenement();
            if (evenement != null) {
                EvenementService es = new EvenementService();
                evenement = new Evenement(colnom_e.getText(), colnom_salle.getText(), coldateDebut.getText(), colnom_discipline.getText(), coldescription.getText(), Integer.parseInt(colnbr_max.getText()), coldateFin.getText());

                es.update(evenement);
                initialize();
                showAlert("Information", "evenement modifié", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Veuillez sélectionner un evenement à modifier", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void showAlert(String information, String EvenementModifié, Alert.AlertType alertType) {
        this.information = information;
        this.evenementModifié = EvenementModifié;
        this.alertType = alertType;
    }


    @FXML
    void supprimerEvenement(ActionEvent event) {
        try {
            Evenement evenement = new Evenement();
            EvenementService es = new EvenementService();

            if (evenement != null) {
                es.delete(es.readById((evenement.getId_e())));
                initialize();
                showAlert("Information", "discipline supprimée", Alert.AlertType.INFORMATION);
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
            ;
        }
    }


    @FXML
    void validerReservation() {
    }



    @FXML
    void validerReservation(ActionEvent event) {

    }

}

