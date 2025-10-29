package com.example.project_7.COMPONENTS;

import java.io.Serializable;
import java.util.ArrayList;

class Todo implements Serializable {
    String name;
    String style;
    Todo(String name,String Style){
        this.name = name;
        this.style = Style;
    }
}
public class ToDoList_Component_Class extends Component_Base_Classes {
    ArrayList<Todo> Completed_todos;
    ArrayList<Todo> Not_Completed_todos;
    String todoName;
    public ToDoList_Component_Class(String TodoName,ArrayList<Todo> Completed_todos,ArrayList<Todo> Not_Completed_todos) {
        this.Completed_todos = Completed_todos;
        this.Not_Completed_todos = Not_Completed_todos;
        this.todoName = TodoName;
    }

    // Export back to component (for restoring in the UI)
    public ToDoList_Component export() {
        return new ToDoList_Component(todoName,Completed_todos,Not_Completed_todos);
    }
}

