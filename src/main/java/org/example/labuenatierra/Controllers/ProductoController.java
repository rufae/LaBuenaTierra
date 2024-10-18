package org.example.labuenatierra.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.example.labuenatierra.Models.Carrito;
import org.example.labuenatierra.Models.Productos;

import java.io.InputStream;
import java.util.Objects;

public class ProductoController {
    public Button buttonAgregar;
    @FXML
    private ImageView productoImage;
    @FXML
    private Label nombreProductoLabel;
    @FXML
    private Label precioLabel;
    @FXML
    private Label labelLaBuenaTierra;
    @FXML
    private Label descripcionProductoLabel;
    @FXML
    private Spinner<Integer> cantidadSpinner;
    private Productos productoSeleccionado;

    // Instancia del carrito
    private static Carrito carrito = Carrito.getInstance();

    @FXML
    private void initialize() {

        // Deshabilitar los botones si es administrador
        if (Session.isAdmin()) {
            buttonAgregar.setDisable(true);
        }

        // Cargar la imagen desde el classpath
        InputStream imageStream = getClass().getResourceAsStream("/images/palmerasdehuevo.jpg");
        if (imageStream != null) {
            productoImage.setImage(new Image(imageStream));
        } else {
            System.out.println("Imagen no encontrada en la ruta especificada.");
        }
    }

    public void setProductoSeleccionado(Productos producto) {
        this.productoSeleccionado = producto;

        // Actualizar los campos con la información del producto seleccionado
        nombreProductoLabel.setText(producto.getNombre());
        precioLabel.setText(String.format("%.2f €", producto.getPrecio()));
        descripcionProductoLabel.setText(producto.getDescripcion());

        // Cargar la imagen del producto
        Image imagenProducto = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/" + producto.getImagen())));
        productoImage.setImage(imagenProducto);
    }

    @FXML
    private void agregarAlCarrito() {
        int cantidad = cantidadSpinner.getValue(); // Obtener el valor del Spinner

        // Agregar el producto al carrito
        if (productoSeleccionado != null && cantidad > 0) {
            carrito.agregarProducto(productoSeleccionado, cantidad);
            System.out.println(cantidad + " " + productoSeleccionado.getNombre() + " han sido añadidos al carrito.");
            carrito.mostrarCarrito(); // Mostrar el contenido del carrito (puedes quitarlo si no lo necesitas)

            // Redirigir a la vista TiendaInicioView.fxml después de añadir el producto
            irATiendaInicio();
        }
    }

    @FXML
    private void irATiendaInicio() {
        Stage stage = (Stage) labelLaBuenaTierra.getScene().getWindow();
        NavigationUtil.cambiarVista("/org/example/labuenatierra/Views/TiendaInicioView.fxml", stage);
    }
}
