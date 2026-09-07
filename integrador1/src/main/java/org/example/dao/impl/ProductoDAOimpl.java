package org.example.dao.impl;

import org.example.dao.ProductoDAO;
import org.example.dto.ProductoDTO;
import org.example.entities.Producto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAOimpl implements ProductoDAO {

    private Connection conn;

    public ProductoDAOimpl(Connection conn){
        this.conn = conn;
    };

    public void insertar(Producto producto) throws SQLException {
        String sql = "INSERT INTO producto (idProducto, nombre, valor) VALUES (?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, producto.getIdProducto());
            stmt.setString(2, producto.getNombre());
            stmt.setFloat(3, producto.getValor());
            stmt.executeUpdate();
        }
    }

    @Override
    public List<ProductoDTO> getProductoMasRecaudado() throws SQLException {
        List<ProductoDTO> productos = new ArrayList<>();
        String sql = """
                SELECT p.idProducto, p.nombre, p.valor, 
                       SUM(fp.cantidad) AS totalVendido,
                       SUM(fp.cantidad * p.valor) AS recaudacion
                FROM producto p
                JOIN factura_producto fp ON p.idProducto = fp.idProducto
                GROUP BY p.idProducto, p.nombre, p.valor
                ORDER BY recaudacion DESC
                LIMIT 1
            """;

        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                int idProducto = rs.getInt("idProducto");
                String nombre = rs.getString("nombre");
                float valor = rs.getFloat("valor");
                int totalVendido = rs.getInt("totalVendido");
                double recaudacion = rs.getDouble("recaudacion");

                productos.add(new ProductoDTO(idProducto, nombre, valor, totalVendido, recaudacion));
            }
        }
        return productos;
    }
}
