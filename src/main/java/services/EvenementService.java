package services;

import javafx.scene.control.TextField;
import utils.DataSource;
import entities.Evenement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.control.TextField;



public class EvenementService implements Gservice<Evenement> {


    private static EvenementService instance;
    private  Connection connexion ;



    private static Statement ste;
    private PreparedStatement pst;
    private String nomSalle;


    public EvenementService() {
        connexion = DataSource.getInstance().getCnx();
    }

    public void add(Evenement evenement) {




        String requete ="insert into evenement (dateDebut,dateFin,description,nbr_max,nom_discipline,nom_e,nom_salle) values ('"+ evenement.getDateDebut() + "','"+ evenement.getDateFin() + "','" + evenement.getDescription() + "','" + evenement.getNbr_max()+ "','" + evenement.getNom_discipline() +  "', '" + evenement.getNom_e()+ "', '" + evenement.getNom_salle() + "')";
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
            String requete = "update evenement set nom_e=?,nom_salle=?,dateDebut=?,nom_discipline=?,dateFin=?  where id_e=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setString(1, evenement.getNom_e());
            pst.setString(2, evenement.getNom_salle());
            pst.setDate(3, evenement.getDateDebut());
            pst.setString(4, evenement.getNom_discipline());
            pst.setDate(5, evenement.getDateFin());
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
            ResultSet rs;
            rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Evenement(
                        rs.getInt(1),
                        rs.getString("nom_e"),
                        rs.getString(2),
                        rs.getDate(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getDate(8)));
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }


    @Override
    public Evenement readById(int id) {
        String requete = "select * from evenement where id=" + id;
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
                        rs.getInt(7),
                rs.getDate(8));
            }
            } catch(SQLException e){
                throw new RuntimeException(e);
            }
            return evenement;
        }







}
