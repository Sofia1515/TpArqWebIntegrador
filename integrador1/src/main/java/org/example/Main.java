package org.example;

import org.example.factory.DAOFactory;
import org.example.utils.Helper;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Obtenemos la fábrica abstracta
            DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);

            // 2. Instanciamos el Helper pasándole la fábrica
            Helper helper = new Helper(mysqlFactory);

            // 3. Reiniciamos y creamos esquema
            helper.dropTables();
            helper.createTables();

            // 4. Cargamos los datos de los CSV a través de los DAO
            helper.populateDB();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
