package service;

import entities.cour;
import utils.DataSource;
import entities.discipline;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public  class DisciplineService implements IService<discipline>{
    private Connection conn;
    private Statement ste;

    private PreparedStatement pst;
    public DisciplineService() {
        conn= DataSource.getInstance().getCnx();
    }

    @Override
    public void add(discipline d) {
        String requete="insert into discipline (nom_discipline, description) values ('"+d.getNom_discipline()+"','"+d.getDescription()+"')";
        try {
            ste=conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(int id) {
        String requete="delete from discipline where id_discipline="+id;
        try {
            ste=conn.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(discipline d) {
        String requete="update discipline set nom_discipline=?,description=? where id_discipline=?";
        try {
            pst=conn.prepareStatement(requete);
            pst.setString(1, d.getDescription());
            pst.setString(2,d.getNom_discipline());

            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<discipline> readAll() {
        String requete="select * from cour";
        List<discipline> list=new ArrayList<>();
        try {
            ste= conn.createStatement();
            ResultSet rs =ste.executeQuery(requete);
            while (rs.next()){
                list.add(new discipline(rs.getString("nom_discipline") ,rs.getString("description")));
            }
            //pst.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public discipline readById(int id) {
        String requete="select * from discipline where id_discipline="+id;
        discipline d = new discipline();
        try {
            ste= conn.createStatement();
            ResultSet rs =ste.executeQuery(requete);
            while (rs.next()){
                d.setNom_discipline(rs.getString("nom_discipline"));
                d.setDescription(rs.getString("description"));

            }
            //pst.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return d;

    }
}

