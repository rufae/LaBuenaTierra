package org.example.labuenatierra.Controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.example.labuenatierra.Models.DatabaseConnection;
import org.example.labuenatierra.Models.Producto;

import java.io.InputStream;
import javafx.scene.control.Label;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TiendaController {

    @FXML
    private ImageView cartIcon;
    @FXML
    private GridPane gridPaneProductos;

    @FXML
    private void initialize() {
        // Cargar el icono del carrito
        InputStream imageStream = getClass().getResourceAsStream("/images/carrito.png");
        if (imageStream != null) {
            cartIcon.setImage(new Image(imageStream));
        } else {
            System.out.println("Imagen no encontrada en la ruta especificada.");
        }

        // Cargar productos al inicializar
        List<Producto> productos = loadProducts();
        int row = 0; // Fila inicial
        int column = 0; // Columna inicial

        for (Producto producto : productos) {
            VBox vbox = crearProductoVBox(producto); // Crear la vista para el producto
            gridPaneProductos.add(vbox, column, row); // Añadir el VBox al GridPane en la posición correcta

            // Actualiza las columnas y filas
            column++;
            if (column > 3) { // Cambia a la siguiente fila después de 3 productos
                column = 0;
                row++;
            }
        }
    }


    private List<Producto> loadProducts() {

        List<Producto> productos = new ArrayList<>();
        String query = "SELECT nombre, precio, imagen FROM productos"; // Asegúrate de que la consulta sea correcta

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                String nombre = resultSet.getString("nombre");
                double precio = resultSet.getDouble("precio");
                String imagen = resultSet.getString("imagen");
                productos.add(new Producto(nombre, precio, imagen)); // Crea un objeto Producto y lo añade a la lista
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }

        return productos; // Devuelve la lista de productos
    }

    // Método para crear la vista de un producto
    private VBox crearProductoVBox(Producto producto) {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);

        // Crear la vista de la imagen del producto
        ImageView imageView = new ImageView();
        imageView.setFitHeight(150);
        imageView.setFitWidth(150);

        // Intentar cargar la imagen desde el recurso
        InputStream imageStream = getClass().getResourceAsStream("/images/" + producto.getImagen());
        if (imageStream != null) {
            Image imagenProducto = new Image(imageStream);
            imageView.setImage(imagenProducto);
        } else {
            System.out.println("Imagen no encontrada: " + producto.getImagen());
            // Aquí puedes establecer una imagen por defecto
            // imageView.setImage(new Image("ruta/a/imagen/por/defecto.png"));
        }

        // Crear la etiqueta del nombre del producto
        Label nombreLabel = new Label(producto.getNombre());
        nombreLabel.getStyleClass().add("label-producto-nombre");

        // Crear la etiqueta del precio
        Label precioLabel = new Label(producto.getPrecio() + "€");
        precioLabel.getStyleClass().add("label-producto-precio");

        // Añadir la imagen, el nombre y el precio al VBox
        vbox.getChildren().addAll(imageView, nombreLabel, precioLabel);

        return vbox;
    }

}

