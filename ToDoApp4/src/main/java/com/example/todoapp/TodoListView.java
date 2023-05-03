package com.example.todoapp;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TodoListView extends ListView<TodoItem> {
    private ObservableList<TodoItem> items;

    public TodoListView(ObservableList<TodoItem> items) {
        this.items = items;
        setItems(items);
        setCellFactory(param -> new TodoItemCell());
    }

    private class TodoItemCell extends ListCell<TodoItem> {
        private TextField taskField;
        private TextField descriptionField;
        private Button deleteButton;
        private Button editButton;
        private VBox content;
        private HBox actions;
        private boolean editing;

        public TodoItemCell() {
            taskField = new TextField();
            taskField.setStyle("-fx-font-weight: bold;");
            descriptionField = new TextField();
            deleteButton = new Button("Delete");
            editButton = new Button("Edit");
            content = new VBox();
            actions = new HBox();

            CheckBox checkBox = new CheckBox();
            content.getChildren().addAll(checkBox, taskField, descriptionField);

            actions.getChildren().addAll(deleteButton, editButton);
            actions.setSpacing(10);
            content.getChildren().add(actions);
            content.setSpacing(10);
            content.setPadding(new Insets(10));
            setContentDisplay(ContentDisplay.GRAPHIC_ONLY);

            deleteButton.setOnAction(event -> {
                TodoItem item = getItem();
                items.remove(item);
                TodoData.getInstance().deleteTodoItem(item);
            });

            editButton.setOnAction(event -> {
                if (editing) {
                    // save changes
                    TodoItem item = getItem();
                    item.setTask(taskField.getText());
                    item.setDescription(descriptionField.getText());
                    editing = false;
                    editButton.setText("Edit");
                    taskField.setEditable(false);
                    descriptionField.setEditable(false);
                    TodoData.getInstance().updateTodoItem(item);
                } else {
                    // start editing
                    editing = true;
                    editButton.setText("Save");
                    taskField.setEditable(true);
                    descriptionField.setEditable(true);
                }
            });
            checkBox.setOnAction(event -> {
                TodoItem item = getItem();
                item.setCompleted(checkBox.isSelected());
                TodoData.getInstance().updateTodoItem(item);
            });

            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    taskField.setStyle("-fx-font-weight: bold; -fx-text-fill: grey;");
                    descriptionField.setStyle("-fx-text-fill: grey;");
                } else {
                    taskField.setStyle("-fx-font-weight: bold; -fx-text-fill: black;");
                    descriptionField.setStyle("-fx-text-fill: black;");
                }
            });


        }

        @Override
        protected void updateItem(TodoItem item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                taskField.setText(item.getTask());
                descriptionField.setText(item.getDescription());
                taskField.setEditable(false);
                descriptionField.setEditable(false);
                deleteButton.setVisible(!editing);
                editButton.setVisible(true);
                ((CheckBox)content.getChildren().get(0)).setSelected(item.isCompleted()); // seteaza starea CheckBox-ului in functie de valoarea proprietatii 'completed' a obiectului 'item'
                setGraphic(content);
            }
        }
    }
}
