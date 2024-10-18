package org.example.labuenatierra.Models;

import java.util.Date;

public class Marketing {
    private String nombre_campania;
    private String descripcion;
    private Date fecha_inicio;
    private Date fecha_fin;
    private String colaborador;

    public Marketing(String nombre_campania, String descripcion, Date fecha_inicio, String colaborador, Date fecha_fin) {
        this.nombre_campania = nombre_campania;
        this.descripcion = descripcion;
        this.fecha_inicio = fecha_inicio;
        this.colaborador = colaborador;
        this.fecha_fin = fecha_fin;
    }

    @Override
    public String toString() {
        return "Marketing{" +
                "nombre_campania='" + nombre_campania + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha_inicio=" + fecha_inicio +
                ", fecha_fin=" + fecha_fin +
                ", colaborador='" + colaborador + '\'' +
                '}';
    }

    public String getNombre_campania() {
        return nombre_campania;
    }

    public void setNombre_campania(String nombre_campania) {
        this.nombre_campania = nombre_campania;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha_inicio() {
        return fecha_inicio;
    }

    public void setFecha_inicio(Date fecha_inicio) {
        this.fecha_inicio = fecha_inicio;
    }

    public Date getFecha_fin() {
        return fecha_fin;
    }

    public void setFecha_fin(Date fecha_fin) {
        this.fecha_fin = fecha_fin;
    }

    public String getColaborador() {
        return colaborador;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }
}
