package org.example.labuenatierra.Controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NavigationUtil {

    public static void cambiarVista(String rutaFXML, Stage stage) {
        // Verificar si el usuario es admin y está en la vista TiendaInicioView.fxml
        if (Session.isAdmin() && "TiendaInicioView".equals(stage.getTitle())) {
            try {
                FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource("/org/example/labuenatierra/Views/AdministradorView.fxml"));
                Parent vista = loader.load();

                Scene nuevaEscena = new Scene(vista);
                stage.setScene(nuevaEscena);
                stage.sizeToScene();
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            // Cambiar a la vista proporcionada en rutaFXML
            try {
                FXMLLoader loader = new FXMLLoader(NavigationUtil.class.getResource(rutaFXML));
                Parent vista = loader.load();

                Scene nuevaEscena = new Scene(vista);
                stage.setScene(nuevaEscena);
                stage.sizeToScene();
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}