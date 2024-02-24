package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;




public class DataSource {
    private String url = "jdbc:mysql://localhost:3306/sportuni";
    private String login = "root";
    private String pwd = "";
    private Connection cnx;
    private static utils.DataSource instance;

    private DataSource() {
        try {
            this.cnx = DriverManager.getConnection(this.url, this.login, this.pwd);
            System.out.println("success");
        } catch (SQLException var2) {
            throw new RuntimeException(var2);
        }
    }

    public static utils.DataSource getInstance() {
        if (instance == null) {
            instance = new utils.DataSource();
        }

        return instance;
    }

    public Connection getCnx() {
        return this.cnx;
    }
}


