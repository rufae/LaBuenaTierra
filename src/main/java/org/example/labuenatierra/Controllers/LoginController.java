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

import java.io.IOException;

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

        // Escuchar cambios en el tipo de usuario seleccionado
        userTypeChoice.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if ("Administrador".equals(newValue)) {
                passwordField.setVisible(true);
                passwordLabel.setVisible(true);
            } else {
                passwordField.setVisible(false);
                passwordLabel.setVisible(false);
            }
        });

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
        String userType = userTypeChoice.getValue();

        // Validar que se haya ingresado un nombre de usuario
        if (username == null || username.trim().isEmpty()) {
            errorMessage.setText("Por favor ingrese un nombre de usuario.");
            errorMessage.setVisible(true);
            return;
        }

        if ("Administrador".equals(userType)) {
            // Validar la contraseña para administrador
            String password = passwordField.getText();
            if (password == null || password.trim().isEmpty()) {
                errorMessage.setText("Por favor ingrese una contraseña.");
                errorMessage.setVisible(true);
            } else if (!password.equals("123")) { // Cambia por la validación real
                errorMessage.setText("Contraseña incorrecta.");
                errorMessage.setVisible(true);
            } else {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/labuenatierra/Views/HomeView.fxml"));
                Parent admin = loader.load();
                Scene adminScene = new Scene(admin);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

                window.setScene(adminScene);
                window.show();

                System.out.println("Iniciando sesión como Administrador...");
                errorMessage.setVisible(false);
            }
        } else if ("Cliente".equals(userType)) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/labuenatierra/Views/TiendaInicioView.fxml"));
            Parent tiendaView = loader.load();
            Scene tiendaScene = new Scene(tiendaView);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();

            window.setScene(tiendaScene);
            window.show();

            System.out.println("Iniciando sesión como Cliente...");
            errorMessage.setVisible(false);
        } else {
            errorMessage.setText("Por favor seleccione un tipo de usuario.");
            errorMessage.setVisible(true);
        }
    }
}
