package org.example;

import enities.Product;
import services.ServiceProduct;

public class Main {
   public static void main(String[] args) {
        Product p1 = new Product("rocket", "elon",120);
        ServiceProduct sp = new ServiceProduct();

       //sp.add(p1);
        //sp.readAll().forEach(System.out::println);
        //sp.update(p1);
        //sp.delete(p1);
    }
}
