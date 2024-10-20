package org.example.labuenatierra.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.labuenatierra.Models.Carrito;
import org.example.labuenatierra.Models.DatabaseConnection;
import org.example.labuenatierra.Models.Productos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;
import java.util.Objects;

public class CompraController {

    @FXML
    public Button aceptarButton;

    @FXML
    public Label labelLaBuenaTierra;

    @FXML
    public Label mensajeCompraLabel;

    @FXML
    private VBox productosVBox; // VBox donde se mostrarán los productos
    @FXML
    private Label totalLabel; // Label para mostrar el total de la compra

    // Instancia del carrito
    private static Carrito carrito = Carrito.getInstance();

    @FXML
    private void initialize() {

        // Deshabilitar los botones si es administrador
        if (Session.isAdmin()) {
            aceptarButton.setDisable(true);
        }

        mostrarProductosEnCarrito();
        actualizarEstadoBotonAceptar(); // Verificar el estado del botón al inicializar
    }

    @FXML
    private void iraTiendaInicio() {
        Stage stage = (Stage) labelLaBuenaTierra.getScene().getWindow();
        NavigationUtil.cambiarVistaAdmin("/org/example/labuenatierra/Views/TiendaInicioView.fxml", stage);

        stage.setTitle("TiendaInicioView");
    }

    private void mostrarProductosEnCarrito() {
        // Limpiar el VBox antes de agregar los productos
        productosVBox.getChildren().clear();

        double total = 0.0; // Inicializa el total

        // Obtener los productos del carrito
        Map<Productos, Integer> productos = carrito.getProductos();

        // Iterar sobre los productos y mostrarlos en la interfaz
        for (Map.Entry<Productos, Integer> entry : productos.entrySet()) {
            Productos producto = entry.getKey();
            int cantidad = entry.getValue();

            // Crear un HBox para cada producto
            HBox productoBox = new HBox();
            productoBox.setSpacing(10.0);

            // Crear ImageView para la imagen del producto
            ImageView productoImage = new ImageView();
            productoImage.setFitHeight(100);
            productoImage.setFitWidth(100);
            String imagenPath = "/images/" + producto.getImagen();
            try {
                productoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagenPath))));
            } catch (IllegalArgumentException e) {
                productoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/error.png"))));
                System.err.println("Error cargando la imagen del producto: " + imagenPath);
            }

            // Crear un VBox para los detalles del producto
            VBox detallesVBox = new VBox();
            detallesVBox.setSpacing(5.0);

            Label nombreLabel = new Label(producto.getNombre());
            Label precioLabel = new Label(String.format("%.2f €", producto.getPrecio() * cantidad));
            Label descripcionLabel = new Label(producto.getDescripcion());
            descripcionLabel.setWrapText(true); // Habilitar el ajuste de texto

            detallesVBox.getChildren().addAll(nombreLabel, precioLabel, descripcionLabel);

            // Botones para modificar la cantidad del producto
            HBox cantidadBox = new HBox();
            cantidadBox.setSpacing(5.0);

            // Botón para disminuir la cantidad
            Button disminuirButton = new Button("-");
            disminuirButton.getStyleClass().add("boton-cantidad"); // Añadir estilo
            disminuirButton.setOnAction(event -> {
                if (cantidad > 1) {
                    carrito.disminuirCantidad(producto);
                    mostrarProductosEnCarrito(); // Refrescar la vista
                } else {
                    carrito.eliminarProducto(producto); // Método para eliminar el producto del carrito
                    mostrarProductosEnCarrito(); // Refrescar la vista
                }
            });

            // Label para mostrar la cantidad actual
            Label cantidadLabel = new Label(String.valueOf(cantidad));
            cantidadLabel.getStyleClass().add("label-cantidad");

            // Botón para aumentar la cantidad
            Button aumentarButton = new Button("+");
            aumentarButton.getStyleClass().add("boton-cantidad"); // Añadir estilo
            aumentarButton.setOnAction(event -> {
                carrito.aumentarCantidad(producto);
                mostrarProductosEnCarrito(); // Refrescar la vista
            });

            // Botón para eliminar el producto
            Button eliminarButton = new Button("Eliminar");
            eliminarButton.getStyleClass().add("boton-eliminar"); // Añadir estilo
            eliminarButton.setOnAction(event -> {
                carrito.eliminarProducto(producto); // Método para eliminar el producto del carrito
                mostrarProductosEnCarrito(); // Refrescar la vista
            });

            cantidadBox.getChildren().addAll(disminuirButton, cantidadLabel, aumentarButton, eliminarButton);
            productoBox.getChildren().addAll(productoImage, detallesVBox, cantidadBox);
            productosVBox.getChildren().add(productoBox); // Agregar el HBox al VBox


            // Sumar al total
            total += producto.getPrecio() * cantidad;
        }

        // Mostrar el total en el Label
        totalLabel.setText("Total: " + String.format("%.2f €", total));
    }


    private void actualizarEstadoBotonAceptar() {
        // Habilitar o deshabilitar el botón según el contenido del carrito
        aceptarButton.setDisable(carrito.getProductos().isEmpty());
    }

    @FXML
    public void aceptarCompra(ActionEvent event) {
        // Lógica para aceptar la compra
        finalizarCompra(); // Llamar al método de finalizar compra
    }

    private void finalizarCompra() {
        // Obtener el ID del cliente
        int idCliente = LoginController.getIdCliente();

        // Obtener el total de la compra
        double total = carrito.getTotal();

        // Registrar el pedido en la base de datos
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Pedidos (id_cliente, fecha_pedido, total, estado) VALUES (?, NOW(), ?, 'Recibido')";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idCliente);
                preparedStatement.setDouble(2, total);
                preparedStatement.executeUpdate();
                System.out.println("Pedido registrado exitosamente.");

                // Limpiar el carrito después de registrar el pedido
                carrito.limpiarCarrito();
                mostrarProductosEnCarrito(); // Actualizar la interfaz para mostrar que el carrito está vacío

                // Actualizar el estado del botón después de la compra
                actualizarEstadoBotonAceptar(); // Desactivar el botón si el carrito está vacío
            } catch (SQLException e) {
                System.out.println("Error al registrar el pedido: " + e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }
    }

}
