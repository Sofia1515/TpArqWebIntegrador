package org.example.dao;

import org.example.entities.Factura;
import java.sql.SQLException;
import java.util.List;

public interface FacturaDAO {
    void insertar(Factura factura) throws SQLException;
}