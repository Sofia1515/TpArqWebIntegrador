package org.example.dao;

import org.example.dto.ClienteDTO;
import org.example.dto.ProductoDTO;
import org.example.entities.Producto;
import java.sql.SQLException;
import java.util.List;

public interface ProductoDAO {
    void insertar(Producto producto) throws SQLException;
    public List<ProductoDTO> getProductoMasRecaudado() throws SQLException;
}