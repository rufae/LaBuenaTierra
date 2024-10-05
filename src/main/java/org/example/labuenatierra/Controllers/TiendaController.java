package org.example.labuenatierra.Controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class TiendaController {

    @FXML
    private ImageView cartIcon;

    @FXML
    private void initialize() {
        // Cargar el icono del carrito
        InputStream imageStream = getClass().getResourceAsStream("/images/carrito.png");
        if (imageStream != null) {
            cartIcon.setImage(new Image(imageStream));
        } else {
            System.out.println("Imagen no encontrada en la ruta especificada.");
        }
        loadProducts();
    }


    private void loadProducts() {
        // Aquí puedes cargar los productos desde una base de datos o lista
        // Por ejemplo, añade productos al GridPane dinámicamente
    }
}

