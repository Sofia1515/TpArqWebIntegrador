package org.example.dao;

import org.example.entities.Cliente;
import java.sql.SQLException;  // 👈 Agregar este import

public interface ClienteDAO {
    void insertar(Cliente cliente) throws SQLException;  // 👈 Agregar throws
}