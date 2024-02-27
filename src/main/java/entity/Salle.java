package entity;

import java.util.List;

public class Salle {
private int id;
private String nom;
private int surface;
private int capacite;
private String discipline;
    private String dispoInter;

    private List<Dispo> dispoList;


    public Salle() {
    }

    public Salle(int id,String nom, int surface, int capacite, String discipline ) {
        this.id = id;
        this.nom=nom;
        this.surface = surface;
        this.capacite = capacite;
        this.discipline = discipline;
    }
    public Salle(String nom,int surface, int capacite, String discipline) {
        this.nom = nom;
        this.surface = surface;
        this.capacite = capacite;
        this.discipline = discipline;
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

    public String getDispoInter() {
        return dispoInter;
    }

    public void setDispoInter(String dispoInter) {
        this.dispoInter = dispoInter;
    }

    public List<Dispo> getDispoList() {
        return dispoList;
    }


    public void setDispoList(List<Dispo> dispoList) {
        this.dispoList = dispoList;
    }

    @Override
    public String toString() {
        return "Salle{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", surface=" + surface +
                ", capacite=" + capacite +
                ", discipline='" + discipline + '\'' +
                '}';
    }
}
