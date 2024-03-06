package Controller;

import Entity.InscriptionComp;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import Service.InscriptionCompService;

import java.io.File;
import java.util.List;

public class InscriptionUserConroller {

    @FXML
    public ImageView image;
    InscriptionCompService ics = new InscriptionCompService();
    private String selectedImagePath; // Ajouter cette variable globale
    private Image selectedImage;
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
    void initialize() {
        assert nom != null : "fx:id=\"nom_comp\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert prenom != null : "fx:id=\"lieu\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert age != null : "fx:id=\"datePicker\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert poids != null : "fx:id=\"discipline\" was not injected: check your FXML file 'Ajouter.fxml'.";
        assert num_tel != null : "fx:id=\"nom_comp\" was not injected: check your FXML file 'Ajouter.fxml'.";
        loadCompIds();

    }

    public void Back(ActionEvent actionEvent) {

        try {
            Parent root = FXMLLoader.load(getClass().getResource("/InterfaceUserComp.fxml"));
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
        InscriptionComp inscriptionComp = new InscriptionComp(nom.getText(), prenom.getText(), Integer.parseInt(age.getText()), Float.parseFloat(poids.getText()), selectedImagePath, Integer.parseInt(num_tel.getText()), idComp);
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
            //afficherINSCompetitions();

            try {
                Parent root = FXMLLoader.load(getClass().getResource("/InterfaceUserComp.fxml"));
                nom.getScene().setRoot(root);


            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

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

    public void setIdCompLabel(int text) {
        id_comp.setValue(text);
    }

    public void addphoto(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        // Filtres pour n'afficher que les fichiers image
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);

        // Afficher la boîte de dialogue de sélection de fichier
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            // Stocker le chemin d'accès à l'image sélectionnée
            selectedImagePath = selectedFile.getAbsolutePath();

            // Créer une image à partir du fichier sélectionné
            selectedImage = new Image(selectedFile.toURI().toString());

            // Afficher l'image dans l'ImageView
            image.setImage(selectedImage);
        }
    }

}
