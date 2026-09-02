package org.example.dao.impl;

import org.example.dao.ClienteDAO;
import org.example.entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ClienteDAOimpl implements ClienteDAO {

    private Connection conn;

    public ClienteDAOimpl(Connection conn){
        this.conn = conn;
    };
    public void insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO Cliente (idCliente, nombre, email) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getEmail());
            stmt.executeUpdate();
        }
    }
}