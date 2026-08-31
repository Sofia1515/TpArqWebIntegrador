package org.example.dao.impl;

import org.example.ConexionDB;
import org.example.entities.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductoDAOimpl {

    public void insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";
        Connection conn = ConexionDB.getInstancia().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, producto.getIdProducto());
            stmt.setString(2, producto.getNombre());
            stmt.setFloat(3, producto.getValor());
            stmt.executeUpdate();
        }
    }
}
