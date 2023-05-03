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

import java.io.*;
import java.util.Scanner;

public class Register extends Application {

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
        Label titleLabel = new Label("To-Do List Register");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black;");
        topBar.getChildren().add(titleLabel);
        topBar.setStyle("-fx-background-color: #B1D4E0;");
        root.setTop(topBar);

        // create main content area with login form
        VBox registerBox = new VBox();
        registerBox.setSpacing(10);
        registerBox.setAlignment(Pos.CENTER);
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

        Button registerButton = new Button("register");
        registerButton.setStyle("-fx-background-color: #2E8BC0; -fx-text-fill: white; -fx-border-radius: 5px;");

        registerButton.setOnAction(e -> {
            register();
        });
        registerBox.getChildren().addAll(usernameField, passwordField, registerButton);
        root.setCenter(registerBox);

        primaryStage.setScene(scene);
        primaryStage.setTitle("To-Do List Register");
        primaryStage.show();
    }
    private void register() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // verificare daca username-ul si parola exista deja in fisierul users.txt
        if (userExists(username, password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Username or password already exists.");
            alert.showAndWait();
            return;
        }

        // validare date de inregistrare
        if (!validateRegistration(username, password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid username or password.");
            alert.showAndWait();
            return;
        }

        // adaugare utilizator in fisierul users.txt
        try {
            FileWriter writer = new FileWriter("users.txt", true);
            writer.write(username + "," + password + "\n");
            writer.close();
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Registration successful.");
            alert.showAndWait();

            // daca inregistrarea a avut succes, revenim la fereastra de logare
            startLogin();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Registration failed.");
            alert.showAndWait();
        }
    }
    private boolean userExists(String username, String password) {
        try {
            Scanner scanner = new Scanner(new File("users.txt"));
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] parts = line.split(",");
                if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Error finding users file.");
            alert.showAndWait();
        }
        return false;
    }
    private boolean validateRegistration(String username, String password) {
        // Verifică dacă username-ul și parola sunt goale
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }

        // Verifică dacă username-ul și parola conțin doar caractere alfanumerice
        String regex = "^[a-zA-Z0-9]+$";
        if (!username.matches(regex) || !password.matches(regex)) {
            return false;
        }

        // Verifică dacă username-ul și parola au lungimea potrivită
        if (username.length() < 3 || username.length() > 20 || password.length() < 6 || password.length() > 20) {
            return false;
        }

        return true;
    }

    private void startLogin() {
        Login login = new Login();
        try {
            login.start(new Stage());
            Stage stage = (Stage) root.getScene().getWindow();
            stage.close();
        } catch (Exception ex) {
            System.err.println("Error starting Login: " + ex.getMessage());
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
