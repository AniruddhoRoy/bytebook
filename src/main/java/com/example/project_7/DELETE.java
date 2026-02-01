package com.example.project_7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;

public class DELETE extends Application {
    public static void main(String[] args)  {
    launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        CodeArea codeArea = new CodeArea();
        codeArea.setWrapText(true);
        codeArea.autosize();
        BorderPane root = new BorderPane();
        root.setCenter(codeArea);
        Scene scene = new Scene(root,400,400);
        stage.setScene(scene);
        stage.show();
    }
}
