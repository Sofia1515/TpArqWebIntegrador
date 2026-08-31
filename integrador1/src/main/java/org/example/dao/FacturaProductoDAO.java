package org.example.dao;

import org.example.ConexionDB;
import org.example.modelo.FacturaProducto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FacturaProductoDAO {

    public void insertar(FacturaProducto fp) throws SQLException {
        String sql = "INSERT INTO Factura_Producto (idFactura, idProducto, cantidad) VALUES (?, ?, ?)";
        Connection conn = ConexionDB.getInstancia().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, fp.getIdFactura());
            stmt.setInt(2, fp.getIdProducto());
            stmt.setInt(3, fp.getCantidad());
            stmt.executeUpdate();
        }
    }
}