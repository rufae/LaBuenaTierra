package org.example.labuenatierra.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.labuenatierra.Models.DatabaseConnection;
import org.example.labuenatierra.Models.Producto;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Pos;


public class TiendaController {

    @FXML
    private ImageView cartIcon;
    @FXML
    private Button cartButton;  // Nuevo botón para el carrito
    @FXML
    private GridPane gridPaneProductos;
    @FXML
    private ChoiceBox<String> categoryChoiceBox;
    @FXML
    private TextField searchField;

    private final ObservableList<String> categorias = FXCollections.observableArrayList(
            "Todos", "Bollería Artesanal", "Productos de Navidad", "Fritos", "Tortas Artesanas"
    );

    @FXML
    private void initialize() {
        // Cargar el icono del carrito
        InputStream imageStream = getClass().getResourceAsStream("/images/carrito.png");
        if (imageStream != null) {
            cartIcon.setImage(new Image(imageStream));
        } else {
            System.out.println("Imagen no encontrada en la ruta especificada.");
        }

        // Hacer el botón del carrito pulsable
        cartButton.setOnMouseClicked(event -> {
            irACompraView();
        });

        // Configurar ChoiceBox
        categoryChoiceBox.setItems(categorias);
        categoryChoiceBox.setValue("Todos"); // Valor por defecto

        // Cargar productos por categoría
        categoryChoiceBox.setOnAction(event -> cargarProductosPorCategoria(categoryChoiceBox.getValue()));

        // Cargar productos al inicializar
        cargarProductosPorCategoria("Todos");
    }

    private void irACompraView() {
        try {
            // Cambia la vista a CompraView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/labuenatierra/Views/CompraView.fxml"));
            Parent compraView = loader.load();
            Stage stage = (Stage) cartButton.getScene().getWindow();  // Cambiado a cartButton
            Scene scene = new Scene(compraView); // Asegúrate de crear una nueva escena
            stage.setScene(scene);
            stage.show();  // Muestra la nueva vista
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void cargarProductosPorCategoria(String categoria) {
        gridPaneProductos.getChildren().clear(); // Limpiar productos previos

        List<Producto> productos = loadProducts(categoria);
        int row = 0;
        int column = 0;

        for (Producto producto : productos) {
            VBox vbox = crearProductoVBox(producto); // Crear la vista para el producto
            gridPaneProductos.add(vbox, column, row); // Añadir el VBox al GridPane

            column++;
            if (column > 3) { // Cambiar a la siguiente fila después de 3 productos
                column = 0;
                row++;
            }
        }
    }

    private List<Producto> loadProducts(String categoria) {
        List<Producto> productos = new ArrayList<>();
        String query;

        if (categoria.equals("Todos")) {
            query = "SELECT nombre, precio, imagen, descripcion FROM productos";
        } else {
            query = "SELECT nombre, precio, imagen, descripcion FROM productos WHERE categoria = ?";
        }

        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query)) {
            if (!categoria.equals("Todos")) {
                statement.setString(1, categoria);
            }

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    double precio = resultSet.getDouble("precio");
                    String imagen = resultSet.getString("imagen");
                    String descripcion = resultSet.getString("descripcion");
                    productos.add(new Producto(nombre, precio, imagen, descripcion));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }


    // Método para crear la vista de un producto
    private VBox crearProductoVBox(Producto producto) {
        VBox vbox = new VBox();
        vbox.setSpacing(10);
        vbox.setAlignment(Pos.CENTER); // Centrar el contenido

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
        }

        // Crear la etiqueta del nombre del producto
        Label nombreLabel = new Label(producto.getNombre());
        nombreLabel.getStyleClass().add("label-producto-nombre");

        // Crear la etiqueta del precio
        Label precioLabel = new Label(producto.getPrecio() + "€");
        precioLabel.getStyleClass().add("label-producto-precio");

        // Añadir la imagen, el nombre y el precio al VBox
        vbox.getChildren().addAll(imageView, nombreLabel, precioLabel);

        // Evento para redirigir a ProductoView.fxml al hacer clic
        vbox.setOnMouseClicked(event -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/labuenatierra/Views/ProductoView.fxml"));
                Parent productoView = loader.load();

                // Obtener el controlador de la vista de producto
                ProductoController productoController = loader.getController();
                // Pasar el producto seleccionado
                productoController.setProductoSeleccionado(producto);

                // Obtener la escena y cargar la nueva vista
                Stage stage = (Stage) vbox.getScene().getWindow();
                Scene scene = new Scene(productoView);
                stage.setScene(scene);

            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        // Efecto de zoom cuando el mouse pasa por encima
        vbox.setOnMouseEntered(event -> {
            vbox.setScaleX(1.1);
            vbox.setScaleY(1.1);
        });

        vbox.setOnMouseExited(event -> {
            vbox.setScaleX(1.0);
            vbox.setScaleY(1.0);
        });

        return vbox;
    }


    private List<Producto> loadProductsByName(String nombre) {
        List<Producto> productos = new ArrayList<>();
        String query = "SELECT nombre, precio, imagen, descripcion FROM productos WHERE LOWER(nombre) LIKE LOWER(?)";

        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, "%" + nombre + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String nombreProducto = resultSet.getString("nombre");
                    double precio = resultSet.getDouble("precio");
                    String imagen = resultSet.getString("imagen");
                    String descripcion = resultSet.getString("descripcion");
                    productos.add(new Producto(nombreProducto, precio, imagen, descripcion));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    private void cargarProductosPorNombre(String nombre) {
        gridPaneProductos.getChildren().clear(); // Limpiar productos previos

        List<Producto> productos = loadProductsByName(nombre);
        int row = 0;
        int column = 0;

        for (Producto producto : productos) {
            VBox vbox = crearProductoVBox(producto); // Crear la vista para el producto
            gridPaneProductos.add(vbox, column, row); // Añadir el VBox al GridPane

            column++;
            if (column > 3) { // Cambiar a la siguiente fila después de 3 productos
                column = 0;
                row++;
            }
        }
    }
    @FXML
    private void buscarProductosPorNombre() {
        String nombreProducto = searchField.getText().trim();
        cargarProductosPorNombre(nombreProducto);
    }
}
