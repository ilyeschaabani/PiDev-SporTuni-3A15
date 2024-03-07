package Service;

import Entity.Competition;
import Service.ICompetitonService;
import Utiils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompetitionService implements ICompetitonService<Competition> {


    private final Connection conn;
    private PreparedStatement pst;
    private Statement ste;

    public CompetitionService() {
        conn = DataSource.getInstance().getCnx();
    }


    public void add(Competition c) {
        String requete = "insert into competition (nom_comp, lieu, date, discipline, id_salle) values ('" + c.getNom_comp() + "','" + c.getLieu_comp() + "','" + c.getDate() + "','" + c.getDiscipline() + "','" + c.getId_salle() + "')";
        try {
            ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void delete(int id) {
        String requete = "delete from competition where id_comp=" + id;
        System.out.println("dinaaaaa");
        try {
            ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public void update(Competition competition) {
        String requete = "UPDATE competition SET nom_comp=?, lieu=?, date=?, discipline=?, id_salle=? WHERE id_comp=?";
        try {
            pst = conn.prepareStatement(requete);

            pst.setString(1, competition.getNom_comp());
            pst.setString(2, competition.getLieu_comp());
            pst.setDate(3, competition.getDate());
            pst.setString(4, competition.getDiscipline());
            pst.setInt(5, competition.getId_salle());
            pst.setInt(6, competition.getId_comp()); // Utilisation de l'ID de la compétition pour identifier l'entrée à mettre à jour
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
    public List<Competition> readAll() {
        String requete = "select * from competition";
        List<Competition> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Competition(rs.getInt("id_comp"), rs.getString("nom_comp"), rs.getString("lieu"), rs.getDate("date"), rs.getString("discipline"), rs.getInt("id_salle")));
            }
            //pst.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }


    public List<Competition> readAllSortedByDate() {
        String requete = "SELECT * FROM competition ORDER BY date";
        List<Competition> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Competition(rs.getInt("id_comp"), rs.getString("nom_comp"), rs.getString("lieu"), rs.getDate("date"), rs.getString("discipline"), rs.getInt("id_salle")));
            }
            //pst.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Competition readById(int id) {
        String requete = "select * from competition where id_comp=" + id;
        Competition c = new Competition();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                c.setId_comp(rs.getInt("id_comp"));
                c.setNom_comp(rs.getString("nom_comp"));
                c.setLieu_comp(rs.getString("lieu"));
                c.setDate(rs.getDate("date"));
                c.setDiscipline(rs.getString("discipline"));
                c.setId_salle(rs.getInt("id_salle"));
            }
            //pst.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return c;

    }


    // Méthode pour récupérer les ID de salle disponibles depuis la base de données
    public List<Integer> getAvailableSalleIdsFromDatabase() {
        List<Integer> availableIds = new ArrayList<>();
        String query = "SELECT id FROM salle";
        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                availableIds.add(rs.getInt("id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des ID de salle depuis la base de données", e);
        }
        return availableIds;
    }


    // Méthode pour récupérer les ID de salle disponibles depuis la base de données
    public List<Integer> getAvailableCOMPETITIONIdsFromDatabase() {
        List<Integer> availableIds = new ArrayList<>();
        String query = "SELECT id_comp FROM competition";
        try (PreparedStatement pst = conn.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                availableIds.add(rs.getInt("id_comp"));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erreur lors de la récupération des ID de salle depuis la base de données", e);
        }
        return availableIds;
    }
}



