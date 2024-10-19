package org.example.labuenatierra.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import org.example.labuenatierra.Models.DatabaseConnection; // Importa tu clase de conexión

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
        clientesBtn.setOnAction(_ -> showClientes());
        inventarioBtn.setOnAction(_ -> showInventario());
        proveedoresBtn.setOnAction(_ -> showProveedores());
        listarEmpleadosBtn.setOnAction(_ -> showEmpleados());
        inicioBtn.setOnAction(_ -> showInicio());

        showInicio();
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
                int finanzaId = finanzasResultSet.getInt("id_finanza"); // Suponiendo que el ID es "id_finanza"
                String tipo = finanzasResultSet.getString("tipo");
                double monto = finanzasResultSet.getDouble("monto");
                String fecha = finanzasResultSet.getDate("fecha").toString();
                String descripcion = finanzasResultSet.getString("descripcion");

                // Crear un contenedor para cada registro de finanzas
                HBox finanzaItem = new HBox(10);
                Label finanzaData = new Label(tipo + " - " + monto + " - " + fecha + " - " + descripcion);

                // Botón de edición
                Button editButton = new Button("Editar");
                editButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                editButton.setOnAction(_ -> editFinanza(finanzaId));

                // Añadir el texto y el botón al contenedor
                finanzaItem.getChildren().addAll(finanzaData, editButton);
                finanzasContainer.getChildren().add(finanzaItem);
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
                int marketingId = marketingResultSet.getInt("id_campaña");
                String nombreCampania = marketingResultSet.getString("nombre_campaña");
                String descripcion = marketingResultSet.getString("descripcion");
                String fechaInicio = marketingResultSet.getDate("fecha_inicio").toString();
                String fechaFin = marketingResultSet.getDate("fecha_fin").toString();
                String colaborador = marketingResultSet.getString("colaborador");

                // Crear un contenedor para cada registro de marketing
                HBox marketingItem = new HBox(10);
                Label marketingData = new Label(nombreCampania + " - " + descripcion + " - " + fechaInicio + " - " + fechaFin + " - " + colaborador);

                // Botón de edición
                Button editButton = new Button("Editar");
                editButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                editButton.setOnAction(_ -> editMarketing(marketingId));

                // Añadir el texto y el botón al contenedor
                marketingItem.getChildren().addAll(marketingData, editButton);
                marketingContainer.getChildren().add(marketingItem);
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

    // Método para editar una entrada de Marketing
    private void editMarketing(int marketingId) {
        mainContent.getChildren().clear(); // Limpiar el contenido actual

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Marketing WHERE id_campaña = ?")) {

            stmt.setInt(1, marketingId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Crear campos de texto para la edición de los datos
                TextField nombreCampaniaField = new TextField(rs.getString("nombre_campaña"));
                TextField descripcionField = new TextField(rs.getString("descripcion"));
                DatePicker fechaInicioPicker = new DatePicker(rs.getDate("fecha_inicio").toLocalDate());
                DatePicker fechaFinPicker = new DatePicker(rs.getDate("fecha_fin").toLocalDate());
                TextField colaboradorField = new TextField(rs.getString("colaborador"));

                // Botón para guardar cambios
                Button saveButton = new Button("Guardar");
                saveButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

                // Acción del botón "Guardar"
                saveButton.setOnAction(_ -> saveMarketingChanges(
                        marketingId,
                        nombreCampaniaField.getText(),
                        descripcionField.getText(),
                        fechaInicioPicker.getValue().toString(),
                        fechaFinPicker.getValue().toString(),
                        colaboradorField.getText()
                ));

                // Añadir campos al VBox
                VBox editContainer = new VBox(10);
                editContainer.setPadding(new Insets(20));
                editContainer.getChildren().addAll(
                        new Label("Nombre de la Campaña:"), nombreCampaniaField,
                        new Label("Descripción:"), descripcionField,
                        new Label("Fecha de Inicio:"), fechaInicioPicker,
                        new Label("Fecha de Fin:"), fechaFinPicker,
                        new Label("Colaborador:"), colaboradorField,
                        saveButton
                );

                // Mostrar el formulario de edición
                mainContent.getChildren().add(editContainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar la entrada de marketing para edición.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            mainContent.getChildren().add(errorLabel);
        }
    }

    // Método para guardar los cambios en una entrada de Marketing
    private void saveMarketingChanges(int marketingId, String nombreCampania, String descripcion, String fechaInicio, String fechaFin, String colaborador) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Marketing SET nombre_campaña = ?, descripcion = ?, fecha_inicio = ?, fecha_fin = ?, colaborador = ? WHERE id_campaña = ?")) {

            stmt.setString(1, nombreCampania);
            stmt.setString(2, descripcion);
            stmt.setDate(3, java.sql.Date.valueOf(fechaInicio));
            stmt.setDate(4, java.sql.Date.valueOf(fechaFin));
            stmt.setString(5, colaborador);
            stmt.setInt(6, marketingId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Mostrar mensaje de éxito y recargar la vista
                handlePlanFinanciero();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Entrada de marketing actualizada exitosamente.");
                alert.showAndWait();
            } else {
                // Mostrar mensaje de error si no se actualizó ningún registro
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo actualizar la entrada de marketing.");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar los cambios.");
            alert.showAndWait();
        }
    }


    // Método para editar una entrada de Finanzas
    private void editFinanza(int finanzaId) {
        mainContent.getChildren().clear(); // Limpiar el contenido actual

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Finanzas WHERE id_finanza = ?")) {

            stmt.setInt(1, finanzaId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Crear campos de texto para la edición de los datos
                TextField tipoField = new TextField(rs.getString("tipo"));
                TextField montoField = new TextField(String.valueOf(rs.getDouble("monto")));
                DatePicker fechaPicker = new DatePicker(rs.getDate("fecha").toLocalDate());
                TextField descripcionField = new TextField(rs.getString("descripcion"));

                // Botón para guardar cambios
                Button saveButton = new Button("Guardar");
                saveButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

                // Acción del botón "Guardar"
                saveButton.setOnAction(_ -> saveFinanzaChanges(finanzaId, tipoField.getText(),
                        Double.parseDouble(montoField.getText()), fechaPicker.getValue().toString(),
                        descripcionField.getText()));

                // Añadir campos al VBox
                VBox editContainer = new VBox(10);
                editContainer.setPadding(new Insets(20));
                editContainer.getChildren().addAll(
                        new Label("Tipo:"), tipoField,
                        new Label("Monto:"), montoField,
                        new Label("Fecha:"), fechaPicker,
                        new Label("Descripción:"), descripcionField,
                        saveButton
                );

                // Mostrar el formulario de edición
                mainContent.getChildren().add(editContainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar la entrada de finanzas para edición.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            mainContent.getChildren().add(errorLabel);
        }
    }

    // Método para guardar los cambios en una entrada de Finanzas
    private void saveFinanzaChanges(int finanzaId, String tipo, double monto, String fecha, String descripcion) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Finanzas SET tipo = ?, monto = ?, fecha = ?, descripcion = ? WHERE id_finanza = ?")) {

            stmt.setString(1, tipo);
            stmt.setDouble(2, monto);
            stmt.setDate(3, java.sql.Date.valueOf(fecha));
            stmt.setString(4, descripcion);
            stmt.setInt(5, finanzaId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Mostrar mensaje de éxito y recargar la vista
                handlePlanFinanciero();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Entrada de finanzas actualizada exitosamente.");
                alert.showAndWait();
            } else {
                // Mostrar mensaje de error si no se actualizó ningún registro
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo actualizar la entrada de finanzas.");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar los cambios.");
            alert.showAndWait();
        }
    }

// Similarmente, se puede implementar el método "editMarketing" y "saveMarketingChanges"
// para la edición de las entradas de marketing.



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
                int clienteId = rs.getInt("id_cliente"); // Suponiendo que el ID del cliente es "id_cliente"
                String nombre = rs.getString("nombre");
                String telefono = rs.getString("telefono");
                String email = rs.getString("email");
                String direccion = rs.getString("direccion");
                String comentarios = rs.getString("comentarios");
                String programaFidelizacion = rs.getString("programa_fidelizacion");

                // Crear un VBox para cada cliente
                VBox clienteBox = new VBox(5); // 5px de espaciado entre líneas
                clienteBox.setStyle("-fx-background-color: #ecf0f1; -fx-padding: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

                // Información del cliente
                Label nombreLabel = new Label("Nombre: " + nombre);
                nombreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #2c3e50;");

                Label telefonoLabel = new Label("Teléfono: " + telefono);
                Label emailLabel = new Label("Email: " + email);
                Label direccionLabel = new Label("Dirección: " + direccion);
                Label comentariosLabel = new Label("Comentarios: " + comentarios);
                Label fidelizacionLabel = new Label("Programa de Fidelización: " + programaFidelizacion);

                // Aplicar estilos a los textos
                telefonoLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                emailLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                direccionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                comentariosLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");
                fidelizacionLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #34495e;");

                // Botón de edición
                Button editButton = new Button("Editar");
                editButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

                // Acción del botón "Editar"
                editButton.setOnAction(_ -> editCliente(clienteId));

                // Añadir las etiquetas y el botón al VBox del cliente
                clienteBox.getChildren().addAll(nombreLabel, telefonoLabel, emailLabel, direccionLabel, comentariosLabel, fidelizacionLabel, editButton);

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

    // Método para editar un cliente
    private void editCliente(int clienteId) {
        mainContent.getChildren().clear(); // Limpiar el contenido actual

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Clientes WHERE id_cliente = ?")) {

            stmt.setInt(1, clienteId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Crear campos de texto para la edición de los datos
                TextField nombreField = new TextField(rs.getString("nombre"));
                TextField telefonoField = new TextField(rs.getString("telefono"));
                TextField emailField = new TextField(rs.getString("email"));
                TextField direccionField = new TextField(rs.getString("direccion"));
                TextField comentariosField = new TextField(rs.getString("comentarios"));
                TextField fidelizacionField = new TextField(rs.getString("programa_fidelizacion"));

                // Botón para guardar cambios
                Button saveButton = new Button("Guardar");
                saveButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

                // Acción del botón "Guardar"
                saveButton.setOnAction(_ -> saveClienteChanges(clienteId, nombreField.getText(),
                        telefonoField.getText(), emailField.getText(), direccionField.getText(),
                        comentariosField.getText(), fidelizacionField.getText()));

                // Añadir campos al VBox
                VBox editContainer = new VBox(10);
                editContainer.setPadding(new Insets(20));
                editContainer.getChildren().addAll(
                        new Label("Nombre:"), nombreField,
                        new Label("Teléfono:"), telefonoField,
                        new Label("Email:"), emailField,
                        new Label("Dirección:"), direccionField,
                        new Label("Comentarios:"), comentariosField,
                        new Label("Programa de Fidelización:"), fidelizacionField,
                        saveButton
                );

                // Mostrar el formulario de edición
                mainContent.getChildren().add(editContainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar el cliente para edición.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            mainContent.getChildren().add(errorLabel);
        }
    }

    // Método para guardar los cambios del cliente
    private void saveClienteChanges(int clienteId, String nombre, String telefono, String email,
                                    String direccion, String comentarios, String fidelizacion) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Clientes SET nombre = ?, telefono = ?, email = ?, direccion = ?, comentarios = ?, programa_fidelizacion = ? WHERE id_cliente = ?")) {

            stmt.setString(1, nombre);
            stmt.setString(2, telefono);
            stmt.setString(3, email);
            stmt.setString(4, direccion);
            stmt.setString(5, comentarios);
            stmt.setString(6, fidelizacion);
            stmt.setInt(7, clienteId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Mostrar mensaje de éxito y recargar la lista de clientes
                showClientes();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cliente actualizado exitosamente.");
                alert.showAndWait();
            } else {
                // Mostrar mensaje de error si no se actualizó ningún registro
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo actualizar el cliente.");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar los cambios.");
            alert.showAndWait();
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
                int productoId = rs.getInt("id_producto"); // Suponiendo que el ID del producto es "id_producto"
                String nombre = rs.getString("nombre");
                double precio = rs.getDouble("precio");
                String imagen = rs.getString("imagen");
                String categoria = rs.getString("categoria");

                // Crear un VBox para la información del producto
                VBox productoBox = new VBox(5); // Espaciado entre los elementos del producto
                productoBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 10; -fx-border-color: #bdc3c7; -fx-border-radius: 5;");

                // Información del producto
                Label productoData = new Label(String.format("Nombre: %s, Precio: %.2f, Imagen: %s, Categoría: %s",
                        nombre, precio, imagen, categoria));
                productoData.setStyle("-fx-font-size: 16px; -fx-text-fill: #34495e;");

                // Botón de edición
                Button editButton = new Button("Editar");
                editButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");

                // Acción del botón "Editar"
                editButton.setOnAction(_ -> editProducto(productoId));

                // Añadir la información y el botón al VBox del producto
                productoBox.getChildren().addAll(productoData, editButton);

                // Añadir el VBox del producto al contenedor principal de productos
                productosContainer.getChildren().add(productoBox);
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

    private void editProducto(int productoId) {
        // Limpiar el contenido actual
        mainContent.getChildren().clear();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Productos WHERE id_producto = ?")) {

            stmt.setInt(1, productoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Crear campos de texto para la edición de los datos
                TextField nombreField = new TextField(rs.getString("nombre"));
                TextField precioField = new TextField(String.valueOf(rs.getDouble("precio")));
                TextField imagenField = new TextField(rs.getString("imagen"));
                TextField categoriaField = new TextField(rs.getString("categoria"));

                // Botón para guardar cambios
                Button saveButton = new Button("Guardar");
                saveButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

                // Acción del botón "Guardar"
                saveButton.setOnAction(_ -> saveProductoChanges(productoId, nombreField.getText(),
                        Double.parseDouble(precioField.getText()), imagenField.getText(), categoriaField.getText()));

                // Añadir campos al VBox
                VBox editContainer = new VBox(10);
                editContainer.setPadding(new Insets(20));
                editContainer.getChildren().addAll(
                        new Label("Nombre:"), nombreField,
                        new Label("Precio:"), precioField,
                        new Label("Imagen:"), imagenField,
                        new Label("Categoría:"), categoriaField,
                        saveButton
                );

                // Mostrar el formulario de edición
                mainContent.getChildren().add(editContainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar el producto para edición.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            mainContent.getChildren().add(errorLabel);
        }
    }

    private void saveProductoChanges(int productoId, String nombre, double precio, String imagen, String categoria) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Productos SET nombre = ?, precio = ?, imagen = ?, categoria = ? WHERE id_producto = ?")) {

            stmt.setString(1, nombre);
            stmt.setDouble(2, precio);
            stmt.setString(3, imagen);
            stmt.setString(4, categoria);
            stmt.setInt(5, productoId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Mostrar mensaje de éxito y recargar la lista de productos
                showInventario();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Producto actualizado exitosamente.");
                alert.showAndWait();
            } else {
                // Mostrar mensaje de error si no se actualizó ningún registro
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo actualizar el producto.");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar los cambios.");
            alert.showAndWait();
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

                // Botón de edición
                Button editButton = new Button("Editar");
                editButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                int proveedorId = rs.getInt("id_proveedor"); // Suponiendo que tienes un campo 'id' para identificar al proveedor

                // Acción del botón "Editar"
                editButton.setOnAction(_ -> editProveedor(proveedorId));

                // Añadir las etiquetas y el botón al VBox del proveedor
                proveedorBox.getChildren().addAll(nombreLabel, contactoLabel, direccionLabel, telefonoLabel, emailLabel, calidadLabel, editButton);

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

    private void editProveedor(int proveedorId) {
        // Limpiar el contenido actual
        mainContent.getChildren().clear();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Proveedores WHERE id_proveedor = ?")) {

            stmt.setInt(1, proveedorId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Crear campos de texto para la edición de los datos
                TextField nombreField = new TextField(rs.getString("nombre"));
                TextField contactoField = new TextField(rs.getString("contacto"));
                TextField direccionField = new TextField(rs.getString("direccion"));
                TextField telefonoField = new TextField(rs.getString("telefono"));
                TextField emailField = new TextField(rs.getString("email"));
                TextField calidadField = new TextField(rs.getString("calidad_producto"));

                // Botón para guardar cambios
                Button saveButton = new Button("Guardar");
                saveButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

                // Acción del botón "Guardar"
                saveButton.setOnAction(_ -> saveProveedorChanges(proveedorId, nombreField.getText(), contactoField.getText(),
                        direccionField.getText(), telefonoField.getText(), emailField.getText(), calidadField.getText()));

                // Añadir campos al VBox
                VBox editContainer = new VBox(10);
                editContainer.setPadding(new Insets(20));
                editContainer.getChildren().addAll(
                        new Label("Nombre:"), nombreField,
                        new Label("Contacto:"), contactoField,
                        new Label("Dirección:"), direccionField,
                        new Label("Teléfono:"), telefonoField,
                        new Label("Email:"), emailField,
                        new Label("Calidad del Producto:"), calidadField,
                        saveButton
                );

                // Mostrar el formulario de edición
                mainContent.getChildren().add(editContainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar el proveedor para edición.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            mainContent.getChildren().add(errorLabel);
        }
    }

    private void saveProveedorChanges(int proveedorId, String nombre, String contacto, String direccion, String telefono, String email, String calidad) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Proveedores SET nombre = ?, contacto = ?, direccion = ?, telefono = ?, email = ?, calidad_producto = ? WHERE id_proveedor = ?")) {

            stmt.setString(1, nombre);
            stmt.setString(2, contacto);
            stmt.setString(3, direccion);
            stmt.setString(4, telefono);
            stmt.setString(5, email);
            stmt.setString(6, calidad);
            stmt.setInt(7, proveedorId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Mostrar mensaje de éxito y recargar la lista de proveedores
                showProveedores();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Proveedor actualizado exitosamente.");
                alert.showAndWait();
            } else {
                // Mostrar mensaje de error si no se actualizó ningún registro
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo actualizar el proveedor.");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar los cambios.");
            alert.showAndWait();
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

                // Crear botón para editar el empleado
                Button editButton = new Button("Editar");
                editButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white;");
                editButton.setOnAction(_ -> editEmpleado(idEmpleado)); // Llamar al método editEmpleado

                // Contenedor horizontal para el empleado y el botón de editar
                HBox empleadoItem = new HBox(10, empleadoData, editButton);
                empleadoItem.setAlignment(Pos.CENTER_LEFT);

                // Añadir al contenedor de empleados
                empleadosContainer.getChildren().add(empleadoItem);
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


    // Método para editar un empleado
    private void editEmpleado(int empleadoId) {
        mainContent.getChildren().clear(); // Limpiar el contenido actual

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Empleados WHERE id_empleado = ?")) {

            stmt.setInt(1, empleadoId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Crear campos de texto para editar los datos del empleado
                TextField nombreApellidosField = new TextField(rs.getString("nombre_apellidos"));
                TextField emailField = new TextField(rs.getString("email"));
                TextField telefonoField = new TextField(rs.getString("telefono"));
                TextField direccionField = new TextField(rs.getString("direccion"));
                TextField salarioField = new TextField(Double.toString(rs.getDouble("salario")));
                TextField puestoTrabajoField = new TextField(rs.getString("puesto_trabajo"));

                // Botón para guardar los cambios
                Button saveButton = new Button("Guardar");
                saveButton.setStyle("-fx-background-color: #27ae60; -fx-text-fill: white;");

                // Acción del botón "Guardar"
                saveButton.setOnAction(_ -> saveEmpleadoChanges(
                        empleadoId,
                        nombreApellidosField.getText(),
                        emailField.getText(),
                        telefonoField.getText(),
                        direccionField.getText(),
                        Double.parseDouble(salarioField.getText()),
                        puestoTrabajoField.getText()
                ));

                // Añadir los campos al VBox
                VBox editContainer = new VBox(10);
                editContainer.setPadding(new Insets(20));
                editContainer.getChildren().addAll(
                        new Label("Nombre y Apellidos:"), nombreApellidosField,
                        new Label("Email:"), emailField,
                        new Label("Teléfono:"), telefonoField,
                        new Label("Dirección:"), direccionField,
                        new Label("Salario:"), salarioField,
                        new Label("Puesto de Trabajo:"), puestoTrabajoField,
                        saveButton
                );

                // Mostrar el formulario de edición
                mainContent.getChildren().add(editContainer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            Label errorLabel = new Label("Error al cargar los datos del empleado para edición.");
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-size: 16px;");
            mainContent.getChildren().add(errorLabel);
        }
    }

    // Método para guardar los cambios en un empleado
    private void saveEmpleadoChanges(int empleadoId, String nombreApellidos, String email, String telefono, String direccion, double salario, String puestoTrabajo) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE Empleados SET nombre_apellidos = ?, email = ?, telefono = ?, direccion = ?, salario = ?, puesto_trabajo = ? WHERE id_empleado = ?")) {

            stmt.setString(1, nombreApellidos);
            stmt.setString(2, email);
            stmt.setString(3, telefono);
            stmt.setString(4, direccion);
            stmt.setDouble(5, salario);
            stmt.setString(6, puestoTrabajo);
            stmt.setInt(7, empleadoId);

            int rowsUpdated = stmt.executeUpdate();

            if (rowsUpdated > 0) {
                // Mostrar mensaje de éxito y recargar la vista
                showEmpleados();
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Empleado actualizado exitosamente.");
                alert.showAndWait();
            } else {
                // Mostrar mensaje de error si no se actualizó ningún registro
                Alert alert = new Alert(Alert.AlertType.ERROR, "No se pudo actualizar el empleado.");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error al guardar los cambios.");
            alert.showAndWait();
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
        addEmployeeButton.setOnAction(_ -> {
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
