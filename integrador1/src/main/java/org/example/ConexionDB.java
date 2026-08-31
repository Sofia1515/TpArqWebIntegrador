package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionDB {
    private static final String URL = "jdbc:mysql://localhost:3306/integrador";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    private static ConexionDB instancia;
    private Connection conexion;

    private ConexionDB() throws SQLException {
        this.conexion = DriverManager.getConnection(URL, USUARIO, PASSWORD);
    }

    public static ConexionDB getInstancia() throws SQLException {
        if (instancia == null || instancia.conexion.isClosed()) {
            instancia = new ConexionDB();
        }
        return instancia;
    }

    public Connection getConnection() {
        return conexion;
    }
}