package org.example.labuenatierra.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class ProductoController {

    @FXML
    private ImageView productoImage;

    @FXML
    private Label nombreProductoLabel;

    @FXML
    private Label precioLabel;

    @FXML
    private Label labelLaBuenaTierra;

    @FXML
    private void initialize() {
        // Cargar la imagen desde el classpath
        InputStream imageStream = getClass().getResourceAsStream("/images/palmerasdehuevo.jpg");
        if (imageStream != null) {
            productoImage.setImage(new Image(imageStream));
        } else {
            System.out.println("Imagen no encontrada en la ruta especificada.");
        }
    }

    @FXML
    private void irATiendaInicio() {
        try {
            // Cargar la nueva vista TiendaInicioView.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/labuenatierra/Views/TiendaInicioView.fxml"));
            Parent tiendaInicioView = loader.load();

            // Obtener el Stage desde el evento de clic en lugar del label
            Stage stage = (Stage) labelLaBuenaTierra.getScene().getWindow(); // Esto puede causar NullPointerException si labelLaBuenaTierra no está en la escena.

            // Cambiar la raíz de la escena
            Scene currentScene = stage.getScene();
            currentScene.setRoot(tiendaInicioView);

            // Ajustar el tamaño de la ventana
            stage.sizeToScene(); // Ajustar al tamaño de la nueva escena

        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
