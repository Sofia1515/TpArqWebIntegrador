package org.example;

import org.example.factory.Factory;
import org.example.utils.Helper;

public class Main {
    public static void main(String[] args) {
        Factory factory = Factory.getFactory(Factory.MYSQL);
        Helper helper = new Helper(factory);

        try {
            helper.populateDB();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}