package services;

import entities.Dispo;
import utils.DataSource;
import entities.Salle;

import java.sql.*;
import java.util.*;

public class SalleService implements Gservice<Salle> {
    private Connection connexion;
    public SalleService(){
        connexion= DataSource.getInstance().getCnx();
    }
    private Statement ste ;//yebath lel sql heya interface
    public void add(Salle s) {
        String requete = "INSERT INTO salle (nom,surface, capacite, discipline) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(requete);
            preparedStatement.setString(1, s.getNom());
            preparedStatement.setInt(2, s.getSurface());
            preparedStatement.setInt(3, s.getCapacite());
            preparedStatement.setString(4, s.getDiscipline());
            preparedStatement.executeUpdate();
            System.out.println("salle added ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Salle salle) {

    }

    @Override
    public void update(Salle salle) {

    }

    public void delete(int id) {
        String requete = "DELETE FROM salle WHERE id = ?";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(requete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("salle deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }



    public void updato(Salle s){


        String requete = "UPDATE salle SET nom=?, surface = ?, capacite = ?, discipline = ?  WHERE id = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(requete);
            ps.setString(1, s.getNom());
            ps.setInt(2, s.getSurface());
            ps.setInt(3, s.getCapacite());
            ps.setString(4, s.getDiscipline());
            ps.setInt(5,s.getId());
            ps.executeUpdate();
            System.out.println("Salle updated!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


    }


    public List<Salle> readAll() {
        String requete="SELECT * FROM salles";
        List<Salle> list=new ArrayList<>();
        try {
            ste= connexion.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                list.add(new Salle(
                        rs.getInt("id_salle"),
                        rs.getString("nom_salle"),
                        rs.getInt("surface"),
                        rs.getInt("capacite"),
                        rs.getString("discipline")));


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Salle readById(int id) {
        String requete="select * from salle where id="+id;
        Salle salle = null;
        try {
            ste= connexion.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                salle = new Salle(
                        rs.getInt("id"),
                        rs.getString("nom"),
                        rs.getInt("surface"),
                        rs.getInt("capacite"),
                        rs.getString("discipline"));


            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return salle;
    }
    public int NbrDeSalleTotale() {
        String req = "SELECT COUNT(id) AS total From salle";
        try {
            PreparedStatement ste = connexion.prepareStatement(req);
            ResultSet rs = ste.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0; // Return 0 if there's an error or no salles
    }




    public List<Salle> readAllWithJoin() {
        List<Salle> salleList = new ArrayList<>();
        String query = "SELECT salle.*, Dispo.dateD, Dispo.dateF " +
                "FROM salle " +
                "LEFT JOIN Dispo ON salle.id = Dispo.idSalle";

        try (PreparedStatement preparedStatement = connexion.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                // Populate Salle object with data from the result set
                Salle salle = new Salle();
                salle.setId(resultSet.getInt("id"));
                salle.setNom(resultSet.getString("nom"));
                salle.setSurface(resultSet.getInt("surface"));
                salle.setCapacite(resultSet.getInt("capacite"));
                salle.setDiscipline(resultSet.getString("discipline"));

                // Check if Dispo data is present
                Timestamp dateDTimestamp = resultSet.getTimestamp("dateD");
                Timestamp dateFTimestamp = resultSet.getTimestamp("dateF");

                if (dateDTimestamp != null && dateFTimestamp != null) {
                    Dispo dispo = new Dispo();
                    dispo.setDateD(dateDTimestamp.toLocalDateTime());
                    dispo.setDateF(dateFTimestamp.toLocalDateTime());
                    salle.setDispoList(Collections.singletonList(dispo)); // You may want to handle multiple dispo records for a salle
                }

                salleList.add(salle);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the exception appropriately
        }

        return salleList;
    }

    public Salle findbynom_salle(String nomSalle) {
        String requete = "SELECT * FROM salles WHERE nom_salle = ?";
        Salle salle = null;
        try {
            PreparedStatement ps = connexion.prepareStatement(requete);
            ps.setString(1, nomSalle);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                salle = new Salle(
                        rs.getInt("id_salle"),
                        rs.getString("nom_salle"),
                        rs.getInt("surface"),
                        rs.getInt("capacite"),
                        rs.getString("discipline")
                );
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return salle;
    }



//    public int NbrDeSalleDispo() {
//        String req = "SELECT COUNT(id) AS total FROM salle WHERE dispo = true";
//        try {
//            PreparedStatement ste = connexion.prepareStatement(req);
//            ResultSet rs = ste.executeQuery();
//            if (rs.next()) {
//                return rs.getInt("total");
//            }
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//        return 0; // Return 0 if there's an error or no salle with dispo=true
//    }

}