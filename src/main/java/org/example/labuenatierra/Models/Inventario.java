package org.example.labuenatierra.Models;

import java.util.Date;

public class Inventario {
    private Integer Cantidad;
    private String tipo_movimiento;
    private Date fecha_movimiento;

    public Inventario(Integer cantidad, String tipo_movimiento, Date fecha_movimiento) {
        Cantidad = cantidad;
        this.tipo_movimiento = tipo_movimiento;
        this.fecha_movimiento = fecha_movimiento;
    }

    @Override
    public String toString() {
        return "Inventario{" +
                "Cantidad=" + Cantidad +
                ", tipo_movimiento='" + tipo_movimiento + '\'' +
                ", fecha_movimiento=" + fecha_movimiento +
                '}';
    }

    public Integer getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Integer cantidad) {
        Cantidad = cantidad;
    }

    public Date getFecha_movimiento() {
        return fecha_movimiento;
    }

    public void setFecha_movimiento(Date fecha_movimiento) {
        this.fecha_movimiento = fecha_movimiento;
    }

    public String getTipo_movimiento() {
        return tipo_movimiento;
    }

    public void setTipo_movimiento(String tipo_movimiento) {
        this.tipo_movimiento = tipo_movimiento;
    }
}
