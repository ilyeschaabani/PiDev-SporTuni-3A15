package service;

import controllers.Cour;
import utils.DataSource;
import entities.cour;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class CourService implements IService<cour>{

    private Connection conn;
    private Statement ste;

    private PreparedStatement pst;
    public CourService() {
        conn= DataSource.getInstance().getCnx();
    }


    public static void ajout(Cour cour) {

    }


    public void add(cour c){
        String requete="insert into cour (nom_cour,date,heure,duree,nom_salle,nb_max) values ('"+c.getNom_cour()+"','"+c.getDate()+"','"+c.getHeure()+"','"+c.getDuree()+"','"+c.getNom_salle()+"','"+c.getNb_max()+"')";
        try {
            ste=conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String requete="delete from cour where id_cour="+id;
        try {
            ste=conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public void update(cour cour) {
        String requete="update cour set nom_cour=?,date=?,heure=?,duree=?,nom_salle=?,nb_max=? where id_cour=?";
        try {
            pst=conn.prepareStatement(requete);
            pst.setString(1,cour.getNom_cour());
            pst.setDate(2,cour.getDate());
            pst.setString(3,cour.getHeure());
            pst.setString(4,cour.getDuree());
            pst.setString(5,cour.getNom_salle());
            pst.setString(6,cour.getNb_max());
            pst.setInt(7,cour.getId_cour());
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<cour> readAll() {
        String requete="select * from cour";
        List<cour> list=new ArrayList<>();
        try {
            ste= conn.createStatement();
            ResultSet rs =ste.executeQuery(requete);
            while (rs.next()){
                list.add(new cour(rs.getString("nom_cour") ,rs.getDate("date"),rs.getString("heure"), rs.getString("duree"),rs.getString("nom_salle"), rs.getString("nb_max")));
            }
            //pst.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public cour readById(int id) {
        String requete="select * from cour where id_cour="+id;
        cour c = new cour();
        try {
            ste= conn.createStatement();
            ResultSet rs =ste.executeQuery(requete);
            while (rs.next()){
                c.setNom_cour(rs.getString("nom_cour"));
                c.setDate(rs.getDate("date"));
                c.setHeure(rs.getString("heure"));
                c.setDuree(rs.getString("duree"));
                c.setNom_salle(rs.getString("nom_salle"));
                c.setNb_max(rs.getString("nb_max"));
            }
            //pst.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return c;

    }
}
