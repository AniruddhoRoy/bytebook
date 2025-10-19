package com.example.project_7.COMPONENTS;

import com.example.project_7.CONSTANTS;
import com.example.project_7.LIB;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ToDoList_Component extends Base_Component {
    private VBox root;
    private VBox taskBox; // must be consistent name, not taskbox lowercase

    public ToDoList_Component() {
        initComponent();
    }

    private void initComponent() {
        root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setStyle("-fx-background-color:#f9f9f9;" +
                "-fx-border-color:#cccccc;" +
                "-fx-border-radius:10;" +
                "-fx-background-radius:10;");
        root.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("To-Do List");
        title.setFont(new Font("Arial Bold", 20));

        TextField textField = new TextField();
        textField.setPromptText("Add new task...");
        textField.setPrefHeight(40); // not 250 — that's too tall

        Button button = new Button("Add");
        button.setStyle("-fx-background-color:#0078D7;" +
                "-fx-text-fill: white; -fx-background-radius: 6;");

        HBox inbox = new HBox(10, textField, button);
        inbox.setAlignment(Pos.CENTER);

        // ✅ IMPORTANT: initialize the correct field name taskBox (not lowercase taskbox)
        taskBox = new VBox(8);
        taskBox.setAlignment(Pos.TOP_LEFT);
        taskBox.setPadding(new Insets(10, 0, 0, 0));

        button.setOnAction(e -> {
            String taskText = textField.getText().trim();
            if (!taskText.isEmpty()) {
                HBox taskRow = createTaskRow(taskText);
                taskBox.getChildren().add(taskRow);
                textField.clear();
            }
        });

        // Context menu for right click on root
        ContextMenu menu = new ContextMenu();
        MenuItem deleteAll = new MenuItem("Delete all tasks");
        deleteAll.setOnAction(e -> taskBox.getChildren().clear());

        MenuItem removeComponent = new MenuItem("Delete Component");
        removeComponent.setOnAction(this::delete);
        menu.getItems().addAll(deleteAll, removeComponent);

        root.setOnContextMenuRequested(e -> {
            menu.show(root, e.getScreenX(), e.getScreenY());
        });

        root.getChildren().addAll(title, inbox, taskBox);
    }

    private HBox createTaskRow(String text) {
        CheckBox checkBox = new CheckBox();
        Label label = new Label(text);
        label.setFont(new Font("Segoe UI", 14));
        label.setStyle("-fx-underline: true;"); // underline always visible

        HBox row = new HBox(10, checkBox, label);
        row.setAlignment(Pos.CENTER_LEFT);

        // Checked and strikethrough logic
        checkBox.selectedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                label.setStyle("-fx-underline: true; -fx-strikethrough: true; -fx-text-fill: gray;");
            } else {
                label.setStyle("-fx-underline: true; -fx-text-fill: black;");
            }
        });

        // Context menu for each task
        ContextMenu contextMenu = new ContextMenu();
        MenuItem edit = new MenuItem("Edit Task");
        MenuItem delete = new MenuItem("Delete Task");

        edit.setOnAction(e -> {
            TextInputDialog dialog = new TextInputDialog(label.getText());
            dialog.setTitle("Edit Task");
            dialog.setHeaderText("Update your task:");
            dialog.showAndWait().ifPresent(newText -> label.setText(newText));
        });

        delete.setOnAction(e -> taskBox.getChildren().remove(row));

        contextMenu.getItems().addAll(edit, delete);
        row.setOnContextMenuRequested(e -> contextMenu.show(row, e.getScreenX(), e.getScreenY()));

        return row;
    }

    // Return the to-do list container
    public VBox getToDoListComponent() {
        return root;
    }

    // Icon button for toolbox
    public Button getComponentButton(ArrayList<Base_Component> components, Stage stage) {
        Button button = new Button();
        button.setGraphic(new LIB().loadImageView(CONSTANTS.ToDoList_icon, 50));
        Tooltip tooltip = new Tooltip("To-Do List");
        Tooltip.install(button, tooltip);

        button.setOnAction(e -> {
            components.add(this);
            stage.close();
        });
        return button;
    }

    public void addTask(String taskText) {
        HBox taskRow = createTaskRow(taskText);
        taskBox.getChildren().add(taskRow);
    }

}
