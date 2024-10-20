package org.example.labuenatierra.Models;

import java.util.HashMap;
import java.util.Map;

public class Carrito {
    private static Carrito instance;
    private final Map<Productos, Integer> productos;

    private Carrito() {
        this.productos = new HashMap<>();
    }

    public static Carrito getInstance() {
        if (instance == null) {
            instance = new Carrito();
        }
        return instance;
    }

    public void agregarProducto(Productos producto, int cantidad) {
        productos.put(producto, productos.getOrDefault(producto, 0) + cantidad);
    }

    public Map<Productos, Integer> getProductos() {
        return productos;
    }

    public void mostrarCarrito() {
        for (Map.Entry<Productos, Integer> entry : productos.entrySet()) {
            System.out.println("Producto: " + entry.getKey().getNombre() + ", Cantidad: " + entry.getValue());
        }
    }

    public double getTotal() {
        double total = 0.0;
        for (Map.Entry<Productos, Integer> entry : productos.entrySet()) {
            Productos producto = entry.getKey();
            int cantidad = entry.getValue();
            total += producto.getPrecio() * cantidad;
        }
        return total;
    }

    public void limpiarCarrito() {
        productos.clear(); // Limpia todos los productos del carrito
    }

    public void aumentarCantidad(Productos producto) {
        if (productos.containsKey(producto)) {
            productos.put(producto, productos.get(producto) + 1);
        }
    }

    public void disminuirCantidad(Productos producto) {
        if (productos.containsKey(producto) && productos.get(producto) > 1) {
            productos.put(producto, productos.get(producto) - 1);
        }
    }

    public void eliminarProducto(Productos producto) {
        productos.remove(producto);
    }
}
