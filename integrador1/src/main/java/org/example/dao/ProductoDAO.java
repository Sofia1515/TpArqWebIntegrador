package org.example.dao;

import org.example.entities.Producto;
import java.sql.SQLException;

public interface ProductoDAO {
    void insertar(Producto producto) throws SQLException;
}