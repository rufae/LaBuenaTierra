package org.example.labuenatierra.Models;

import java.util.HashMap;
import java.util.Map;

public class Carrito {
    private static Carrito instance;
    private final Map<Producto, Integer> productos;

    private Carrito() {
        this.productos = new HashMap<>();
    }

    public static Carrito getInstance() {
        if (instance == null) {
            instance = new Carrito();
        }
        return instance;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        productos.put(producto, productos.getOrDefault(producto, 0) + cantidad);
    }

    public Map<Producto, Integer> getProductos() {
        return productos;
    }

    public void mostrarCarrito() {
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            System.out.println("Producto: " + entry.getKey().getNombre() + ", Cantidad: " + entry.getValue());
        }
    }

    public double getTotal() {
        double total = 0.0;
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();
            total += producto.getPrecio() * cantidad;
        }
        return total;
    }

    public void limpiarCarrito() {
        productos.clear(); // Limpia todos los productos del carrito
    }

    public void aumentarCantidad(Producto producto) {
        if (productos.containsKey(producto)) {
            productos.put(producto, productos.get(producto) + 1);
        }
    }

    public void disminuirCantidad(Producto producto) {
        if (productos.containsKey(producto) && productos.get(producto) > 1) {
            productos.put(producto, productos.get(producto) - 1);
        }
    }

    public void eliminarProducto(Producto producto) {
        productos.remove(producto);
    }
}
