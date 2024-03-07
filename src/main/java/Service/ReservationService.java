package Service;

import Entity.ReservationEv;
import Utiils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationService implements Gservice<ReservationEv>{
    private static ReservationService instance;
    private Connection connexion;

    private static Statement ste;
    private PreparedStatement pst;

    public ReservationService() {
        connexion = DataSource.getInstance().getCnx();
    }

    public void add(ReservationEv reservation) {
        String requete = "INSERT INTO reservation (dateR, id_u, nbr_max, id_e) VALUES (?, ?, ?, ?)";
        try {
            pst = connexion.prepareStatement(requete);
            pst.setDate(1, reservation.getDateR());
            pst.setInt(2, reservation.getId_u());
            pst.setInt(3, reservation.getNbr_max()); // Assuming this is the foreign key ID
            pst.setInt(4, reservation.getId_e()); // Assuming this is the foreign key ID
            pst.executeUpdate();
            System.out.println("Reservation added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(ReservationEv reservation) {
        try {
            String requete = "DELETE FROM reservation WHERE idR=?";
            pst = connexion.prepareStatement(requete);
            pst.setInt(1, reservation.getIdR());
            pst.executeUpdate();
            System.out.println("Reservation deleted successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(ReservationEv reservation) {
        try {
            String requete = "UPDATE reservation SET dateR=?, id_u=?, nbr_max=?, id_e=? WHERE idR=?";
            pst = connexion.prepareStatement(requete);
            pst.setDate(1, reservation.getDateR());
            pst.setInt(2, reservation.getId_u());
            pst.setInt(3, reservation.getNbr_max()); // Assuming this is the foreign key ID
            pst.setInt(4, reservation.getId_e()); // Assuming this is the foreign key ID
            pst.setInt(5, reservation.getIdR());
            pst.executeUpdate();
            System.out.println("Reservation updated successfully");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public List<ReservationEv> readAll() {
        String requete = "SELECT * FROM reservation";
        List<ReservationEv> list = new ArrayList<>();
        try {
            ste = connexion.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                ReservationEv reservation = new ReservationEv();
                reservation.setIdR(rs.getInt("idR"));
                reservation.setDateR(rs.getDate("dateR"));
                reservation.setId_u(rs.getInt("id_u"));
                reservation.setNbr_max(rs.getInt("nbr_max"));
                reservation.setId_e(rs.getInt("id_e"));
                list.add(reservation);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public ReservationEv readById(int id) {
        String requete = "SELECT * FROM reservation WHERE idR=?";
        ReservationEv reservation = null;
        try {
            pst = connexion.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                reservation = new ReservationEv();
                reservation.setIdR(rs.getInt("idR"));
                reservation.setDateR(rs.getDate("dateR"));
                reservation.setId_u(rs.getInt("id_u"));
                // Assuming nbr_max and id_e are foreign keys to Evenement
                EvenementService evenementService = new EvenementService(); // You may want to use a singleton pattern here
                reservation.setNbr_max(rs.getInt("nbr_max"));
                reservation.setId_e(rs.getInt("id_e"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return reservation;
    }

    public List<ReservationEv> getAllReservations() {
        List<ReservationEv> reservations = new ArrayList<>();
        reservations = readAll(); // Reusing readAll method for simplicity
        return reservations;
    }
}
