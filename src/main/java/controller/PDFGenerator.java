package controller;

import entity.Discipline;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.state.PDExtendedGraphicsState;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class PDFGenerator {

    public void generatePDF(String filename, List<Discipline> disciplines) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 24);
                contentStream.setNonStrokingColor(Color.BLUE); // Changer la couleur du texte à Bleu
                contentStream.setStrokingColor(Color.BLACK); // Couleur des lignes du tableau en Noir

                // Dessiner le titre centré "PDF Discipline" en rouge
                contentStream.beginText();
                contentStream.newLineAtOffset((page.getMediaBox().getWidth() - PDType1Font.HELVETICA_BOLD.getStringWidth("PDF Discipline") / 1000 * 24) / 2, 750);
                contentStream.showText("PDF Discipline");
                contentStream.endText();

                float margin = 50;
                float y = 700;
                float tableWidth = 500;

                // Dessiner le fond blanc du tableau
                contentStream.setNonStrokingColor(Color.WHITE); // Changer la couleur du fond à Blanc
                contentStream.fillRect(margin, y, tableWidth, -20 * (disciplines.size() + 1)); // Calculer la hauteur en fonction du nombre de lignes

                // Dessiner le tableau avec des bordures arrondies
                drawRoundedRect(contentStream, margin, y, tableWidth, -20 * (disciplines.size() + 1), 10);

                // En-tête du tableau
                String[] headers = {"ID", "Nom", "Description"};
                float[] columnWidths = {50, 200, 250}; // Ajuster la largeur des colonnes
                drawTable(contentStream, y, tableWidth, margin, headers, columnWidths, PDType1Font.HELVETICA_BOLD, 12f);

                // Contenu du tableau
                for (Discipline discipline : disciplines) {
                    y -= 20; // Ajuster la hauteur de chaque ligne
                    String[] rowData = {
                            String.valueOf(discipline.getId_discipline()),
                            discipline.getNom_discipline(),
                            discipline.getDescription()
                    };
                    drawTable(contentStream, y, tableWidth, margin, rowData, columnWidths, PDType1Font.HELVETICA, 12f);
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
            float textX = nextX + (cellWidth - textWidth) / 2; // Centrer le texte
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

    private void drawRoundedRect(PDPageContentStream contentStream, float x, float y, float width, float height, float radius) throws IOException {
        contentStream.moveTo(x + radius, y);
        contentStream.lineTo(x + width - radius, y);
        contentStream.curveTo(x + width - radius / 2, y, x + width, y + radius / 2, x + width, y + radius);
        contentStream.lineTo(x + width, y + height - radius);
        contentStream.curveTo(x + width, y + height - radius / 2, x + width - radius / 2, y + height, x + width - radius, y + height);
        contentStream.lineTo(x + radius, y + height);
        contentStream.curveTo(x + radius / 2, y + height, x, y + height - radius / 2, x, y + height - radius);
        contentStream.lineTo(x, y + radius);
        contentStream.curveTo(x, y + radius / 2, x + radius / 2, y, x + radius, y);
        contentStream.closePath();
        contentStream.stroke();
    }
}
