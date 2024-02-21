package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private static String url ="jdbc:mysql://localhost:3306/pidev";
    private static String login ="root";
    private static String pwd ="";
    private static Connection cnx;
    private static DataSource instance;

    private DataSource() {
        try {
            cnx = DriverManager.getConnection(url, login, pwd);
            System.out.println("Connection established successfully");
        } catch (SQLException e) {
            System.err.println("Error establishing connection: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static DataSource getInstance() {
        if (instance == null) {
            instance = new DataSource();
        }
        return instance;
    }

    public static Connection getCnx() {
        return cnx;
    }
}
