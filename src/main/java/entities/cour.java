package entities;

import java.sql.Date;

public class cour {
    private int id_cour;
    private String nom_cour;
    private Date date;
    private String heure;
    private String duree;
    private String nom_salle;
    private String nb_max;
    private int id_u;


    public cour() {
    }

    public cour(int id_cour, String nom_cour, Date date, String heure, String duree, String nom_salle, String nb_max) {
        this.id_cour = id_cour;
        this.nom_cour = nom_cour;
        this.date = date;
        this.heure = heure;
        this.duree = duree;
        this.nom_salle = nom_salle;
        this.nb_max = nb_max;

    }

    public cour(String nom_cour, Date date, String heure, String duree, String nom_salle, String nb_max) {
        this.nom_cour = nom_cour;
        this.date = date;
        this.heure = heure;
        this.duree = duree;
        this.nom_salle = nom_salle;
        this.nb_max = nb_max;
    }



    public int getId_cour() {
        return id_cour;
    }

    public void setId_cour(int id_cour) {
        this.id_cour = id_cour;
    }

    public String getNom_cour() {
        return nom_cour;
    }

    public void setNom_cour(String nom_cour) {
        this.nom_cour = nom_cour;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getHeure() {
        return heure;
    }

    public void setHeure(String heure) {
        this.heure = heure;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getNom_salle() {
        return nom_salle;
    }

    public void setNom_salle(String nom_salle) {
        this.nom_salle = nom_salle;
    }

    public String getNb_max() {
        return nb_max;
    }

    public void setNb_max(String nb_max) {
        this.nb_max = String.valueOf(nb_max);
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }

    @Override
    public String toString() {
        return "cour{" +
                "id_cour=" + id_cour +
                ", nom_cour='" + nom_cour + '\'' +
                ", date=" + date +
                ", heure='" + heure + '\'' +
                ", duree='" + duree + '\'' +
                ", nom_salle='" + nom_salle + '\'' +
                ", nb_max=" + nb_max +
                ", id_u=" + id_u +
                '}';
    }
}
