package com.example.project_7.COMPONENTS;
import com.example.project_7.CONSTANTS;
import com.example.project_7.CONSTANTS.Language;
import com.example.project_7.EditPageController;
import com.example.project_7.LIB;

import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;

import java.io.*;
import java.util.ArrayList;

public class Code_Component_cpp extends Base_Component {
    private SplitPane splitPane;
    private CodeArea codeArea;
//    private CodeAreaCustom codeArea;
    private double size = 400;
    VBox root = new VBox();
    Language language;
    void updateSize(double val){
        this.size = val;
        splitPane.setMinHeight(this.size);
    }
    void addContextMenu(CodeArea area){
        ContextMenu contextMenu = new ContextMenu();
        Menu size_menu = new Menu("Size");
        MenuItem delete = new MenuItem("Delete");
        MenuItem run = new MenuItem("run code");
        MenuItem x1 = new MenuItem("1x");
        MenuItem x2 = new MenuItem("2x");
        MenuItem x3 = new MenuItem("3x");
        MenuItem x4 = new MenuItem("4x");
        x1.setOnAction((e) -> {
            updateSize(300);});
        x2.setOnAction((e) -> {
            updateSize(400);});
        x3.setOnAction((e) -> {
            updateSize(500);});
        x4.setOnAction((e) -> {
            updateSize(600);});
// Add items to submenu
        size_menu.getItems().addAll(x1, x2, x3, x4);
        run.setOnAction(e-> {
            if(language==Language.CPP){
                runCpp(codeArea.getText());
            } else if (language==Language.PYTHON) {
                runPy(codeArea.getText());
            }

        });
        delete.setOnAction(this::delete);
        contextMenu.getItems().addAll(size_menu,run,delete);
        area.setOnContextMenuRequested(event ->{
            contextMenu.show(area,event.getScreenX(), event.getScreenY());
        });
    }

    public  String escapeForHtml(String code) {

        return code
                .replace("&", "&amp;")   // must replace & first
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }

    public String getHtml() {
        return ("""
                <div style="display: flex; gap: 10px;">
                        <div style="flex: 1;
                                    background-color: #e0f7fa;
                                    padding: 10px;
                                    border: 1px solid #b2ebf2;
                                    border-radius: 5px;
                                    box-sizing: border-box;">
                            <pre><code>%s</code></pre>
                        </div>
                    </div>
            """.formatted(escapeForHtml((codeArea.getText()))));
    }
    public Code_Component_cpp(Language language){
        splitPane = new SplitPane();
        this.language = language;
        updateSize(size);
        loadCodeArea();
        addContextMenu(codeArea);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(splitPane);
    }
    public Code_Component_cpp(double size,String code ,Language language){
        this.language = language;
        splitPane = new SplitPane();
        updateSize(size);
        loadCodeArea();
        codeArea.replaceText(code);

        addContextMenu(codeArea);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(splitPane);
    }
    public VBox getCodeComponentCpp() {
        return root;
    }
    public Button getComponentButton(ArrayList<Base_Component> components, EditPageController instance,Language language){
        Button button = new Button();
        if(language == Language.CPP){
            button.setGraphic(new LIB().loadImageView(CONSTANTS.Cpp_Icon,50));
        } else if (language == Language.PYTHON) {
            button.setGraphic(new LIB().loadImageView(CONSTANTS.Python_Icon,50));
        }

        button.setOnAction(e->{
            components.add(new Code_Component_cpp(language));
            instance.refresh();
        });
        return button;
    }
    public Code_Component_cpp_Class export(){
        return new Code_Component_cpp_Class(size,codeArea.getText(),language);
    }
    /** Create and add the code editor area */
    private void loadCodeArea() {
        codeArea = new CodeArea();
        codeArea.setWrapText(true);
        codeArea.setStyle("-fx-font-size: 16px; -fx-font-family: 'Consolas';");
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        if(language==Language.CPP){
            codeArea.replaceText(CONSTANTS.Cpp_default_snippet);
        } else if (language==Language.PYTHON) {
            codeArea.replaceText(CONSTANTS.Python_default_snippet);
        }
        VBox codeBox = new VBox(codeArea);
        VBox.setVgrow(codeArea, Priority.ALWAYS);

        splitPane.getItems().add(codeBox);
    }
    void runCpp(String sourceCode) {

        try {
            // 1. Write temp.cpp
            File cppFile = new File("temp.cpp");
            try (FileWriter fw = new FileWriter(cppFile)) {
                fw.write(sourceCode);
            } catch (IOException e) {
                showError("File Error", "Unable to write temp.cpp:\n" + e.getMessage());
                return;
            }

            // 2. Compile
            ProcessBuilder build = new ProcessBuilder(
                    "g++", cppFile.getAbsolutePath(), "-o", "temp.exe"
            );
            build.redirectErrorStream(true);

            Process compileProcess;
            try {
                compileProcess = build.start();
            } catch (IOException e) {
                showError("Compiler Error", "Failed to start g++ compiler:\n" + e.getMessage());
                return;
            }
            String compileOutput = new String(compileProcess.getInputStream().readAllBytes());
            int exitCode = compileProcess.waitFor();
            if(exitCode!=0){
                showError("Compiler Error",compileOutput );

                return ;
            }
            try {
                Process runProcess = new ProcessBuilder(
                        "cmd.exe","/c","start", "cmd.exe", "/k",
                        "temp.exe & pause & del temp.exe & del temp.cpp & exit"
                ).start();
            } catch (IOException e) {
                showError("Execution Error", "Failed to open CMD:\n" + e.getMessage());
            }

        } catch (Exception ex) {
            showError("Unexpected Error", ex.getMessage());
        }
    }
    void runPy(String sourceCode) {

        try {
            // 1. Write temp.py
            File pyFile = new File("temp.py");
            try (FileWriter fw = new FileWriter(pyFile)) {
                fw.write(sourceCode);
            } catch (IOException e) {
                showError("File Error", "Unable to write temp.py:\n" + e.getMessage());
                return;
            }

            // 2. Execute Python in new CMD window
            try {
                // Works on Windows
                Process runProcess = new ProcessBuilder(
                        "cmd.exe", "/c", "start", "cmd.exe", "/k",
                        "python temp.py & pause & del temp.py & exit"
                ).start();

            } catch (IOException e) {
                showError("Execution Error", "Failed to open CMD:\n" + e.getMessage());
                return;
            }

        } catch (Exception ex) {
            showError("Unexpected Error", ex.getMessage());
        }
    }

    private void showError(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
