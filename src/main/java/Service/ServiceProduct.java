package Service;

import Entity.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServiceProduct implements IProductService<Product> {
    private Connection connection;

    public ServiceProduct() {
        connection = Utiils.DataSource.getInstance().getCnx();
    }
    public void update(Product product) {
        String req = "UPDATE produit SET nom=?, prix=?, Category=? WHERE id_Product=?";
        System.out.println(this.getid(product));
        try (PreparedStatement pstm = connection.prepareStatement(req)) {
            pstm.setString(1, product.getNom());
            pstm.setDouble(2, product.getPrix());
            pstm.setString(3, product.getCategory());
            pstm.setInt(4,  product.getId_Product());
            pstm.executeUpdate();
            System.out.println("Produit mis à jour avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour du produit: " + e.getMessage());
        }
    }
    @Override
    public Product readById(int id) {
        // Implementation not provided
        return null;
    }

    @Override
    public void add(Product prod) {
        String query = "INSERT INTO produit (nom, Category, prix) VALUES (?, ?, ?)";
        try (PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, prod.getNom());
            pst.setString(2, prod.getCategory());
            pst.setDouble(3, prod.getPrix());
            pst.executeUpdate();
            System.out.println("Produit ajouté avec succès !");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout du produit: " + e.getMessage());
        }
    }

    @Override
    public void delete(Product product) throws SQLException {

    }

    public void delete(int id) throws SQLException {
        String req = "DELETE FROM produit WHERE id_Product = ?";
        try (PreparedStatement pstm = connection.prepareStatement(req)) {
            pstm.setInt(1, id);
            int rowsAffected = pstm.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Produit " + id + " est supprimé avec succès !");
            } else {
                System.out.println("Le produit avec le nom " + id + " n'existe pas !");
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression du produit: " + e.getMessage());
        }
    }
    @Override
    public List<Product> readAll() {
        String query = "SELECT * FROM produit";
        List<Product> productList = new ArrayList<>();
        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                //int id = rs.getInt("id");
                String nom = rs.getString("nom");
                String category = rs.getString("Category");
                double prix = rs.getDouble("prix");
                productList.add(new Product( nom, category, prix));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la lecture de tous les produits: " + e.getMessage());
        }
        return productList;
    }




    public int getid(Product product) {
        System.out.println(product.getCategory());
        String query = "SELECT id_Product FROM produit WHERE nom=? and  Category=? and prix=? ";
        int id = 0;
        try (PreparedStatement pst = connection.prepareStatement(query)){
            pst.setString(1, product.getNom());
            pst.setString(2, product.getCategory());
            pst.setDouble(3, product.getPrix());
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {id=rs.getInt("id_Product");
                System.out.println("test");

            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la lecture de tous les produits: " + e.getMessage());
        }
        return id;
    }


    public int getNombreTotalProduct() {
        int totalProduct = 0;
        String query = "SELECT COUNT(*) AS total_product FROM produit"; // Assuming the table name is "product"

        try {
            PreparedStatement pst = connection.prepareStatement(query);
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                totalProduct = resultSet.getInt("total_product");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return totalProduct;
    }
}
