package Controller;
import Entity.Cour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import Utiils.DataSource;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ListCell;
import javafx.scene.control.Separator;
import javafx.scene.layout.VBox;
import Utiils.DataSource;

import java.net.URL;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;
public class Notification {

    @FXML
    private ListView<String> notificationsListView;

    @FXML
    private Label clDate;

    @FXML
    private Label clDiscipline;

    @FXML
    private Label clHeureDebut;

    @FXML
    private Label clNom;

    @FXML
    private Label clNomSalle;

    @FXML
    private Label courseStatusLabel;

    private Connection conn;

    private ObservableList<String> notificationsList = FXCollections.observableArrayList();

    public void updateNotificationsList(ObservableList<String> notifications) {
        notificationsList.setAll(notifications);
        System.out.println("Notifications mises à jour : " + notifications);
    }

    public void initData(Cour cour) {
        clNom.setText("Nom du cours : " + cour.getNom_cour());
        clDiscipline.setText("Discipline : " + cour.getNom_discipline());
        clDate.setText("Date : " + cour.getDate().toString());
        clHeureDebut.setText("Heure début : " + cour.getHeure_debut());
        clNomSalle.setText("Nom de la salle : " + cour.getNom_salle());

        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        LocalDate courseDate = cour.getDate().toLocalDate();
        LocalTime courseStartTime = LocalTime.parse(cour.getHeure_debut());

        if (currentDate.isAfter(courseDate) || (currentDate.isEqual(courseDate) && currentTime.isAfter(courseStartTime))) {
            courseStatusLabel.setText("État du cours : Terminé");
        } else {
            courseStatusLabel.setText("État du cours : En cours");
        }
    }

    @FXML
    void initialize() {
        loadAndDisplayNotifications();
    }

    private void loadAndDisplayNotifications() {
        try {
            // Connexion à la base de données
            Connection conn = DataSource.getCnx();

            String query = "SELECT * FROM Notifications";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            ObservableList<String> notifications = FXCollections.observableArrayList();

            while (resultSet.next()) {
                // Récupération des données de la base de données
                String courseName = resultSet.getString("nom_cour");
                String discipline = resultSet.getString("nom_discipline");
                Date date = resultSet.getDate("date");
                Time startTime = resultSet.getTime("heure_debut");
                String roomName = resultSet.getString("nom_salle");

                LocalDate courseDate = date.toLocalDate();
                LocalTime courseStartTime = startTime.toLocalTime();

                LocalDate currentDate = LocalDate.now();
                LocalTime currentTime = LocalTime.now();

                String courseStatus;
                if (currentDate.isAfter(courseDate) || (currentDate.isEqual(courseDate) && currentTime.isAfter(courseStartTime))) {
                    courseStatus = "Terminé";
                } else {
                    courseStatus = "En cours";
                }

                // Création de l'élément de la liste avec l'état du cours
                String notificationDetails = String.format(
                        "Nom du cours : %s\nDiscipline : %s\nDate : %s\nHeure début : %s\nNom de la salle : %s\nÉtat du cours : %s\n",
                        courseName, discipline, date.toString(), startTime.toString(), roomName, courseStatus);

                // Ajout de l'élément à la liste
                notifications.add(notificationDetails);
            }

            // Mise à jour de la liste des notifications dans la ListView
            notificationsListView.setCellFactory(lv -> new ListCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);

                        // Changer la couleur en fonction de l'état du cours
                        if (item.contains("En cours")) {
                            setTextFill(javafx.scene.paint.Color.GREEN);
                        } else if (item.contains("Terminé")) {
                            setTextFill(javafx.scene.paint.Color.RED);
                        }

                        setStyle("-fx-border-color: black; -fx-border-width: 0 0 1 0;"); // Ajoute un bord en bas pour simuler un séparateur
                    }
                }
            });

            // Définir les notifications dans la ListView
            notificationsListView.setItems(notifications);
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert("Erreur", "Impossible de charger les notifications.", Alert.AlertType.ERROR);
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String title, String content, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}