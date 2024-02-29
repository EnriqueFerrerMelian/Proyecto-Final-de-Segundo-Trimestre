package org.example.clases;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.Date;

public class Factura {
    private int[] contadores = { 0, 0, 0, 0, 0, 0 };
    String id;
    int mesa, pedido;
    float total;
    boolean ocupada;
    ObservableList<Producto> pedidosData;
    public Factura(String id, int mesa, boolean ocupada) {
        this.id = id;
        this.mesa = mesa;
        this.ocupada = ocupada;
        pedidosData = FXCollections.observableArrayList();
    }
    public Factura(){
        pedidosData = FXCollections.observableArrayList();
    }

    public ObservableList<Producto> getPedidosData() {
        return pedidosData;
    }

    public void setPedidosData(ObservableList<Producto> pedidosData) {
        this.pedidosData = pedidosData;
    }

    public int[] getContadores() {
        return contadores;
    }

    public void setContadores(int[] contadores) {
        this.contadores = contadores;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void setOcupada(boolean ocupada) {
        this.ocupada = ocupada;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getMesa() {
        return mesa;
    }

    public void setMesa(int mesa) {
        this.mesa = mesa;
    }

    @Override
    public String toString() {
        return "Factura{" +
                "id=" + id +
                ", mesa=" + mesa +
                ", pedido=" + pedido +
                ", total=" + total +
                '}';
    }
}
