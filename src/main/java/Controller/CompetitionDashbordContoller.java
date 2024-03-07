package Controller;

import Entity.Competition;
import Service.CompetitionService ;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTreeTableCell;
import javafx.scene.control.cell.TreeItemPropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;



public class CompetitionDashbordContoller {
    Service.CompetitionService cs = new Service.CompetitionService();
    @FXML
    private WebView MapView;
    @FXML
    private ComboBox<Integer> id_comp_place;

    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private DatePicker date;
    @FXML
    private TextField nom_comp;
    @FXML
    private TextField lieu;

    @FXML
    private TextField discipline;
    @FXML
    private Label datteee;
    @FXML
    private ComboBox<Integer> id_salle;
    @FXML
    private Label nomCompErrorLabel;
    @FXML
    private Label lieuErrorLabel;
    @FXML
    private Label disciplineErrorLabel;
    @FXML
    private Label dateErrorLabel;
    @FXML
    private Label idSalleErrorLabel;

    @FXML
    private Label label1;
    @FXML
    private Label label2;
    @FXML
    private Label label3;

    @FXML
    private TreeTableView<Competition> tvc;

    @FXML
    private TreeTableColumn<Competition, Integer> id_comp;

    @FXML
    private TreeTableColumn<Competition, String> NomCompetition;

    @FXML
    private TreeTableColumn<Competition, String> Lieu;

    @FXML
    private TreeTableColumn<Competition, java.sql.Date> Datetvc;

    @FXML
    private TreeTableColumn<Competition, String> Discipline;

    @FXML
    private TreeTableColumn<Competition, Integer> IDsalle;

    @FXML
    void AddComp(ActionEvent event) {
        // Vérifiez si les champs requis sont vides
        if (nom_comp.getText().isEmpty() || lieu.getText().isEmpty() || discipline.getText().isEmpty() || id_salle.getValue() == null || date.getValue() == null) {
            setupValidation();
            return;
        }

        int idSalle = id_salle.getValue();
        //initialisation des id des salles dispo
        loadSalleIds();

        // Date
        Date currentDate = Date.valueOf(date.getValue());

        // Créez une nouvelle instance de la compétition avec les données fournies
        Competition competition = new Competition(nom_comp.getText(), lieu.getText(), currentDate, discipline.getText(), idSalle);
        if (setupValidationtype() == 0) {
            // Ajoutez la compétition à la base de données
            cs.add(competition);

            // Affichez une alerte de succès
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Compétition ajoutée.");
            alert.showAndWait();
            clearFields();
            afficherCompetitions();

        }
    }

    // Méthode pour effacer les champs de saisie après avoir ajouté une compétition
    public void clearFields() {
        nom_comp.clear();
        lieu.clear();
        discipline.clear();
        id_salle.setValue(null); // Effacer la sélection de la ComboBox id_salle
        date.setValue(null);
    }

    private void showErrorAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    void initialize() {
        assert nom_comp != null : "fx:id=\"nom_comp\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert lieu != null : "fx:id=\"lieu\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert date != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert discipline != null : "fx:id=\"discipline\" was not injected: check your FXML file 'Ajouter.fxml'.";
        //initialisation des id des salles dispo
        loadSalleIds();
        loadCompIds();
        // Appeler la méthode pour afficher les compétitions
        afficherCompetitions();
        //preparation des buttons
        initializetreetableviewbutton();
        // Ajoutez un écouteur d'événements à la sélection de la TreeTableView
        tvc.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                // Récupérez l'objet Competition correspondant à la ligne sélectionnée
                Competition selectedCompetition = newSelection.getValue();
                // Activez l'édition des cellules pour cet objet
                activation_des_cellules(selectedCompetition);
                //update selected object
                //onEditButtonClick(selectedCompetition);
            }
        });


    }


    public void afficherCompetitions() {
        try {
            ObservableList<Competition> observableList = FXCollections.observableList(cs.readAll());

            TreeItem<Competition> root = new TreeItem<>(null);
            root.setExpanded(true);

            for (Competition competition : observableList) {
                TreeItem<Competition> item = new TreeItem<>(competition);
                root.getChildren().add(item);
            }

            tvc.setRoot(root);
            tvc.setShowRoot(false);

            id_comp.setCellValueFactory(new TreeItemPropertyValueFactory<>("id_comp"));
            NomCompetition.setCellValueFactory(new TreeItemPropertyValueFactory<>("nom_comp"));
            Lieu.setCellValueFactory(new TreeItemPropertyValueFactory<>("lieu_comp"));
            Datetvc.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
            Discipline.setCellValueFactory(new TreeItemPropertyValueFactory<>("discipline"));
            IDsalle.setCellValueFactory(new TreeItemPropertyValueFactory<>("id_salle"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void selectdate(ActionEvent event) {
        datteee.setText(date.getValue().toString());
    }

    // Méthode pour charger les ID de salle depuis la base de données et les ajouter au ComboBox
    @FXML
    private void loadSalleIds() {
        // Récupérez les ID de salle depuis la base de données
        List<Integer> salleIds = cs.getAvailableSalleIdsFromDatabase();

        // Effacez le ComboBox avant d'ajouter de nouvelles valeurs
        //id_salle.getItems().clear();

        // Ajoutez les ID de salle au ComboBox
        id_salle.getItems().addAll(salleIds);
    }

    public void setupValidation() {
        // Validation pour le nom de la compétition
        if (nom_comp.getText().isEmpty()) {
            nom_comp.getStyleClass().add("empty-field");
            nomCompErrorLabel.setText("Veuillez donner le nom de la compétition");
            nomCompErrorLabel.setTextFill(Color.RED);
        } else {
            nom_comp.getStyleClass().remove("empty-field");
            nomCompErrorLabel.setText("Validé !");
            nomCompErrorLabel.setTextFill(Color.GREEN);
        }

        // Validation pour le lieu de la compétition
        if (lieu.getText().isEmpty()) {
            lieu.getStyleClass().add("empty-field");
            lieuErrorLabel.setText("Veuillez donner le lieu de la compétition");
            lieuErrorLabel.setTextFill(Color.RED);
        } else {
            lieu.getStyleClass().remove("empty-field");
            lieuErrorLabel.setText("Validé !");
            lieuErrorLabel.setTextFill(Color.GREEN);
        }

        // Validation pour la date de la compétition
        if (date.getValue() == null) {
            date.getStyleClass().add("empty-field");
            dateErrorLabel.setText("Veuillez sélectionner une date");
            dateErrorLabel.setTextFill(Color.RED);
        } else {
            date.getStyleClass().remove("empty-field");
            dateErrorLabel.setText("Validé !");
            dateErrorLabel.setTextFill(Color.GREEN);
        }

        // Validation pour la discipline de la compétition
        if (discipline.getText().isEmpty()) {
            discipline.getStyleClass().add("empty-field");
            disciplineErrorLabel.setText("Veuillez donner la discipline de la compétition");
            disciplineErrorLabel.setTextFill(Color.RED);
        } else {
            discipline.getStyleClass().remove("empty-field");
            disciplineErrorLabel.setText("Validé !");
            disciplineErrorLabel.setTextFill(Color.GREEN);
        }

        // Validation pour l'ID de la salle
        if (id_salle.getValue() == null) {
            id_salle.getStyleClass().add("empty-field");
            idSalleErrorLabel.setText("Veuillez sélectionner une salle");
            idSalleErrorLabel.setTextFill(Color.RED);
        } else {
            id_salle.getStyleClass().remove("empty-field");
            idSalleErrorLabel.setText("Validé !");
            idSalleErrorLabel.setTextFill(Color.GREEN);
        }
    }


    public int setupValidationtype() {
        int error = 0;
        // Validation pour le nom de la compétition
        if (!isValidString(nom_comp.getText())) {
            displayError(nom_comp, nomCompErrorLabel, "Le nom de la compétition doit être une chaîne de caractères");
            error++;
        } else {
            displaySuccess(nom_comp, nomCompErrorLabel);

        }

        // Validation pour le lieu de la compétition
        if (!isValidString(lieu.getText())) {
            displayError(lieu, lieuErrorLabel, "Le lieu de la compétition doit être une chaîne de caractères");
            error++;
        } else {
            displaySuccess(lieu, lieuErrorLabel);
        }

        // Validation pour la date de la compétition
        if (!isValidDate(date.getValue())) {
            displayError(date, dateErrorLabel, "Veuillez sélectionner une date valide");
            error++;
        } else {
            displaySuccess(date, dateErrorLabel);
        }

        // Validation pour la discipline de la compétition
        if (!isValidString(discipline.getText())) {
            displayError(discipline, disciplineErrorLabel, "La discipline doit être une chaîne de caractères");
            error++;
        } else {
            displaySuccess(discipline, disciplineErrorLabel);
        }

        return error;
    }

    private boolean isValidString(String input) {
        // Vérifiez si la chaîne n'est pas vide et ne contient que des lettres ou des espaces
        return input.matches("[a-zA-Z\\s]+");
    }

    // Méthode pour vérifier si une date est valide
    private boolean isValidDate(LocalDate date) {
        // Vérifiez si la date n'est pas dans le futur
        return !date.isBefore(LocalDate.now());
    }

    private boolean isValidDate1(LocalDate date) {
        // Vérifiez si la date n'est pas dans le futur
        return !date.isAfter(LocalDate.now());
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


    // Méthode pour gérer le clic sur le bouton Supprimer
    public Void onDeleteButtonClick(Competition item) {
        cs.delete(item.getId_comp());
        System.out.println("Supprimer: " + item);
        afficherCompetitions();
        return null;
    }


    public void activation_des_cellules(Competition item) {
        // Activer l'édition des cellules dans chaque colonne
        NomCompetition.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        Lieu.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        Discipline.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn());
        IDsalle.setCellFactory(TextFieldTreeTableCell.forTreeTableColumn(new IntegerStringConverter()));
// Configuration de la colonne Datetvc pour utiliser TextFieldTreeTableCell
        Datetvc.setCellFactory(column -> {
            TextFieldTreeTableCell<Competition, Date> cell = new TextFieldTreeTableCell<>();
            cell.setConverter(new StringConverter<Date>() {
                @Override
                public String toString(Date object) {
                    return object != null ? object.toString() : "";
                }

                @Override
                public Date fromString(String string) {
                    return string != null && !string.isEmpty() ? Date.valueOf(string) : null;
                }
            });
            return cell;
        });

// Conversion java.sql.Date en String et vice versa
        Datetvc.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));


        // Ajouter des écouteurs pour détecter les modifications


        NomCompetition.setOnEditCommit(event -> {
            item.setNom_comp(event.getNewValue());
        });

        Lieu.setOnEditCommit(event -> {
            item.setLieu_comp(event.getNewValue());
        });

        Datetvc.setOnEditCommit(event -> {
            item.setDate(event.getNewValue());
        });

        Discipline.setOnEditCommit(event -> {
            item.setDiscipline(event.getNewValue());
        });

        IDsalle.setOnEditCommit(event -> {
            item.setId_salle(event.getNewValue());
        });
    }


    // Méthode pour gérer le clic sur le bouton Modifier
    public Void onEditButtonClick(Competition item) {
        TreeItem<Competition> selectedTreeItem = tvc.getSelectionModel().getSelectedItem();
        if (selectedTreeItem != null) {
            Competition selectedCompetition = selectedTreeItem.getValue();
            try {
                // Mettre à jour l'objet Competition dans la base de données
                cs.update(selectedCompetition);
                System.out.println("Modifier: " + selectedCompetition);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Aucune ligne sélectionnée, afficher un message d'erreur ou prendre d'autres mesures
            System.err.println("Aucune ligne sélectionnée.");
        }
        return null;
    }


    // Méthode pour charger les données dans le TableView
    private void initializetreetableviewbutton() {

        TreeTableColumn<Competition, Void> actionColumn = new TreeTableColumn<>("Actions");
        actionColumn.setCellFactory(new Controller.ButtonCellFactory(tvc, this::onDeleteButtonClick, this::onEditButtonClick));

// Ajouter la colonne d'actions à votre TreeTableView
        tvc.getColumns().add(actionColumn);


    }


    public void AddCompetitors(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/InscriptionDashbord.fxml"));
            nom_comp.getScene().setRoot(root);


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

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



    public void sort(ActionEvent actionEvent) {

        try {
            ObservableList<Competition> observableList = FXCollections.observableList(cs.readAllSortedByDate());

            TreeItem<Competition> root = new TreeItem<>(null);
            root.setExpanded(true);

            for (Competition competition : observableList) {
                TreeItem<Competition> item = new TreeItem<>(competition);
                root.getChildren().add(item);
            }

            tvc.setRoot(root);
            tvc.setShowRoot(false);

            id_comp.setCellValueFactory(new TreeItemPropertyValueFactory<>("id_comp"));
            NomCompetition.setCellValueFactory(new TreeItemPropertyValueFactory<>("nom_comp"));
            Lieu.setCellValueFactory(new TreeItemPropertyValueFactory<>("lieu_comp"));
            Datetvc.setCellValueFactory(new TreeItemPropertyValueFactory<>("date"));
            Discipline.setCellValueFactory(new TreeItemPropertyValueFactory<>("discipline"));
            IDsalle.setCellValueFactory(new TreeItemPropertyValueFactory<>("id_salle"));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void pdf(ActionEvent actionEvent) throws IOException {
        // Récupérer les compétitions triées par date
        List<Competition> competitions = cs.readAllSortedByDate();

        // Vérifier si la liste de compétitions est nulle ou vide
        if (competitions == null || competitions.isEmpty()) {
            System.out.println("La liste des compétitions est vide ou nulle.");
            return; // Sortir de la méthode si la liste est invalide
        }

        // Générer le PDF
        PDFGenerator1 pdfGenerator = new PDFGenerator1();
        String pdfFilePath = "competitions.pdf";
        pdfGenerator.generatePDF(pdfFilePath, competitions);

        // Ouvrir le PDF généré
        try {
            Desktop.getDesktop().open(new File(pdfFilePath));
        } catch (IOException e) {
            System.out.println("Erreur lors de l'ouverture du fichier PDF: " + e.getMessage());
        }
    }

    public List<Competition> getThreeClosestCompetitions(List<Competition> competitions) {
        LocalDate currentDate = LocalDate.now();
        List<Competition> closestCompetitions = competitions.stream()
                .filter(competition -> {
                    LocalDate competitionDate = toLocalDate(competition.getDate());
                    return competitionDate.isAfter(currentDate) && competitionDate.isBefore(currentDate.plusDays(30));
                })
                .sorted(Comparator.comparing(Competition::getDate))
                .limit(3)
                .collect(Collectors.toList());

        return closestCompetitions;
    }

    public LocalDate toLocalDate(java.sql.Date date) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(date.getTime()), ZoneId.systemDefault()).toLocalDate();
    }

    public void displayCompetitions(List<Competition> competitions, int numDays, Label label1, Label label2, Label label3) {
        List<Competition> closestCompetitions = getThreeClosestCompetitions(competitions);
        System.out.println(closestCompetitions.size());
        if (closestCompetitions.isEmpty()) {
            label1.setText("Aucune compétition dans ce délai.");
            label2.setText("");
            label3.setText("");
        } else if (closestCompetitions.size() == 1) {
            Competition competition = closestCompetitions.get(0);
            label1.setText("--->" + competition.getNom_comp() + " - Début dans " + ChronoUnit.DAYS.between(LocalDate.now(), toLocalDate(competition.getDate())) + " jours dans la salle :" + competition.getId_salle());
            label2.setText("");
            label3.setText("");
        } else if (closestCompetitions.size() == 2) {
            Competition competition1 = closestCompetitions.get(0);
            Competition competition2 = closestCompetitions.get(1);
            label1.setText("--->" + competition1.getNom_comp() + " - Début dans " + ChronoUnit.DAYS.between(LocalDate.now(), toLocalDate(competition1.getDate())) + " jours dans la salle :" + competition1.getId_salle());
            label2.setText("--->" + competition2.getNom_comp() + " - Début dans " + ChronoUnit.DAYS.between(LocalDate.now(), toLocalDate(competition2.getDate())) + " jours dans la salle :" + competition2.getId_salle());
            label3.setText("");
        } else {
            Competition competition1 = closestCompetitions.get(0);
            Competition competition2 = closestCompetitions.get(1);
            Competition competition3 = closestCompetitions.get(2);
            label1.setText("--->" + competition1.getNom_comp() + " - Début dans " + ChronoUnit.DAYS.between(LocalDate.now(), toLocalDate(competition1.getDate())) + " jours dans la salle :" + competition1.getId_salle());
            label2.setText("--->" + competition2.getNom_comp() + " - Début dans " + ChronoUnit.DAYS.between(LocalDate.now(), toLocalDate(competition2.getDate())) + " jours dans la salle :" + competition2.getId_salle());
            label3.setText("--->" + competition3.getNom_comp() + " - Début dans " + ChronoUnit.DAYS.between(LocalDate.now(), toLocalDate(competition3.getDate())) + " jours dans la salle :" + competition3.getId_salle());
        }
    }

    public void cmp(ActionEvent actionEvent) {
        int numDays = 30; // Nombre de jours
        List<Competition> competitions = cs.readAllSortedByDate();
        List<Competition> closestCompetitions = getThreeClosestCompetitions(competitions);
        System.out.println(closestCompetitions.size());
        displayCompetitions(closestCompetitions, numDays, label1, label2, label3);
    }
    @FXML
    void Back (ActionEvent event) {
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


    public void map(ActionEvent actionEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Map.fxml"));
            nom_comp.getScene().setRoot(root);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    //--------------------Mappp------------------------------------

    @FXML
    private void loadCompIds() {
        // Récupérez les ID de salle depuis la base de données
        List<Integer> competitionIds = cs.getAvailableCOMPETITIONIdsFromDatabase();
        // Ajoutez les ID de salle au ComboBox
        id_comp_place.getItems().addAll(competitionIds);
    }

    @FXML
    public void Map(ActionEvent actionEvent) {
        int id = id_comp_place.getValue();
        Competition comp = cs.readById(id);
        openGoogleMaps(comp.getLieu_comp());
    }

    private void openGoogleMaps(String address) {
        if (!address.isEmpty()) {
            try {
                String addressEncoded = java.net.URLEncoder.encode(address, StandardCharsets.UTF_8);
                String url = "https://www.google.com/maps/search/?api=1&query=" + addressEncoded;
                Desktop.getDesktop().browse(new URI(url));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("L'adresse de la compétition est vide.");
        }
    }
}
