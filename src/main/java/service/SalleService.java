package service;

import entity.Dispo;
import util.DataSource;
import entity.Salle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalleService implements IService<Salle> {
    private Connection connexion;
    public SalleService(){
        connexion= DataSource.getInstance().getCnx();
    }
    private Statement ste ;//yebath lel sql heya interface
    public void add(Salle s) {
        String requete = "INSERT INTO salle (nom,surface, capacite, discipline) VALUES (?, ?, ?, ?,?)";
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

    @Override
    public void update(Salle salle, int id) {

    }

    /* public void update(Salle s ,int id  ) {
         String requete = "UPDATE salle SET nom=?, surface = ?, capacite = ?, discipline = ?, dispo = ? WHERE id = ?";
         try {
             PreparedStatement preparedStatement = connexion.prepareStatement(requete);
             preparedStatement.setString(1, s.getNom());
             preparedStatement.setInt(2, s.getSurface());
             preparedStatement.setInt(3, s.getCapacite());
             preparedStatement.setString(4, s.getDiscipline());
             preparedStatement.setBoolean(5, s.getDispo());
             preparedStatement.setInt(6,id);
             preparedStatement.executeUpdate();
         } catch (SQLException e) {
             throw new RuntimeException(e);
         }

     }*/
    public void updato(Salle s){


        String requete = "UPDATE salle SET nom=?, surface = ?, capacite = ?, discipline = ? = ? WHERE id = ?";
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
    public List<Salle> readAllD() {
        String salleRequete = "SELECT * FROM salle";
        String dispoRequete = "SELECT * FROM dispo WHERE id_salle = ?";

        List<Salle> list = new ArrayList<>();

        try {
            ste = connexion.createStatement();
            ResultSet rsSalle = ste.executeQuery(salleRequete);
            while (rsSalle.next()) {
                Salle salle = new Salle(
                        rsSalle.getInt("id"),
                        rsSalle.getString("nom"),
                        rsSalle.getInt("surface"),
                        rsSalle.getInt("capacite"),
                        rsSalle.getString("discipline")
                );

                // Fetch dispo data for the current salle
                PreparedStatement preparedStatement = connexion.prepareStatement(dispoRequete);
                preparedStatement.setInt(1, salle.getId());
                ResultSet rsDispo = preparedStatement.executeQuery();
                while (rsDispo.next()) {
                    Dispo dispo = new Dispo(
                            rsDispo.getInt("id_dispo"),
                            rsDispo.getInt("id_salle"),
                            rsDispo.getTimestamp("date_debut").toLocalDateTime(),
                            rsDispo.getTimestamp("date_fin").toLocalDateTime(),
                            rsDispo.getBoolean("dispo")
                    );
                    salle.getDispoList().add(dispo);
                }

                list.add(salle);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<Salle> readAll() {
        String requete="SELECT * FROM salle";
        List<Salle> list=new ArrayList<>();
        try {
            ste= connexion.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                  list.add(new Salle(
                          rs.getInt("id"),
                        rs.getString("nom"),
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
    public int NbrDeSalleDispo() {
        String req = "SELECT COUNT(id) AS total FROM salle WHERE dispo = true";
        try {
            PreparedStatement ste = connexion.prepareStatement(req);
            ResultSet rs = ste.executeQuery();
            if (rs.next()) {
                return rs.getInt("total");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0; // Return 0 if there's an error or no salle with dispo=true
    }

}