package services;

import services.Gservice;
import utils.DataSource;
import entities.Evenement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;

public class Uservice implements Gservice<Evenement> {
    private Connection connexion = DataSource.getInstance().getCnx();
    private Statement ste;
    private PreparedStatement pst;

    public Uservice() {
    }

    @Override
    public void add(Evenement evenement) {
        String requete = "insert into evenement (nom_e,nom_salle,date_e,nom_discipline,description,Type) values ('" + evenement.getNom_e() + "','" + evenement.getNom_salle() + "','" + Evenement.getDate_e() + "','" + evenement.getDescription() + "','" + Evenement.getType() + "')";
        try {
            ste = connexion.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void delete(Evenement evenement) {
        try {
            String requete = "delete from evenement where id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, evenement.getId_e());
            pst.executeUpdate();
            System.out.println("Suppression avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Override
    public void update(Evenement evenement) {
        try {
            String requete = "update evenement set nom_e=?,nom_salle=?,date_e=?,nom_discipline=?,type=? where id_e=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setString(1, evenement.getNom_e());
            pst.setString(2, evenement.getNom_salle());
            pst.setDate(3, evenement.getDate_e());
            pst.setString(4, evenement.getNom_discipline());
            pst.SetType(5, evenement.getType());
            pst.setInt(6, evenement.getId_e());
            pst.executeUpdate();
            System.out.println("Mise a jour avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }


    @Override
    public List<Evenement> readAll() {


        String requete = "select * from evenement";
        List<Evenement> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Evenement(
                        rs.getInt(1),
                        rs.getString("nom_e"),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getType(7)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    @Override
    public Evenement readById(int id_e) {
        String requete = "select * from evenement where id_e=" + id_e;
        Evenement evenement = null;
        try {
            ste = connexion.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                evenement = new Evenement(
                        rs.getInt(1),
                        rs.getString("nom_e"),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getType(7));
            }
            } catch(SQLException e){
                throw new RuntimeException(e);
            }
            return evenement;
        }
    }
}