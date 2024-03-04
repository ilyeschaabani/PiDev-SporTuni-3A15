package controllers;

import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class AvisSondageController {

    @FXML
    private RadioButton satisfaitButton;

    @FXML
    private RadioButton decuButton;

    @FXML
    private ToggleGroup reponseGroup;

    @FXML
    private TextField nomField;

    public void submitSurvey() {
        RadioButton selectedRadioButton = (RadioButton) reponseGroup.getSelectedToggle();
        if (selectedRadioButton != null) {
            String reponse = selectedRadioButton.getText();
            String nom = nomField.getText();
            System.out.println("Réponse sélectionnée : " + reponse);
            System.out.println("Nom : " + nom);
        } else {
            System.out.println("Aucune réponse sélectionnée");
        }
    }
}
