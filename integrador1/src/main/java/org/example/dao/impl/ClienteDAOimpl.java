package org.example.dao.impl;

import org.example.dao.ClienteDAO;
import org.example.dto.ClienteDTO;
import org.example.entities.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAOimpl implements ClienteDAO {

    private Connection conn;

    public ClienteDAOimpl(Connection conn){
        this.conn = conn;
    };

    public void insertar(Cliente cliente) throws SQLException {
        String sql = "INSERT INTO cliente (idCliente, nombre, email) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, cliente.getIdCliente());
            stmt.setString(2, cliente.getNombre());
            stmt.setString(3, cliente.getEmail());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<ClienteDTO> getClientesOrdenadosPorFacturacion() throws SQLException {
        List<ClienteDTO> clientes = new ArrayList<>();

        String query = "SELECT c.idCliente, c.nombre, c.email, " +
                "       SUM(p.valor * fp.cantidad) AS total " +
                "FROM cliente c " +
                "JOIN factura f ON c.idCliente = f.idCliente " +
                "JOIN factura_producto fp ON f.idFactura = fp.idFactura " +
                "JOIN producto p ON fp.idProducto = p.idProducto " +
                "GROUP BY c.idCliente, c.nombre, c.email " +
                "ORDER BY total DESC";

        try (PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ClienteDTO cliente = new ClienteDTO(
                        rs.getInt("idCliente"),
                        rs.getString("nombre"),
                        rs.getString("email"),
                        rs.getDouble("total")
                );
                clientes.add(cliente);
            }
        }
        return clientes;
    }
}