package org.example;

import enities.Product;
import services.ServiceProduct;

public class Main {
    public static void main(String[] args) {
        Product p1 = new Product("Ballo", "Foot");
        ServiceProduct sp = new ServiceProduct();

        // Uncomment and use the appropriate methods as needed
        sp.add(p1);
        // sp.readAll().forEach(System.out::println);
        // sp.update(p1);
        // sp.delete(p1);
    }
}
