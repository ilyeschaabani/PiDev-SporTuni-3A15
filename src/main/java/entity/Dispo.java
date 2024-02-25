package entity;

import java.time.LocalDateTime;

public class Dispo {
    private int id_Dispo;
    private int id_salle;
    private LocalDateTime dateD;
    private LocalDateTime dateF;


    public Dispo() {}

    public Dispo(int id_Dispo, int id_salle, LocalDateTime dateD, LocalDateTime dateF) {
        this.id_Dispo = id_Dispo;
        this.id_salle = id_salle;
        this.dateD = dateD;
        this.dateF = dateF;
    }

    public Dispo(int id_salle, LocalDateTime dateD, LocalDateTime dateF) {
        this.id_salle = id_salle;
        this.dateD = dateD;
        this.dateF = dateF;

    }

    public Dispo(LocalDateTime dateD, LocalDateTime dateF) {
        this.dateD = dateD;
        this.dateF = dateF;
    }

    public int getId_Dispo() {
        return id_Dispo;
    }

    public void setId_Dispo(int id_Dispo) {
        this.id_Dispo = id_Dispo;
    }

    public int getId_salle() {
        return id_salle;
    }

    public void setId_salle(int id_salle) {
        this.id_salle = id_salle;
    }

    public LocalDateTime getDateD() {
        return dateD;
    }

    public void setDateD(LocalDateTime dateD) {
        this.dateD = dateD;
    }

    public LocalDateTime getDateF() {
        return dateF;
    }

    public void setDateF(LocalDateTime dateF) {
        this.dateF = dateF;
    }



    @Override
    public String toString() {
        return "Dispo{" +
                "id_Dispo=" + id_Dispo +
                ", id_salle=" + id_salle +
                ", dateDebut=" + dateD +
                ", dateFin=" + dateF +
                '}';
    }
}
