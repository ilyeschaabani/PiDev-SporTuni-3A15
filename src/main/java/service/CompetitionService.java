
package service;

import entities.Competition;
import utils.DataSource;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class CompetitionService implements IService<Competition>{




        private Connection conn ;
        private PreparedStatement pst;
        private Statement ste ;

        public CompetitionService() {

            conn= DataSource.getCnx();
        }


        public void add(Competition c){
            String requete = "insert into competition (nom_comp, lieu, date, discipline, id_salle) values ('" + c.getNom_comp() + "','" + c.getLieu_comp() + "','" + c.getDate() + "','" + c.getDiscipline() + "','" + c.getId_salle() + "')";
            try {
                ste=conn.createStatement();
                ste.executeUpdate(requete);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        }

        @Override
        public void delete(int id ) {
            String requete="delete from competition where id_comp="+id;
            try {
                ste=conn.createStatement();
                ste.executeUpdate(requete);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



    @Override
    public void update(Competition competition, int id_com) {
        String requete = "UPDATE competition SET nom_comp=?, lieu=?, date=?, discipline=?, id_salle=? WHERE id_comp=?";
        try {
            pst = conn.prepareStatement(requete);

            pst.setString(1, competition.getNom_comp());
            pst.setString(2, competition.getLieu_comp());
            pst.setDate(3, competition.getDate());
            pst.setString(4, competition.getDiscipline());
            pst.setInt(5, competition.getId_salle());
            pst.setInt(6, id_com); // Utilisation de l'ID de la compétition pour identifier l'entrée à mettre à jour
            pst.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @Override
        public List<Competition> readAll() {
            String requete="select * from competition";
            List<Competition> list=new ArrayList<>();
            try {
                ste= conn.createStatement();
                ResultSet rs =ste.executeQuery(requete);
                while (rs.next()){
                    list.add(new Competition(rs.getInt("id_comp"),rs.getString("nom_comp") ,rs.getString("lieu"),rs.getDate("date"), rs.getString("discipline"),rs.getInt("id_salle")));
                }
                //pst.close();

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return list;
        }

        @Override
        public Competition readById(int id) {
            String requete="select * from competition where id_comp="+id;
            Competition c = new Competition();
            try {
                ste= conn.createStatement();
                ResultSet rs =ste.executeQuery(requete);
                while (rs.next()){
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

    }



