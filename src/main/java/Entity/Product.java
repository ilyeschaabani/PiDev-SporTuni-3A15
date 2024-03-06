package Entity;

public class Product {
    private int id_Product;
    private String nom;
    private String category;
    private String description;
    private double prix;

    public Product() {

    }


    // Constructeur
    public Product(int id_Product, String nom, String category, double prix) {
        this.id_Product = id_Product;
        this.nom = nom;
        this.category = category;
        this.prix = prix;
    }
    public Product( String nom, String category,double prix) {
        this.nom = nom;
        this.category = category;
        this.prix = prix;
    }


    // Getters et setters


    public  int getId_Product() {
        return id_Product;
    }

    public void setId_Product(int id_Product) {
        this.id_Product = id_Product;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    // Méthode pour afficher les détails du produit
    @Override
    public String toString() {
        return "Product{" +
                "id_Product=" + id_Product +
                ", nom='" + nom + '\'' +
                ", category='" + category + '\'' +
                ", prix=" + prix +
                '}';
    }
}
