package org.example.labuenatierra.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.labuenatierra.Models.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class NuevoUsuarioController {
    public Label errorLabel;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField addressField;
    @FXML
    private PasswordField passwordField;


    @FXML
    private void handleBackToLoginClick(ActionEvent event) {
        // Obtener el Stage actual
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        NavigationUtil.cambiarVistaCliente("/org/example/labuenatierra/Views/LoginView.fxml", stage);

        // Limpiar la sesión actual
        Session.logout();
    }

    @FXML
    private void handleSaveDataClick(ActionEvent event) {
        String username = usernameField.getText();
        String phone = phoneField.getText();
        String email = emailField.getText();
        String address = addressField.getText();
        String password = passwordField.getText();

        // Validación de los campos
        if (!validarCadena(username) || !validarCadena(address) || !validarCadena(password)) {
            mostrarAlerta("El nombre de usuario, dirección o contraseña no pueden estar vacíos.");
            return;
        }

        if (!validarTelefono(phone)) {
            mostrarAlerta("El teléfono deben de ser exactamente 9 dígitos numéricos.");
            return;
        }

        if (!validarCorreo(email)) {
            mostrarAlerta("El correo electrónico debe tener un formato válido (por ejemplo, usuario@gmail.com).");
            return;
        }

        // Guardar los datos en la base de datos
        try (Connection conn = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO clientes (nombre, telefono, email, direccion, contraseña) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, username);
                stmt.setString(2, phone);
                stmt.setString(3, email);
                stmt.setString(4, address);
                stmt.setString(5, password);
                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        NavigationUtil.cambiarVistaCliente("/org/example/labuenatierra/Views/LoginView.fxml", stage);
    }

    // Métodos de validación
    private boolean validarCadena(String cadena) {
        return cadena != null && !cadena.trim().isEmpty();
    }

    private boolean validarTelefono(String telefono) {
        return telefono.matches("\\d{9}"); // Debe contener exactamente 9 dígitos numéricos
    }

    private boolean validarCorreo(String correo) {
        return correo.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$"); // Formato de correo electrónico
    }

    // Método para mostrar una alerta al usuario
    private void mostrarAlerta(String mensaje) {
        errorLabel.setText(mensaje);
        errorLabel.setStyle("-fx-text-fill: red;");
    }
}
