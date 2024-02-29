package org.example.clases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class Pedido {
    int mesa, producto, cantidad;
    String factura;
    double total;
    LocalDate fecha;

    public Pedido(int mesa, String factura, int producto, int cantidad, double total) {
        this.mesa = mesa;
        this.factura = factura;
        this.producto = producto;
        this.cantidad = cantidad;
        this.total = total;
        this.fecha = LocalDate.now();

    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    public int getProducto() {
        return producto;
    }

    public void setProducto(int producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getFactura() {
        return factura;
    }

    public void setFactura(String factura) {
        this.factura = factura;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "mesa=" + mesa +
                ", producto=" + producto +
                ", cantidad=" + cantidad +
                ", factura='" + factura + '\'' +
                ", total=" + total +
                ", fecha=" + fecha +
                '}';
    }
}
