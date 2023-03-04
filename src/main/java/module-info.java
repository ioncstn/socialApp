module com.example.lab6_2v {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.lab6_2v to javafx.fxml;
    exports com.example.lab6_2v;
    opens domain to javafx.fxml;
    exports domain;
}