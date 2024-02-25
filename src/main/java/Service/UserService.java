package service;

import Entity.User;
import Service.IService;
import utils.DataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class UserService implements IService<User> {
    private Connection connexion;
    private Statement ste;
    private PreparedStatement pst;
    public UserService() {
        connexion= DataSource.getInstance().getCnx();
    }
    @Override
    public void add(User user) {
        String requete="insert into user (nom,prenom,email,mdp,role) values ('"+user.getNom()+"','"+user.getPrenom()+"','"+user.getEmail()+"','"+user.getPassword()+"','"+user.getRole()+"')";
        try {
            ste=connexion.createStatement();
            ste.executeUpdate(requete);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // trie pa id
    public List<User> trier() {
        List<User> list = new ArrayList<>();
        try {
            String requete = "select * from user order by id";
            ste = connexion.createStatement();
            ResultSet rs = ste.executeQuery(requete);
            while (rs.next()) {
                list.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
    // delete user with id

    public void delete(int id) {
        try {
            String requete = "delete from user where id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, id);
            pst.executeUpdate();
            System.out.println("Suppression avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        try {
            String requete = "delete from user where id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setInt(1, user.getId_user());
            pst.executeUpdate();
            System.out.println("Suppression avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Override
    public void update(User user) {
        try {
            String requete = "update user set nom=?,prenom=?,email=?,password=?,role=?,numero =?,adress =?  where id=?";
            PreparedStatement pst = connexion.prepareStatement(requete);
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getPassword());
            pst.setString(5, user.getRole());
            pst.setInt(6, user.getNumero());
            pst.setString(7, user.getAdresse());
            pst.setInt(6, user.getId_user());
            pst.executeUpdate();
            System.out.println("Mise a jour avec succes");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    @Override
    public List<User> readAll() {
        String requete="select * from user";
        List<User> list=new ArrayList<>();
        try {
            ste= connexion.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                list.add(new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7),
                        rs.getString(8)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public User readById(int id) {
        String requete="select * from user where id="+id;
        User user = null;
        try {
            ste= connexion.createStatement();
            ResultSet rs= ste.executeQuery(requete);
            while(rs.next()){
                user = new User(
                        rs.getInt(1),
                        rs.getString("nom"),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getString(7));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }
}
