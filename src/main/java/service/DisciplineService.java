package service;

import util.DataSource;
import entity.Discipline;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DisciplineService implements IService<Discipline> {
    private Connection connexion;
    public DisciplineService(){
        connexion= DataSource.getInstance().getCnx();
    }
    private Statement ste ;
    public void add(Discipline d) {
        String requete = "INSERT INTO discipline (nom_discipline, description, image_url) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(requete);
            preparedStatement.setString(1, d.getNom_discipline());
            preparedStatement.setString(2, d.getDescription());
            preparedStatement.setString(3, d.getImageUrl());
            preparedStatement.executeUpdate();
            System.out.println("Discipline ajoutée ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void delete(int id) {
        String requete = "DELETE FROM discipline WHERE id_discipline = ?";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(requete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("Discipline supprimée");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public void update(Discipline discipline, int id) {

    }
/*
     public void update(Discipline s ,int id  ) {
         String requete = "UPDATE discipline SET nom_discipline=?, description = ? WHERE id = ?";
         try {
             PreparedStatement ps= connexion.prepareStatement(requete);
             ps.setString(1, s.getNom_discipline());
             ps.setString(2, s.getDescription());
             ps.setInt(3,s.getId_discipline());
             ps.executeUpdate();
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }

     }*/
public void updato(Discipline d) {
    String requete = "UPDATE discipline SET nom_discipline=?, description=?, image_url=? WHERE id_discipline=?";
    try {
        PreparedStatement ps = connexion.prepareStatement(requete);
        ps.setString(1, d.getNom_discipline());
        ps.setString(2, d.getDescription());
        ps.setString(3, d.getImageUrl());
        ps.setInt(4, d.getId_discipline());
        int result = ps.executeUpdate();
        if (result > 0) {
            System.out.println("Discipline mise à jour !");
        } else {
            System.out.println("Aucune discipline mise à jour !");
        }
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}

    public List<Discipline> readAll() {
        String requete = "SELECT * FROM discipline";
        List<Discipline> list = new ArrayList<>();
        try {
            Statement ste = connexion.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                Discipline discipline = new Discipline(
                        rs.getInt("id_discipline"),
                        rs.getString("nom_discipline"),
                        rs.getString("description"),
                        rs.getString("image_url"));
                list.add(discipline);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Discipline readById(int id) {
        String requete = "SELECT * FROM discipline WHERE id_discipline=?";
        try {
            PreparedStatement ps = connexion.prepareStatement(requete);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Discipline(
                        rs.getInt("id_discipline"),
                        rs.getString("nom_discipline"),
                        rs.getString("description"),
                        rs.getString("image_url"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
    public int getNombreTotalDisciplines() {
        String requete = "SELECT COUNT(*) FROM discipline";
        try {
            Statement statement = connexion.createStatement();
            ResultSet resultSet = statement.executeQuery(requete);
            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }



}