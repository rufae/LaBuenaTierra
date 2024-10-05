package org.example.labuenatierra.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

public class HomeController {

    @FXML
    private VBox mainContent;

    @FXML
    private Button clientesBtn;

    @FXML
    private Button inventarioBtn;

    @FXML
    private Button inicioBtn;

    @FXML
    private void initialize() {
        // Configurar acción para el botón de Clientes
        clientesBtn.setOnAction(event -> showClientes());

        // Configurar acción para el botón de Inventario
        inventarioBtn.setOnAction(event -> showInventario());

        // Otras acciones
        inicioBtn.setOnAction(event -> showInicio());
    }

    // Método para mostrar información de clientes
    private void showClientes() {
        mainContent.getChildren().clear(); // Limpiar contenido actual
        Label clientesInfo = new Label("Información de los clientes");
        mainContent.getChildren().add(clientesInfo); // Añadir nuevo contenido
    }

    // Método para mostrar información de inventario
    private void showInventario() {
        mainContent.getChildren().clear(); // Limpiar contenido actual
        Label inventarioInfo = new Label("Información del inventario");
        mainContent.getChildren().add(inventarioInfo); // Añadir nuevo contenido
    }

    // Método para volver al contenido inicial
    private void showInicio() {
        mainContent.getChildren().clear(); // Limpiar contenido actual
        Label Bienvenida = new Label("¡Bienvenido al sistema!");
        mainContent.getChildren().addAll(Bienvenida); // Añadir contenido de inicio
    }
}
