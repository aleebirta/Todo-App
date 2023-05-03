package com.example.todoapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Login extends Application {

    private BorderPane root;
    private Scene scene;
    private TextField usernameField;
    private PasswordField passwordField;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create root layout and scene
        root = new BorderPane();
        scene = new Scene(root, 300, 300);

        // set scene styles
        scene.setFill(Color.web("#f7f7f7"));

        // create top bar with app title
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("To-Do List Login");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black;");
        topBar.getChildren().add(titleLabel);
        topBar.setStyle("-fx-background-color: #B1D4E0;");
        root.setTop(topBar);

        // create main content area with login form
        VBox loginBox = new VBox();
        loginBox.setSpacing(10);
        loginBox.setAlignment(Pos.CENTER);
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        HBox usernameBox = new HBox();
        usernameBox.getChildren().addAll(new Label("Username: "), usernameField);
        usernameBox.setAlignment(Pos.CENTER);
        usernameBox.setMargin(usernameField, new Insets(0, 20, 0, 20));

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        HBox passwordBox = new HBox();
        passwordBox.getChildren().addAll(new Label("Password: "), passwordField);
        passwordBox.setAlignment(Pos.CENTER);
        passwordBox.setMargin(passwordField, new Insets(0, 20, 0, 20));

        Button loginButton = new Button("Login");
        loginButton.setStyle("-fx-background-color: #2E8BC0; -fx-text-fill: white; -fx-border-radius: 5px;");

        loginButton.setOnAction(e -> {
            if (login()) {
                primaryStage.close();
                TodoApp todoApp = new TodoApp();
                try {
                    todoApp.start(new Stage());
                } catch (Exception ex) {
                    System.err.println("Error starting TodoApp: " + ex.getMessage());
                }
            }
        });

        Button registerButton = new Button("Register");
        registerButton.setStyle("-fx-background-color: #2E8BC0; -fx-text-fill: white; -fx-border-radius: 5px;");
        registerButton.setOnAction(e -> {
            Register register = new Register();
            try {
                register.start(new Stage());
            } catch (Exception ex) {
                System.err.println("Error starting Register: " + ex.getMessage());
            }
        });


        loginBox.getChildren().addAll(usernameField, passwordField, loginButton, registerButton);
        root.setCenter(loginBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("To-Do List Login");
        primaryStage.show();
    }

    private boolean login() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        try (Scanner scanner = new Scanner(new File("users.txt"))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Authentication Error");
        alert.setHeaderText(null);
        alert.setContentText("Invalid username or password");
        alert.showAndWait();
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
}
