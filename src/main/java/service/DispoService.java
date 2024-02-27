package service;

import entity.Salle;
import util.DataSource;
import entity.Dispo;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DispoService implements IService<Dispo> {
    private Connection connexion;
    private Statement ste;

    public DispoService() {
        connexion = DataSource.getInstance().getCnx();
    }

    @Override
    public void add(Dispo dispo) {
        String requete = "INSERT INTO dispo (id_salle, dateD, dateF) VALUES (?, ?, ?)";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(requete);
            preparedStatement.setInt(1, dispo.getId_salle());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(dispo.getDateD()));
            preparedStatement.setTimestamp(3, Timestamp.valueOf(dispo.getDateF()));
             preparedStatement.executeUpdate();
            System.out.println("Dispo added ");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String requete = "DELETE FROM dispo WHERE id_Dispo = ?";
        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(requete);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            System.out.println("disponibilte deleted");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Dispo dispo, int id) {
    }

    @Override
    public void updato(Dispo dispo) {
        String requete = "UPDATE dispo SET idSalle=?, dateD = ?, dateF = ? WHERE idDispo = ?";
        try {
            PreparedStatement ps = connexion.prepareStatement(requete);
            ps.setInt(1, dispo.getId_salle());
            ps.setTimestamp(2, Timestamp.valueOf(dispo.getDateD()));
            ps.setTimestamp(3, Timestamp.valueOf(dispo.getDateF()));
            ps.setInt(4,dispo.getId_Dispo());
            ps.executeUpdate();
            System.out.println("Disponibilté updated!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<Dispo> readAll() {
        String requete = "SELECT * FROM dispo";
        List<Dispo> list = new ArrayList<>();
        try {
            Statement ste = connexion.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new Dispo(
                        rs.getInt("id_dispo"),
                        rs.getInt("id_salle"),
                        rs.getTimestamp("date_debut").toLocalDateTime(),
                        rs.getTimestamp("date_fin").toLocalDateTime()
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Dispo readById(int id) {
        String requete="select * from dispo where idDispo="+id;
        Dispo dispo = null;
        try {
            ste= connexion.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                dispo = new Dispo(
                        rs.getInt("id dispo"),
                        rs.getInt("id salle"),
                        rs.getTimestamp("date_debut").toLocalDateTime(),
                        rs.getTimestamp("date_fin").toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dispo;
    }
}
