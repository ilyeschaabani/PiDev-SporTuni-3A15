package controllers;

import entities.Evenement;
import entities.MailUtil;
import entities.ReservationEv;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import services.EvenementService;
import services.ReservationService;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class OneEventCardController {
    public Text nom_e;
    public Text nom_salle;
    public Text dateDebut;
    public Text description;
    public Text discipline;
    public Text nb_left;
    public HBox AddToCart;

    public void showAlert(String title, String msg)
    {
        Alert alert = new Alert(AlertType.INFORMATION);

        // Set the title and content text
        alert.setTitle(title);
        alert.setHeaderText(null);  // Set header text to null to hide it
        alert.setContentText(msg);

        // Show the alert dialog
        alert.showAndWait();
    }
    public void setEventData(Evenement evenement) {
        // Instancier le service de produit
        EvenementService EvenementService = new EvenementService();


        nom_e.setText(evenement.getNom_e());

        nom_salle.setText("Nom Salle:" + evenement.getNom_salle());
        dateDebut.setText("Date de début:" + evenement.getDateDebut());
        description.setText("Description:" + evenement.getDescription());
        discipline.setText("discipline" + evenement.getNom_discipline());
        int nb_taken = EvenementService.GetNombreDePlaces(evenement.getId_e());
        if (nb_taken >= evenement.getNbr_max()) {
            nb_left.setText("Nb places:" + nb_taken + "/" + evenement.getNbr_max());
            nb_left.setStyle("text-color:red");
            AddToCart.getChildren().get(0).setDisable(true);
        } else {
            nb_left.setText("" + nb_taken + "/" + evenement.getNbr_max());
        }
        AddToCart.setOnMouseClicked(event -> {
            System.out.println("Adding a new Reservation for : " + evenement.getId_e());
            ReservationService reservationService = new ReservationService();
            if(nb_taken<=evenement.getNbr_max()) {
                try {
                    MailUtil.sendMailAcceptation("Votre réservation à été accepté");
                    // FOR INTEGRATION CHANGE USERID
                    int userid=1;
                    java.util.Date currentDate=new java.util.Date();
                    java.sql.Date sqlDate = new java.sql.Date(currentDate.getTime());

                    ReservationEv ReservationEv=new ReservationEv(sqlDate,userid,evenement.getNbr_max(),evenement.getId_e());
                    reservationService.add(ReservationEv);
                    showAlert("Succees","Votre réservation à été bien envoyé, Nous allons vous contactez via E-mail ultérieurement");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }else {
                try {
                    MailUtil.sendMailRefus("Votre réservation à été réjeté");
                    showAlert("Succees","Votre réservation à été bien envoyé, Nous allons vous contactez via E-mail ultérieurement");

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

                 


        });
        //nb_left.setText(""+evenement.get);

    

       
    }
}

