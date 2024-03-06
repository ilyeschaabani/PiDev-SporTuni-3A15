package controllers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.EvenementService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class statsPage {
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
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Enregistrer le fichier PDF");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers PDF", "*.pdf"));
        File selectedFile = fileChooser.showSaveDialog(((Node) event.getSource()).getScene().getWindow());

        if (selectedFile != null) {
            EvenementService evenementService=new EvenementService();
            List<Evenement> evenements = evenementService.readAll();

            try {
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
