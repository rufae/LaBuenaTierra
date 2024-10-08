package org.example.labuenatierra.Models;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Definir las constantes URL, USER y PASSWORD
    private static final String URL = "jdbc:mysql://localhost:3306/LABUENATIERRA"; // Cambia el nombre de tu base de datos si es necesario
    private static final String USER = "root"; // Cambia por tu usuario de MySQL
    private static final String PASSWORD = "root"; // Cambia por tu contraseña de MySQL

    private static Connection connection = null;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexión exitosa a la base de datos");
            } catch (SQLException e) {
                System.out.println("Error al conectar a la base de datos: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Conexión cerrada");
            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
}

