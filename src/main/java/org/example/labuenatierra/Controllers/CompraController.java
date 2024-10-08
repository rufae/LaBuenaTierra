package org.example.labuenatierra.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.labuenatierra.Models.Carrito;
import org.example.labuenatierra.Models.DatabaseConnection;
import org.example.labuenatierra.Models.Producto;

import java.io.IOException;
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
    private ImageView homeIcon;

    @FXML
    private VBox productosVBox; // VBox donde se mostrarán los productos
    @FXML
    private Label totalLabel; // Label para mostrar el total de la compra

    // Instancia del carrito
    private static Carrito carrito = Carrito.getInstance(); // Asegúrate de que esta instancia sea la misma que en ProductoController

    @FXML
    private void initialize() {
        mostrarProductosEnCarrito();
        actualizarEstadoBotonAceptar(); // Verificar el estado del botón al inicializar
    }

    @FXML
    private void iraTiendaInicio() {
        try {
            // Cargar la nueva vista TiendaInicioView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/labuenatierra/Views/TiendaInicioView.fxml"));
            Parent tiendaInicioView = loader.load();

            // Obtener el Stage desde el evento de clic en lugar del label
            Stage stage = (Stage) labelLaBuenaTierra.getScene().getWindow(); // Esto puede causar NullPointerException si labelLaBuenaTierra no está en la escena.

            // Cambiar la raíz de la escena
            Scene currentScene = stage.getScene();
            currentScene.setRoot(tiendaInicioView);

            // Ajustar el tamaño de la ventana
            stage.sizeToScene(); // Ajustar al tamaño de la nueva escena

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void mostrarProductosEnCarrito() {
        // Limpiar el VBox antes de agregar los productos
        productosVBox.getChildren().clear();

        double total = 0.0; // Inicializa el total

        // Obtener los productos del carrito
        Map<Producto, Integer> productos = carrito.getProductos();

        // Iterar sobre los productos y mostrarlos en la interfaz
        for (Map.Entry<Producto, Integer> entry : productos.entrySet()) {
            Producto producto = entry.getKey();
            int cantidad = entry.getValue();

            // Crear un HBox para cada producto
            HBox productoBox = new HBox();
            productoBox.setSpacing(10.0);

            // Crear ImageView para la imagen del producto
            ImageView productoImage = new ImageView();
            productoImage.setFitHeight(100);
            productoImage.setFitWidth(100);
            String imagenPath = "/images/" + producto.getImagen(); // Asumiendo que el nombre de la imagen está en la base de datos
            try {
                productoImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagenPath))));
            } catch (IllegalArgumentException e) {
                // Si la imagen no se encuentra, puedes usar una imagen predeterminada
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
            productoBox.getChildren().addAll(productoImage, detallesVBox);
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
        // Obtener el ID del cliente (esto puede depender de cómo gestiones la sesión del usuario)
        int idCliente = LoginController.getIdCliente(); // Cambia esto por el ID real del cliente

        // Obtener el total de la compra
        double total = carrito.getTotal(); // Suponiendo que tienes un método para obtener el total

        // Registrar el pedido en la base de datos
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO Pedidos (id_cliente, fecha_pedido, total, estado) VALUES (?, NOW(), ?, 'Recibido')";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setInt(1, idCliente);
                preparedStatement.setDouble(2, total);
                preparedStatement.executeUpdate();
                System.out.println("Pedido registrado exitosamente.");

                // Limpiar el carrito después de registrar el pedido
                carrito.limpiarCarrito(); // Asegúrate de tener este método en tu clase Carrito
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
