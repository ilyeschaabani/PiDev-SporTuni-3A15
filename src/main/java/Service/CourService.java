package Service;

import Entity.Cour;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourService implements ICourService<Cour> {
    private Connection conn;

    private Statement ste;
    private static CourService instance;

    private PreparedStatement pst;

    public CourService() {
        conn = Utiils.DataSource.getInstance().getCnx();
    }



    @Override
    public void add(Cour c) {
        String requete = "INSERT INTO cour (nom_cour, date, heure_debut, heure_fin, nom_salle, nb_max, nom_discipline) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try {
            pst = conn.prepareStatement(requete);
            pst.setString(1, c.getNom_cour());
            pst.setDate(2, c.getDate());
            pst.setString(3, c.getHeure_debut());
            pst.setString(4, c.getHeure_fin());
            pst.setString(5, c.getNom_salle());
            pst.setString(6, c.getNb_max());

            pst.setString(7, c.getNom_discipline());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String requete = "DELETE FROM cour WHERE id_cour=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void update(Cour c, int id) {}

    public void updato(Cour cour) throws SQLException {
        String query = "UPDATE cour SET nom_cour=?, nom_discipline=?, date=?, heure_debut=?, heure_fin=?, nom_salle=?, nb_max=? WHERE id_cour=?";
        PreparedStatement pst = null;
        try {
            pst = conn.prepareStatement(query);
            pst.setString(1, cour.getNom_cour());
            pst.setString(2, cour.getNom_discipline());
            pst.setDate(3, cour.getDate());
            pst.setString(4, cour.getHeure_debut());
            pst.setString(5, cour.getHeure_fin());
            pst.setString(6, cour.getNom_salle());
            pst.setString(7, cour.getNb_max());
            pst.setInt(8, cour.getId_cour());
            pst.executeUpdate();
        } finally {
            if (pst != null) {
                pst.close();
            }
        }
    }


    @Override
    public List<Cour> readAll() {
        String requete = "SELECT * FROM cour";
        List<Cour> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                Cour c = new Cour(rs.getInt("id_cour"), rs.getString("nom_cour"), rs.getDate("date"), rs.getString("heure_debut"), rs.getString("heure_fin"), rs.getString("nom_salle"), rs.getString("nb_max"), null);
                c.setId_cour(rs.getInt("id_cour"));
                c.setNom_cour(rs.getString("nom_cour"));
                c.setDate(rs.getDate("date"));
                c.setHeure_debut(rs.getString("heure_debut"));
                c.setHeure_fin(rs.getString("heure_fin"));
                c.setNom_salle(rs.getString("nom_salle"));
                c.setNb_max(rs.getString("nb_max"));
                c.setNom_discipline(rs.getString("nom_discipline"));
                list.add(c);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Cour readById(int id) {
        String requete = "SELECT * FROM cour WHERE id_cour=?";
        Cour c = null;
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                c = new Cour(
                        rs.getInt("id_cour"),
                        rs.getString("nom_cour"),
                        rs.getDate("date"),
                        rs.getString("heure_debut"),
                        rs.getString("heure_fin"),
                        rs.getString("nom_salle"),
                        rs.getString("nb_max"),
                        null); // Vous devez définir la discipline ici, mais cela dépend de votre logique métier.
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return c;
    }


    public int getNombreTotalCours() {
        int totalCours = 0;
        String query = "SELECT COUNT(*) AS total_cours FROM cour"; // Il faut utiliser le nom de la table en minuscules, car les noms de table sont généralement sensibles à la casse dans les bases de données.

        try {
            pst = conn.prepareStatement(query);
            ResultSet resultSet = pst.executeQuery();

            if (resultSet.next()) {
                totalCours = resultSet.getInt("total_cours");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return totalCours;
    }
    public static CourService getInstance() {
        if (instance == null) {
            instance = new CourService();
        }
        return instance;}


    public void updateRating(int courId, double newRating) {
        String query = "UPDATE Cour SET avis = ? WHERE id_cour = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setDouble(1, newRating);
            preparedStatement.setInt(2, courId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public double getAvis(int courId) {
        String query = "SELECT avis FROM cour WHERE id_cour = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, courId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                return rs.getDouble("avis");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return 0;
    }
}
