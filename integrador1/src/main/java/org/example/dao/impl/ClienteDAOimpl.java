package org.example.dao.impl;

import org.example.ConexionDB;
import org.example.entities.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAOimpl {

    public void insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";
        Connection conn = ConexionDB.getInstancia().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getEmail());
            stmt.executeUpdate();
        }
    }
}