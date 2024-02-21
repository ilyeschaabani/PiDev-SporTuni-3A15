package entities;

public class discipline {
    private int id_discipline;
    private String nom_discipline;
    private String description;

    public discipline() {
    }

    public discipline(int id_discipline, String nom_discipline, String description) {
        this.id_discipline = id_discipline;
        this.nom_discipline = nom_discipline;
        this.description = description;
    }

    public discipline(String nom_discipline, String description) {
        this.nom_discipline = nom_discipline;
        this.description = description;
    }



    public int getId_discipline() {
        return id_discipline;
    }

    public String getNom_discipline() {
        return nom_discipline;
    }

    public String getDescription() {
        return description;
    }

    public void setId_discipline(int id_discipline) {
        this.id_discipline = id_discipline;
    }

    public void setNom_discipline(String nom_discipline) {
        this.nom_discipline = nom_discipline;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "discipline{" +
                "id_discipline=" + id_discipline +
                ", nom_discipline='" + nom_discipline + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
