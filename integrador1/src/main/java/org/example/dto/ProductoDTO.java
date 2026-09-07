package org.example.dto;

public class ProductoDTO {
    private int idProducto;
    private String nombre;
    private float valor;
    private int totalVendido;
    private double recaudacion;

    public ProductoDTO(int idProducto, String nombre, float valor, int totalVendido, double recaudacion) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.valor = valor;
        this.totalVendido = totalVendido;
        this.recaudacion = recaudacion;
    }

    public int getIdProducto() { return idProducto; }
    public String getNombre() { return nombre; }
    public float getValor() { return valor; }
    public int getTotalVendido() { return totalVendido; }
    public double getRecaudacion() { return recaudacion; }

    @Override
    public String toString() {
        return "ProductoDTO {" +
                "idProducto=" + idProducto +
                ", nombre='" + nombre + '\'' +
                ", valor unitario=$" + valor +
                ", totalVendido=" + totalVendido +
                ", recaudacion=$" + recaudacion +
                '}';
    }
}
