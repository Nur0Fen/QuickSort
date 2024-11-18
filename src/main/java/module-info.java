module com.example.sort1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.sort1 to javafx.fxml;
    exports com.example.sort1;
}