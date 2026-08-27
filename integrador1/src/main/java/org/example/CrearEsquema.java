package org.example;

import java.sql.Connection;
import java.sql.Statement;

public class CrearEsquema {
    public static void main(String[] args) {
        String[] sentencias = {
                """
            CREATE TABLE IF NOT EXISTS Cliente (
                idCliente INT PRIMARY KEY AUTO_INCREMENT,
                nombre VARCHAR(500),
                email VARCHAR(150)
            )
            """,
                """
            CREATE TABLE IF NOT EXISTS Factura (
                idFactura INT PRIMARY KEY AUTO_INCREMENT,
                idCliente INT,
                FOREIGN KEY (idCliente) REFERENCES Cliente(idCliente)
            )
            """,
                """
            CREATE TABLE IF NOT EXISTS Producto (
                idProducto INT PRIMARY KEY AUTO_INCREMENT,
                nombre VARCHAR(45),
                valor FLOAT
            )
            """,
                """
            CREATE TABLE IF NOT EXISTS Factura_Producto (
                idFactura INT,
                idProducto INT,
                cantidad INT,
                PRIMARY KEY (idFactura, idProducto),
                FOREIGN KEY (idFactura) REFERENCES Factura(idFactura),
                FOREIGN KEY (idProducto) REFERENCES Producto(idProducto)
            )
            """
        };

        try (Connection conn = ConexionDB.getConnection();
             Statement stmt = conn.createStatement()) {

            for (String sql : sentencias) {
                stmt.execute(sql);
                System.out.println("Tabla creada correctamente.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}   