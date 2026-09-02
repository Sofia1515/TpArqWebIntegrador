package org.example.dao;

import org.example.dto.ClienteDTO;
import org.example.entities.Cliente;
import java.sql.SQLException;
import java.util.List;

public interface ClienteDAO {
    void insertar(Cliente cliente) throws SQLException;
    public List<ClienteDTO> getClientesOrdenadosPorFacturacion() throws SQLException;
}