package Utiils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DataSource {

    private  String url ="jdbc:mysql://localhost:3306/sportuni";
    private  String login="root";
    private  String pwd="";
    private static Connection cnx;
    private static DataSource instance;

    private DataSource(){
        try {
            cnx= DriverManager.getConnection(url,login,pwd);
            System.out.println("success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static DataSource getInstance(){
        if(instance==null)
            instance=new DataSource();
        return instance;
    }

    public static Connection getCnx() {
        return cnx;
    }
}
