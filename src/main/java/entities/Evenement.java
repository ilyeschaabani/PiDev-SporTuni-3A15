package entities;
import java.util.Date;

public class Evenement {
        private int id_e;
        private String nom_e;
        private String nom_salle;
        private Date date_e;
        private String nom_discipline;
        private String description;
        private int id_u;
        private Type type;

    public enum Type {
            STAGE,
            FETE,
            PASSAGE_DE_GRADE
        }

        // Constructeur
        public Evenement(int id_e, String nom_e, String nom_salle, Date date_e, String nom_discipline, String description, int id_u, Type type) {
            this.id_e = id_e;
            this.nom_e = nom_e;
            this.nom_salle = nom_salle;
            this.date_e=date_e;
            this.nom_discipline = nom_discipline;
            this.description = description;
            this.id_u = id_u;
            this.type = type;
        }

        // Getters et setters
        public int getId_e() {
            return id_e;
        }

        public void setId_e(int id_e) {
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
    public java.sql.Date getDate_e() {
        return date_e;
    }

    public void setDate_e(Date date_e) {
        this.date_e=date_e;
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

        public static Type getType() {
            return type;
        }

        public void setType(Type type) {
            this.type = type;
        }

        @Override
        public String toString() {
            return "Evenement{" +
                    "id_e=" + id_e +
                    ", nom_e='" + nom_e + '\'' +
                    ", nom_salle='" + nom_salle + '\'' +
                    ", date_e='" + date_e + '\'' +
                    ", nom_discipline='" + nom_discipline + '\'' +
                    ", description='" + description + '\'' +
                    ", id_u=" + id_u +
                    ", type=" + type +
                    '}';
        }
    }

