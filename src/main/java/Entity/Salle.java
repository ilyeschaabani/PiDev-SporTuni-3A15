package Entity;

import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class Salle {
    private int id;
    private String nom;
    private int surface;
    private int capacite;
    private String discipline;
    private Dispo dispoInter;
    private double rate;

    private LocalDate dateD;
    private LocalDate dateF;


    private List<Dispo> dispoList;


    public Salle() {
    }

    public Salle(int id,String nom, int surface, int capacite, String discipline, LocalDate dateD, LocalDate dateF) {
        this.id = id;
        this.nom=nom;
        this.surface = surface;
        this.capacite = capacite;
        this.discipline = discipline;
        this.dateD= dateD;
        this.dateF = dateF;

    }
    public Salle(String nom,int surface, int capacite, String discipline) {
        this.nom = nom;
        this.surface = surface;
        this.capacite = capacite;
        this.discipline = discipline;
    }

    public Salle(int id, String nom, int surface, int capacite, String discipline) {
        this.id = id;
        this.nom = nom;
        this.surface = surface;
        this.capacite = capacite;
        this.discipline = discipline;
    }

    public Salle(int idSalle, String nomSalle, int surfaceSalle, int capaciteSalle, String disciplineSalle, LocalDateTime dateD, LocalDateTime dateF, Dispo dispo) {
    }

    public Salle(int id, String nom, int surface, int capacite, String discipline, List<Dispo> disponibiliteList) {
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

    public Dispo getDispoInter() {
        return dispoInter;
    }

    public void setDispoInter(Dispo dispoInter) {
        this.dispoInter = dispoInter;
    }

    public List<Dispo> getDispoList() {
        return dispoList;
    }


    public void setDispoList(List<Dispo> dispoList) {
        this.dispoList = dispoList;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    @Override
    public String toString() {
        return nom;
    }


    // Add these properties if they are not already present
    public LocalDateTime getDateD() {
        if (dispoList != null && !dispoList.isEmpty()) {
            return dispoList.get(0).getDateD(); // Assuming DateD is in the first Dispo object
        }
        return null;
    }

    public LocalDateTime getDateF() {
        if (dispoList != null && !dispoList.isEmpty()) {
            return dispoList.get(0).getDateF(); // Assuming DateF is in the first Dispo object
        }
        return null;
    }

}
