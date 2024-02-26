package entities;
import java.sql.Date;

public class Evenement {
        private int id_e;
        private String nom_e;
        private String nom_salle;
        private Date dateDebut;
        private String nom_discipline;
        private String description;
        private int id_u;
        private int nbr_max;
        private Date dateFin;




    // Constructeur
        public Evenement(int id_e, String nom_e, String nom_salle, Date dateDebut, String nom_discipline, String description, int id_u, int nbr_max, Date dateFin) {
            this.id_e = id_e;
            this.nom_e = nom_e;
            this.nom_salle = nom_salle;
            this.dateDebut=dateDebut;
            this.nom_discipline = nom_discipline;
            this.description = description;
            this.id_u = id_u;
            this.nbr_max= nbr_max;
            this.dateFin=dateFin;

        }
    public Evenement(String nom_e, String nom_salle, String dateDebut, String nom_discipline, String description, int nbr_max, String dateFin ) {

        this.nom_e = nom_e;
        this.nom_salle = nom_salle;
        this.dateDebut= Date.valueOf(dateDebut);
        this.nom_discipline = nom_discipline;
        this.description = description;
        this. nbr_max= nbr_max;
        this.dateFin= Date.valueOf(dateFin);
    }


    public Evenement(String text, String text1, String text2, int i, String text3, String text4, String text5) {
    }

    public Evenement() {

    }


    // Getters et setters
        public int getId_e() {
            return id_e;
        }

        public  void setId_e(int id_e) {
            this.id_e = id_e;
        }

        public String getNom_e() {
            return nom_e;
        }

        public void setNom_e(String nom_e) {
            this.nom_e = nom_e;
        }

        public String getNom_salle() {
            return nom_salle;
        }

        public void setNom_salle(String nom_salle) {
            this.nom_salle = nom_salle;
        }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date  dateDebut) {
        this.dateDebut = dateDebut;
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
    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    @Override
    public String toString() {
        return "Evenement{" +
                "id_e=" + id_e +
                ", nom_e='" + nom_e + '\'' +
                ", nom_salle='" + nom_salle + '\'' +
                ", dateDebut=" + dateDebut +
                ", nom_discipline='" + nom_discipline + '\'' +
                ", description='" + description + '\'' +
                ", id_u=" + id_u +
                ", nbr_max=" + nbr_max +
                ", dateFin=" + dateFin +
                '}';
    }
}


