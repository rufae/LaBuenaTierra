package org.example.labuenatierra.Models;

public class Clientes {
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
    private String contrasenia;
    private String comentarios;
    private Integer programa_fidelizacion;

    @Override
    public String toString() {
        return "Clientes{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", contrasenia='" + contrasenia + '\'' +
                ", comentarios='" + comentarios + '\'' +
                ", programa_fidelizacion=" + programa_fidelizacion +
                '}';
    }

    public Clientes(String nombre, String email, String telefono, String direccion, String contrasenia, String comentarios, Integer programa_fidelizacion) {
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.contrasenia = contrasenia;
        this.comentarios = comentarios;
        this.programa_fidelizacion = programa_fidelizacion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getComentarios() {
        return comentarios;
    }

    public void setComentarios(String comentarios) {
        this.comentarios = comentarios;
    }

    public Integer getPrograma_fidelizacion() {
        return programa_fidelizacion;
    }

    public void setPrograma_fidelizacion(Integer programa_fidelizacion) {
        this.programa_fidelizacion = programa_fidelizacion;
    }
}
