package entities;
import java.sql.Date;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ReservationEv {
    private int idR;
    private Date dateR;
    private int id_u; // ID USER
    private int nbr_max;
    private int id_e; // ID EVENT

    public ReservationEv(int idR, Date dateR, int id_u, int nbr_max, int id_e) {
        this.idR = idR;
        this.dateR = dateR;
        this.id_u = id_u;
        this.nbr_max = nbr_max;
        this.id_e = id_e;
    }

    public ReservationEv(Date dateR, int id_u, int nbr_max, int id_e) {
        this.dateR = dateR;
        this.id_u = id_u;
        this.nbr_max = nbr_max;
        this.id_e = id_e;
    }

    public ReservationEv() {

    }

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public Date getDateR() {
        return dateR;
    }

    public void setDateR(Date dateR) {
        this.dateR = dateR;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }

    public int getNbr_max() {
        return nbr_max;
    }

    public void setNbr_max(int nbr_max) {
        this.nbr_max = nbr_max;
    }

    public int getId_e() {
        return id_e;
    }

    public void setId_e(int id_e) {
        this.id_e = id_e;
    }
}

