package org.example.labuenatierra.Models;

import java.util.Date;

public class Pedidos {
    private Date fecha_pedido;
    private double total;
    private String estado;

    public Date getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Date fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Pedidos{" +
                "fecha_pedido=" + fecha_pedido +
                ", total=" + total +
                ", estado='" + estado + '\'' +
                '}';
    }
}
