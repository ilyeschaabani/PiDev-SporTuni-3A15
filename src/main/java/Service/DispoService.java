package Service;

import Entity.Salle;
import Utiils.DataSource;
import Entity.Dispo;

import java.awt.*;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DispoService implements service.ISalleService<Dispo> {
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
            preparedStatement.setObject(1, dispo.getSalle().getId());
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
            ps.setObject(1, dispo.getSalle());
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
                        (Salle) rs.getObject("salle"),
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
                        (Salle) rs.getObject("salle"),
                        rs.getTimestamp("date_debut").toLocalDateTime(),
                        rs.getTimestamp("date_fin").toLocalDateTime());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dispo;
    }
    public void deleteExpiredDispos() {
        String deleteQuery = "DELETE FROM dispo WHERE dateF < ?";

        try {
            PreparedStatement preparedStatement = connexion.prepareStatement(deleteQuery);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(LocalDateTime.now()));
            int rowsAffected = preparedStatement.executeUpdate();

            System.out.println(rowsAffected + " expired dispos deleted");
            if (rowsAffected > 0) {
                showNotification1(rowsAffected + " salle(s) disponible(s)");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void showNotification1(String message) {
        if (SystemTray.isSupported()) {
            try {
                SystemTray tray = SystemTray.getSystemTray();
                Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/Images/notif.png"));

                TrayIcon trayIcon = new TrayIcon(icon, "Notification");
                trayIcon.setImageAutoSize(true);

                trayIcon.addActionListener(e -> {
                    // Handle the tray icon click event if needed
                });

                tray.add(trayIcon);
                trayIcon.displayMessage("Notification", message, TrayIcon.MessageType.INFO);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }
}

