package Entity;

import java.time.LocalDateTime;

public class Dispo {
    private int id_Dispo;
    private Salle salle;
    private LocalDateTime dateD;
    private LocalDateTime dateF;


    public Dispo() {}

    public Dispo(int id_Dispo, Salle salle, LocalDateTime dateD, LocalDateTime dateF) {
        this.id_Dispo = id_Dispo;
        this.salle = salle;
        this.dateD = dateD;
        this.dateF = dateF;
    }

    public Dispo(Salle salle, LocalDateTime dateD, LocalDateTime dateF) {
        this.salle = salle;
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



    public LocalDateTime getDateD() {
        return dateD;
    }

    public Salle getSalle() {
        return salle;
    }

    public void setSalle(Salle salle) {
        this.salle = salle;
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
                ", salle=" + salle +
                ", dateD=" + dateD +
                ", dateF=" + dateF +
                '}';
    }
}