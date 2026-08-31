package org.example.dao;

public interface FacturaProductoDAO {
    public abstract void insertar(int idFactura, int idProducto, int cantidad);
}
