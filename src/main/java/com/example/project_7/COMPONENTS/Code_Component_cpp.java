package com.example.project_7.COMPONENTS;
import com.example.project_7.CONSTANTS;
import com.example.project_7.LIB;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private CodeArea terminal;
    private int initialLength;
    private double size = 400;
    VBox root = new VBox();
    void updateSize(double val){
        this.size = val;
        splitPane.setMinHeight(this.size);
    }
    void addContextMenu(CodeArea area){
        ContextMenu contextMenu = new ContextMenu();
        Menu size_menu = new Menu("Size");
        MenuItem delete = new MenuItem("Delete");
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
        delete.setOnAction(this::delete);
        contextMenu.getItems().addAll(size_menu,delete);
        area.setOnContextMenuRequested(event ->{
            contextMenu.show(area,event.getScreenX(), event.getScreenY());
        });
    }


    public String getHtml() {
        return """
                <div style="display: flex; gap: 10px;">
                        <div style="flex: 1;
                                    background-color: #e0f7fa;
                                    padding: 10px;
                                    border: 1px solid #b2ebf2;
                                    border-radius: 5px;
                                    box-sizing: border-box;">
                            <pre><code>%s</code></pre>
                        </div>
                        <div style="flex: 1;
                                    background-color: #f0fff0;
                                    padding: 10px;
                                    border: 1px solid #d0f0c0;
                                    border-radius: 5px;
                                    box-sizing: border-box;">
                            <pre><code>%s</code></pre>
                        </div>
                    </div>
            """.formatted((codeArea.getText()), (terminal.getText()));
    }
    public Code_Component_cpp(){
        splitPane = new SplitPane();
        updateSize(size);
        loadCodeArea();
        loadTerminal();
        addContextMenu(codeArea);
        addContextMenu(terminal);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(splitPane);
    }
    public Code_Component_cpp(double size,String code ,String output){
        splitPane = new SplitPane();
        updateSize(size);
        loadCodeArea();
        codeArea.replaceText(code);
        loadTerminal();
        terminal.replaceText(output);
        addContextMenu(codeArea);
        addContextMenu(terminal);
        root.setAlignment(Pos.CENTER);
        root.getChildren().add(splitPane);
    }
    public VBox getCodeComponentCpp() {
        return root;
    }
    public Button getComponentButton(ArrayList<Base_Component> components, Stage childStage){
        Button button = new Button();
        button.setGraphic(new LIB().loadImageView(CONSTANTS.Cpp_Icon,50));
        button.setOnAction(e->{
//            containerNode.getChildren().add(this.getIamgecomponent(childStage));
            components.add(this);
            childStage.close();
        });
        return button;
    }
    public Code_Component_cpp_Class export(){
        return new Code_Component_cpp_Class(size,codeArea.getText(),terminal.getText());
    }
    /** Create and add the code editor area */
    private void loadCodeArea() {
        codeArea = new CodeArea();
        codeArea.setWrapText(true);
        codeArea.setStyle("-fx-font-size: 16px; -fx-font-family: 'Consolas';");
        codeArea.setParagraphGraphicFactory(LineNumberFactory.get(codeArea));
        codeArea.replaceText(CONSTANTS.Cpp_default_snippet);

        VBox codeBox = new VBox(codeArea);
        VBox.setVgrow(codeArea, Priority.ALWAYS);

        splitPane.getItems().add(codeBox);
    }

    /** Create and add the terminal section */
    private void loadTerminal() {
        Button runButton = new Button("Run");

//Tooltip for run button
        Tooltip runtooltip=new Tooltip("Click to compile code");
        Tooltip.install(runButton,runtooltip);
        terminal = new CodeArea("Byte book compiler v0.7 ==>");
        terminal.setWrapText(true);
//        terminal.setEditable(false);
        terminal.setPadding(new Insets(10));
        VBox.setVgrow(terminal, Priority.ALWAYS);
        VBox terminalContainer = new VBox(5, runButton, terminal);
        terminalContainer.setAlignment(Pos.CENTER);
        initialLength = terminal.getLength();
        terminal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode() == KeyCode.BACK_SPACE) {
                int caret = terminal.getCaretPosition();
                if (caret > initialLength) {
                    // Handles surrogate pairs correctly
                    String text = terminal.getText();
                    int prev = Character.offsetByCodePoints(text, caret, -1);
                    terminal.replaceText(prev, caret, "");
                }
                event.consume(); // Prevent default behavior
            }
        });
        terminal.caretPositionProperty().addListener((obs, oldPos, newPos) -> {
            try{
                if (newPos < initialLength) {
//                        codeArea.appendText(lastCharacter);
                    terminal.moveTo(initialLength);  // force caret to end of read-only content
                }
            }catch (Exception exception){

            }

        });
        runButton.setOnAction(e -> {
            terminal.clear();
            runCpp(codeArea.getText(),terminal);
        });

        splitPane.getItems().add(terminalContainer);
        splitPane.setDividerPositions(0.6); // top = 60%, bottom = 40%
    }
     void runCpp(String sourceCode, CodeArea terminal) {
         try {
             File cppFile = new File("temp.cpp");
             try (FileWriter writer = new FileWriter(cppFile)) {
                 writer.write(sourceCode);
             }

             appendText(terminal, "> Compiling...\n");
             Process compileProcess = new ProcessBuilder("g++", cppFile.getAbsolutePath(), "-o", "temp.exe")
                     .redirectErrorStream(true)
                     .start();

             String compileOutput = new String(compileProcess.getInputStream().readAllBytes());
             int compileExit = compileProcess.waitFor();

             if (compileExit != 0) {
                 appendText(terminal, "Compilation failed:\n" + compileOutput + "\n");
                 return;
             }

             appendText(terminal, "Compilation successful.\n> Running program...\n");
             Process runProcess = new ProcessBuilder("./temp.exe")
                     .redirectErrorStream(true)
                     .start();

             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(runProcess.getOutputStream()));
             BufferedReader reader = new BufferedReader(new InputStreamReader(runProcess.getInputStream()));
             new Thread(() -> {
                 try {
                     int c;
                     while ((c = reader.read()) != -1) { // read char by char
                         char ch = (char) c;
                         int unicode = (int)ch;
                         if(c!=13){

                             appendText(terminal,String.valueOf(ch));
                         }
                     }
                 } catch (IOException ignored) {}
             }).start();
             terminal.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
                 if (event.getCode() == KeyCode.ENTER) {
                     event.consume(); // stop default newline
                     String text = terminal.getText(initialLength, terminal.getLength());
                     try {
                         appendText(terminal,"\n");
                         writer.write(text);
                         writer.newLine();
                         writer.flush();
                     }catch (IOException ex) {
//                         appendText(terminal, "\n[Input error]\n");
                     }

                 }
             });
             new Thread(() -> {
                 try {
                     runProcess.waitFor();
                     Platform.runLater(() -> appendText(terminal, "\n> Program finished.\n"));
                 } catch (InterruptedException ignored) {}
             }).start();
         }catch (Exception ex) {
             appendText(terminal, "Error: " + ex.getMessage() + "\n");
         }

     }
         private void appendText(CodeArea terminal, String text) {
             //this is used because code is running in other threads
             Platform.runLater(() ->{

                 terminal.appendText(text);
                 initialLength = terminal.getLength();
//                 System.out.println(text.codePointAt(0)+ "  "+initialLength);
             });

         }
}
