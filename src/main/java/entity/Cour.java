package entity;

import java.sql.Date;

public class Cour {

    private int id_cour;
    private String nom_cour;
    private Date date;
    private String heure_debut;
    private String heure_fin;
    private String nom_salle;
    private String nb_max;
    private String nom_discipline;
    private int id_u;

    public Cour(int id_cour,String nom_cour, Date date, String heure_debut, String heure_fin, String nom_salle, String nb_max, String nom_discipline ) {
    }

    public Cour(int id_cour, String nom_cour, Date date, String heure_debut, String heure_fin, String nom_salle, String nb_max, String nom_discipline, int id_u) {
        this.id_cour = id_cour;
        this.nom_cour = nom_cour;
        this.date = date;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.nom_salle = nom_salle;
        this.nb_max = nb_max;
        this.nom_discipline = nom_discipline;
        this.id_u = id_u;
    }

    public Cour(String nom_cour, Date date, String heure_debut, String heure_fin, String nom_salle, String nb_max, String nom_discipline) {
        this.nom_cour = nom_cour;
        this.date = date;
        this.heure_debut = heure_debut;
        this.heure_fin = heure_fin;
        this.nom_salle = nom_salle;
        this.nb_max = nb_max;
        this.nom_discipline = nom_discipline;
    }

    public Cour() {

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

    public String getHeure_debut() {
        return heure_debut;
    }

    public void setHeure_debut(String heure_debut) {
        this.heure_debut = heure_debut;
    }

    public String getHeure_fin() {
        return heure_fin;
    }

    public void setHeure_fin(String heure_fin) {
        this.heure_fin = heure_fin;
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
        this.nb_max = nb_max;
    }

    public String getNom_discipline() {
        return nom_discipline;
    }

    public void setNom_discipline(String nom_discipline) {
        this.nom_discipline = nom_discipline;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }

    @Override
    public String toString() {
        return "Cour{" +
                "id_cour=" + id_cour +
                ", nom_cour='" + nom_cour + '\'' +
                ", date=" + date +
                ", heure_debut='" + heure_debut + '\'' +
                ", heure_fin='" + heure_fin + '\'' +
                ", nom_salle='" + nom_salle + '\'' +
                ", nombre participants max='" + nb_max + '\'' +
                ", nom_discipline='" + nom_discipline + '\'' +
                ", id_u=" + id_u +
                '}';
    }


}