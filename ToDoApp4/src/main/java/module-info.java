module com.example.todoapp4 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.todoapp to javafx.fxml;
    exports com.example.todoapp;
}