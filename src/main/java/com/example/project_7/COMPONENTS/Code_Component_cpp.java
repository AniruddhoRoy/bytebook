package com.example.project_7.COMPONENTS;
import com.example.project_7.CONSTANTS;
import com.example.project_7.CONSTANTS.Language;

import com.example.project_7.EditPageController;
import com.example.project_7.LIB;

import javafx.geometry.Pos;
import javafx.scene.control.*;

import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;


import java.io.*;
import java.util.ArrayList;

import static com.example.project_7.Dialogs.ConformationAlert;
import static com.example.project_7.Dialogs.ErrorAlert;

public class Code_Component_cpp extends Base_Component {
    private SplitPane splitPane;
    private CodeArea codeArea;
    private double size = 300;
    VBox root = new VBox();
    Language language;
    private String font_style = "-fx-font-size: 16px; -fx-font-family: 'Consolas';";
    void updateSize(double val){
        this.size = val;
        splitPane.setMinHeight(this.size);
    }
    void addContextMenu(CodeArea area){
        ContextMenu contextMenu = new ContextMenu();
        Menu size_menu = new Menu("Size");
        Menu font_size_menu = new Menu("Font_Size");
        MenuItem delete = new MenuItem("Delete");
        MenuItem run = new MenuItem("run code");
        int[] heights = {200, 250, 300, 350, 400, 450, 500};
        for(int h:heights){
            MenuItem item = new MenuItem(h+" px");
            item.setOnAction(e->{
                updateSize(h);
            });
            size_menu.getItems().add(item);
        }
        int[] font_sizes = {12, 14, 16, 18, 20, 22, 24};
        for(int size:font_sizes){
            MenuItem item = new MenuItem(size+" px");
            item.setOnAction(e->{
                font_style = "-fx-font-size: "+size+"px; -fx-font-family: 'Consolas';";
                codeArea.setStyle(font_style);
            });
            font_size_menu.getItems().add(item);
        }
        run.setOnAction(e-> {
            if(language==Language.CPP){
                runCpp(codeArea.getText());
            } else if (language==Language.PYTHON) {
                runPy(codeArea.getText());
            }

        });
        delete.setOnAction(e->{
            if(ConformationAlert("Delete code","Are you sure?","do you really want to delete this code?")){
                delete(e);
            }
        });
        contextMenu.getItems().addAll(size_menu,font_size_menu,run,delete);
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
    public Code_Component_cpp(double size,String code ,Language language,String font_style){
        this.language = language;
        this.font_style = font_style;
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
        return new Code_Component_cpp_Class(size,codeArea.getText(),language,font_style);
    }
    /** Create and add the code editor area */
    private void loadCodeArea() {
        codeArea = new CodeArea();
//        codeArea = new Custome_codeArea();
        codeArea.setWrapText(true);
        codeArea.setStyle(font_style);
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
                ErrorAlert("File Permission Error","Unable to write temp.cpp",e.getMessage());
//                showError("File Error", "Unable to write temp.cpp:\n" + e.getMessage());
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
                ErrorAlert("Compiler Error","Failed to start g++ compiler",e.getMessage());
//                showError("Compiler Error", "Failed to start g++ compiler:\n" + e.getMessage());
                return;
            }
            String compileOutput = new String(compileProcess.getInputStream().readAllBytes());
            int exitCode = compileProcess.waitFor();
            if(exitCode!=0){
                ErrorAlert("Compiler Error","g++ compiler error",compileOutput);
//                showError("Compiler Error",compileOutput );
                return ;
            }
            try {
                Process runProcess = new ProcessBuilder(
                        "cmd.exe","/c","start", "cmd.exe", "/k",
                        "temp.exe & pause & del temp.exe & del temp.cpp & exit"
                ).start();
            } catch (IOException e) {
                ErrorAlert("Execution Error","Failed to open CMD",e.getMessage());
//                showError("Execution Error", "Failed to open CMD:\n" + e.getMessage());
            }

        } catch (Exception ex) {
            ErrorAlert("Unexpected Error","",ex.getMessage());
//            showError("Unexpected Error", ex.getMessage());
        }
    }
    void runPy(String sourceCode) {

        try {
            // 1. Write temp.py
            File pyFile = new File("temp.py");
            try (FileWriter fw = new FileWriter(pyFile)) {
                fw.write(sourceCode);
            } catch (IOException e) {
                ErrorAlert("File Permission Error","Unable to write temp.cpp",e.getMessage());
                return;
            }

            // 2. Execute Python in new CMD window
            try {
                Process runProcess = new ProcessBuilder(
                        "cmd.exe", "/c", "start", "cmd.exe", "/k",
                        "python temp.py & pause & del temp.py & exit"
                ).start();

            } catch (IOException e) {
                ErrorAlert("Execution Error","Failed to open CMD",e.getMessage());
                return;
            }

        } catch (Exception ex) {
            ErrorAlert("Unexpected Error","",ex.getMessage());
        }
    }
}
