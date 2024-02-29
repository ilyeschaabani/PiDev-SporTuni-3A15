package controllers;

import entities.Competition;
import entities.InscriptionComp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.util.converter.FloatStringConverter;
import javafx.util.converter.IntegerStringConverter;
import service.InscriptionCompService;

import javax.print.DocFlavor;
import java.util.List;


public class InscriptionCompController {

    InscriptionCompService ics = new InscriptionCompService();

    @FXML
    private TextField age;
    @FXML
    private TextField poids;
    @FXML
    private TextField num_tel;
    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private ComboBox<Integer> id_comp;
    @FXML
    private Label id_CompErrorLabel;
    @FXML
    private Label nomErrorLabel;
    @FXML
    private Label prenomErrorLabel;
    @FXML
    private Label ageErrorLabel;
    @FXML
    private Label poidsErrorLabel;
    @FXML
    private Label num_telErrorLabel;

    @FXML
    private TreeTableView<InscriptionComp> tvc1;

    @FXML
    private TreeTableColumn<Competition, Integer> ID_inscri;

    @FXML
    private TreeTableColumn<Competition, String> NOM;

    @FXML
    private TreeTableColumn<Competition, String> PRENOM;

    @FXML
    private TreeTableColumn<Competition, Integer> AGE;
    @FXML
    private TreeTableColumn<Competition, Integer> ID_COMP;

    @FXML
    private TreeTableColumn<Competition, Float> POIDS;

    @FXML
    private TreeTableColumn<Competition, Integer> NUM_TEL;
    @FXML
    private TreeTableColumn<Competition, DocFlavor.BYTE_ARRAY> IMAGE;


    @FXML
    void initialize() {
        assert nom != null : "fx:id=\"nom_comp\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert prenom != null : "fx:id=\"lieu\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert age != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert poids != null : "fx:id=\"discipline\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert num_tel != null : "fx:id=\"nom_comp\" was not injected: check your FXML file 'Ajouter.fxml'.";
        loadCompIds();
        afficherINSCompetitions();
        initializetreetableviewbutton();

        // Ajoutez un écouteur d'événements à la sélection de la TreeTableView
        tvc1.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Récupérez l'objet Competition correspondant à la ligne sélectionnée
                InscriptionComp selectedCompetitor = newSelection.getValue();
                // Activez l'édition des cellules pour cet objet
                activation_des_cellules1(selectedCompetitor);
                //update selected object
                //onEditButtonClick(selectedCompetition);
            }
        });

    }


    public void Back(ActionEvent actionEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/AjouterCompetition.fxml"));
            nom.getScene().setRoot(root);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    public void inscription(ActionEvent actionEvent) {

        // Vérifiez si les champs requis sont vides
        if (nom.getText().isEmpty() || prenom.getText().isEmpty() || age.getText().isEmpty() || poids.getText().isEmpty() || num_tel.getText().isEmpty() || id_comp.getValue() == null) {
            setupValidation();
            return;
        }
        int idComp = id_comp.getValue();
        //initialisation des id des salles dispo
        loadCompIds();


        // Créez une nouvelle instance d'inscription de compétiteur avec les données fournies
        InscriptionComp inscriptionComp = new InscriptionComp(nom.getText(), prenom.getText(), Integer.parseInt(age.getText()), Float.parseFloat(poids.getText()), Integer.parseInt(num_tel.getText()), idComp);
        if (setupValidationtype() == 0) {
            // Ajoutez l'inscription de compétiteur à la base de données
            ics.add(inscriptionComp);

            // Affichez une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Inscription de compétiteur ajoutée.");
            alert.showAndWait();
            clearFields(); // Effacez les champs après l'ajout
            // Vous pouvez ajouter d'autres actions ici, par exemple mettre à jour l'affichage des compétiteurs, etc.
            afficherINSCompetitions();

        }
    }

    // Méthode pour effacer les champs de saisie après avoir ajouté une inscription de compétiteur
    public void clearFields() {
        nom.clear();
        prenom.clear();
        age.clear();
        poids.clear();
        num_tel.clear();
        id_comp.setValue(null); // Effacer la sélection de la ComboBox id_comp
    }

    // Méthode pour afficher une alerte d'erreur
    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }


    @FXML
    private void loadCompIds() {
        // Récupérez les ID de salle depuis la base de données
        List<Integer> competitionIds = ics.getAvailableCOMPETITIONIdsFromDatabase();

        // Effacez le ComboBox avant d'ajouter de nouvelles valeurs
        //id_salle.getItems().clear();

        // Ajoutez les ID de salle au ComboBox
        id_comp.getItems().addAll(competitionIds);
    }

    public void setupValidation() {

        if (nom.getText().isEmpty()) {
            nom.getStyleClass().add("empty-field");
            nomErrorLabel.setText("Veuillez donner le nom de la compétition");
            nomErrorLabel.setTextFill(Color.RED);
        } else {
            nom.getStyleClass().remove("empty-field");
            nomErrorLabel.setText("Validé !");
            nomErrorLabel.setTextFill(Color.GREEN);
        }


        if (prenom.getText().isEmpty()) {
            prenom.getStyleClass().add("empty-field");
            prenomErrorLabel.setText("Veuillez donner le prenom");
            prenomErrorLabel.setTextFill(Color.RED);
        } else {
            prenom.getStyleClass().remove("empty-field");
            prenomErrorLabel.setText("Validé !");
            prenomErrorLabel.setTextFill(Color.GREEN);
        }


        if (age.getText().isEmpty()) {
            age.getStyleClass().add("empty-field");
            ageErrorLabel.setText("Veuillez donner l'age");
            ageErrorLabel.setTextFill(Color.RED);
        } else {
            ageErrorLabel.getStyleClass().remove("empty-field");
            ageErrorLabel.setText("Validé !");
            ageErrorLabel.setTextFill(Color.GREEN);
        }


        if (poids.getText().isEmpty()) {
            poids.getStyleClass().add("empty-field");
            poidsErrorLabel.setText("Veuillez donner le poids");
            poidsErrorLabel.setTextFill(Color.RED);
        } else {
            poids.getStyleClass().remove("empty-field");
            poidsErrorLabel.setText("Validé !");
            poidsErrorLabel.setTextFill(Color.GREEN);
        }

        // Validation pour l'ID de la salle
        if (num_tel.getText().isEmpty()) {
            num_tel.getStyleClass().add("empty-field");
            num_telErrorLabel.setText("Veuillez donner numero de telephone");
            num_telErrorLabel.setTextFill(Color.RED);
        } else {
            num_tel.getStyleClass().remove("empty-field");
            num_telErrorLabel.setText("Validé !");
            num_telErrorLabel.setTextFill(Color.GREEN);
        }
    }


    public int setupValidationtype() {
        int error = 0;
        // Validation pour le nom de la compétition
        if (!isValidString(nom.getText())) {
            displayError(nom, nomErrorLabel, "Le nom  doit être une chaîne de caractères");
            error++;
        } else {
            displaySuccess(nom, nomErrorLabel);

        }

        // Validation pour le lieu de la compétition
        if (!isValidString(prenom.getText())) {
            displayError(prenom, prenomErrorLabel, "Le prenom doit être une chaîne de caractères");
            error++;
        } else {
            displaySuccess(prenom, prenomErrorLabel);
        }

        // Validation pour l'âge

        int ageValue = Integer.parseInt(age.getText());
        if (!isValidInt(ageValue)) {
            displayError(age, ageErrorLabel, "L'âge doit être un entier positif");
            error++;
        } else {
            displaySuccess(age, ageErrorLabel);
        }


// Validation pour le poids

        float poidsValue = Float.parseFloat(poids.getText());
        if (!isValidFloat(poidsValue)) {
            displayError(poids, poidsErrorLabel, "Le poids doit être un nombre décimal positif");
            error++;
        } else {
            displaySuccess(poids, poidsErrorLabel);
        }


// Validation pour le numéro de téléphone

        int numTelValue = Integer.parseInt(num_tel.getText());
        if (!isValidInt(numTelValue)) {
            displayError(num_tel, num_telErrorLabel, "Le numéro de téléphone doit être un entier positif");
            error++;
        } else {
            displaySuccess(num_tel, num_telErrorLabel);
        }


        return error;
    }

    private boolean isValidFloat(float poidsValue) {
        // Vérifie si le poids est positif
        return poidsValue >= 0;
    }

    private boolean isValidString(String input) {
        // Vérifiez si la chaîne n'est pas vide et ne contient que des lettres ou des espaces
        return input.matches("[a-zA-Z\\s]+");
    }

    // Méthode pour vérifier si un entier est valide
    private boolean isValidInt(Integer value) {
        // Vérifiez si la valeur est positive
        return value > 0;
    }

    // Méthode pour afficher un message d'erreur
    private void displayError(javafx.scene.control.Control control, javafx.scene.control.Label errorLabel, String errorMessage) {
        control.getStyleClass().add("error-field");
        errorLabel.setText(errorMessage);
        errorLabel.setTextFill(Color.RED);
    }

    // Méthode pour afficher un message de succès
    private void displaySuccess(javafx.scene.control.Control control, javafx.scene.control.Label errorLabel) {
        control.getStyleClass().remove("error-field");
        errorLabel.setText("Validé !");
        errorLabel.setTextFill(Color.GREEN);
    }


    public void afficherINSCompetitions() {
        try {
            ObservableList<InscriptionComp> observableList = FXCollections.observableList(ics.readAll());

            TreeItem<InscriptionComp> root = new TreeItem<>(null);
            root.setExpanded(true);

            for (InscriptionComp i : observableList) {
                TreeItem<InscriptionComp> item = new TreeItem<>(i);
                root.getChildren().add(item);
            }

            tvc1.setRoot(root);
            tvc1.setShowRoot(false);

            ID_inscri.setCellValueFactory(new TreeItemPropertyValueFactory<>("num_inscri"));
            NOM.setCellValueFactory(new TreeItemPropertyValueFactory<>("nom"));
            PRENOM.setCellValueFactory(new TreeItemPropertyValueFactory<>("prenom"));
            AGE.setCellValueFactory(new TreeItemPropertyValueFactory<>("age"));
            POIDS.setCellValueFactory(new TreeItemPropertyValueFactory<>("poids"));
            IMAGE.setCellValueFactory(new TreeItemPropertyValueFactory<>("image"));
            NUM_TEL.setCellValueFactory(new TreeItemPropertyValueFactory<>("num_tel"));
            ID_COMP.setCellValueFactory(new TreeItemPropertyValueFactory<>("id_comp"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public Void onDeleteButtonClick(InscriptionComp item) {
        ics.delete(item.getNum_inscri());
        System.out.println("Supprimer: " + item);
        afficherINSCompetitions();
        return null;
    }

    private void initializetreetableviewbutton() {

        TreeTableColumn<InscriptionComp, Void> actionColumn = new TreeTableColumn<>("Actions");
        actionColumn.setCellFactory(new ButtonCellFactoryINC(tvc1, this::onDeleteButtonClick, this::onEditButtonClick));

// Ajouter la colonne d'actions à votre TreeTableView
        tvc1.getColumns().add(actionColumn);


    }


    public void activation_des_cellules1(InscriptionComp item) {
        // Activer l'édition des cellules dans chaque colonne

        NOM.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        PRENOM.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        AGE.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new IntegerStringConverter()));
        NUM_TEL.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new IntegerStringConverter()));
        ID_COMP.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new IntegerStringConverter()));
        POIDS.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new FloatStringConverter()));

        // Ajouter des écouteurs pour détecter les modifications

        NOM.setOnEditCommit(event -> {
            item.setNom(event.getNewValue());
        });

        PRENOM.setOnEditCommit(event -> {
            item.setPrenom(event.getNewValue());
        });

        AGE.setOnEditCommit(event -> {
            item.setAge(event.getNewValue());
        });

        POIDS.setOnEditCommit(event -> {
            item.setPoids(event.getNewValue());
        });
        NUM_TEL.setOnEditCommit(event -> {
            item.setNum_tel(event.getNewValue());
        });


    }


    private Void onEditButtonClick(InscriptionComp inscriptionComp) {
        TreeItem<InscriptionComp> selectedTreeItem = tvc1.getSelectionModel().getSelectedItem();
        if (selectedTreeItem != null) {
            InscriptionComp selectedcompetitor = selectedTreeItem.getValue();
            try {
                // Mettre à jour l'objet Competition dans la base de données
                ics.update(selectedcompetitor);
                System.out.println("Modifier: " + selectedcompetitor);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Aucune ligne sélectionnée, afficher un message d'erreur ou prendre d'autres mesures
            System.err.println("Aucune ligne sélectionnée.");
        }
        return null;
    }


}
