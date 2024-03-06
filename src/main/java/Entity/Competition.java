package Entity;

import java.sql.Date;

public class Competition {

    private int id_comp;
    private String nom_comp;
    private String lieu_comp;
    private Date date;
    private String discipline;
    private int id_salle;

    public Competition() {
    }

    public Competition(String nom_comp, String lieu_comp, Date date, String discipline, int id_salle) {
        this.nom_comp = nom_comp;
        this.lieu_comp = lieu_comp;
        this.date = date;
        this.discipline = discipline;
        this.id_salle = id_salle;
    }

    public Competition(int id_comp, String nom_comp, String lieu_comp, Date date, String discipline, int id_salle) {
        this.id_comp = id_comp;
        this.nom_comp = nom_comp;
        this.lieu_comp = lieu_comp;
        this.date = date;
        this.discipline = discipline;
        this.id_salle = id_salle;
    }

    @Override
    public String toString() {
        return "Competition{" +
                "id_comp=" + id_comp +
                ", nom_comp='" + nom_comp + '\'' +
                ", lieu_comp='" + lieu_comp + '\'' +
                ", date=" + date +
                ", discipline='" + discipline + '\'' +
                ", id_salle=" + id_salle +
                '}';
    }

    public int getId_comp() {
        return id_comp;
    }

    public void setId_comp(int id_comp) {
        this.id_comp = id_comp;
    }

    public String getNom_comp() {
        return nom_comp;
    }

    public void setNom_comp(String nom_comp) {
        this.nom_comp = nom_comp;
    }

    public String getLieu_comp() {
        return lieu_comp;
    }

    public void setLieu_comp(String lieu_comp) {
        this.lieu_comp = lieu_comp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public int getId_salle() {
        return id_salle;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }
}
