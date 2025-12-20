package com.example.project_7;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.ArrayList;

public class DOCK{
    static HBox getDock(ArrayList<Button>buttons)  {
        HBox root = new HBox();
        root.setAlignment(Pos.CENTER);
        root.setSpacing(10);
        root.setPadding(new Insets(10));
        for(Button btn : buttons){
            root.getChildren().add(btn);
        }
        return root;
    }
}
