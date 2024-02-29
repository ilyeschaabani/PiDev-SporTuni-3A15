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
    private Statement ste ;//yebath lel sql heya interface
    public void add(Discipline s) {
        String requete = "INSERT INTO discipline (nom_discipline, description) VALUES (?, ?)";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(requete);
            preparedStatement.setString(1, s.getNom_discipline());
            preparedStatement.setString(2, s.getDescription());
            preparedStatement.executeUpdate();
            System.out.println("discipline ajouté ");
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
            System.out.println("discipline deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Discipline salle, int id) {

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
public void updato(Discipline s){
    String requete = "UPDATE discipline SET nom_discipline=?, description = ? WHERE id_discipline = ?";
    try {
        PreparedStatement ps = connexion.prepareStatement(requete);
        ps.setString(1, s.getNom_discipline());
        ps.setString(2, s.getDescription());
        ps.setInt(3, s.getId_discipline());
        int result = ps.executeUpdate();
        if (result > 0) {
            System.out.println("discipline updated!");
        } else {
            System.out.println("No discipline updated!");
        }
    } catch (SQLException e) {
        System.out.println(e.getMessage());
    }
}

    public List<Discipline> readAll() {
        String requete="SELECT * FROM discipline";
        List<Discipline> list=new ArrayList<>();
        try {
            ste= connexion.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                  list.add(new Discipline(
                          rs.getInt("id_discipline"),
                        rs.getString("nom_discipline"),
                        rs.getString("description")));

            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Discipline readById(int id) {
        String requete="select * from discilpine where id="+id;
        Discipline discipline = null;
        try {
            ste= connexion.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                discipline = new Discipline(
                        rs.getInt("id_discipline"),
                        rs.getString("nom_discipline"),
                        rs.getString("description"));            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return discipline ;
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