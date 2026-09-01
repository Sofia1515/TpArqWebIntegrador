/*package org.example.dao.impl;

import org.example.dao.FacturaDAO;
import org.example.entities.Factura;
import org.example.factory.MySqlDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FacturaDAOimpl implements FacturaDAO {
    private Connection conn;

    public FacturaDAOimpl(Connection conn){
        this.conn = conn;
    };
    public void insertar(Factura factura) throws SQLException {
        String sql = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, factura.getIdFactura());
            stmt.setInt(2, factura.getIdCliente());
            stmt.executeUpdate();
        }
    }
}

 */
package org.example.dao.impl;

import org.example.dao.FacturaDAO;
import org.example.entities.Factura;

import java.sql.*;
        import java.util.ArrayList;
import java.util.List;

public class FacturaDAOimpl implements FacturaDAO {

    private Connection conn;

    public FacturaDAOimpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insertar(Factura factura) throws SQLException {
        String sql = "INSERT INTO Factura (idFactura, idCliente) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, factura.getIdFactura());
            stmt.setInt(2, factura.getIdCliente());
            stmt.executeUpdate();
        }
    }

}