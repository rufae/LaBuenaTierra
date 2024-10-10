package org.example.labuenatierra.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import org.example.labuenatierra.Models.DatabaseConnection; // Importa tu clase de conexión
import org.example.labuenatierra.Models.Empleado;

public class AdministradorController {

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

            // Obtener datos de la tabla Finanzas
            String finanzasQuery = "SELECT * FROM Finanzas";
            ResultSet finanzasResultSet = statement.executeQuery(finanzasQuery);
            Label finanzasLabel = new Label("Finanzas:");
            mainContent.getChildren().add(finanzasLabel);
            while (finanzasResultSet.next()) {
                String tipo = finanzasResultSet.getString("tipo");
                double monto = finanzasResultSet.getDouble("monto");
                String fecha = finanzasResultSet.getDate("fecha").toString();
                String descripcion = finanzasResultSet.getString("descripcion");
                Label finanzaData = new Label(tipo + " - " + monto + " - " + fecha + " - " + descripcion);
                mainContent.getChildren().add(finanzaData);
            }

            // Obtener datos de la tabla Marketing
            String marketingQuery = "SELECT * FROM Marketing";
            ResultSet marketingResultSet = statement.executeQuery(marketingQuery);
            Label marketingLabel = new Label("Marketing:");
            mainContent.getChildren().add(marketingLabel);
            while (marketingResultSet.next()) {
                String nombreCampaña = marketingResultSet.getString("nombre_campaña");
                String descripcion = marketingResultSet.getString("descripcion");
                String fechaInicio = marketingResultSet.getDate("fecha_inicio").toString();
                String fechaFin = marketingResultSet.getDate("fecha_fin").toString();
                String colaborador = marketingResultSet.getString("colaborador");
                Label marketingData = new Label(nombreCampaña + " - " + descripcion + " - " + fechaInicio + " - " + fechaFin + " - " + colaborador);
                mainContent.getChildren().add(marketingData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar los datos: " + e.getMessage());
            mainContent.getChildren().add(errorLabel);
        }
    }

    // Método para mostrar información de clientes
    private void showClientes() {
        mainContent.getChildren().clear(); // Limpiar contenido actual

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Clientes")) {

            // Mostrar la información de los clientes
            StringBuilder clientesInfo = new StringBuilder("Información de los clientes:\n");
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String direccion = rs.getString("direccion");
                String comentarios = rs.getString("comentarios");
                String programa_fidelizacion = rs.getString("programa_fidelizacion");

                clientesInfo.append(String.format("Nombre: %s, Teléfono: %s, Email: %s, Dirección: %s, comentarios: %s, programa_fidelizacion: %s\n",
                        nombre, telefono, email, direccion, comentarios, programa_fidelizacion));
            }

            Label clientesLabel = new Label(clientesInfo.toString());
            mainContent.getChildren().add(clientesLabel); // Añadir nuevo contenido

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar los clientes.");
            mainContent.getChildren().add(errorLabel); // Mostrar mensaje de error
        }
    }

    // Método para mostrar información de inventario
    private void showInventario() {
        mainContent.getChildren().clear(); // Limpiar contenido actual

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Productos")) {

            // Mostrar la información de los productos
            StringBuilder inventarioInfo = new StringBuilder("Información del inventario:\n");
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String imagen = rs.getString("imagen");
                String categoria = rs.getString("categoria");

                inventarioInfo.append(String.format("Nombre: %s, Precio: %.2f, Imagen: %s, Categoría: %s\n",
                        nombre, precio, imagen, categoria));
            }

            Label inventarioLabel = new Label(inventarioInfo.toString());
            mainContent.getChildren().add(inventarioLabel); // Añadir nuevo contenido

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar el inventario.");
            mainContent.getChildren().add(errorLabel); // Mostrar mensaje de error
        }
    }

    // Método para mostrar información de proveedores
    private void showProveedores() {
        mainContent.getChildren().clear(); // Limpiar contenido actual

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM Proveedores")) {

            // Mostrar la información de los proveedores
            StringBuilder proveedoresInfo = new StringBuilder("Información de los proveedores:\n");
            while (rs.next()) {
                String nombre = rs.getString("nombre");
                String contacto = rs.getString("contacto");
                String direccion = rs.getString("direccion");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String calidad_producto = rs.getString("calidad_producto");

                proveedoresInfo.append(String.format("Nombre: %s, Contacto: %s, Dirección: %s, Teléfono: %s, Email: %s, Calidad: %s\n",
                        nombre, contacto, direccion, telefono, email, calidad_producto));
            }

            Label proveedoresLabel = new Label(proveedoresInfo.toString());
            mainContent.getChildren().add(proveedoresLabel); // Añadir nuevo contenido

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar los proveedores.");
            mainContent.getChildren().add(errorLabel); // Mostrar mensaje de error
        }
    }

    // Método para volver al contenido inicial
    private void showInicio() {
        mainContent.getChildren().clear(); // Limpiar contenido actual
        Label Bienvenida = new Label("¡Bienvenido al sistema!");
        mainContent.getChildren().addAll(Bienvenida); // Añadir contenido de inicio
    }

    @FXML
    private void redirectShop() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/labuenatierra/Views/TiendaInicioView.fxml"));
        Parent tiendaInicioView = fxmlLoader.load();

        // Obtener el Stage desde el evento de clic en lugar del label
        Stage stage = (Stage) buttonShop.getScene().getWindow();

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

            StringBuilder empleadosInfo = new StringBuilder("Información de los Empleados:\n\n");

            while (resultSet.next()) {
                int idEmpleado = resultSet.getInt("id_empleado");
                String nombreApellidos = resultSet.getString("nombre_apellidos");
                String email = resultSet.getString("email");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                double salario = resultSet.getDouble("salario");
                String puestoTrabajo = resultSet.getString("puesto_trabajo");

                empleadosInfo.append("ID: ").append(idEmpleado)
                        .append(", Nombre: ").append(nombreApellidos)
                        .append(", Email: ").append(email)
                        .append(", Teléfono: ").append(telefono)
                        .append(", Dirección: ").append(direccion)
                        .append(", Salario: ").append(salario)
                        .append(", Puesto: ").append(puestoTrabajo)
                        .append("\n");
            }

            Label empleadosLabel = new Label(empleadosInfo.toString());
            mainContent.getChildren().add(empleadosLabel); // Añadir nuevo contenido

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al obtener la información de los empleados.");
            mainContent.getChildren().add(errorLabel);
        }
    }

    @FXML
    private void showAddEmployeeForm() {
        // Limpiar contenido actual
        mainContent.getChildren().clear();

        // Crear campos de texto para cada dato del empleado
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
        addEmployeeButton.setOnAction(event -> {
            addEmployee(nombreField.getText(), emailField.getText(), telefonoField.getText(),
                    direccionField.getText(), Double.parseDouble(salarioField.getText()), puestoField.getText());
        });

        // Añadir los elementos al mainContent
        mainContent.getChildren().addAll(
                nombreLabel, nombreField,
                emailLabel, emailField,
                telefonoLabel, telefonoField,
                direccionLabel, direccionField,
                salarioLabel, salarioField,
                puestoLabel, puestoField,
                addEmployeeButton);
    }

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
                mainContent.getChildren().add(successLabel);
            } else {
                Label errorLabel = new Label("No se pudo agregar el empleado.");
                mainContent.getChildren().add(errorLabel);
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al agregar empleado: " + e.getMessage());
            mainContent.getChildren().add(errorLabel);
        }

    }

}
