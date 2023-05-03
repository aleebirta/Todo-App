package com.example.todoapp;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TodoData {
    private static TodoData instance = new TodoData();
    private List<TodoItem> todoItems = new ArrayList<>();

    private TodoData() {}

    public static TodoData getInstance() {
        return instance;
    }

    public List<TodoItem> getTodoItems() {
        return todoItems;
    }

    public void addTodoItem(TodoItem item) {
        todoItems.add(item);
    }

    public void deleteTodoItem(TodoItem item) {
        todoItems.remove(item);
    }

    public void updateTodoItem(TodoItem item) {
        // nothing to do if item isn't in list
        if (!todoItems.contains(item)) {
            return;
        }

        // find item in list and update properties
        TodoItem foundItem = null;
        for (TodoItem i : todoItems) {
            if (i.equals(item)) {
                foundItem = i;
                break;
            }
        }
        if (foundItem != null) {
            foundItem.setTask(item.getTask());
            foundItem.setDescription(item.getDescription());
        }
    }

    public void addTodoItem(TodoItem item, String filename) throws IOException {
        todoItems.add(item);
        FileWriter writer = new FileWriter(filename, true); // open the file in append mode
        String task = item.getTask().replace(",", ""); // Remove commas to avoid issues with CSV format
        String description = item.getDescription().replace(",", "");
        writer.write(description + "," + "\n"); // Save description  in the CSV file
        writer.close();
    }
}
