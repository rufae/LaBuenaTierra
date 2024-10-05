package org.example.labuenatierra.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class ProductoController {

    @FXML
    private ImageView productoImage;

    @FXML
    private Label nombreProductoLabel;

    @FXML
    private Label precioLabel;

    @FXML
    private Button tallaXSButton;

    @FXML
    private Button tallaSButton;

    @FXML
    private Button tallaMButton;

    @FXML
    private Button tallaLButton;

    @FXML
    private Button tallaXLButton;

    @FXML
    private void initialize() {
        // Cargar la imagen desde el classpath
        InputStream imageStream = getClass().getResourceAsStream("/images/carrito.png");
        if (imageStream != null) {
            productoImage.setImage(new Image(imageStream));
        } else {
            System.out.println("Imagen no encontrada en la ruta especificada.");
        }
    }
    @FXML
    private void seleccionarTalla(String talla) {
        System.out.println("Talla seleccionada: " + talla);
    }

    @FXML
    private void seleccionarTallaXS() {
        seleccionarTalla("XS");
    }

    @FXML
    private void seleccionarTallaS() {
        seleccionarTalla("S");
    }

    @FXML
    private void seleccionarTallaM() {
        seleccionarTalla("M");
    }

    @FXML
    private void seleccionarTallaL() {
        seleccionarTalla("L");
    }

    @FXML
    private void seleccionarTallaXL() {
        seleccionarTalla("XL");
    }

}
