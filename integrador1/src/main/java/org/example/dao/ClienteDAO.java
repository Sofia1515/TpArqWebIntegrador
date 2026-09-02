package org.example.dao;

import org.example.entities.Cliente;
import java.sql.SQLException;

public interface ClienteDAO {
    void insertar(Cliente cliente) throws SQLException;
}