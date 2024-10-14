package org.example.labuenatierra.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import org.example.labuenatierra.Models.DatabaseConnection; // Importa tu clase de conexión
import org.example.labuenatierra.Models.Empleado;

public class AdministradorController {

    public Button logoutButton;
    public Button planFinancieroBtn;
    @FXML
    private VBox mainContent;

    @FXML
    private Button clientesBtn;

    @FXML
    private Button inventarioBtn;

    @FXML
    private Button inicioBtn;

    @FXML
    private Button buttonShop;

    @FXML
    private Button proveedoresBtn;
    @FXML
    private Button listarEmpleadosBtn;
    @FXML
    private VBox addEmployeeForm;

    @FXML
    private TextField nombreField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField telefonoField;

    @FXML
    private TextField direccionField;

    @FXML
    private TextField salarioField;

    @FXML
    private TextField puestoField;

    @FXML
    private void initialize() {
        // Configurar acciones para los botones
        clientesBtn.setOnAction(event -> showClientes());
        inventarioBtn.setOnAction(event -> showInventario());
        proveedoresBtn.setOnAction(event -> showProveedores());
        listarEmpleadosBtn.setOnAction(event -> showEmpleados());
        inicioBtn.setOnAction(event -> showInicio());

    }

    @FXML
    private void handlePlanFinanciero() {
        // Limpiar contenido previo
        mainContent.getChildren().clear();

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // VBox principal para contener la información
            VBox mainContainer = new VBox(20); // Espaciado entre secciones
            mainContainer.setPadding(new Insets(20, 20, 20, 20));

            // Obtener datos de la tabla Finanzas
            String finanzasQuery = "SELECT * FROM Finanzas";
            ResultSet finanzasResultSet = statement.executeQuery(finanzasQuery);

            // Sección de Finanzas
            Label finanzasLabel = new Label("Finanzas:");
            finanzasLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            VBox finanzasContainer = new VBox(5); // Espaciado entre elementos de finanzas
            finanzasContainer.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

            while (finanzasResultSet.next()) {
                String tipo = finanzasResultSet.getString("tipo");
                double monto = finanzasResultSet.getDouble("monto");
                String fecha = finanzasResultSet.getDate("fecha").toString();
                String descripcion = finanzasResultSet.getString("descripcion");
                Label finanzaData = new Label(tipo + " - " + monto + " - " + fecha + " - " + descripcion);
                finanzasContainer.getChildren().add(finanzaData);
            }

            // Obtener datos de la tabla Marketing
            String marketingQuery = "SELECT * FROM Marketing";
            ResultSet marketingResultSet = statement.executeQuery(marketingQuery);

            // Sección de Marketing
            Label marketingLabel = new Label("Marketing:");
            marketingLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            VBox marketingContainer = new VBox(5); // Espaciado entre elementos de marketing
            marketingContainer.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

            while (marketingResultSet.next()) {
                String nombreCampaña = marketingResultSet.getString("nombre_campaña");
                String descripcion = marketingResultSet.getString("descripcion");
                String fechaInicio = marketingResultSet.getDate("fecha_inicio").toString();
                String fechaFin = marketingResultSet.getDate("fecha_fin").toString();
                String colaborador = marketingResultSet.getString("colaborador");
                Label marketingData = new Label(nombreCampaña + " - " + descripcion + " - " + fechaInicio + " - " + fechaFin + " - " + colaborador);
                marketingContainer.getChildren().add(marketingData);
            }

            // Añadir las secciones al contenedor principal
            mainContainer.getChildren().addAll(finanzasLabel, finanzasContainer, marketingLabel, marketingContainer);

            // Crear un ScrollPane para permitir el desplazamiento
            ScrollPane scrollPane = new ScrollPane(mainContainer);
            scrollPane.setFitToWidth(true); // Ajustar el ancho del contenido al ancho del ScrollPane
            scrollPane.setPrefHeight(400); // Establecer una altura preferida para el ScrollPane
            scrollPane.setStyle("-fx-background-color: transparent;"); // Hacer que el fondo sea transparente

            // Añadir el ScrollPane al mainContent
            mainContent.getChildren().add(scrollPane);

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar los datos: " + e.getMessage());
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            mainContent.getChildren().add(errorLabel); // Mostrar mensaje de error
        }
    }


    // Método para mostrar información de clientes
    private void showClientes() {
        mainContent.getChildren().clear(); // Limpiar contenido actual

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Clientes")) {

            // Título principal estilizado
            Label titulo = new Label("Lista de Clientes");
            titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            // VBox para contener la información de los clientes
            VBox clientesContainer = new VBox(10); // 10px de espaciado entre elementos
            clientesContainer.setPadding(new Insets(20, 20, 20, 20));

            // Iterar sobre los resultados y crear un bloque visual para cada cliente
            while (rs.next()) {
                // Crear un VBox para cada cliente
                VBox clienteBox = new VBox(5); // 5px de espaciado entre líneas
                clienteBox.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

                // Información del cliente
                Label nombreLabel = new Label("Nombre: " + rs.getString("nombre"));
                nombreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

                Label telefonoLabel = new Label("Teléfono: " + rs.getString("telefono"));
                Label emailLabel = new Label("Email: " + rs.getString("email"));
                Label direccionLabel = new Label("Dirección: " + rs.getString("direccion"));
                Label comentariosLabel = new Label("Comentarios: " + rs.getString("comentarios"));
                Label fidelizacionLabel = new Label("Programa de Fidelización: " + rs.getString("programa_fidelizacion"));

                // Aplicar estilos a los textos
                telefonoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                direccionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                comentariosLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                fidelizacionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

                // Añadir las etiquetas al VBox del cliente
                clienteBox.getChildren().addAll(nombreLabel, telefonoLabel, emailLabel, direccionLabel, comentariosLabel, fidelizacionLabel);

                // Añadir el VBox del cliente al contenedor principal
                clientesContainer.getChildren().add(clienteBox);
            }

            // Crear un ScrollPane para permitir el desplazamiento
            ScrollPane scrollPane = new ScrollPane(clientesContainer);
            scrollPane.setFitToWidth(true); // Ajustar el ancho del contenido al ancho del ScrollPane
            scrollPane.setPrefHeight(400); // Establecer una altura preferida para el ScrollPane
            scrollPane.setStyle("-fx-background-color: transparent;"); // Hacer que el fondo sea transparente

            // Añadir el título y el ScrollPane al mainContent
            mainContent.getChildren().addAll(titulo, scrollPane);

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar los clientes.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            mainContent.getChildren().add(errorLabel); // Mostrar mensaje de error
        }
    }


    @FXML
    private void showInventario() {
        mainContent.getChildren().clear(); // Limpiar contenido actual

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Productos")) {

            // VBox principal para contener la información del inventario
            VBox mainContainer = new VBox(20); // Espaciado entre productos
            mainContainer.setPadding(new Insets(20, 20, 20, 20));

            // Título de la sección
            Label inventarioLabel = new Label("Información del Inventario:");
            inventarioLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            mainContainer.getChildren().add(inventarioLabel); // Añadir el título

            // Contenedor para los productos
            VBox productosContainer = new VBox(10); // Espaciado entre productos
            productosContainer.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

            while (rs.next()) {
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String imagen = rs.getString("imagen");
                String categoria = rs.getString("categoria");

                // Crear una etiqueta con la información del producto
                Label productoData = new Label(String.format("Nombre: %s, Precio: %.2f, Imagen: %s, Categoría: %s",
                        nombre, precio, imagen, categoria));
                productoData.setStyle("-fx-font-size: 16px; -fx-text-fill: #34495e;"); // Estilo para cada producto
                productosContainer.getChildren().add(productoData); // Añadir datos del producto
            }

            // Añadir el contenedor de productos al contenedor principal
            mainContainer.getChildren().add(productosContainer);

            // Crear un ScrollPane para permitir el desplazamiento
            ScrollPane scrollPane = new ScrollPane(mainContainer);
            scrollPane.setFitToWidth(true); // Ajustar el ancho del contenido al ancho del ScrollPane
            scrollPane.setPrefHeight(400); // Establecer una altura preferida para el ScrollPane
            scrollPane.setStyle("-fx-background-color: transparent;"); // Hacer que el fondo sea transparente

            // Añadir el ScrollPane al mainContent
            mainContent.getChildren().add(scrollPane);

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar el inventario: " + e.getMessage());
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            mainContent.getChildren().add(errorLabel); // Mostrar mensaje de error
        }
    }


    // Método para mostrar información de proveedores
    private void showProveedores() {
        mainContent.getChildren().clear(); // Limpiar contenido actual

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Proveedores")) {

            // Título principal estilizado
            Label titulo = new Label("Lista de Proveedores");
            titulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

            // VBox para contener la información de los proveedores
            VBox proveedoresContainer = new VBox(10); // 10px de espaciado entre elementos
            proveedoresContainer.setPadding(new Insets(20, 20, 20, 20));

            // Iterar sobre los resultados y crear un bloque visual para cada proveedor
            while (rs.next()) {
                // Crear un VBox para cada proveedor
                VBox proveedorBox = new VBox(5); // 5px de espaciado entre líneas
                proveedorBox.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

                // Información del proveedor
                Label nombreLabel = new Label("Nombre: " + rs.getString("nombre"));
                nombreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

                Label contactoLabel = new Label("Contacto: " + rs.getString("contacto"));
                Label direccionLabel = new Label("Dirección: " + rs.getString("direccion"));
                Label telefonoLabel = new Label("Teléfono: " + rs.getString("telefono"));
                Label emailLabel = new Label("Email: " + rs.getString("email"));
                Label calidadLabel = new Label("Calidad del Producto: " + rs.getString("calidad_producto"));

                // Aplicar estilos a los textos
                contactoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                direccionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                telefonoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                calidadLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

                // Añadir las etiquetas al VBox del proveedor
                proveedorBox.getChildren().addAll(nombreLabel, contactoLabel, direccionLabel, telefonoLabel, emailLabel, calidadLabel);

                // Añadir el VBox del proveedor al contenedor principal
                proveedoresContainer.getChildren().add(proveedorBox);
            }

            // Crear un ScrollPane para permitir el desplazamiento
            ScrollPane scrollPane = new ScrollPane(proveedoresContainer);
            scrollPane.setFitToWidth(true); // Ajustar el ancho del contenido al ancho del ScrollPane
            scrollPane.setPrefHeight(400); // Establecer una altura preferida para el ScrollPane
            scrollPane.setStyle("-fx-background-color: transparent;"); // Hacer que el fondo sea transparente

            // Añadir el título y el ScrollPane al mainContent
            mainContent.getChildren().addAll(titulo, scrollPane);

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar los proveedores.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            mainContent.getChildren().add(errorLabel); // Mostrar mensaje de error
        }
    }

    // Método para volver al contenido inicial

    private void showInicio() {
        mainContent.getChildren().clear();

        Label titulo = new Label("Panel de Administración - LaBuenaTierra");
        titulo.getStyleClass().add("label-titulo");

        Label bienvenida = new Label("¡Bienvenido al sistema de gestión de LaBuenaTierra!");
        bienvenida.getStyleClass().add("label-bienvenida");

        Label descripcion = new Label("Aquí puedes gestionar clientes, productos, empleados, finanzas y más. "
                + "Utiliza el menú lateral para navegar por las diferentes secciones.");
        descripcion.getStyleClass().add("label-descripcion");

        Label espaciador = new Label(" ");
        espaciador.setMinHeight(20);

        mainContent.getChildren().addAll(titulo, bienvenida, descripcion, espaciador);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void redirectShop() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/labuenatierra/Views/TiendaInicioView.fxml"));
        Parent tiendaInicioView = fxmlLoader.load();

        // Obtener el Stage desde el evento de clic en lugar del label
        Stage stage = (Stage) buttonShop.getScene().getWindow();

        // Al cambiar a AdministradorView.fxml
        stage.setTitle("AdministradorView");

        // Cambiar la raíz de la escena
        Scene currentScene = stage.getScene();
        currentScene.setRoot(tiendaInicioView);

        // Ajustar el tamaño de la ventana
        stage.sizeToScene(); // Ajustar al tamaño de la nueva escena

    }

    // Método para mostrar información de empleados
    @FXML
    private void showEmpleados() {
        mainContent.getChildren().clear(); // Limpiar contenido actual

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM Empleados";
            ResultSet resultSet = statement.executeQuery(query);

            VBox mainContainer = new VBox(20); // Contenedor principal para los empleados
            mainContainer.setPadding(new Insets(20, 20, 20, 20));

            Label empleadosLabel = new Label("Información de los Empleados:");
            empleadosLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");
            mainContainer.getChildren().add(empleadosLabel); // Añadir título

            // Contenedor para los datos de empleados
            VBox empleadosContainer = new VBox(10);
            empleadosContainer.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

            while (resultSet.next()) {
                int idEmpleado = resultSet.getInt("id_empleado");
                String nombreApellidos = resultSet.getString("nombre_apellidos");
                String email = resultSet.getString("email");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                double salario = resultSet.getDouble("salario");
                String puestoTrabajo = resultSet.getString("puesto_trabajo");

                // Crear una etiqueta con la información del empleado
                Label empleadoData = new Label(String.format("ID: %d, Nombre: %s, Email: %s, Teléfono: %s, Dirección: %s, Salario: %.2f, Puesto: %s",
                        idEmpleado, nombreApellidos, email, telefono, direccion, salario, puestoTrabajo));
                empleadoData.setStyle("-fx-font-size: 16px; -fx-text-fill: #34495e;"); // Estilo para cada empleado
                empleadosContainer.getChildren().add(empleadoData); // Añadir datos del empleado
            }

            // Añadir el contenedor de empleados al contenedor principal
            mainContainer.getChildren().add(empleadosContainer);

            // Crear un ScrollPane para permitir el desplazamiento
            ScrollPane scrollPane = new ScrollPane(mainContainer);
            scrollPane.setFitToWidth(true); // Ajustar el ancho del contenido al ancho del ScrollPane
            scrollPane.setPrefHeight(400); // Establecer una altura preferida para el ScrollPane
            scrollPane.setStyle("-fx-background-color: transparent;"); // Hacer que el fondo sea transparente

            // Añadir el ScrollPane al mainContent
            mainContent.getChildren().add(scrollPane);

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al obtener la información de los empleados.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            mainContent.getChildren().add(errorLabel); // Mostrar mensaje de error
        }
    }

    // Método para mostrar el formulario de agregar empleados
    @FXML
    private void showAddEmployeeForm() {
        mainContent.getChildren().clear(); // Limpiar contenido actual

        // Crear campos de texto para cada dato del empleado
        VBox formContainer = new VBox(10);
        formContainer.setPadding(new Insets(20));
        formContainer.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 20; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

        Label titleLabel = new Label("Agregar Nuevo Empleado");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

        Label nombreLabel = new Label("Nombre:");
        TextField nombreField = new TextField();

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField();

        Label telefonoLabel = new Label("Teléfono:");
        TextField telefonoField = new TextField();

        Label direccionLabel = new Label("Dirección:");
        TextField direccionField = new TextField();

        Label salarioLabel = new Label("Salario:");
        TextField salarioField = new TextField();

        Label puestoLabel = new Label("Puesto de Trabajo:");
        TextField puestoField = new TextField();

        // Botón para agregar el empleado
        Button addEmployeeButton = new Button("Agregar Empleado");
        addEmployeeButton.setStyle("-fx-background-color: #2ecc71; -fx-text-fill: white; -fx-font-weight: bold;");
        addEmployeeButton.setOnAction(event -> {
            addEmployee(nombreField.getText(), emailField.getText(), telefonoField.getText(),
                    direccionField.getText(), Double.parseDouble(salarioField.getText()), puestoField.getText());
        });

        // Añadir los elementos al formContainer
        formContainer.getChildren().addAll(
                titleLabel,
                nombreLabel, nombreField,
                emailLabel, emailField,
                telefonoLabel, telefonoField,
                direccionLabel, direccionField,
                salarioLabel, salarioField,
                puestoLabel, puestoField,
                addEmployeeButton);

        mainContent.getChildren().add(formContainer); // Añadir el formulario al mainContent
    }

    // Método para agregar empleado a la base de datos
    private void addEmployee(String nombre, String email, String telefono, String direccion, double salario, String puesto) {
        String query = "INSERT INTO Empleados (nombre_apellidos, email, telefono, direccion, salario, puesto_trabajo) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, telefono);
            preparedStatement.setString(4, direccion);
            preparedStatement.setDouble(5, salario);
            preparedStatement.setString(6, puesto);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                Label successLabel = new Label("Empleado agregado con éxito.");
                successLabel.setStyle("-fx-text-fill: green; -fx-font-size: 16px;");
                mainContent.getChildren().add(successLabel);
            } else {
                Label errorLabel = new Label("No se pudo agregar el empleado.");
                errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
                mainContent.getChildren().add(errorLabel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al agregar empleado: " + e.getMessage());
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            mainContent.getChildren().add(errorLabel);
        }
    }
}
