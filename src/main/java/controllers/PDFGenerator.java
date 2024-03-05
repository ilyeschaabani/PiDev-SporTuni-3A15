package controllers;

import entities.Competition;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    public void generatePDF(String filename, List<Competition> competitions) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
                contentStream.setNonStrokingColor(Color.RED);
                contentStream.setStrokingColor(Color.RED);

                // Dessiner le titre "Rapport Compétition" en rouge dégradé
                contentStream.beginText();
                contentStream.newLineAtOffset(200, 750);
                contentStream.showText("Rapport Compétition");
                contentStream.endText();

                float margin = 50;
                float y = 700;
                float tableWidth = 500;

                // Dessiner le fond gris clair du tableau
                float backgroundWidth = margin + tableWidth; // Largeur du fond gris
                contentStream.setNonStrokingColor(Color.LIGHT_GRAY);
                contentStream.addRect(margin, y, backgroundWidth, -200); // Ajustement de la largeur
                contentStream.fill();

                contentStream.setLineWidth(1f);

// Dessiner le contour rouge du tableau
                contentStream.setStrokingColor(Color.RED);
                contentStream.addRect(margin, y, backgroundWidth, -200); // Ajustement de la largeur
                contentStream.stroke();


                // En-tête du tableau
                String[] headers = {"ID", "Nom", "Lieu", "Date", "Discipline", "ID Salle"};
                float[] columnWidths = {50, 100, 100, 100, 100, 100};
                drawTable(contentStream, y, tableWidth, margin, headers, columnWidths, PDType1Font.HELVETICA_BOLD, 12f);
                // Dessiner les lignes du tableau
                //drawTableLines(contentStream, y, tableWidth, margin, competitions.size(), headers.length);

                // Contenu du tableau
                y -= 20; // Ajuster la hauteur du contenu
                for (Competition competition : competitions) {
                    String[] rowData = {
                            String.valueOf(competition.getId_comp()),
                            competition.getNom_comp(),
                            competition.getLieu_comp(),
                            competition.getDate().toString(), // Assurez-vous que la date est au format String
                            competition.getDiscipline(),
                            String.valueOf(competition.getId_salle())
                    };
                    drawTable(contentStream, y, tableWidth, margin, rowData, columnWidths, PDType1Font.HELVETICA, 12f);
                    y -= 20; // Ajuster la hauteur de chaque ligne
                }
            }

            document.save(filename);
            System.out.println("PDF généré avec succès.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void drawTable(PDPageContentStream contentStream, float y, float tableWidth, float margin,
                           String[] content, float[] columnWidths, PDType1Font font, float fontSize) throws IOException {
        final int cols = content.length;
        float rowHeight = fontSize + 2 * 2; // Augmenter le padding pour une ligne plus grande

        // Dessiner les lignes du tableau
        contentStream.setLineWidth(0.5f);
        contentStream.setFont(font, fontSize);
        contentStream.setNonStrokingColor(Color.BLACK); // Couleur noire pour les lignes et les informations

        float textY = y - fontSize;
        float nextX = margin; // Ajout de cette ligne pour garder la trace de la position actuelle en x
        for (int i = 0; i < content.length; i++) {
            float textWidth = font.getStringWidth(content[i]) * fontSize / 1000f;
            float cellWidth = columnWidths[i];
            float textX = nextX + (cellWidth - textWidth) / 2; // Utilisation de nextX pour positionner le texte
            contentStream.beginText();
            contentStream.newLineAtOffset(textX, textY);
            contentStream.showText(content[i]);
            contentStream.endText();
            nextX += columnWidths[i]; // Mise à jour de nextX pour la prochaine colonne

            // Dessiner les lignes verticales du tableau
            contentStream.moveTo(nextX, y); // Utilisation de nextX pour la position de la ligne verticale
            contentStream.lineTo(nextX, y - 20);
            contentStream.stroke();
        }

        // Dessiner la ligne horizontale en bas du tableau
        contentStream.moveTo(margin, y - 20);
        contentStream.lineTo(margin + tableWidth, y - 20); // Ajustement de la position de la ligne horizontale
        contentStream.stroke();
    }

}
