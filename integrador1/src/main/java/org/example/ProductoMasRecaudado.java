package org.example;

import org.example.factory.MySqlDAOFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProductoMasRecaudado {

    public static void main(String[] args) {
        // 1. Obtener la conexión desde la fábrica (reutiliza la que ya tienen)
        try (Connection conn = MySqlDAOFactory.getConn()) {

            // 2. Consulta SQL para obtener el producto que más recaudó
            String sql = """
                SELECT p.idProducto, p.nombre, p.valor, 
                       SUM(fp.cantidad) AS totalVendido,
                       SUM(fp.cantidad * p.valor) AS recaudacion
                FROM Producto p
                JOIN Factura_Producto fp ON p.idProducto = fp.idProducto
                GROUP BY p.idProducto, p.nombre, p.valor
                ORDER BY recaudacion DESC
                LIMIT 1
            """;

            // 3. Ejecutar la consulta
            try (PreparedStatement ps = conn.prepareStatement(sql);
                 ResultSet rs = ps.executeQuery()) {

                // 4. Procesar el resultado
                if (rs.next()) {
                    int idProducto = rs.getInt("idProducto");
                    String nombre = rs.getString("nombre");
                    double valor = rs.getDouble("valor");
                    int totalVendido = rs.getInt("totalVendido");
                    double recaudacion = rs.getDouble("recaudacion");

                    // 5. Mostrar el resultado
                    System.out.println("🏆 Producto que más recaudó:");
                    System.out.println("ID: " + idProducto);
                    System.out.println("Nombre: " + nombre);
                    System.out.println("Valor unitario: $" + valor);
                    System.out.println("Cantidad vendida: " + totalVendido);
                    System.out.println("Recaudación total: $" + recaudacion);
                } else {
                    System.out.println("⚠️ No hay productos en la base de datos.");
                }
            }

        } catch (SQLException e) {
            System.err.println("❌ Error al consultar la base de datos:");
            e.printStackTrace();
        }
    }
}