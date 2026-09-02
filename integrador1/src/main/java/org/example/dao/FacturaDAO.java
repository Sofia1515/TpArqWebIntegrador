package org.example.dao;

import org.example.entities.Factura;
import java.sql.SQLException;

public interface FacturaDAO {
    void insertar(Factura factura) throws SQLException;
}