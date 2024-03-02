package controller;
import entity.Cour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.CourService;
import util.DataSource;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConsulterCour {
    private CourService cs = new CourService();
    private ObservableList<Cour> courList = FXCollections.observableArrayList();
    private Cour selectedCour;
    private ObservableList<String> notificationsList = FXCollections.observableArrayList();
    private ObservableList<VBox> courBoxes = FXCollections.observableArrayList();

    @FXML
    private TableView<Cour> tvCour;

    @FXML
    private TableColumn<Cour, String> clNom;

    @FXML
    private TableColumn<Cour, String> clDiscipline;

    @FXML
    private TableColumn<Cour, Date> clDate;

    @FXML
    private TableColumn<Cour, String> clHeureDebut;

    @FXML
    private TableColumn<Cour, String> clHeureFin;

    @FXML
    private TableColumn<Cour, String> clNbParticipants;

    @FXML
    private TableColumn<Cour, String> clNomSalle;

    @FXML
    private TableColumn<Cour, Void> clAction; // Nouvelle colonne pour le bouton S'inscrire

    private List<Cour> coursInscrits = new ArrayList<>();

    private Connection conn;
    @FXML
    void initialize() {
        try {
            courList.addAll(cs.readAll());
            tvCour.setItems(courList);
            clNom.setCellValueFactory(new PropertyValueFactory<>("nom_cour"));
            clDiscipline.setCellValueFactory(new PropertyValueFactory<>("nom_discipline"));
            clDate.setCellValueFactory(new PropertyValueFactory<>("date"));
            clHeureDebut.setCellValueFactory(new PropertyValueFactory<>("heure_debut"));
            clHeureFin.setCellValueFactory(new PropertyValueFactory<>("heure_fin"));
            clNbParticipants.setCellValueFactory(new PropertyValueFactory<>("nb_max"));
            clNomSalle.setCellValueFactory(new PropertyValueFactory<>("nom_salle"));

            // Ajout du bouton "S'inscrire" à la colonne d'action
            clAction.setCellFactory(param -> new TableCell<>() {
                private final Button btn = new Button("S'inscrire");

                {
                    btn.setOnAction(event -> {
                        Cour cour = getTableView().getItems().get(getIndex());
                        inscrireAuCours(cour);
                    });
                }

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        Cour cour = getTableView().getItems().get(getIndex());
                        if (coursInscrits.contains(cour)) {
                            // Si le cours est déjà inscrit, désactiver le bouton
                            btn.setDisable(true);
                        } else {
                            // Sinon, activer le bouton
                            btn.setDisable(false);
                        }
                        setGraphic(btn);
                    }
                }
            });
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }


    @FXML
    void mouseClicked2(MouseEvent mouseEvent) {
        selectedCour = tvCour.getSelectionModel().getSelectedItem();
        if (selectedCour != null) {
            // Handle selection
        }
    }

    // Dans la méthode inscrireAuCours de ConsulterCour.java
    private void inscrireAuCours(Cour cour) {
        // Vérifier si le cours est déjà inscrit
        if (coursInscrits.contains(cour)) {
            showAlert("Erreur", "Vous êtes déjà inscrit à ce cours.", Alert.AlertType.ERROR);
            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Inscription au cours");
        alert.setHeaderText("Voulez-vous vous inscrire à ce cours ?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Ajouter le cours à la liste des cours inscrits
            coursInscrits.add(cour);

            // Ajouter la notification formatée à la liste des notifications
            notificationsList.add(formatCourseDetails(cour));
            showNotifications(); // Assurez-vous d'appeler cette méthode ici

            // Enregistrer la notification dans la base de données
            saveNotification(cour);
        }
    }



    private void saveNotification(Cour cour) {
        // Utiliser la connexion obtenue à partir de DataSource
        Connection conn = DataSource.getCnx();

        // Code to save the notification in the database
        String query = "INSERT INTO Notifications (nom_cour, nom_discipline, date, heure_debut, nom_salle) VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, cour.getNom_cour());
            statement.setString(2, cour.getNom_discipline());
            statement.setDate(3, cour.getDate());
            statement.setString(4, cour.getHeure_debut());
            statement.setString(5, cour.getNom_salle());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private String formatCourseDetails(Cour cour) {
        return String.format("Nom du cours : %s\nDiscipline : %s\nDate : %s\nHeure début : %s\nNom de la salle : %s\n",
                cour.getNom_cour(), cour.getNom_discipline(), cour.getDate(), cour.getHeure_debut(), cour.getNom_salle());
    }


    private void showNotifications() {
        try {
            // Charger l'interface FXML des notifications
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Notification.fxml"));
            Parent root = loader.load();

            // Obtenir le contrôleur des notifications
            Notification controller = loader.getController();

            // Passer les notifications au contrôleur
            controller.updateNotificationsList(notificationsList);

            // Afficher la scène des notifications
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Notifications");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
