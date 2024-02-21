package org.example;

import entities.Competition;

import service.CompetitionService;
import utils.DataSource;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {



    public static void main(String[] args) {

        DataSource ds1=DataSource.getInstance();

        Competition c1=new Competition("Competition2024", "Bizert",Date.valueOf("2024-02-13"),"gymnastique",1) ;
        Competition c2=new Competition("Competition2023", "Menzah",Date.valueOf("2023-04-13"),"gymnastique",1) ;
        Competition c3=new Competition("Competition2022", "Nabeul",Date.valueOf("2022-06-13"),"gymnastique",1) ;
        Competition c4=new Competition("Competition2025", "sousse",Date.valueOf("2025-01-13"),"gymnastique",1) ;

        CompetitionService cs= new CompetitionService();

        //cs.add(c1);
        //cs.add(c2);
        //cs.add(c3);
        //cs.add(c4);
        //cs.delete(4);
        //cs.update(c1 , 2); //pas de modification dans la BD

        //cs.readAll().forEach(System.out::println);
        //System.out.println(cs.readById(3));

        System.out.println("Hello world!");
    }




}