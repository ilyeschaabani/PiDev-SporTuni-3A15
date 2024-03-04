package controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.PieChart;
import entities.Evenement;
import entities.ReservationEv;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.EvenementService;
import services.ReservationService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.chart.PieChart;
import utils.DataSource;

public class StatisticsController implements Initializable {
    public PieChart piechart;


        private Statement st;
        private ResultSet rs;
        private Connection cnx;


        ObservableList<PieChart.Data>data=FXCollections.observableArrayList();

        /**
         * Initializes the controller class.
         */
        @Override
        public void initialize(URL url, ResourceBundle rb) {
            System.out.println("ENTRY");
            cnx=DataSource.getInstance().getCnx();
            System.out.println("ENTERING STAT");
            stat();
        }
        private void stat()
        {

            System.out.println("ENTERED STATS");

            try {

                String query = "SELECT COUNT(*),nom_salle FROM evenement GROUP BY nom_salle" ;

                PreparedStatement PreparedStatement = cnx.prepareStatement(query);
                rs = PreparedStatement.executeQuery();


                while (rs.next()){
                    data.add(new PieChart.Data(rs.getString("nom_salle"),rs.getInt("COUNT(*)")));
                }
            } catch (SQLException ex) {
                System.out.println("TEST");
                System.out.println(ex.getMessage());
            }

            piechart.setTitle("*Statistiques des evenements par salle *");
            piechart.setLegendSide(Side.LEFT);
            piechart.setData(data);

        }
    public void goToStats(ActionEvent event) {
        FXMLLoader innerLoader = new FXMLLoader(getClass().getResource("/StatsEvents.fxml"));
        try {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = innerLoader.load();
            Scene scene = new Scene(root,stage.getWidth(), stage.getHeight() );

            stage.setTitle("Scene One");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void goToPdf(ActionEvent event) throws SQLException {
        // Afficher la boîte de dialogue de sélection de fichier
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            // Générer le fichier PDF avec l'emplacement de sauvegarde sélectionné
            // Récupérer la liste des visites médicales
            EvenementService evenementService=new EvenementService();
            List<Evenement> evenements = evenementService.readAll();

            try {
                // Créer le document PDF
                Document document = new Document(PageSize.A4.rotate());
                PdfWriter.getInstance(document, new FileOutputStream(selectedFile));
                document.open();

                // Titre du document
                Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 32, Font.BOLD, BaseColor.DARK_GRAY);
                Paragraph title = new Paragraph("Liste des evenements", titleFont);
                title.setAlignment(Element.ALIGN_CENTER);
                title.setSpacingAfter(20);
                document.add(title);

                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                table.setSpacingBefore(10);

                // Ajouter les en-têtes de colonnes
                String[] headers = {"ID", "description", "date début", "Nom Salle", "Nombre de places maximale"};
                Font headerFont = new Font(Font.FontFamily.TIMES_ROMAN, 14, Font.BOLD, BaseColor.BLACK);
                for (String header : headers) {
                    PdfPCell cell = new PdfPCell(new Paragraph(header, headerFont));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    table.addCell(cell);
                }

                // Ajouter les données des visites médicales
                for (Evenement e : evenements) {
                    table.addCell(String.valueOf(e.getId_e()));
                    table.addCell(String.valueOf(e.getDescription()));
                    table.addCell(String.valueOf(e.getDateDebut()));
                    table.addCell(e.getNom_salle());
                    table.addCell(String.valueOf(e.getNbr_max()));
                }

                document.add(table);
                document.close();

                System.out.println("Le fichier PDF a été généré avec succès.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void goToEvents(ActionEvent event) {
        FXMLLoader innerLoader = new FXMLLoader(getClass().getResource("/AjouterEvenement.fxml"));
        try {

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Parent root = innerLoader.load();
            Scene scene = new Scene(root,stage.getWidth(), stage.getHeight() );

            stage.setTitle("Scene One");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

    }

    }