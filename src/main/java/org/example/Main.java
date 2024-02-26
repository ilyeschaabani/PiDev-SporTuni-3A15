package org.example;

import entities.Evenement;
import services.Gservice;
import services.EvenementService;
import java.sql.Connection;

import java.util.List;
import utils.DataSource;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.text.ParseException;
import java.sql.SQLException;
import java.sql.Statement;


// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

    public class Main {

        public static void main(String[] args) {
            DataSource ds1= DataSource.getInstance();
            System.out.println(ds1);

            EvenementService es = new EvenementService();

            // Exemple de dates sous forme de String
            String dateDebutString = "2023-01-01";
            String dateFinString = "2023-01-02";

            // Conversion des dates String en java.sql.Date
            Date dateDebutSQL = Date.valueOf(dateDebutString);
            Date dateFinSQL = Date.valueOf(dateFinString);

            Evenement ev = new Evenement(4, "Stage ", "complexe ElMenzah", dateDebutSQL, "Karate", "stage régional", 4, 2000, dateFinSQL);

            es.add(ev);



                //es.readAll().forEach(System.out::println);
                //es.update(ev);
                //es.delete(ev);
                es.readAll( ).forEach(System.out::println);

        }
    }





