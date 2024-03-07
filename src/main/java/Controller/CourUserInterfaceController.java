package Controller;
import Entity.Cour;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import Service.CourService;
import Utiils.DataSource;
import org.controlsfx.control.Notifications;

import javafx.event.ActionEvent;

import java.awt.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourUserInterfaceController {
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
    private Button historique;

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

            // Vérifier si le cours est déjà dans la liste des notifications
            if (!notificationsList.contains(formatCourseDetails(cour))) {
                // Ajouter la notification formatée à la liste des notifications
                notificationsList.add(formatCourseDetails(cour));
                showNotifications(); // Afficher la notification dans l'interface Notification
            }

            // Enregistrer la notification dans la base de données
            saveNotification(cour);

            // Afficher la notification pour l'inscription réussie
            handleButtonAction(null);

            String subject = "Inscription au cours : " + cour.getNom_cour();
            String message = formatCourseDetails(cour); // Utilisez votre méthode existante pour formater les détails du cours
        }
    }

    // Méthode pour afficher une notification lors de l'inscription réussie
    public void handleButtonAction(ActionEvent event) {
        Image img = new javafx.scene.image.Image("C:\\Users\\rawen\\Documents\\GitHub\\PiDev-SporTuni-3A15\\src\\main\\resources\\Images\\small_tick.png");
        ImageView imageView = new ImageView(img);
        imageView.setFitWidth(40); // Spécifiez la largeur souhaitée
        imageView.setFitHeight(40);

        Notifications notificationBuilder = Notifications.create()
                .title("Inscription avec succès")
                .text("Vous avez été inscrit à ce cours.")
                .graphic(imageView)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT)
                .onAction(new EventHandler<ActionEvent >() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Cliqué sur la notification");
                    }
                });
        notificationBuilder.show();
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

    public void Historique(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/Notification.fxml"));
            historique.getScene().setRoot(root);
        } catch (Exception e) {
            showAlert("Error", e.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
