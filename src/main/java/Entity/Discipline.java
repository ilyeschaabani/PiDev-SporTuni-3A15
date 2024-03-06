package Entity;
import javafx.scene.image.Image;

public class Discipline {
    private int id_discipline;
    private String nom_discipline;

    private String description;

    private String imageUrl; // Chemin de l'image
    private Image image; // Image
    public Discipline() {
    }

    public Discipline(int id_discipline, String nom_discipline, String description, String imageUrl) {
        this.id_discipline = id_discipline;
        this.nom_discipline = nom_discipline;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public Discipline(String nom_discipline, String description, String imageUrl) {
        this.nom_discipline = nom_discipline;
        this.description = description;
        this.imageUrl = imageUrl;
        this.image = new Image(imageUrl);

    }

    public Discipline(String nom_discipline, String description) {
        this.nom_discipline = nom_discipline;
        this.description = description;
    }

    public Discipline(int id_discipline, String nom_discipline, String description) {
        this.id_discipline = id_discipline;
        this.nom_discipline = nom_discipline;
        this.description = description;
    }

    public Discipline(int id_discipline) {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
        this.image = new Image(imageUrl);
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public int getId_discipline() {
        return id_discipline;
    }

    public void setId_discipline(int id_discipline) {
        this.id_discipline = id_discipline;
    }

    public String getNom_discipline() {
        return nom_discipline;
    }

    public void setNom_discipline(String nom_discipline) {
        this.nom_discipline = nom_discipline;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }


    @Override
    public String toString() {
        return "Salle{" +
                "id=" + id_discipline +
                ", nom='" + nom_discipline + '\'' +
                ", description=" + description +
                '}';
    }
}
