package services;

import enities.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class ServiceProduct implements IService<Product> {
    private Connection connexion;

    public ServiceProduct() {
        connexion = edu.esprit.utils.DataSource.getInstance().getCnx();
    }

    public static void ajouter(Product product) {
    }


    @Override
    public void update(Product product) {
            try {
                String requete = "update Produit set nom=?,Category=? where id_Product=?";
                PreparedStatement pst = connexion.prepareStatement(requete);
                pst.setString(1, product.getNom());
                pst.setString(2, product.getCategory());
                pst.setInt(3, product.getId_Product());
                pst.executeUpdate();
                System.out.println("Mise a jour avec succes");
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }


    }

    @Override
    public Product readById(int id) {
        return null;
    }


    @Override
    public void add(Product prod) {
        String requete="insert into Produit (nom,Category) values ('"+prod.getNom()+"','"+prod.getCategory()+"')";
        try {
            Statement ste = connexion.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Product prod) {
        String requete = "DELETE FROM produits WHERE id = ?";
        try {
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, prod.getId_Product());
            pst.executeUpdate();
            System.out.println("Produit supprimé avec succès !");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<Product> readAll() {
        // Implémentation de la récupération de tous les produits
        String requete = "SELECT * FROM produits";
        List<Product> productList = new ArrayList<>();
        try (PreparedStatement pst = connexion.prepareStatement(requete);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String category = rs.getString("Category");
                double prix = rs.getDouble("prix");
                productList.add(new Product(id, nom, category));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }

    public Product getOneById(int id) {
        // Implémentation pour récupérer un produit par son ID
        String requete = "SELECT * FROM produits WHERE id = ?";
        try (PreparedStatement pst = connexion.prepareStatement(requete)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int productId = rs.getInt("id");
                    String productName = rs.getString("nom");
                    String productCategory = rs.getString("Category");
                    double productPrice = rs.getDouble("prix");
                    return new Product(productId, productName, productCategory);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null; // Si aucun produit trouvé avec cet ID
    }

    public Set<Product> getAll() {
        // Implémentation pour récupérer tous les produits sous forme d'ensemble (sans doublons)
        Set<Product> productSet = new HashSet<>();
        // ... récupération des produits ...
        return productSet;
    }
}

