package utils;

import dao.ClienteDAO;
import dao.FacturaDAO;
import dao.FacturaProductoDAO;
import dao.ProductoDAO;
import entities.Cliente;
import entities.Factura;
import entities.FacturaProducto;
import entities.Producto;
import factory.DAOFactory;
import factory.MySqlDAOFactory;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Método auxiliar para no repetir código DDL
private void executeDDL(String sql) throws SQLException {
    Connection conn = MySqlDAOFactory.getConn();
    if (conn == null || conn.isClosed()) {
        throw new SQLException("La conexión a la base de datos no está abierta.");
    }
    try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.execute();
    }
}

        public void dropTables() throws SQLException {
            executeDDL("DROP TABLE IF EXISTS factura_producto");
            executeDDL("DROP TABLE IF EXISTS factura");
            executeDDL("DROP TABLE IF EXISTS cliente");
            executeDDL("DROP TABLE IF EXISTS producto");
            System.out.println("Tablas eliminadas con éxito.");
        }

        public void createTables() throws SQLException {
            executeDDL("CREATE TABLE IF NOT EXISTS cliente (" +
                    "idCliente INT PRIMARY KEY, " +
                    "nombre VARCHAR(500), " +
                    "email VARCHAR(150))");

            executeDDL("CREATE TABLE IF NOT EXISTS producto (" +
                    "idProducto INT PRIMARY KEY, " +
                    "nombre VARCHAR(45), " +
                    "valor
            public class Helper {

                private DAOFactory factory;

                public Helper(DAOFactory factory) {
                    this.factory = factory;
                }
                FLOAT)");

                executeDDL("CREATE TABLE IF NOT EXISTS factura (" +
                                   "idFactura INT PRIMARY KEY, " +
                                   "idCliente INT, " +
                                   "FOREIGN KEY (idCliente) REFERENCES cliente(idCliente))");

                executeDDL("CREATE TABLE IF NOT EXISTS factura_producto (" +
                                   "idFactura INT, " +
                                   "idProducto INT, " +
                                   "cantidad INT, " +
                                   "PRIMARY KEY (idFactura, idProducto), " +
                                   "FOREIGN KEY (idFactura) REFERENCES factura(idFactura), " +
                                   "FOREIGN KEY (idProducto) REFERENCES producto(idProducto))");

        System.out.println("Tablas creadas con éxito.");
            }