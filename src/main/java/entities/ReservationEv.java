package entities;
import java.sql.Date;
public class ReservationEv {
    private int idR;
    private Date dateR;
    private   int id_u;
    private Evenement nbr_max;
    private Evenement id_e;

    public ReservationEv(int idR,Date dateR,Evenement nbr_max,Evenement id_e) {
        this.idR = idR;
        this.dateR = dateR;
        this.id_u = id_u;
        this.nbr_max = nbr_max;
        this.id_e = id_e;
    }

    public int getIdR() {
        return idR;
    }

    public void setIdR(int idR) {
        this.idR = idR;
    }

    public int getId_u() {
        return id_u;
    }

    public void setId_u(int id_u) {
        this.id_u = id_u;
    }

    public Date getDateR() {
        return dateR;
    }

    public void setDateR(Date dateR) {
        this.dateR = dateR;
    }

    @Override
    public String toString() {
        return "ReservationEv{" +
                "idR=" + idR +
                ", dateR=" + dateR +
                ", id_u=" + id_u +
                ", nbr_max=" + nbr_max +
                ", id_e=" + id_e +
                '}';
    }
}
