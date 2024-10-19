package org.example.labuenatierra.Controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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
import org.example.labuenatierra.Models.Productos;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.geometry.Pos;


public class TiendaController {

    public Label labelLaBuenaTierra;
    public Button logoutButton;
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
    private static final Logger logger = Logger.getLogger(TiendaController.class.getName());


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
        cartButton.setOnMouseClicked(_ -> irACompraView());

        // Configurar ChoiceBox
        categoryChoiceBox.setItems(categorias);
        categoryChoiceBox.setValue("Todos"); // Valor por defecto

        // Cargar productos por categoría
        categoryChoiceBox.setOnAction(_ -> cargarProductosPorCategoria(categoryChoiceBox.getValue()));

        // Cargar productos al inicializar
        cargarProductosPorCategoria("Todos");
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        try {
            // Cargar la vista de Login
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/labuenatierra/Views/LoginView.fxml"));
            Parent loginView = loader.load();

            // Obtener el Stage actual
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // Cambiar a la escena de Login
            Scene loginScene = new Scene(loginView);
            stage.setScene(loginScene);
            stage.setTitle("Login"); // Opcional: establecer el título de la ventana
            stage.show();

            // Limpiar la sesión actual
            Session.logout();
        }catch (IOException e) {
            logger.log(Level.SEVERE, "Error al cargar la vista de LoginView.fxml", e);
        }
    }

    @FXML
    private void iraTiendaInicio() {
        Stage stage = (Stage) labelLaBuenaTierra.getScene().getWindow();

        // Al cambiar a AdministradorView.fxml
        stage.setTitle("TiendaInicioView");

        NavigationUtil.cambiarVista("/org/example/labuenatierra/Views/TiendaInicioView.fxml", stage);

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
            logger.log(Level.SEVERE, "Error al cargar la vista de CompraView.fxml", e);
        }
    }

    private void cargarProductosPorCategoria(String categoria) {
        gridPaneProductos.getChildren().clear(); // Limpiar productos previos

        List<Productos> productos = loadProducts(categoria);
        int row = 0;
        int column = 0;

        for (Productos producto : productos) {
            VBox vbox = crearProductoVBox(producto); // Crear la vista para el producto
            gridPaneProductos.add(vbox, column, row); // Añadir el VBox al GridPane

            column++;
            if (column > 3) { // Cambiar a la siguiente fila después de 3 productos
                column = 0;
                row++;
            }
        }
    }

    private List<Productos> loadProducts(String categoria) {
        List<Productos> productos = new ArrayList<>();
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
                    productos.add(new Productos(nombre, precio, imagen, descripcion));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al cargar productos desde la base de datos", e);
        }
        return productos;
    }


    // Método para crear la vista de un producto
    private VBox crearProductoVBox(Productos producto) {
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
        vbox.setOnMouseClicked(_ -> {
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
                logger.log(Level.SEVERE, "Error al cargar la vista de ProductoView.fxml", e);
            }
        });

        // Efecto de zoom cuando el mouse pasa por encima
        vbox.setOnMouseEntered(_ -> {
            vbox.setScaleX(1.1);
            vbox.setScaleY(1.1);
        });

        vbox.setOnMouseExited(_ -> {
            vbox.setScaleX(1.0);
            vbox.setScaleY(1.0);
        });

        return vbox;
    }


    private List<Productos> loadProductsByName(String nombre) {
        List<Productos> productos = new ArrayList<>();
        String query = "SELECT nombre, precio, imagen, descripcion FROM productos WHERE LOWER(nombre) LIKE LOWER(?)";

        try (PreparedStatement statement = DatabaseConnection.getConnection().prepareStatement(query)) {
            statement.setString(1, "%" + nombre + "%");

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String nombreProducto = resultSet.getString("nombre");
                    double precio = resultSet.getDouble("precio");
                    String imagen = resultSet.getString("imagen");
                    String descripcion = resultSet.getString("descripcion");
                    productos.add(new Productos(nombreProducto, precio, imagen, descripcion));
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al cargar productos desde la base de datos", e);
        }

        return productos;
    }

    private void cargarProductosPorNombre(String nombre) {
        gridPaneProductos.getChildren().clear(); // Limpiar productos previos

        List<Productos> productos = loadProductsByName(nombre);
        int row = 0;
        int column = 0;

        for (Productos producto : productos) {
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
