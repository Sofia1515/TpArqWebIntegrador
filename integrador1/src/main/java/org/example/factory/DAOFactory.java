package org.example.factory;

import org.example.dao.ClienteDAO;
import org.example.dao.FacturaDAO;
import org.example.dao.FacturaProductoDAO;
import org.example.dao.ProductoDAO;

import java.sql.SQLException;


public abstract class DAOFactory {
    public static final int MYSQL = 1;

    public abstract ClienteDAO getClienteDAO() throws SQLException;
    public abstract ProductoDAO getProductoDAO() throws SQLException;
    public abstract FacturaDAO getFacturaDAO() throws SQLException;
    public abstract FacturaProductoDAO getFacturaProductoDAO() throws SQLException;

    public static DAOFactory getDAOFactory(int whichFactory) throws ClassNotFoundException {
        switch (whichFactory) {
            case MYSQL:
                return MySqlDAOFactory.getInstance();
            default:
                return null;
        }
    }
}
