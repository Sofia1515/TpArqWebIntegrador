package org.example.utils;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.example.dao.ClienteDAO;
import org.example.dao.FacturaDAO;
import org.example.dao.FacturaProductoDAO;
import org.example.dao.ProductoDAO;
import org.example.entities.Cliente;
import org.example.entities.Factura;
import org.example.entities.FacturaProducto;
import org.example.entities.Producto;
import org.example.factory.DAOFactory;
import org.example.factory.MySqlDAOFactory;


import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Helper {

    private DAOFactory factory;

    public Helper(DAOFactory factory) {
        this.factory = factory;
    }

    // Metodo auxiliar para no repetir código DDL
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
                "valor FLOAT)");  //aca le faltaba FLOAT");

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

    public void populateDB() throws Exception {
        populateClientes("src/main/resources/clientes.csv");
        populateProductos("src/main/resources/productos.csv");
        populateFacturas("src/main/resources/facturas.csv");
        populateFacturasProductos("src/main/resources/facturas-productos.csv");
        System.out.println("Base de datos poblada exitosamente desde los CSV.");
    }

    private void populateClientes(String path) throws Exception {
        ClienteDAO clienteDAO = factory.getClienteDAO();
        try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path))) {
            for (CSVRecord row : parser) {
                int id = Integer.parseInt(row.get("idCliente").trim());
                String nombre = row.get("nombre").trim();
                String email = row.get("email").trim();
                clienteDAO.insertar(new Cliente(id, nombre, email));
            }
        }
    }
    private void populateProductos(String path) throws Exception {
        ProductoDAO productoDAO = factory.getProductoDAO();
        try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path))) {
            for (CSVRecord row : parser) {
                int id = Integer.parseInt(row.get("idProducto").trim());
                String nombre = row.get("nombre").trim();
                float valor = Float.parseFloat(row.get("valor").trim().replace(",", "."));

                productoDAO.insertar(new Producto(id, nombre, valor));
            }
        }
    }

    private void populateFacturas(String path) throws Exception {
        FacturaDAO facturaDAO = factory.getFacturaDAO();
        try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path))) {
            for (CSVRecord row : parser) {
                int idFactura = Integer.parseInt(row.get("idFactura").trim());
                int idCliente = Integer.parseInt(row.get("idCliente").trim());

                facturaDAO.insertar(new Factura(idFactura, idCliente));
            }
        }
    }

    private void populateFacturasProductos(String path) throws Exception {
        FacturaProductoDAO fpDAO = factory.getFacturaProductoDAO();
        try (CSVParser parser = CSVFormat.DEFAULT.withHeader().parse(new FileReader(path))) {
            for (CSVRecord row : parser) {
                int idFactura = Integer.parseInt(row.get("idFactura").trim());
                int idProducto = Integer.parseInt(row.get("idProducto").trim());
                int cantidad = Integer.parseInt(row.get("cantidad").trim());
                fpDAO.insertar(new FacturaProducto(idFactura, idProducto, cantidad));
            }
        }
    }

}