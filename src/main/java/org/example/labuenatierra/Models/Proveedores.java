package org.example.labuenatierra.Models;

public class Proveedores {
    private String nombre;
    private String contacto;
    private String email;
    private String telefono;
    private String direccion;
    private String calidad_producto;

    public Proveedores(String nombre, String contacto, String email, String telefono, String direccion, String calidad_producto) {
        this.nombre = nombre;
        this.contacto = contacto;
        this.email = email;
        this.telefono = telefono;
        this.direccion = direccion;
        this.calidad_producto = calidad_producto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getContacto() {
        return contacto;
    }

    public void setContacto(String contacto) {
        this.contacto = contacto;
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

    public String getCalidad_producto() {
        return calidad_producto;
    }

    public void setCalidad_producto(String calidad_producto) {
        this.calidad_producto = calidad_producto;
    }

    @Override
    public String toString() {
        return "Empleado{" +
                "nombre='" + nombre + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", direccion='" + direccion + '\'' +
                ", contacto=" + contacto +
                ", calidad del producto='" + calidad_producto + '\'' +
                '}';
    }
}
