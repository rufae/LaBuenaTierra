package org.example.labuenatierra.Models;

import java.util.Date;

public class Finanzas {
    private String tipo;
    private double monto;
    private Date fecha;
    private String descripcion;

    @Override
    public String toString() {
        return "Finanzas{" +
                "tipo='" + tipo + '\'' +
                ", monto=" + monto +
                ", fecha=" + fecha +
                ", descripcion='" + descripcion + '\'' +
                '}';
    }

    public Finanzas(String tipo, Date fecha, double monto, String descripcion) {
        this.tipo = tipo;
        this.fecha = fecha;
        this.monto = monto;
        this.descripcion = descripcion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
