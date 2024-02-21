package entity;

public class Salle {
private int id;
private String nom;
private int surface;
private int capacite;
private String discipline;
private boolean dispo;

    public Salle() {
    }

    public Salle(int id,String nom, int surface, int capacite, String discipline, boolean dispo) {
        this.id = id;
        this.nom=nom;
        this.surface = surface;
        this.capacite = capacite;
        this.discipline = discipline;
        this.dispo = dispo;
    }
    public Salle(String nom,int surface, int capacite, String discipline, boolean dispo) {
        this.nom = nom;
        this.surface = surface;
        this.capacite = capacite;
        this.discipline = discipline;
        this.dispo = dispo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getSurface() {
        return surface;
    }

    public void setSurface(int surface) {
        this.surface = surface;
    }

    public int getCapacite() {
        return capacite;
    }

    public void setCapacite(int capacite) {
        this.capacite = capacite;
    }

    public String getDiscipline() {
        return discipline;
    }

    public void setDiscipline(String discipline) {
        this.discipline = discipline;
    }

    public boolean getDispo() {
        return dispo;
    }

    public void setDispo(boolean dispo) {
        this.dispo = dispo;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", surface=" + surface +
                ", capacite=" + capacite +
                ", discipline='" + discipline + '\'' +
                ", dispo=" + dispo +
                '}';
    }
}
