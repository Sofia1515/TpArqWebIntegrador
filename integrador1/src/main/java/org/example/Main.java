package org.example;

import org.example.dao.ClienteDAO;
import org.example.dao.ProductoDAO;
import org.example.dto.ClienteDTO;
import org.example.dto.ProductoDTO;
import org.example.factory.DAOFactory;
import org.example.utils.Helper;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // 1. Obtenemos la fábrica abstracta
            DAOFactory mysqlFactory = DAOFactory.getDAOFactory(DAOFactory.MYSQL);

            // 2. Instanciamos el Helper pasándole la fábrica
            Helper helper = new Helper(mysqlFactory);

            // 3. Reiniciamos y creamos esquema
            helper.dropTables();
            helper.createTables();

            // 4. Cargamos los datos de los CSV a través de los DAO
            helper.populateDB();

            // 5. Prueba punto 3: Consulta SQL para obtener el producto que más recaudó
            System.out.println("\n--- Producto que más recaudó ---");
            ProductoDAO productoDAO = mysqlFactory.getProductoDAO();
            List<ProductoDTO> masRecaudados = productoDAO.getProductoMasRecaudado();

            for (ProductoDTO p : masRecaudados) {
                System.out.println(p);
            }



            // 5. Prueba Punto 4: Imprimir lista de clientes ordenados por mayor facturacion
            System.out.println("\n--- Clientes ordenados por mayor facturación ---");
            ClienteDAO clienteDAO = mysqlFactory.getClienteDAO();
            List<ClienteDTO> ranking = clienteDAO.getClientesOrdenadosPorFacturacion();

            for (ClienteDTO cliente : ranking) {
                System.out.println(cliente);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
