package org.example.dao.impl;

import org.example.dao.FacturaProductoDAO;
import org.example.entities.FacturaProducto;
import org.example.factory.MySqlDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FacturaProductoDAOimpl implements FacturaProductoDAO {
    private Connection conn;

    public FacturaProductoDAOimpl(Connection conn){
        this.conn = conn;
    };
    public void insertar(FacturaProducto fp) throws SQLException {
        String sql = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fp.getIdFactura());
            stmt.setInt(2, fp.getIdProducto());
            stmt.setInt(3, fp.getCantidad());
            stmt.executeUpdate();
        }
    }
}