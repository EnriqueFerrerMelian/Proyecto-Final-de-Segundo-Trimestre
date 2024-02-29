module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires jasperreports;

    opens org.example to javafx.fxml;
    exports org.example;
    exports org.example.clases;
    opens org.example.clases to javafx.fxml;
}
