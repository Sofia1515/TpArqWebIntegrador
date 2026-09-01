package org.example.factory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlDAOFactory extends DAOFactory{
    public static final String URI = "jdbc:mysql://localhost:3306/integrador"; // Tu base de datos
    public static final String USER = "root";
    public static final String PASS = "password";

    private static MySqlDAOFactory instance;
    private static Connection conn;

    public static MySqlDAOFactory getInstance(){
        if(instance == null){
            instance = new MySqlDAOFactory();
        }
        return instance;
    }

    public static Connection getConn() throws SQLException {
        if (conn == null || conn.isClosed()) {
            try {
                Class.forName(DRIVER);
                conn = DriverManager.getConnection(URI, USER, PASS);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}
