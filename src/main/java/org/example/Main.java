package org.example;

import entity.Cour;
import service.CourService;
import service.DisciplineService;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        CourService cs = new CourService();

        DisciplineService ss=new DisciplineService();
        //ss.add(s1);
        //ss.add(s2);
        //ss.add(s4);
      //  ss.readAll().forEach(System.out::println);
       // ss.update(s5,2);
        //ss.delete(s4);
     //   ss.readAll().forEach(System.out::println);
        //System.out.println(ss.readById(11));
       // Cour c1 = new Cour("cour 1", Date.valueOf("2024-02-13"), "15:30:00", "16:30:00", "salle1", "30","box");
        //cs.add(c1);
    }
}