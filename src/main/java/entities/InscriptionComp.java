package entities;

import java.util.Arrays;

public class InscriptionComp {

    private int num_inscri;
    private String nom;
    private String prenom;
    private int age;
    private float poids;
    private byte[] image;
    private int num_tel;
    private int id_comp;

    public InscriptionComp() {
    }

    public InscriptionComp(String nom, String prenom, int age, float poids, byte[] image, int num_tel, int id_comp) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.poids = poids;
        this.image = image;
        this.num_tel = num_tel;
        this.id_comp = id_comp;
    }

    public InscriptionComp(int num_inscri, String nom, String prenom, int age, float poids, int num_tel, int id_comp) {
        this.num_inscri = num_inscri;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.poids = poids;
        this.num_tel = num_tel;
        this.id_comp = id_comp;
    }

    public InscriptionComp(int num_inscri, String nom, String prenom, int age, float poids, byte[] image, int num_tel, int id_comp) {
        this.num_inscri = num_inscri;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.poids = poids;
        this.image = image;
        this.num_tel = num_tel;
        this.id_comp = id_comp;
    }

    public InscriptionComp(String nom, String prenom, int age, float poids, int num_tel, int id_comp) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.poids = poids;
        this.num_tel = num_tel;
        this.id_comp = id_comp;
    }

    @Override
    public String toString() {
        return "InscriptionComp{" +
                "num_inscri=" + num_inscri +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", age=" + age +
                ", poids=" + poids +
                ", image=" + Arrays.toString(image) +
                ", num_tel=" + num_tel +
                ", id_comp=" + id_comp +
                '}';
    }

    public int getNum_inscri() {
        return num_inscri;
    }

    public void setNum_inscri(int num_inscri) {
        this.num_inscri = num_inscri;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getPoids() {
        return poids;
    }

    public void setPoids(float poids) {
        this.poids = poids;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getNum_tel() {
        return num_tel;
    }

    public void setNum_tel(int num_tel) {
        this.num_tel = num_tel;
    }

    public int getId_comp() {
        return id_comp;
    }

    public void setId_comp(int id_comp) {
        this.id_comp = id_comp;
    }
}
