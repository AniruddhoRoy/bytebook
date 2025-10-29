package com.example.project_7.COMPONENTS;

import com.example.project_7.CONSTANTS;
import com.example.project_7.LIB;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.ArrayList;

public class ToDoList_Component extends Base_Component {
    private VBox root = new VBox();
    private TextField TodoListHading;
    private VBox LeftBar = new VBox();
    private VBox RightBar = new VBox();
    private HBox Binder = new HBox(LeftBar,RightBar);
    TextField AddTodoTextBox = new TextField();
    Button AddTodoButton = new Button("ADD");

    private String blueStyle = "-fx-text-fill: blue;";
    private String redStyle = "-fx-text-fill: red;";
    private String greenStyle = "-fx-text-fill: green;";
    private String blackStyle = "-fx-text-fill: black;";
    private String fontSize = "-fx-font-size: 15px;";
    private String fontColor = "-fx-text-fill: gray;";
    private void initComponet(){
        TodoListHading = new TextField();
        TodoListHading.setPromptText("Heading is not set");
        TodoListHading.setStyle("-fx-font-size: 25px; -fx-text-fill: gray; -fx-font-weight: bold;");
        TodoListHading.setAlignment(Pos.CENTER);
        LeftBar.getChildren().add(new Label("Not Completed"));
        RightBar.getChildren().add(new Label("Completed"));
        LeftBar.setSpacing(10);
        RightBar.setSpacing(10);
        HBox.setHgrow(LeftBar, Priority.ALWAYS);
        HBox.setHgrow(RightBar, Priority.ALWAYS);

        AddTodoTextBox.setPromptText("Enter Todo.......");
        AddTodoTextBox.setMinWidth(400);
        AddTodoTextBox.setStyle("-fx-font-size: 15px; -fx-text-fill: gray;");
        Binder.setAlignment(Pos.CENTER);
        Binder.setSpacing(10);
        Binder.setPadding(new Insets(20));
        AddTodoButton.setOnAction((e)->{
            addTodo(AddTodoTextBox.getText(),false,null);
            AddTodoTextBox.clear();
        });
        HBox inputBinder = new HBox(AddTodoTextBox,AddTodoButton);
        inputBinder.setAlignment(Pos.CENTER);
        inputBinder.setSpacing(10);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        root.getChildren().addAll(TodoListHading,inputBinder,Binder);
    }
    public ToDoList_Component() {
        initComponet();

    }
    public ToDoList_Component(String TodoName,ArrayList<Todo> Completed_todos,ArrayList<Todo> Not_Completed_todos){
        initComponet();
        for(Todo todo:Completed_todos )
        {
            addTodo(todo.name,true,todo.style);
        }
        for(Todo todo:Not_Completed_todos )
        {
            addTodo(todo.name,false,todo.style);
        }
        TodoListHading.setText(TodoName);
    }
    void addTodo(String text,boolean isCompleted,String style){
        if(text.isEmpty())return;
        CheckBox checkBox = new CheckBox(text);
        checkBox.setSelected(isCompleted);
        checkBox.setStyle(fontSize+blueStyle);
        checkBox.setOnAction(e->{
            if(checkBox.isSelected())
            {
                RightBar.getChildren().add(checkBox);
            }else{
                LeftBar.getChildren().add(checkBox);
            }
        });
        ContextMenu contextMenu = new ContextMenu();
        MenuItem blue = new MenuItem("Medium Priority", new Rectangle(10, 10, Color.BLUE));
        blue.setOnAction(e->{
            checkBox.setStyle(fontSize+blueStyle);
        });
        MenuItem green = new MenuItem("Low Priority", new Rectangle(10, 10, Color.GREEN));
        green.setOnAction(e->{
            checkBox.setStyle(fontSize+greenStyle);
        });
        MenuItem red = new MenuItem("High Priority", new Rectangle(10, 10, Color.RED));
        red.setOnAction(e->{
            checkBox.setStyle(fontSize+redStyle);
        });
        MenuItem block = new MenuItem("Optional Priority", new Rectangle(10, 10, Color.BLACK));
        block.setOnAction(e->{
            checkBox.setStyle(fontSize+ blackStyle);
        });
        MenuItem remove = new MenuItem("Remove Todo");
        remove.setOnAction(e->{
            if(checkBox.isSelected())
            {
                RightBar.getChildren().remove(checkBox);
            }else{
                LeftBar.getChildren().remove(checkBox);
            }
        });
        MenuItem edit = new MenuItem("Edit");
        edit.setOnAction(e->{
            AddTodoTextBox.setText(checkBox.getText());
            RightBar.getChildren().remove(checkBox);
            LeftBar.getChildren().remove(checkBox);
        });
        contextMenu.getItems().addAll(blue,green,red, block,remove,edit);
        checkBox.setContextMenu(contextMenu);
        if(isCompleted) {
            RightBar.getChildren().add(checkBox);
        }else{
            LeftBar.getChildren().add(checkBox);
        }
        if(style!=null){
            checkBox.setStyle(style);
        }
    }

    public VBox getToDoListComponent() {
        return root;
    }


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
    public ToDoList_Component_Class export(){
        ArrayList<Todo> Completed_todos = new ArrayList<>();
        ArrayList<Todo> Not_Completed_todos = new ArrayList<>();
        for(Node node:LeftBar.getChildren()){
            if(node instanceof CheckBox){
                CheckBox checkBox = (CheckBox) node;
                Not_Completed_todos.add(new Todo(checkBox.getText(),checkBox.getStyle()));
            }

        }
        for(Node node:RightBar.getChildren()){
            if(node instanceof CheckBox){
                CheckBox checkBox = (CheckBox) node;
                Completed_todos.add(new Todo(checkBox.getText(),checkBox.getStyle()));
            }

        }
        return new ToDoList_Component_Class(TodoListHading.getText(),Completed_todos,Not_Completed_todos);
    }
}
