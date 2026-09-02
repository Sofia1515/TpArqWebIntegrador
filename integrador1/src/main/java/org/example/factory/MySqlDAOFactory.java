package org.example.factory;

import org.example.dao.ClienteDAO;
import org.example.dao.FacturaDAO;
import org.example.dao.FacturaProductoDAO;
import org.example.dao.ProductoDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.example.dao.impl.ClienteDAOimpl;
import org.example.dao.impl.FacturaDAOimpl;
import org.example.dao.impl.FacturaProductoDAOimpl;
import org.example.dao.impl.ProductoDAOimpl;

public class MySqlDAOFactory extends DAOFactory{
    public static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    public static final String URI = "jdbc:mysql://localhost:3306/integrador?createDatabaseIfNotExist=true";
    public static final String USER = "root";
    public static final String PASS = "";

    private static MySqlDAOFactory instance;
    private static Connection conn;

    // Constructor privado para garantizar el patrón Singleton
    private MySqlDAOFactory() {}

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

    @Override
    public ClienteDAO getClienteDAO() throws SQLException {
        return new ClienteDAOimpl(getConn());
    }

    @Override
    public ProductoDAO getProductoDAO() throws SQLException {
        return new ProductoDAOimpl(getConn());
    }

    @Override
    public FacturaDAO getFacturaDAO() throws SQLException {
        return new FacturaDAOimpl(getConn());
    }

    @Override
    public FacturaProductoDAO getFacturaProductoDAO() throws SQLException {
        return new FacturaProductoDAOimpl(getConn());
    }
}
