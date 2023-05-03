package com.example.todoapp;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class TodoApp extends Application {
    private BorderPane root;
    private Scene scene;
    private ObservableList<TodoItem> todoItems;

    @Override
    public void start(Stage primaryStage) throws Exception {
        // create list of to-do items
        todoItems = FXCollections.observableArrayList();

        // create root layout and scene
        root = new BorderPane();
        scene = new Scene(root, 600, 500);

        // set scene styles
        scene.setFill(Color.web("#f7f7f7"));

        // create top bar with app title
        HBox topBar = new HBox();
        topBar.setPadding(new Insets(10));
        topBar.setAlignment(Pos.CENTER);
        Label titleLabel = new Label("To-Do List");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: black;");
        topBar.getChildren().add(titleLabel);
        topBar.setStyle("-fx-background-color: #B1D4E0;");
        root.setTop(topBar);

        // create main content area
        TodoListView todoListView = new TodoListView(todoItems);
        todoListView.setStyle("-fx-background-color: #f7f7f7;");
        root.setCenter(todoListView);

        // create bottom bar with add item button
        HBox bottomBar = new HBox();
        bottomBar.setPadding(new Insets(10));
        bottomBar.setAlignment(Pos.CENTER);
        TextField newItemField = new TextField();
        newItemField.setPromptText("Add new item...");
        newItemField.setStyle("-fx-background-color: #e8e8e8; -fx-border-radius: 5px; -fx-border-color: #d9d9d9;");
        TextField newDescriptionField = new TextField();
        newDescriptionField.setPromptText("Add description...");
        newDescriptionField.setStyle("-fx-background-color: #e8e8e8; -fx-border-radius: 5px; -fx-border-color: #d9d9d9;");

        Button addItemButton = new Button("Add Item");
        addItemButton.setStyle("-fx-background-color: #2E8BC0; -fx-text-fill: white; -fx-border-radius: 5px;");

        addItemButton.setOnAction(e -> {
            if (!newItemField.getText().isEmpty()) {
                TodoItem newItem = new TodoItem(newItemField.getText(), newDescriptionField.getText());
                todoItems.add(newItem);
                newItemField.setText("");
                newDescriptionField.setText("");
                try {
                    TodoData.getInstance().addTodoItem(newItem, "todo_items.csv");
                } catch (IOException ex) {
                    System.err.println("Error writing to CSV file: " + ex.getMessage());
                }
            }
        });

        bottomBar.getChildren().addAll(newItemField, newDescriptionField, addItemButton);
        root.setBottom(bottomBar);

        primaryStage.setScene(scene);
        primaryStage.setTitle("To-Do List App");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}