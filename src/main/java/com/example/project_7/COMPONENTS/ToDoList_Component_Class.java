package com.example.project_7.COMPONENTS;

import java.util.ArrayList;

public class ToDoList_Component_Class extends Component_Base_Classes {
    public ArrayList<String> tasks;

    // Constructor — stores the list of tasks
    public ToDoList_Component_Class(ArrayList<String> tasks) {
        this.tasks = tasks;
    }

    // Export back to component (for restoring in the UI)
    public ToDoList_Component export() {
        ToDoList_Component component = new ToDoList_Component();
        for (String task : tasks) {
            component.addTask(task);
        }
        return component;
    }
}

