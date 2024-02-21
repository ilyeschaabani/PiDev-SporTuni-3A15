package org.example;

import service.CourService;
import utils.DataSource;
import entities.cour;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;


public class Main {

    public static void main(String[] args) {
        cour c1 = new cour("cour 1", Date.valueOf("2024-02-13"), "15:30:00", "60", "salle1", "30");
        cour c2 = new cour("cour 2", Date.valueOf("2024-02-16"), "11:30:00", "30", "salle5", "20");

        CourService cs=new CourService();
        cs.add(c2);
        //cs.delete(5);
        cs.readAll().forEach(System.out::println);
    }
}
