module org.example.labuenatierra {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens org.example.labuenatierra to javafx.fxml;
    opens org.example.labuenatierra.Controllers to javafx.fxml;
    exports org.example.labuenatierra;

}