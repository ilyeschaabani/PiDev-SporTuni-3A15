package org.example;

import entities.Evenement;
import services.Gservice;
import services.Uservice;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

    public class Main {
        public static void main(String[] args) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // Format de date
            Date date_e;
            Uservice usr = null;
            try {
                date_e = sdf.parse("01/01/2024");
                Evenement ev = new Evenement(4, "les champions ", "complexe ElMenzah" ,date_e,"Karate", "stage des adhérants");
                usr = new Uservice();
                usr.add(ev);
            } catch (ParseException e) {
                e.printStackTrace();

                //usr.add(ev);
                //usr.readAll().forEach(System.out::println);
                //usr.update(ev);
                //usr.delete(ev);
                usr.readAll().forEach(System.out::println);

            }
        }
    }





