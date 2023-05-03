package com.example.todoapp;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

public class TodoItem {
    private String task;
    private String description;
    private BooleanProperty completed;

    public TodoItem(String task, String description) {
        this.task = task;
        this.description = description;
        this.completed = new SimpleBooleanProperty(false);
    }
    public BooleanProperty completedProperty() {
        return completed;
    }
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isCompleted() {
        return completed.get();
    }

    public void setCompleted(boolean completed) {
        this.completed.set(completed);
    }

    @Override
    public String toString() {
        return task;
    }
}
