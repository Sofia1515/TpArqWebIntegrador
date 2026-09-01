package org.example.dao;

import org.example.entities.FacturaProducto;
import java.sql.SQLException;

public interface FacturaProductoDAO {
    void insertar(FacturaProducto facturaProducto) throws SQLException;
}