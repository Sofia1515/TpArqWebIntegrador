package org.example.dao.impl;

import org.example.ConexionDB;
import org.example.entities.Factura;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FacturaDAOimpl {

    public void insertar(Factura factura) throws SQLException {
        String sql = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";
        Connection conn = ConexionDB.getInstancia().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, factura.getIdFactura());
            stmt.setInt(2, factura.getIdCliente());
            stmt.executeUpdate();
        }
    }
}