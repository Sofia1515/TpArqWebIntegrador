package org.example.dao.impl;

import org.example.entities.Producto;
import org.example.factory.MySqlDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ProductoDAOimpl {
    private Connection conn;

    public ProductoDAOimpl(Connection conn){
        this.conn = conn;
    };
    public void insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO Producto (idProducto, nombre, valor) VALUES (?, ?, ?)";


        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, producto.getIdProducto());
            stmt.setString(2, producto.getNombre());
            stmt.setFloat(3, producto.getValor());
            stmt.executeUpdate();
        }
    }
}
