package org.example.labuenatierra.Controllers;

public class Session {
    private static boolean isAdmin;

    public static boolean isAdmin() {
        return isAdmin;
    }

    public static void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public static void logout() {
        isAdmin = false;
    }
}
