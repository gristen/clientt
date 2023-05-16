module com.example.clieeeent {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires lombok;
    requires okhttp3;
    requires com.google.gson;

    opens com.example.clieeeent to javafx.fxml;
    exports com.example.clieeeent;
    exports com.example.clieeeent.entity;

    opens com.example.clieeeent.entity to com.google.gson;
    exports com.example.clieeeent.controller;
    opens com.example.clieeeent.controller to javafx.fxml;

    exports com.example.clieeeent.response;
    opens com.example.clieeeent.response to com.google.gson;
}