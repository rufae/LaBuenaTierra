package org.example.labuenatierra.Controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.labuenatierra.Models.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernameField;
    @FXML
    private ChoiceBox<String> userTypeChoice;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label passwordLabel;
    @FXML
    private Button loginBtn;
    @FXML
    private Label errorMessage;

    @FXML
    public void initialize() {
        // Inicializar el ChoiceBox con las opciones
        userTypeChoice.setItems(FXCollections.observableArrayList("Selecciona tipo", "Cliente", "Administrador"));
        userTypeChoice.getSelectionModel().selectFirst(); // Seleccionar la primera opción ("Selecciona tipo")

        // Manejar el evento de clic del botón de inicio de sesión
        loginBtn.setOnAction(event -> {
            try {
                handleLogin(event);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void handleLogin(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String password = passwordField.getText(); // Siempre obtener la contraseña ingresada
        String userType = userTypeChoice.getValue();

        // Validar que se haya ingresado un nombre de usuario
        if (username == null || username.trim().isEmpty()) {
            errorMessage.setText("Por favor ingrese un nombre de usuario.");
            errorMessage.setVisible(true);
            return;
        }

        if ("Administrador".equals(userType)) {
            // Validar la contraseña para administrador
            if (password == null || password.trim().isEmpty()) {
                errorMessage.setText("Por favor ingrese una contraseña.");
                errorMessage.setVisible(true);
            } else if (!password.equals("123")) { // Cambia por la validación real
                errorMessage.setText("Contraseña incorrecta.");
                errorMessage.setVisible(true);
            } else {
                // Cargar vista de administrador
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/labuenatierra/Views/AdministradorView.fxml"));
                Parent admin = loader.load();
                Scene adminScene = new Scene(admin);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(adminScene);
                window.show();

                System.out.println("Iniciando sesión como Administrador...");
                errorMessage.setVisible(false);
            }
        } else if ("Cliente".equals(userType)) {
            // Validar la contraseña para cliente desde la base de datos
            if (validateClientCredentials(username, password)) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/labuenatierra/Views/TiendaInicioView.fxml"));
                Parent tiendaView = loader.load();
                Scene tiendaScene = new Scene(tiendaView);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(tiendaScene);
                window.show();

                System.out.println("Iniciando sesión como Cliente...");
                errorMessage.setVisible(false);
            } else {
                errorMessage.setText("Usuario o contraseña incorrectos.");
                errorMessage.setVisible(true);
            }
        } else {
            errorMessage.setText("Por favor seleccione un tipo de usuario.");
            errorMessage.setVisible(true);
        }
    }

    // Método para validar credenciales de cliente
    private boolean validateClientCredentials(String username, String password) {
        // Conectar a la base de datos y verificar las credenciales del cliente
        String query = "SELECT * FROM clientes WHERE nombre = ? AND contraseña = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            return resultSet.next(); // Si hay algún resultado, las credenciales son correctas
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @FXML
    private void handleNewUserLinkClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/labuenatierra/Views/NuevoUsuarioView.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
