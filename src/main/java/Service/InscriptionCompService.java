package Service;

import Service.ICompetitonService;
import Entity.InscriptionComp;
import Utiils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InscriptionCompService implements ICompetitonService<InscriptionComp> {

    private final Connection conn;
    private PreparedStatement pst;
    private Statement ste;

    public InscriptionCompService() {
        conn = DataSource.getInstance().getCnx();
    }


    @Override
    public void add(InscriptionComp ins) {
        String requete = "insert into inscriptioncomp (id_comp, nom, prenom, age, poids,num_tel,image) values ('" + ins.getId_comp() + "','" + ins.getNom() + "','" + ins.getPrenom() + "','" + ins.getAge() + "','" + ins.getPoids() + "','" + ins.getNum_tel() + "','" + ins.getImage() + "')";
        try {
            ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public void delete(int id) {
        String requete = "delete from inscriptioncomp where num_inscri=" + id;
        //System.out.println("dinaaaaa");
        try {
            ste = conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(InscriptionComp ins) {
        String requete = "UPDATE inscriptioncomp SET id_comp=?,nom=?, prenom=?, age=?, poids=?, num_tel=?  WHERE num_inscri=?";
        try {
            pst = conn.prepareStatement(requete);
            pst.setInt(1, ins.getId_comp());
            pst.setString(2, ins.getNom());
            pst.setString(3, ins.getPrenom());
            pst.setInt(4, ins.getAge());
            pst.setFloat(5, ins.getPoids());
            pst.setInt(6, ins.getNum_tel());

            pst.setInt(7, ins.getNum_inscri()); // Utilisation de l'ID of competitor pour identifier l'entrée à mettre à jour
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<InscriptionComp> readAll() {
        String requete = "select * from inscriptioncomp";
        List<InscriptionComp> list = new ArrayList<>();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new InscriptionComp(rs.getInt("num_inscri"), rs.getString("nom"), rs.getString("prenom"), rs.getInt("age"), rs.getFloat("poids"), rs.getString("image"), rs.getInt("num_tel"), rs.getInt("id_comp")));
            }
            //pst.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public InscriptionComp readById(int id) {
        String requete = "select * from inscriptioncomp where num_inscri=" + id;
        InscriptionComp c = new InscriptionComp();
        try {
            ste = conn.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                c.setNum_inscri(rs.getInt("num_inscri"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setAge(rs.getInt("age"));
                c.setPoids(rs.getFloat("poids"));
                c.setId_comp(rs.getInt("id_comp"));
            }
            //pst.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return c;

    }


    public List<InscriptionComp> readByCompId(int id_comp) {
        String requete = "SELECT * FROM inscriptioncomp WHERE id_comp = ?";
        List<InscriptionComp> result = new ArrayList<>();
        try {
            PreparedStatement pst = conn.prepareStatement(requete);
            pst.setInt(1, id_comp);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                InscriptionComp c = new InscriptionComp();
                c.setNum_inscri(rs.getInt("num_inscri"));
                c.setNom(rs.getString("nom"));
                c.setPrenom(rs.getString("prenom"));
                c.setAge(rs.getInt("age"));
                c.setPoids(rs.getFloat("poids"));
                c.setId_comp(rs.getInt("id_comp"));
                c.setNum_tel(rs.getInt("num_tel")); // Ajout de la récupération du numéro de téléphone
                result.add(c);
            }
            pst.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return result;
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
