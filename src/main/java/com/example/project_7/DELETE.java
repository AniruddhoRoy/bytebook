package com.example.project_7;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

public class DELETE extends Application {

    @Override
    public void start(Stage stage) throws Exception {
       Custome_codeArea codeArea = new Custome_codeArea();

        BorderPane root = new BorderPane();
        root.setCenter(codeArea);
        Scene scene = new Scene(root,400,400);

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }


}
